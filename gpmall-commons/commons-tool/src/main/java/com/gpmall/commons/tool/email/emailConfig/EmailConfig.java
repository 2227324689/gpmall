package com.gpmall.commons.tool.email.emailConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Administrator
 * 2019/8/26 0026
 * 14:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    //服务器的IP和端口
    private String mailServerHost;
    private String mailServerPort;
    //发送邮件的邮箱
    private String fromAddress;
    //接收邮件的邮箱,用逗号分隔标识多个
    private List<String> toAddresss;
    //抄送地址
    private List<String> ccAddresss;
    //登录邮件服务器的用户名和密码
    private String username;
    private String password;
    //是否需要身份验证
    private boolean mailSmtpAuth = false;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private Vector<String> attachFileNames;

    private String mailDebug = "false";//默认值是false

    /**发送哦html时指定的content-type**/
    private String content_type = "text/html; charset=utf-8";
    //邮件模板的路劲
    private String templatePath = "emailTemplate";

    //邮件激活URL
    private String userMailActiveUrl;


    public Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.mailServerHost);
        properties.put("mail.smtp.port", this.mailServerPort);
        properties.put("mail.smtp.auth", this.mailSmtpAuth);
        return properties;
    }

    /**
     * 获得收件人地址
     * @return
     */
    public Address[] getToInternetAddress(){
        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();
        toAddresss.forEach(toAddress->{
            try {
                internetAddressList.add(new InternetAddress(toAddress));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        });
        return (Address[]) internetAddressList.toArray(addresses);
    }

    /**
     * 获得抄送地址
     * @return
     */
    public Address[] getCcInternetAddress(){
        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();
        toAddresss.forEach(toAddress->{
            try {
                internetAddressList.add(new InternetAddress(toAddress));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        });
        return (Address[]) internetAddressList.toArray(addresses);
    }
}
