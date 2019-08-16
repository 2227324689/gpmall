package com.gpmall.shopping.services;

import com.alibaba.fastjson.JSON;
import com.gpmall.shopping.IHomeService;
import com.gpmall.shopping.constant.GlobalConstants;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.converter.ContentConverter;
import com.gpmall.shopping.dal.entitys.*;
import com.gpmall.shopping.dal.persistence.PanelContentMapper;
import com.gpmall.shopping.dal.persistence.PanelMapper;
import com.gpmall.shopping.dto.HomePageResponse;
import com.gpmall.shopping.dto.PanelDto;
import com.gpmall.shopping.services.cache.CacheManager;
import com.gpmall.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    CacheManager cacheManager;

    @Override
    public HomePageResponse homepage() {
        log.info("Begin HomeServiceImpl.homepage");
        HomePageResponse response=new HomePageResponse();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            String json= cacheManager.checkCache(GlobalConstants.HOMEPAGE_CACHE_KEY);
            if(StringUtils.isNoneEmpty(json)){
                List<PanelDto> panelDtoList=JSON.parseArray(json,PanelDto.class);
                Set set=new HashSet(panelDtoList);
                response.setPanelContentItemDtos(set);
                return response;
            }
            Example panelExample = new Example(Panel.class);
            Example.Criteria criteria = panelExample.createCriteria();
            criteria.andEqualTo("position", 0);
            criteria.andEqualTo("status", 1);
            panelExample.setOrderByClause("sort_order");
            List<Panel> panels = panelMapper.selectByExample(panelExample);
            Set<PanelDto> panelContentItemDtos = new HashSet<PanelDto>();
            panels.parallelStream().forEach(panel -> {
                List<PanelContentItem> panelContentItems = panelContentMapper.selectPanelContentAndProductWithPanelId(panel.getId());
                PanelDto panelDto = contentConverter.panen2Dto(panel);
                panelDto.setPanelContentItems(contentConverter.panelContentItem2Dto(panelContentItems));
                panelContentItemDtos.add(panelDto);
            });
            cacheManager.setCache(GlobalConstants.HOMEPAGE_CACHE_KEY,JSON.toJSONString(panelContentItemDtos),GlobalConstants.HOMEPAGE_EXPIRE_TIME);
            response.setPanelContentItemDtos(panelContentItemDtos);
        }catch (Exception e){
            log.error("HomeServiceImpl.homepage Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }




}
