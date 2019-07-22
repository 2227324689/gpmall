package com.gpmall.sso.controller;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.annotation.Anoymous;
import com.gpmall.commons.intercepter.TokenIntercepter;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.user.IUserLoginService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.UserLoginRequest;
import com.gpmall.user.dto.UserLoginResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/21
 */
@RestController
public class LoginController {

    @Reference(timeout = 3000)
    IUserLoginService iUserLoginService;

    @Anoymous
    @PostMapping("/login")
    public ResponseData login(String userName, String password, HttpServletResponse response){
        UserLoginRequest loginRequest=new UserLoginRequest();
        loginRequest.setPassword(userName);
        loginRequest.setUserName(password);
        UserLoginResponse userLoginResponse=iUserLoginService.login(loginRequest);
        if(userLoginResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            response.addHeader("Set-Cookie",
                    "access_token=" + userLoginResponse.getToken() + ";Path=/;HttpOnly");
         return new ResponseUtil().setData(userLoginResponse);
        }
        return new ResponseUtil().setErrorMsg(userLoginResponse.getMsg());
    }

    @GetMapping("/checkLogin")
    public ResponseData checkLogin(HttpServletRequest request){
        String userInfo=(String)request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        Object object=JSON.parse(userInfo);
        return new ResponseUtil().setData(object);
    }

    @GetMapping("/loginOut")
    public ResponseData loginOut(){
        return new ResponseUtil().setData(null);
    }

    @GetMapping("/uploadImages")
    public ResponseData uploadHead(){
        //TODO
        return new ResponseUtil<>().setData(null);
    }
}
