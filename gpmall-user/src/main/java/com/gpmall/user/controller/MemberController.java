package com.gpmall.user.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.user.IMemberService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.QueryMemberRequest;
import com.gpmall.user.dto.QueryMemberResponse;
import com.gpmall.user.dto.UpdateMemberRequest;
import com.gpmall.user.dto.UpdateMemberResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 会员信息更新
     * @return
     */
    @PutMapping("member")
    @ApiOperation("会员信息更新")
    public ResponseData updateUser(@RequestBody UpdateMemberRequest request) {
        UpdateMemberResponse response = memberService.updateMember(request);
        if(response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(null);
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}

