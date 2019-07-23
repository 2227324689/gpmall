package com.gpmall.shopping.dal.persistence;

import com.gpmall.shopping.dal.entitys.ItemDesc;
import com.gpmall.shopping.dal.entitys.ItemDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDescMapper {
    long countByExample(ItemDescExample example);

    int deleteByExample(ItemDescExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    List<ItemDesc> selectByExampleWithBLOBs(ItemDescExample example);

    List<ItemDesc> selectByExample(ItemDescExample example);

    ItemDesc selectByPrimaryKey(Long itemId);

    int updateByExampleSelective(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByExampleWithBLOBs(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByExample(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByPrimaryKeySelective(ItemDesc record);

    int updateByPrimaryKeyWithBLOBs(ItemDesc record);

    int updateByPrimaryKey(ItemDesc record);
}