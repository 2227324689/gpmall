package com.gpmall.shopping.controller;/**
 * Created by mic on 2019/7/26.
 */

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.IProductService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.ProductDetailRequest;
import com.gpmall.shopping.dto.ProductDetailResponse;
import com.gpmall.user.annotation.Anoymous;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Reference
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

}
