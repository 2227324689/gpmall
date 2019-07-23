package com.gpmall.user;

import com.gpmall.user.dto.UserRegisterRequest;
import com.gpmall.user.dto.UserRegisterResponse;

public interface IUserRegisterService {

    /**
     * 实现用户注册功能
     * @param request
     * @return
     */
    UserRegisterResponse register(UserRegisterRequest request);
}
