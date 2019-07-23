package com.gpmall.shopping.services;

import com.gpmall.shopping.ICartService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.CartListByIdRequest;
import com.gpmall.shopping.dto.CartListByIdResponse;
import com.gpmall.shopping.dto.CartProductDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-19:02
 *
 * 购物车的信息，统一采用缓存存储
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {
        CartListByIdResponse response=new CartListByIdResponse();
        List<CartProductDto> productDtos=new ArrayList<>();
        response.setCartProductDtos(productDtos);
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return response;
    }
}
