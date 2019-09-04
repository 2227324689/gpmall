package com.gpmall.commons.tool.email;


import com.gpmall.commons.tool.email.emailConfig.EmailConfig;
import com.gpmall.commons.tool.email.freeMarker.FreeMarkerUtil;
import jodd.util.ArraysUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

/**
 * Administrator
 * 2019/8/26 0026
 * 14:39
 * 默认的邮件发送器
 */
@Component
public class DefaultEmailSender extends AbstractEmailSender {
    @Autowired
    EmailConfig emailConfig;

    /**
     * 在初始化的基础上修改Properties
     */
    @Override
    public void initProperties(MailData mailData) throws Exception {
        if(mailData.getToAddresss() == null || mailData.getToAddresss().isEmpty()){
            mailData.setToAddresss(emailConfig.getToAddresss());
        }
        if(!StringUtils.isNoneBlank(mailData.getContent())){
            mailData.setContent(emailConfig.getContent());
        }
        if(!StringUtils.isNoneBlank(mailData.getSubject())){
            mailData.setSubject(emailConfig.getSubject());
        }
        if(mailData.getAttachFileNames() == null || mailData.getAttachFileNames().isEmpty()){
            mailData.setAttachFileNames(mailData.getAttachFileNames());
        }
        //emailConfig.setAttachFileNames(null);
    }

    @Override
    public void doSend(MailData mailData)throws Exception {
        /**创建一个邮件的会话**/
        Authenticator authenticator = null;
        if (emailConfig.isMailSmtpAuth()) {//如果需要身份认证，则创建一个密码验证器
            authenticator =  new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(emailConfig.getUsername(),emailConfig.getPassword());
                }
            };
        }
        Session session = Session.getDefaultInstance(emailConfig.getProperties(),authenticator);
        /**创建邮件信心**/
        Message message = new MimeMessage(session);
        //发送地址
        message.setFrom(new InternetAddress(emailConfig.getFromAddress()));
        //发送邮件给自己，解决发送邮件554的问题
        message.addRecipients(Message.RecipientType.CC ,new Address[]{new InternetAddress(emailConfig.getFromAddress())});
        //接收地址
        message.addRecipients(Message.RecipientType.TO ,mailData.getToInternetAddress());
        //抄送地址
        message.addRecipients(Message.RecipientType.CC ,mailData.getCcInternetAddress());
        //邮件主题
        message.setSubject(mailData.getSubject());
        //邮件内容
        message.setText(mailData.getContent());
        //发送时间
        message.setSentDate(new Date());
        //发送邮件
        Transport.send(message);
    }

    @Override
    public void doHtmlSend(MailData mailData) throws Exception {
        /**创建一个邮件的会话**/
        Authenticator authenticator = null;
        if (emailConfig.isMailSmtpAuth()) {//如果需要身份认证，则创建一个密码验证器
            authenticator =  new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(emailConfig.getUsername(),emailConfig.getPassword());
                }
            };
        }
        Session session = Session.getDefaultInstance(emailConfig.getProperties(),authenticator);
        /**创建邮件信心**/
        Message message = new MimeMessage(session);
        //发送地址
        message.setFrom(new InternetAddress(emailConfig.getFromAddress()));
        //发送邮件给自己，解决发送邮件554的问题
        message.addRecipients(Message.RecipientType.CC ,new Address[]{new InternetAddress(emailConfig.getFromAddress())});
        //接收地址
        message.addRecipients(Message.RecipientType.TO ,mailData.getToInternetAddress());
        //抄送地址
        message.addRecipients(Message.RecipientType.CC ,mailData.getCcInternetAddress());
        //邮件主题
        message.setSubject(mailData.getSubject());
        //邮件内容
        Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(mailData.getContent(),mailData.getContent_type());
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        message.setSentDate(new Date());
        //发送邮件
        Transport.send(message);

    }

    @Override
    public void doSendWithAttachFile(MailData mailData) throws Exception {
        /**创建一个邮件的会话**/
        Authenticator authenticator = null;
        if (emailConfig.isMailSmtpAuth()) {//如果需要身份认证，则创建一个密码验证器
            authenticator =  new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(emailConfig.getUsername(),emailConfig.getPassword());
                }
            };
        }
        Session session = Session.getDefaultInstance(emailConfig.getProperties(),authenticator);
        /**创建邮件信心**/
        Message message = new MimeMessage(session);
        //发送地址
        message.setFrom(new InternetAddress(emailConfig.getFromAddress()));
        //发送邮件给自己，解决发送邮件554的问题
        message.addRecipients(Message.RecipientType.CC ,new Address[]{new InternetAddress(emailConfig.getFromAddress())});
        //接收地址
        message.addRecipients(Message.RecipientType.TO ,mailData.getToInternetAddress());
        //抄送地址
        message.addRecipients(Message.RecipientType.CC ,mailData.getCcInternetAddress());
        //邮件主题
        message.setSubject(mailData.getSubject());
        //邮件内容
        Multipart multipart = new MimeMultipart();
            BodyPart bodyPart= new MimeBodyPart();
            ((MimeBodyPart) bodyPart).setText(mailData.getContent());
        //添加文本类容
        multipart.addBodyPart(bodyPart);
        //添加附件
        Vector<String> files = mailData.getAttachFileNames();
        if(files != null && files.size()>0){
            files.forEach(filePath->{
                MimeBodyPart bodyPartFile = new MimeBodyPart();
                try {
                    File file = new File(filePath);
                    bodyPartFile.attachFile(file);
                    bodyPartFile.setFileName(file.getName());
                    //bodyPartFile.setDataHandler(new DataHandler(new FileDataSource(file)));
                    multipart.addBodyPart(bodyPartFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        message.setContent(multipart);
        message.setSentDate(new Date());
        //发送邮件
        Transport.send(message);
    }

    @Override
    public void doSendHtmlMailUseTemplate(MailData mailData) throws Exception {
        /**创建一个邮件的会话**/
        Authenticator authenticator = null;
        if (emailConfig.isMailSmtpAuth()) {//如果需要身份认证，则创建一个密码验证器
            authenticator =  new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(emailConfig.getUsername(),emailConfig.getPassword());
                }
            };
        }
        Session session = Session.getDefaultInstance(emailConfig.getProperties(),authenticator);
        /**创建邮件信心**/
        Message message = new MimeMessage(session);
        //发送地址
        message.setFrom(new InternetAddress(emailConfig.getFromAddress()));
        //发送邮件给自己，解决发送邮件554的问题
        message.addRecipients(Message.RecipientType.CC ,new Address[]{new InternetAddress(emailConfig.getFromAddress())});
        //接收地址
        message.addRecipients(Message.RecipientType.TO ,mailData.getToInternetAddress());
        //抄送地址
        message.addRecipients(Message.RecipientType.CC ,mailData.getCcInternetAddress());
        //邮件主题
        message.setSubject(mailData.getSubject());
        //邮件内容
        Multipart multipart = new MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
        String content = FreeMarkerUtil.getMailTextForTemplate(emailConfig.getTemplatePath(),mailData.getFileName(),mailData.getDataMap());
        bodyPart.setContent(content,mailData.getContent_type());
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        message.setSentDate(new Date());
        //发送邮件
        Transport.send(message);
    }

}
