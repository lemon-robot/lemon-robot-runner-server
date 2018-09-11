package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.DataSetDef;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface DataSetDefMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrDataSetDef
     * @return 成功数量
     */
    Integer insertDataSetDef(DataSetDef lrDataSetDef);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteDataSetDefByNo(String no);

    /**
     * 修改 
     * @param lrDataSetDef
     * @return 成功数量
     */
    Integer updateDataSetDef(DataSetDef lrDataSetDef);

    /**
     * 查询 
     * @param lrDataSetDef
     * @return 集合
     */
    List<DataSetDef> selectDataSetDef(DataSetDef lrDataSetDef);

    /**
     * 统计数量 
     * @param lrDataSetDef
     * @return 数量
     */
    Integer countDataSetDef(DataSetDef lrDataSetDef);

}

