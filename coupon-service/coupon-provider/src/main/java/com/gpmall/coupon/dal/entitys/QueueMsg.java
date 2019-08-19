package com.gpmall.coupon.dal.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_queue_msg")
public class QueueMsg implements Serializable {
    @Id
    private Integer id;

    /**
     * 队列名称
     */
    private String queue;

    /**
     * 队列topic
     */
    private String topic;

    /**
     * 队列listener的方法名
     */
    private String method;

    /**
     * 队列listener的方法参数
     */
    private String args;

    /**
     * 方法执行完成的时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    private Date created;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取队列名称
     *
     * @return queue - 队列名称
     */
    public String getQueue() {
        return queue;
    }

    /**
     * 设置队列名称
     *
     * @param queue 队列名称
     */
    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     * 获取队列topic
     *
     * @return topic - 队列topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * 设置队列topic
     *
     * @param topic 队列topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * 获取队列listener的方法名
     *
     * @return method - 队列listener的方法名
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置队列listener的方法名
     *
     * @param method 队列listener的方法名
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取队列listener的方法参数
     *
     * @return args - 队列listener的方法参数
     */
    public String getArgs() {
        return args;
    }

    /**
     * 设置队列listener的方法参数
     *
     * @param args 队列listener的方法参数
     */
    public void setArgs(String args) {
        this.args = args;
    }

    /**
     * 获取方法执行完成的时间
     *
     * @return finish_time - 方法执行完成的时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置方法执行完成的时间
     *
     * @param finishTime 方法执行完成的时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }
}