package com.gpmall.shopping.services;

import com.gpmall.shopping.IProductCateService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.converter.ProductCateConverter;
import com.gpmall.shopping.dal.entitys.ItemCat;
import com.gpmall.shopping.dal.entitys.ItemCatExample;
import com.gpmall.shopping.dal.persistence.ItemCatMapper;
import com.gpmall.shopping.dto.AllProductCateRequest;
import com.gpmall.shopping.dto.AllProductCateResponse;
import com.gpmall.shopping.services.cache.CacheManager;
import com.gpmall.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by oahnus on 2019/8/8
 * 21:53.
 */
@Slf4j
@Service
public class ProductCateServiceImpl implements IProductCateService {
    @Autowired
    ItemCatMapper itemCatMapper;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    ProductCateConverter productCateConverter;

    @Override
    public AllProductCateResponse getAllProductCate(AllProductCateRequest request) {
        AllProductCateResponse response = new AllProductCateResponse();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        try{
            // TODO 查询缓存
            ItemCatExample itemCatExample = new ItemCatExample();
            ItemCatExample.Criteria criteria = itemCatExample.createCriteria();
            String orderCol = "sort_order";
            String orderDir = "asc";
            if(request.getSort().equals("1")){
                orderCol="price";
                orderDir="asc";
            }else if(request.getSort().equals("-1")){
                orderCol="price";
                orderDir="desc";
            }
            itemCatExample.setOrderByClause(orderCol + " " + orderDir);

            List<ItemCat> itemCats = itemCatMapper.selectByExample(itemCatExample);
            response.setProductCateDtoList(productCateConverter.items2Dto(itemCats));
            //设置缓存
//            cacheManager.setCache(generatorProduceCacheKey(request),JSON.toJSON(productDetailDto).toString(),GlobalConstants.PRODUCT_ITEM_EXPIRE_TIME);
        }catch (Exception e){
            log.error("ProductCateServiceImpl.getAllProductCate Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
