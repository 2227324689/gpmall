package com.gpmall.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.ICartService;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.dto.*;
import com.gpmall.shopping.form.CartForm;
import com.gpmall.user.intercepter.TokenIntercepter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:52
 */
@RestController
@RequestMapping("/shopping")
public class CartController {

    @Reference(timeout = 3000)
    ICartService iCartService;

    /**
     * 获得购物车列表
     * @param request
     * @return
     */
    @GetMapping("/carts")
    public ResponseData carts(HttpServletRequest request){
        String userInfo=(String)request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject jsonObject=JSON.parseObject(userInfo);
        long uid=Long.parseLong(jsonObject.getString("uid"));
        CartListByIdRequest cartListByIdRequest=new CartListByIdRequest();
        cartListByIdRequest.setUserId(uid);
        CartListByIdResponse response=iCartService.getCartListById(cartListByIdRequest);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getCartProductDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 添加商品到购物车
     * @param cartForm
     * @return
     */
    @PostMapping("/carts")
    public ResponseData carts(@RequestBody CartForm cartForm){
        AddCartRequest request=new AddCartRequest();
        request.setItemId(cartForm.getProductId());
        request.setNum(cartForm.getProductNum());
        request.setUserId(cartForm.getUserId());
        AddCartResponse response=iCartService.addToCart(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 更新购物车中的商品
     * @param cartForm
     * @return
     */
    @PutMapping("/carts")
    public ResponseData updateCarts(@RequestBody CartForm cartForm){
        UpdateCartNumRequest request=new UpdateCartNumRequest();
        request.setChecked(cartForm.getChecked());
        request.setItemId(cartForm.getProductId());
        request.setNum(cartForm.getProductNum());
        request.setUserId(cartForm.getUserId());
        UpdateCartNumResponse response=iCartService.updateCartNum(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 删除购物车中的商品
     * @return
     */
    @DeleteMapping("/carts/{uid}/{pid}")
    public ResponseData deleteCarts(@PathVariable("uid") long uid,@PathVariable("pid") long pid){
        DeleteCartItemRequest request=new DeleteCartItemRequest();
        request.setUserId(uid);
        request.setItemId(pid);

        DeleteCartItemResponse response=iCartService.deleteCartItem(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 对购物车的全选操作
     * @param cartForm
     * @return
     */
    @PutMapping("/items")
    public ResponseData checkCarts(@RequestBody CartForm cartForm){
        CheckAllItemRequest request=new CheckAllItemRequest();
        request.setChecked(cartForm.getChecked());
        request.setUserId(cartForm.getUserId());
        CheckAllItemResponse response=iCartService.checkAllCartItem(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 删除购物车中选中的商品
     * @param id
     * @return
     */
    @DeleteMapping("/items/{id}")
    public ResponseData deleteCheckCartItem(@PathVariable("id")Long id){
        DeleteCheckedItemRequest request=new DeleteCheckedItemRequest();
        request.setUserId(id);
        request.setUserId(request.getUserId());
        DeleteCheckedItemResposne response=iCartService.deleteCheckedItem(request);
        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }


}
