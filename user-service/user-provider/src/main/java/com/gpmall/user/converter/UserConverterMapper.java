package com.gpmall.user.converter;

import com.gpmall.user.dal.entitys.Member;
import com.gpmall.user.dal.entitys.User;
import com.gpmall.user.dto.UserLoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/22-18:05
 */
@Mapper
public interface UserConverterMapper {

    UserConverterMapper INSTANCE= Mappers.getMapper(UserConverterMapper.class);

    @Mappings({})
    UserLoginResponse converter(Member member);

}
