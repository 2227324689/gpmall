package com.gpmall.pay.biz.abs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.result.AbstractResponse;
import com.gpmall.commons.tool.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 *
 * @author 风骚的Michael 老师
 */
public abstract class BasePayment implements Payment {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	public static Map<String, BasePayment> paymentMap = new ConcurrentHashMap<String, BasePayment>();

	@PostConstruct
	public void init() {
		paymentMap.put(getPayChannel(), this);
	}

	/**
	 * 获取验证器
	 *
	 * @return
	 */
	public abstract Validator getValidator();

	/**
	 * 创建上下文信息
	 *
	 * @param request
	 * @return
	 */
	public abstract Context createContext(AbstractRequest request);

	/**
	 * 为下层的支付渠道的数据做好准备
	 *
	 * @param request
	 * @param context
	 * @throws BizException
	 */
	public  void prepare(AbstractRequest request, Context context)throws BizException{
		SortedMap<String, Object> sParaTemp = new TreeMap<String, Object>();
		context.setsParaTemp(sParaTemp);
	};


	/**
	 * 基本业务处理
	 *
	 * @param request
	 * @param context
	 * @return AbstractResponse
	 * @throws BizException
	 */
	public abstract AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException;

	/***
	 * 基本业务处理完成后执行的后续操作
	 * @param request
	 * @param respond
	 * @param context
	 * @return
	 * @throws BizException
	 */
	public abstract void afterProcess(AbstractRequest request, AbstractResponse respond,Context context) throws BizException;

	/**
	 * 核心处理器
	 */
	@Override
	public <T extends AbstractResponse> T process(AbstractRequest request) throws BizException {
		log.info("Begin BasePayment.process:{}", JSON.toJSONString(request));
		AbstractResponse response = null;
		//创建上下文
		Context context = createContext(request);
		//验证
		getValidator().validate(request);
		//准备
		prepare(request, context);
		//执行
		response = generalProcess(request, context);
		//善后
		afterProcess(request, response, context);
		return (T) response;
	}

	/**
	 * 获取支付渠道
	 *
	 * @return
	 */
	public abstract String getPayChannel();
}
