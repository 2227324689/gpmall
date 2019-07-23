package com.gpmall.shopping.converter;

import com.gpmall.shopping.dal.entitys.Panel;
import com.gpmall.shopping.dal.entitys.PanelContent;
import com.gpmall.shopping.dal.entitys.PanelContentItem;
import com.gpmall.shopping.dto.PanelContentDto;
import com.gpmall.shopping.dto.PanelContentItemDto;
import com.gpmall.shopping.dto.PanelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:37
 */
@Mapper(componentModel = "spring")
public interface ContentConverter {

    @Mappings({})
    PanelContentDto panelContent2Dto(PanelContent panelContent);

    @Mappings({})
    PanelContentDto panelContentItem2Dto(PanelContentItem panelContentItem);

    @Mappings({})
    PanelDto panen2Dto(Panel panel);

    List<PanelContentDto> panelContents2Dto(List<PanelContent> panelContents);

    List<PanelContentItemDto> panelContentItem2Dto(List<PanelContentItem> panelContentItems);

}
