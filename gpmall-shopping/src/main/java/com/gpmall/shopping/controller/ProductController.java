package com.gpmall.shopping.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.IProductService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.*;
import com.gpmall.shopping.form.PageInfo;
import com.gpmall.shopping.form.PageResponse;
import com.gpmall.user.annotation.Anoymous;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/26-上午12:14
 */
@Slf4j
@RestController
@RequestMapping("/shopping")
public class ProductController {

    @Reference(timeout = 3000)
    IProductService productService;

    @Anoymous
    @GetMapping("/product/{id}")
    public ResponseData product(@PathVariable long id){
        ProductDetailRequest request=new ProductDetailRequest();
        request.setId(id);
        ProductDetailResponse response=productService.getProductDetail(request);

        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getProductDetailDto());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 返回商品列表
     * @param pageInfo
     * @return
     */
    @Anoymous
    @GetMapping("/goods")
    public ResponseData goods(PageInfo pageInfo){
        AllProductRequest request=new AllProductRequest();
        request.setCid(pageInfo.getCid());
        request.setPage(pageInfo.getPage());
        request.setPriceGt(pageInfo.getPriceGt());
        request.setPriceLte(pageInfo.getPriceLte());
        request.setSize(pageInfo.getSize());
        request.setSort(pageInfo.getSort());
        AllProductResponse response=productService.getAllProduct(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            PageResponse pageResponse=new PageResponse();
            pageResponse.setData(response.getProductDtoList());
            pageResponse.setTotal(response.getTotal());
            return new ResponseUtil().setData(pageResponse);
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 返回推荐的商品
     * @return
     */
    @Anoymous
    @GetMapping("/recommend")
    public ResponseData recommend(){
        RecommendResponse response=productService.getRecommendGoods();
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getPanelContentItemDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

}
