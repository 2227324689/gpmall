package com.gpmall.user;

import com.gpmall.user.dto.UserVerifyRequest;
import com.gpmall.user.dto.UserVerifyResponse;

public interface IUserVerifyService {



    /**
     * 激活邮件
     * @param userVerifyRequest
     * @return
     */
    UserVerifyResponse verifyMemer(UserVerifyRequest userVerifyRequest);
}
