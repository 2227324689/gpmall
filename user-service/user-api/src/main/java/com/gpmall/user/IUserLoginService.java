package com.gpmall.user;

import com.gpmall.user.dto.CheckAuthRequest;
import com.gpmall.user.dto.CheckAuthResponse;
import com.gpmall.user.dto.UserLoginRequest;
import com.gpmall.user.dto.UserLoginResponse;

public interface IUserLoginService {

    /**
     * 用户登录
     * @param request
     * @return
     */
    UserLoginResponse login(UserLoginRequest request);


    /**
     * 校验过程
     * @param request
     * @return
     */
    CheckAuthResponse validToken(CheckAuthRequest request);
}
