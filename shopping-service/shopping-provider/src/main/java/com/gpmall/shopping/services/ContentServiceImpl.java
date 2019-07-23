package com.gpmall.shopping.services;

import com.gpmall.shopping.IContentService;
import com.gpmall.shopping.constant.GlobalConstants;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.converter.ContentConverter;
import com.gpmall.shopping.dal.entitys.PanelContent;
import com.gpmall.shopping.dal.entitys.PanelContentExample;
import com.gpmall.shopping.dal.persistence.PanelContentMapper;
import com.gpmall.shopping.dto.NavListResponse;
import com.gpmall.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-16:23
 */
@Slf4j
@Service
public class ContentServiceImpl implements IContentService {

    @Autowired
    PanelContentMapper panelContentMapper;

    @Autowired
    ContentConverter contentConverter;

    @Override
    public NavListResponse queryNavList() {
        NavListResponse response=new NavListResponse();
        try {
            PanelContentExample exampleContent = new PanelContentExample();
            exampleContent.setOrderByClause("sort_order");
            PanelContentExample.Criteria criteriaContent = exampleContent.createCriteria();
            criteriaContent.andPanelIdEqualTo(GlobalConstants.HEADER_PANEL_ID);
            List<PanelContent> pannelContents = panelContentMapper.selectByExample(exampleContent);
            //添加缓存操作 TODO
            response.setPannelContentDtos(contentConverter.panelContents2Dto(pannelContents));
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("ContentServiceImpl.queryNavList Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
