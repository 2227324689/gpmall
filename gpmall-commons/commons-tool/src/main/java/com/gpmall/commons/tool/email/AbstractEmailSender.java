package com.gpmall.commons.tool.email;

import com.gpmall.commons.tool.exception.BaseBusinessException;
import com.gpmall.commons.tool.exception.BizException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * Administrator
 * 2019/8/26 0026
 * 14:44
 */
public abstract  class AbstractEmailSender implements EmailSender {
    /**
     * 发送简单的文本邮件
     * @throws Exception
     */
    @Override
    public void sendMail(MailData mailData) throws Exception {
        initProperties(mailData);
        //验证发送的数据
        validatorMailData(mailData);
        doSend(mailData);
    }
    /**
     * 发送待附件的邮件
     * @throws Exception
     */
    @Override
    public void sendMailWithAttachFile(MailData mailData) throws Exception{
        initProperties(mailData);
        //验证发送的数据
        validatorMailData(mailData);
        //验证发送的附件
        validatorMailDataAttachFile(mailData);
        doSendWithAttachFile(mailData);
    }

    /**
     * 发送HTML内容的邮件
     * @throws Exception
     */
    public void sendHtmlMail(MailData mailData)throws Exception{
        initProperties(mailData);
        //验证发送的数据
        validatorMailData(mailData);

        doHtmlSend(mailData);
    }
    /**
     * 使用Html模板发送邮件
     * @throws Exception
     */
    public void sendHtmlMailUseTemplate(MailData mailData)throws Exception{
        initProperties(mailData);
        //验证发送的数据
        validatorMailData(mailData);

        doSendHtmlMailUseTemplate(mailData);
    }

    public abstract void initProperties(MailData mailData) throws Exception;

    public abstract void doSend(MailData mailData) throws Exception;

    public abstract void doHtmlSend(MailData mailData) throws Exception;

    public abstract void doSendWithAttachFile(MailData mailData) throws Exception;

    public abstract void doSendHtmlMailUseTemplate(MailData mailData) throws Exception;

    public void validatorMailData(MailData mailData){
        if(mailData.getToAddresss()==null || mailData.getToAddresss().isEmpty()){
            throw new BizException("error","邮件发送地址不允许为空.");
        }
        if(!StringUtils.isNoneBlank(mailData.getSubject())){
            throw new BizException("error","邮件主题不允许为空.");
        }
        if(!StringUtils.isNoneBlank(mailData.getContent())){
            throw new BizException("error","邮件内容不允许为空.");
        }
    }
    public void validatorMailDataAttachFile(MailData mailData){
       if(ArrayUtils.isEmpty(mailData.getAttachFileNames().toArray())){
           throw new BizException("error","邮件附件不允许为空.");
       }
    }
}
