package com.gpmall.user.services;

import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.user.IUserRegisterService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dal.entitys.User;
import com.gpmall.user.dal.entitys.UserExample;
import com.gpmall.user.dal.persistence.UserMapper;
import com.gpmall.user.dto.UserLoginResponse;
import com.gpmall.user.dto.UserRegisterRequest;
import com.gpmall.user.dto.UserRegisterResponse;
import com.gpmall.user.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-12:50
 */
@Slf4j
@Service
public class UserRegisterServiceImpl implements IUserRegisterService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        log.info("Begin UserLoginServiceImpl.register: request:"+request);
        UserRegisterResponse response=new UserRegisterResponse();
        try {
            validUserRegisterRequest(request);
            User user=new User();
            user.setUsername(request.getUserName());
            user.setPassword(DigestUtils.md5DigestAsHex(request.getUserPwd().getBytes()));
            user.setState(1);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            if(userMapper.insert(user)!=1){
                response.setCode(SysRetCodeConstants.USER_REGISTER_FAILED.getCode());
                response.setMsg(SysRetCodeConstants.USER_REGISTER_FAILED.getMessage());
                return response;
            }
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("UserLoginServiceImpl.register Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    //校验参数以及校验用户名是否存在
    private void validUserRegisterRequest(UserRegisterRequest request){
        request.requestCheck();
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andStateEqualTo(1).andUsernameEqualTo(request.getUserName());
        List<User> users=userMapper.selectByExample(userExample);
        if(users!=null&&users.size()>0){
            throw new ValidateException(SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getCode(),SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getMessage());
        }
    }
}
