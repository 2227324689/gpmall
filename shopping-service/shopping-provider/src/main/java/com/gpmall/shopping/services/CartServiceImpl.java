package com.gpmall.shopping.services;

import com.alibaba.fastjson.JSON;
import com.gpmall.shopping.ICartService;
import com.gpmall.shopping.constant.GlobalConstants;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.converter.CartItemConverter;
import com.gpmall.shopping.dal.entitys.Item;
import com.gpmall.shopping.dal.persistence.ItemMapper;
import com.gpmall.shopping.dto.*;
import com.gpmall.shopping.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    ItemMapper itemMapper;

    /**
     * 根据用户id获得购物车中的商品列表
     * @param request
     * @return
     */
    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {
        CartListByIdResponse response=new CartListByIdResponse();
        List<CartProductDto> productDtos=new ArrayList<>();
        response.setCartProductDtos(productDtos);
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        return response;
    }


    @Override
    public AddCartResponse addToCart(AddCartRequest request) {
        AddCartResponse response=new AddCartResponse();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try{
            request.requestCheck();
            boolean exists=redissonClient.getMap(generatorCartItemKey(request)).containsKey(request.getItemId());
            if(exists){
                String cartItemJson=redissonClient.getMap(generatorCartItemKey(request)).get(request.getItemId()).toString();
                CartProductDto cartProductDto=JSON.parseObject(cartItemJson,CartProductDto.class);
                cartProductDto.setProductNum(cartProductDto.getProductNum().longValue()+request.getNum().longValue());
                redissonClient.getMap(generatorCartItemKey(request)).put(request.getItemId(),JSON.toJSON(cartProductDto));
                return response;
            }
            Item item=itemMapper.selectByPrimaryKey(request.getItemId().longValue());
            if(item!=null){
                CartProductDto cartProductDto=CartItemConverter.item2Dto(item);
                cartProductDto.setChecked("1");
                cartProductDto.setProductNum(request.getNum().longValue());
                redissonClient.getMap(generatorCartItemKey(request)).put(request.getItemId(),JSON.toJSON(cartProductDto));
                return response;
            }
            response.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
        }catch (Exception e){
            log.error("CartServiceImpl.addToCart Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        return null;
    }

    @Override
    public CheckAllItemResponse checkAllCartItem(CheckAllItemRequest request) {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(DeleteCartItemRequest request) {
        return null;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItem(DeleteCheckedItemRequest request) {
        return null;
    }

    private String generatorCartItemKey(AddCartRequest request){
        StringBuilder sb=new StringBuilder(GlobalConstants.CART_ITEM_CACHE_PREFIX);
        sb.append(":").append(request.getUserId().intValue());
        return sb.toString();
    }

}
