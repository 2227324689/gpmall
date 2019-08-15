package com.gpmall.shopping.dal.persistence;

import com.gpmall.commons.tool.tkmapper.TkMapper;
import com.gpmall.shopping.dal.entitys.Panel;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PanelMapper extends TkMapper<Panel> {

    List<Panel> selectPanelContentById(@Param("panelId")Integer panelId);
}