package com.gpmall.shopping.controller;/**
 * Created by mic on 2019/8/2.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.shopping.constants.ShoppingRetCode;
import com.gpmall.shopping.form.AddressForm;
import com.gpmall.user.IAddressService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.*;
import com.gpmall.user.intercepter.TokenIntercepter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/2-上午9:36
 */
@Slf4j
@RestController
@RequestMapping("/shopping")
public class AddressController {

    @Reference(timeout = 3000)
	IAddressService addressService;

    /**
     * 获取当前用户的地址列表
     * @return
     */
    @GetMapping("/addresses")
    public ResponseData addressList(HttpServletRequest request){
        String userInfo=(String)request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object= JSON.parseObject(userInfo);
        Long uid=Long.parseLong(object.get("uid").toString());
        AddressListRequest addressListRequest=new AddressListRequest();
        addressListRequest.setUserId(uid);
        AddressListResponse response=addressService.addressList(addressListRequest);
        if(response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())){
            return new ResponseUtil().setData(response.getAddressDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @PostMapping("/addresses")
    public ResponseData addressAdd(@RequestBody  AddressForm form,HttpServletRequest servletRequest){

        System.out.println(form.is_Default());
        System.out.println(form.toString() );

        AddAddressRequest request = new AddAddressRequest();
        String userInfo=(String)servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object= JSON.parseObject(userInfo);
        Long uid=Long.parseLong(object.get("uid").toString());
        request.setUserId(uid);
        request.setUserName(form.getUserName());
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setIsDefault(form.is_Default());
        AddAddressResponse response = addressService.createAddress(request);

        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
    @DeleteMapping("/addresses/{addressid}")
    public ResponseData addressDel(@PathVariable("addressid") Long addressid){
        DeleteAddressRequest request = new DeleteAddressRequest();
        request.setAddressId(addressid);
        DeleteAddressResponse response = addressService.deleteAddress(request);

        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());

    }

    @PutMapping("/addresses")
    public ResponseData addressUpdate(@RequestBody AddressForm form,HttpServletRequest servletRequest){
        UpdateAddressRequest request = new UpdateAddressRequest();
        String userInfo=(String)servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object= JSON.parseObject(userInfo);
        Long uid=Long.parseLong(object.get("uid").toString());
        request.setAddressId(form.getAddressId());
        request.setIsDefault(form.is_Default());
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setUserId(uid);
        request.setUserName(form.getUserName());

        UpdateAddressResponse response = addressService.updateAddress(request);

        if(response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
