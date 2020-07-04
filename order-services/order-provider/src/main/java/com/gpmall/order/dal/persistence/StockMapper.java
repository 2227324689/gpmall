package com.gpmall.order.dal.persistence;

import com.gpmall.commons.tool.tkmapper.TkMapper;
import com.gpmall.order.dal.entitys.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author： wz
 * @Date: 2019-09-16 00:09
 **/
public interface StockMapper extends TkMapper<Stock> {
 void updateStock(Stock stock);
 Stock selectStockForUpdate(Long itemId);
 Stock selectStock(Long itemId);

 List<Stock> findStocksForUpdate(@Param("itemIds") List<Long> itemIds);


 /**
  * 预先扣减库存
  * @param stock
  * @return
  */
 int preDeductionStock(Stock stock);

 /**
  * 释放扣减的库存
  * @param stock
  * @return
  */
 int releaseDeductionStock(Stock stock);
}