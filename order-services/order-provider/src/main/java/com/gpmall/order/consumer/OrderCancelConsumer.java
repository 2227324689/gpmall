package com.gpmall.order.consumer;

import com.gpmall.commons.config.RabbitMqConfig;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.constants.OrderConstants;
import com.gpmall.order.dal.entitys.Order;
import com.gpmall.order.dal.entitys.OrderItem;
import com.gpmall.order.dal.entitys.Stock;
import com.gpmall.order.dal.persistence.OrderItemMapper;
import com.gpmall.order.dal.persistence.OrderMapper;
import com.gpmall.order.dal.persistence.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 延迟队列订单取消监听
 * @Author： wz
 * @Date: 2019-09-18 01:20
 **/
@Slf4j
@Component
@Transactional
public class OrderCancelConsumer {
    @Autowired
	private OrderMapper orderMapper;
    @Autowired
	private OrderItemMapper orderItemMapper;
    @Autowired
	private StockMapper stockMapper;
    //context 为订单号
    @RabbitListener(queues = RabbitMqConfig.DELAY_QUEUE)
	public void process(String context,Message message,Channel channel)throws  IOException{
    try {
		log.info("开始执行订单[{}]的支付超时订单关闭......", context);
		Order order=new Order();
		order.setOrderId(context);
		//先查询订单是否是待支付状态
		Order order1=orderMapper.selectByPrimaryKey(order);
		//未付款才去走逻辑
		if(order1.getStatus()==0){
			order.setStatus(OrderConstants.ORDER_STATUS_TRANSACTION_CANCEL);
			//将订单状态改为取消
			orderMapper.updateByPrimaryKey(order);
			//将订单商品的库存状态改为释放
			orderItemMapper.updateStockStatus(2,context);
			//将库存还回去
			List<OrderItem> list=orderItemMapper.queryByOrderId(context);
			List<Long> itemIds=list.stream().map(OrderItem::getItemId).sorted().collect(Collectors.toList());
			//锁 itemIds
			List<Stock> stocks=stockMapper.findStocksForUpdate(itemIds);
			stocks.forEach(stock -> {
				list.forEach(one->{
					if(Objects.equals(one.getItemId(),stock.getItemId())){
						stock.setLockCount(-one.getNum());
						stock.setStockCount(one.getNum().longValue());
						//释放库存
						stockMapper.updateStock(stock);
						return;
					}
				});
			});
		}
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		log.info("超时订单{}处理完毕",context);
	}catch (Exception e){
    	log.error("超时订单处理失败:{}",context);
    	e.printStackTrace();
    	//这里会不断消费吗？
    	channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
	}
  }
}