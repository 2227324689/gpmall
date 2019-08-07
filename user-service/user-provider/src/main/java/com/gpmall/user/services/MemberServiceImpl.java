package com.gpmall.user.services;/**
 * Created by mic on 2019/7/30.
 */

import com.gpmall.user.IMemberService;
import com.gpmall.user.IUserLoginService;
import com.gpmall.user.constants.SysRetCodeConstants;
import com.gpmall.user.converter.MemberConverter;
import com.gpmall.user.dal.entitys.Member;
import com.gpmall.user.dal.persistence.MemberMapper;
import com.gpmall.user.dto.*;
import com.gpmall.user.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-下午11:51
 */
@Slf4j
@Service
public class MemberServiceImpl implements IMemberService{

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IUserLoginService userLoginService;

    @Autowired
    MemberConverter memberConverter;

    /**
     * 根据用户id查询用户会员信息
     * @param request
     * @return
     */
    @Override
    public QueryMemberResponse queryMemberById(QueryMemberRequest request) {
        QueryMemberResponse queryMemberResponse=new QueryMemberResponse();
        try{
            request.requestCheck();
            Member member=memberMapper.selectByPrimaryKey(request.getUserId());
            if(member==null){
                queryMemberResponse.setCode(SysRetCodeConstants.DATA_NOT_EXIST.getCode());
                queryMemberResponse.setMsg(SysRetCodeConstants.DATA_NOT_EXIST.getMessage());
            }
            queryMemberResponse=memberConverter.member2Res(member);
            queryMemberResponse.setCode(SysRetCodeConstants.SUCCESS.getCode());
            queryMemberResponse.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("MemberServiceImpl.queryMemberById Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(queryMemberResponse,e);
        }
        return queryMemberResponse;
    }

    @Override
    public HeadImageResponse updateHeadImage(HeadImageRequest request) {
        HeadImageResponse response=new HeadImageResponse();
        //TODO
        return response;
    }

    @Override
    public UpdateMemberResponse updateMember(UpdateMemberRequest request) {
        UpdateMemberResponse response = new UpdateMemberResponse();
        try{
            request.requestCheck();
            CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
            checkAuthRequest.setToken(request.getToken());
            CheckAuthResponse authResponse = userLoginService.validToken(checkAuthRequest);
            if (!authResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
                response.setCode(authResponse.getCode());
                response.setMsg(authResponse.getMsg());
                return response;
            }
            Member member = memberConverter.updateReq2Member(request);
            int row = memberMapper.updateByPrimaryKeySelective(member);
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            log.info("MemberServiceImpl.updateMember effect row :"+row);
        }catch (Exception e){
            log.error("MemberServiceImpl.updateMember Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
