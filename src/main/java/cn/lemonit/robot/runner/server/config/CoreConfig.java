package cn.lemonit.robot.runner.server.config;

import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@MapperScan(basePackages = "cn.lemonit.robot.runner.server.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class CoreConfig {

    @Value("${cn.lemonit.robot.data-source.jdbc-url}")
    private String dataSourceJdbcUrl;
    @Value("${cn.lemonit.robot.workspaceDirName}")
    private String workspaceDirName;

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSource() {
        FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + workspaceDirName + File.separator);
        if (dataSourceJdbcUrl == null || dataSourceJdbcUrl.trim().equals("")) {
            dataSourceJdbcUrl =
                    StringDefine.SQLITE_JDBC_PREFIX +
                            FileUtil.getRuntimePath() +
                            File.separator + StringDefine.SQLITE_DB_NAME;
        }
        System.out.println("jdbcurl : " + dataSourceJdbcUrl);
        return DataSourceBuilder.create()
                .url(dataSourceJdbcUrl)
                .driverClassName(dataSourceJdbcUrl.split(":")[1].equals("sqlite") ? "org.sqlite.JDBC" : "com.mysql.jdbc.Driver")
                .build();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/" + dataSourceJdbcUrl.split(":")[1] + "/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
