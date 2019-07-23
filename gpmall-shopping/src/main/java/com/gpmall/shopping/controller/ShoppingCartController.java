package com.gpmall.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.ICartService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.CartListByIdRequest;
import com.gpmall.shopping.dto.CartListByIdResponse;
import com.gpmall.user.intercepter.TokenIntercepter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:52
 */
@RestController
@RequestMapping("/shopping")
public class ShoppingCartController {

    @Reference
    ICartService iCartService;

    @GetMapping("/carts")
    public ResponseData carts(HttpServletRequest request){
        String userInfo=(String)request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject jsonObject=JSON.parseObject(userInfo);
        int uid=Integer.parseInt(jsonObject.getString("uid"));
        CartListByIdRequest cartListByIdRequest=new CartListByIdRequest();
        cartListByIdRequest.setUserId(uid);
        CartListByIdResponse response=iCartService.getCartListById(cartListByIdRequest);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getCartProductDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
