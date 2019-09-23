package com.gpmall.commons.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 延迟队列设置
 * @Author： wz
 * @Date: 2019-09-17 23:17
 **/
@Configuration
public class RabbitMqConfig {

	public static final String DELAY_EXCHANGE="delay_exchange";

	public static  final String DELAY_QUEUE="delay_queue";

	//延时队列
	@Bean
	public Queue delayQueue(){
		return new Queue(DELAY_QUEUE,true);
	}
	//延时交换机
	@Bean
	public FanoutExchange delayExchange(){
		Map<String,Object> args=new HashMap<>();
		args.put("x-delayed-type","direct");
		FanoutExchange topic=new FanoutExchange(DELAY_EXCHANGE,true,false,args);
		topic.setDelayed(true);
		return topic;
	}

	//绑定延时队列与交换机
	@Bean
	public Binding delayBind(){
		return BindingBuilder.bind(delayQueue()).to(delayExchange());
	}

	//消息转换器
	Jackson2JsonMessageConverter jsonMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	//消息发送器
	@Bean
	RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
		final RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}