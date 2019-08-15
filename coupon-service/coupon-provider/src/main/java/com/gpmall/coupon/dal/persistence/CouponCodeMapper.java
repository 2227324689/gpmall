package com.gpmall.coupon.dal.persistence;

import com.gpmall.coupon.dal.entitys.CouponCode;
import com.gpmall.coupon.dal.entitys.CouponCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponCodeMapper {
    long countByExample(CouponCodeExample example);

    int deleteByExample(CouponCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CouponCode record);

    int insertSelective(CouponCode record);

    List<CouponCode> selectByExample(CouponCodeExample example);

    CouponCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CouponCode record, @Param("example") CouponCodeExample example);

    int updateByExample(@Param("record") CouponCode record, @Param("example") CouponCodeExample example);

    int updateByPrimaryKeySelective(CouponCode record);

    int updateByPrimaryKey(CouponCode record);
}