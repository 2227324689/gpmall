package com.gpmall.user.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.user.IMemberService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.dto.QueryMemberRequest;
import com.gpmall.user.dto.QueryMemberResponse;
import com.gpmall.user.dto.UpdateMemberRequest;
import com.gpmall.user.dto.UpdateMemberResponse;
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
@RequestMapping("/user")
public class MemberController {

    @Reference(timeout = 3000)
    IMemberService memberService;

    /**
     * 根据ID查询单条会员信息
     * @param id 编号
     * @return
     */
    @GetMapping("/member/{id}")
    public ResponseData searchMemberById(@PathVariable(name = "id")long id) {
        QueryMemberRequest request=new QueryMemberRequest();
        request.setUserId(id);
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
    public ResponseData updateUser(@RequestBody UpdateMemberRequest request) {
        UpdateMemberResponse response = memberService.updateMember(request);
        if(response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(null);
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}

