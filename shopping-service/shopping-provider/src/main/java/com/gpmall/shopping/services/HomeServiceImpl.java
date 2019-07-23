package com.gpmall.shopping.services;

import com.gpmall.shopping.IHomeService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.converter.ContentConverter;
import com.gpmall.shopping.dal.entitys.*;
import com.gpmall.shopping.dal.persistence.PanelContentMapper;
import com.gpmall.shopping.dal.persistence.PanelMapper;
import com.gpmall.shopping.dto.HomePageResponse;
import com.gpmall.shopping.dto.PanelContentItemDto;
import com.gpmall.shopping.dto.PanelDto;
import com.gpmall.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-17:49
 */
@Slf4j
@Service
public class HomeServiceImpl implements IHomeService {

    @Autowired
    PanelMapper panelMapper;
    @Autowired
    PanelContentMapper panelContentMapper;
    @Autowired
    ContentConverter contentConverter;

    @Override
    public HomePageResponse homepage() {
        log.info("Begin HomeServiceImpl.homepage");
        PanelExample panelExample=new PanelExample();
        PanelExample.Criteria criteria=panelExample.createCriteria();
        criteria.andPositionEqualTo(0);
        criteria.andStatusEqualTo(1);
        panelExample.setOrderByClause("sort_order");
        HomePageResponse response=new HomePageResponse();
        try {
            List<Panel> panels = panelMapper.selectByExample(panelExample);
            List<PanelDto> panelContentItemDtos = new ArrayList<>();
            panels.parallelStream().forEach(panel -> {
                List<PanelContentItem> panelContentItems = panelContentMapper.selectPanelContentAndProductWithPanelId(panel.getId());
                PanelDto panelDto = contentConverter.panen2Dto(panel);
                panelDto.setPanelContentItems(contentConverter.panelContentItem2Dto(panelContentItems));
                panelContentItemDtos.add(panelDto);
            });
            //TODO 数据缓存
            response.setPanelContentItemDtos(panelContentItemDtos);
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("HomeServiceImpl.homepage Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
