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
public abstract class BasePayment<R extends AbstractResponse,P extends AbstractRequest,C extends Context>  implements Payment<R,P> {

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
	public abstract C createContext(P request);

	/**
	 * 为下层的支付渠道的数据做好准备
	 *
	 * @param request
	 * @param context
	 * @throws BizException
	 */
	public  void prepare(P request, C context)throws BizException{
		SortedMap<String, Object> sParaTemp = new TreeMap<>();
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
	public abstract R generalProcess(P request, C context) throws BizException;

	/***
	 * 基本业务处理完成后执行的后续操作
	 * @param request
	 * @param respond
	 * @param context
	 * @return
	 * @throws BizException
	 */
	public abstract void afterProcess(P request, R respond,C context) throws BizException;

	/**
	 * 核心处理器
	 */
	@Override
	public R process(P request) throws BizException {
		log.info("Begin BasePayment.process:{}", JSON.toJSONString(request));
		R response = null;
		//创建上下文

		C context = createContext(request);
		//验证
		getValidator().validate(request);
		//准备
		prepare(request, context);
		//执行
		response = generalProcess(request, context);
		//善后
		afterProcess(request, response, context);
		return response;
	}

	/**
	 * 获取支付渠道
	 *
	 * @return
	 */
	public abstract String getPayChannel();
}
