package com.gpmall.commons.tool.email;

/**
 * Administrator
 * 2019/8/26 0026
 * 14:43
 */
public interface EmailSender {
    /**
     * 发送简单的文本邮件
     * @throws Exception
     */
    public void sendMail(MailData mailData) throws Exception;

    /**
     * 发送待附件的邮件
     * @throws Exception
     */
    public void sendMailWithAttachFile(MailData mailData) throws Exception;

    /**
     * 发送HTML内容的邮件
     * @throws Exception
     */
    public void sendHtmlMail(MailData mailData)throws Exception;

    /**
     * 使用Html模板发送邮件
     * @throws Exception
     */
    public void sendHtmlMailUseTemplate(MailData mailData)throws Exception;
}
