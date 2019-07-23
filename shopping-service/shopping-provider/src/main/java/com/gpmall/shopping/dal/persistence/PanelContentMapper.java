package com.gpmall.shopping.dal.persistence;

import com.gpmall.shopping.dal.entitys.PanelContent;
import com.gpmall.shopping.dal.entitys.PanelContentExample;
import java.util.List;

import com.gpmall.shopping.dal.entitys.PanelContentItem;
import org.apache.ibatis.annotations.Param;

public interface PanelContentMapper {
    long countByExample(PanelContentExample example);

    int deleteByExample(PanelContentExample example);

    int insert(PanelContent record);

    int insertSelective(PanelContent record);

    List<PanelContent> selectByExample(PanelContentExample example);

    int updateByExampleSelective(@Param("record") PanelContent record, @Param("example") PanelContentExample example);

    int updateByExample(@Param("record") PanelContent record, @Param("example") PanelContentExample example);

    List<PanelContentItem> selectPanelContentAndProductWithPanelId(@Param("panelId")Integer panelId);
}