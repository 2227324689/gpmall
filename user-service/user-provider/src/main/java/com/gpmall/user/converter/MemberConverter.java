package com.gpmall.user.converter;/**
 * Created by mic on 2019/7/31.
 */

import com.gpmall.user.dal.entitys.Member;
import com.gpmall.user.dto.QueryMemberResponse;
import com.gpmall.user.dto.UpdateMemberRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/31-上午12:05
 */
@Mapper(componentModel = "spring")
public interface MemberConverter {

    @Mappings({})
    QueryMemberResponse member2Res(Member member);

    @Mappings({})
    Member updateReq2Member(UpdateMemberRequest request);
}
