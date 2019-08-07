package com.gpmall.user.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.user.IMemberService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.QueryMemberRequest;
import com.gpmall.user.dto.QueryMemberResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MemberController
 * @Description 会员中心控制层
 * @Author Nuo Mark
 * @Date 2019-08-07 14:26
 * @Version 1.0
 **/
@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    IMemberService memberService;

    @GetMapping("/searchMemberById")
    public ResponseData searchMemberById( QueryMemberRequest request) {
        QueryMemberResponse queryMemberResponse = memberService.queryMemberById(request);
        if (!queryMemberResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg(queryMemberResponse.getMsg());
        }
        return new ResponseUtil<>().setData(queryMemberResponse);
    }
}