package com.gpmall.commons.tool.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 事务监听器
 * @Author： wz
 * @Date: 2019-08-22 17:50
 **/

public class TransactionListenerImpl implements TransactionListener {
	private AtomicInteger transactionIndex = new AtomicInteger(0);

	private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

	@Override
	public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		int value = transactionIndex.getAndIncrement();
		int status = value % 3;
		localTrans.put(msg.getTransactionId(), status);
		return LocalTransactionState.UNKNOW;
	}

	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt msg) {
		Integer status = localTrans.get(msg.getTransactionId());
		if (null != status) {
			switch (status) {
				case 0:
					return LocalTransactionState.UNKNOW;
				case 1:
					return LocalTransactionState.COMMIT_MESSAGE;
				case 2:
					return LocalTransactionState.ROLLBACK_MESSAGE;
			}
		}
		return LocalTransactionState.COMMIT_MESSAGE;
	}
}