package com.gpmall.user.controller;

import com.alibaba.fastjson.JSON;
import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.commons.tool.utils.CookieUtil;
import com.gpmall.user.IKaptchaService;
import com.gpmall.user.IUserLoginService;
import com.gpmall.user.annotation.Anoymous;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.KaptchaCodeRequest;
import com.gpmall.user.dto.KaptchaCodeResponse;
import com.gpmall.user.dto.UserLoginRequest;
import com.gpmall.user.dto.UserLoginResponse;
import com.gpmall.user.intercepter.TokenIntercepter;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/21
 */

@RestController
@RequestMapping("/user")
public class LoginController {

    @Reference(timeout = 3000)
    IUserLoginService iUserLoginService;

    @Reference(timeout = 3000)
    IKaptchaService kaptchaService;

    /**
     * 验证码开关
     */
    @Value("${captchaFlag:true}")
    private boolean captchaFlag;

    @Anoymous
    @PostMapping("/login")
    public ResponseData login(@RequestBody Map<String,String> map,
                              HttpServletRequest request,HttpServletResponse response){
        UserLoginRequest loginRequest=new UserLoginRequest();
        loginRequest.setPassword(map.get("userPwd"));
        loginRequest.setUserName(map.get("userName"));
        String captcha=map.get("captcha");

        if (captchaFlag) {
            KaptchaCodeRequest kaptchaCodeRequest = new KaptchaCodeRequest();
            String uuid = CookieUtil.getCookieValue(request, "kaptcha_uuid");
            kaptchaCodeRequest.setCode(captcha);
            kaptchaCodeRequest.setUuid(uuid);
            KaptchaCodeResponse kaptchaCodeResponse = kaptchaService.validateKaptchaCode(kaptchaCodeRequest);
            if (!kaptchaCodeResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
                return new ResponseUtil<>().setErrorMsg(kaptchaCodeResponse.getMsg());
            }
        }
        UserLoginResponse userLoginResponse=iUserLoginService.login(loginRequest);
        if(userLoginResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            Cookie cookie=CookieUtil.genCookie(TokenIntercepter.ACCESS_TOKEN,userLoginResponse.getToken(),"/",24*60*60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
         return new ResponseUtil().setData(userLoginResponse);
        }
        return new ResponseUtil().setErrorMsg(userLoginResponse.getMsg());
    }

    @GetMapping("/login")
    public ResponseData checkLogin(HttpServletRequest request){
        String userInfo=(String)request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        Object object=JSON.parse(userInfo);
        return new ResponseUtil().setData(object);
    }

    @GetMapping("/loginOut")
    public ResponseData loginOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (null!=cookies) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(TokenIntercepter.ACCESS_TOKEN)){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie); //覆盖原来的token
                }
            }
        }
        return new ResponseUtil().setData(null);
    }



    @GetMapping("/uploadImages")
    public ResponseData uploadHead(){
        //TODO
        return new ResponseUtil<>().setData(null);
    }
}
