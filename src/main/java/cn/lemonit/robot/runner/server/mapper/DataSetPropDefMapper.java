package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.DataSetPropDef;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface DataSetPropDefMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrDataSetPropDef
     * @return 成功数量
     */
    Integer insertDataSetPropDef(DataSetPropDef lrDataSetPropDef);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteDataSetPropDefByKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteDataSetPropDef(DataSetPropDef lrDataSetPropDef);

    /**
     * 修改 
     * @param lrDataSetPropDef
     * @return 成功数量
     */
    Integer updateDataSetPropDef(DataSetPropDef lrDataSetPropDef);

    /**
     * 查询 
     * @param lrDataSetPropDef
     * @return 集合
     */
    List<DataSetPropDef> selectDataSetPropDef(DataSetPropDef lrDataSetPropDef);

    /**
     * 统计数量 
     * @param lrDataSetPropDef
     * @return 数量
     */
    Integer countDataSetPropDef(DataSetPropDef lrDataSetPropDef);

}

