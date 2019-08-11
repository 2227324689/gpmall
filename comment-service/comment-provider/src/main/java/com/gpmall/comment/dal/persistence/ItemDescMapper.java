package com.gpmall.comment.dal.persistence;

import com.gpmall.comment.dal.entitys.ItemDesc;
import com.gpmall.comment.dal.entitys.ItemDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDescMapper {
    int countByExample(ItemDescExample example);

    int deleteByExample(ItemDescExample example);

    int deleteByPrimaryKey(String id);

    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    List<ItemDesc> selectByExample(ItemDescExample example);

    ItemDesc selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByExample(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByPrimaryKeySelective(ItemDesc record);

    int updateByPrimaryKey(ItemDesc record);
}