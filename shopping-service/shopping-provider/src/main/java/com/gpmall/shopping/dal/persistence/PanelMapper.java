package com.gpmall.shopping.dal.persistence;

import com.gpmall.shopping.dal.entitys.Panel;
import com.gpmall.shopping.dal.entitys.PanelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PanelMapper {
    long countByExample(PanelExample example);

    int deleteByExample(PanelExample example);

    int insert(Panel record);

    int insertSelective(Panel record);

    List<Panel> selectByExample(PanelExample example);

    int updateByExampleSelective(@Param("record") Panel record, @Param("example") PanelExample example);

    int updateByExample(@Param("record") Panel record, @Param("example") PanelExample example);
}