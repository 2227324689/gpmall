package com.gpmall.comment.dal.persistence;

import com.gpmall.comment.dal.entitys.ItemCat;
import com.gpmall.comment.dal.entitys.ItemCatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemCatMapper {
    int countByExample(ItemCatExample example);

    int deleteByExample(ItemCatExample example);

    int deleteByPrimaryKey(String id);

    int insert(ItemCat record);

    int insertSelective(ItemCat record);

    List<ItemCat> selectByExample(ItemCatExample example);

    ItemCat selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ItemCat record, @Param("example") ItemCatExample example);

    int updateByExample(@Param("record") ItemCat record, @Param("example") ItemCatExample example);

    int updateByPrimaryKeySelective(ItemCat record);

    int updateByPrimaryKey(ItemCat record);
}