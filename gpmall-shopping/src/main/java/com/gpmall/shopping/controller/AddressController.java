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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "AddressController", description = "地址控制层")
public class AddressController {

    @Reference(timeout = 3000)
    IAddressService addressService;

    /**
     * 获取当前用户的地址列表
     *
     * @return
     */
    @GetMapping("/addresses")
    @ApiOperation("获取当前用户的地址列表")
    public ResponseData addressList(HttpServletRequest request) {
        String userInfo = (String) request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        AddressListRequest addressListRequest = new AddressListRequest();
        addressListRequest.setUserId(uid);
        AddressListResponse response = addressService.addressList(addressListRequest);
        if (response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getAddressDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @ApiOperation("添加地址")
    @PostMapping("/addresses")
    @ApiImplicitParam(name = "form", value = "地址信息", dataType = "AddressForm", required = true)
    public ResponseData addressAdd(@RequestBody AddressForm form, HttpServletRequest servletRequest) {
        log.debug(form.is_Default()+"");
        log.debug(form.toString());

        AddAddressRequest request = new AddAddressRequest();
        String userInfo = (String) servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        request.setUserId(uid);
        request.setUserName(form.getUserName());
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setIsDefault(form.is_Default() ? 1 : null);
        AddAddressResponse response = addressService.createAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @ApiOperation("删除地址")
    @DeleteMapping("/addresses/{addressid}")
    @ApiImplicitParam(name = "addressid", value = "地址ID", paramType = "path", required = true)
    public ResponseData addressDel(@PathVariable("addressid") Long addressid) {
        DeleteAddressRequest request = new DeleteAddressRequest();
        request.setAddressId(addressid);
        DeleteAddressResponse response = addressService.deleteAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());

    }

    @ApiOperation("更新地址")
    @PutMapping("/addresses")
    @ApiImplicitParam(name = "form", value = "地址信息", dataType = "AddressForm", required = true)
    public ResponseData addressUpdate(@RequestBody AddressForm form, HttpServletRequest servletRequest) {
        UpdateAddressRequest request = new UpdateAddressRequest();
        String userInfo = (String) servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        request.setAddressId(form.getAddressId());
        request.setIsDefault(form.is_Default() ? 1 : null);
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setUserId(uid);
        request.setUserName(form.getUserName());

        UpdateAddressResponse response = addressService.updateAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
