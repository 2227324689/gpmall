package com.gpmall.user.services;

import com.alibaba.fastjson.JSON;
import com.gpmall.user.IUserLoginService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.converter.UserConverterMapper;
import com.gpmall.user.dal.entitys.Member;
import com.gpmall.user.dal.persistence.MemberMapper;
import com.gpmall.user.dal.persistence.UserMapper;
import com.gpmall.user.dto.CheckAuthRequest;
import com.gpmall.user.dto.CheckAuthResponse;
import com.gpmall.user.dto.UserLoginRequest;
import com.gpmall.user.dto.UserLoginResponse;
import com.gpmall.user.utils.ExceptionProcessorUtils;
import com.gpmall.user.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-13:21
 */
@Slf4j
@Service
public class UserLoginServiceImpl implements IUserLoginService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MemberMapper memberMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        log.info("Begin UserLoginServiceImpl.login: request:"+request);
        UserLoginResponse response=new UserLoginResponse();
        try {
            request.requestCheck();
            Example example = new Example(Member.class);
            example.createCriteria().andEqualTo("state",1).andEqualTo("username",request.getUserName());

            List<Member> member = memberMapper.selectByExample(example);
            if(member==null||member.size()==0) {
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            //验证是否已经激活
            if("N".equals(member.get(0).getIsVerified())){
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            if(!DigestUtils.md5DigestAsHex(request.getPassword().getBytes()).equals(member.get(0).getPassword())){
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            Map<String,Object> map=new HashMap<>();
            map.put("uid",member.get(0).getId());
            map.put("file",member.get(0).getFile());

            String token=JwtTokenUtils.builder().msg(JSON.toJSON(map).toString()).build().creatJwtToken();
            response=UserConverterMapper.INSTANCE.converter(member.get(0));
            response.setToken(token);
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            log.error("UserLoginServiceImpl.login Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public CheckAuthResponse validToken(CheckAuthRequest request) {
        log.info("Begin UserLoginServiceImpl.validToken: request:"+request);
        CheckAuthResponse response=new CheckAuthResponse();
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        try{
            request.requestCheck();
            String decodeMsg=JwtTokenUtils.builder().token(request.getToken()).build().freeJwt();
            if(StringUtils.isNotBlank(decodeMsg)){
                log.info("validate success");
                response.setUserinfo(decodeMsg);
                return response;
            }
            response.setCode(SysRetCodeConstants.TOKEN_VALID_FAILED.getCode());
            response.setMsg(SysRetCodeConstants.TOKEN_VALID_FAILED.getMessage());
        }catch (Exception e){
            log.error("UserLoginServiceImpl.validToken Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
