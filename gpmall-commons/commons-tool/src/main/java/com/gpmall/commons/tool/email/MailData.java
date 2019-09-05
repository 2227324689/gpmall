package com.gpmall.commons.tool.email;

import lombok.Data;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;

/**
 * Administrator
 * 2019/8/28 0028
 * 15:48
 */
@Data
public class MailData {
    //接收邮件的邮箱,用逗号分隔标识多个
    private List<String> toAddresss = Collections.emptyList();
    //抄送地址
    private List<String> ccAddresss = Collections.emptyList();;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private Vector<String> attachFileNames = new Vector<>();;
    /**发送哦html时指定的content-type**/
    private String content_type = "text/html; charset=utf-8";

    private String fileName;//邮件模板的文件名

    private Map<String,Object> dataMap;//邮件版本需要替换的数据
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
        ccAddresss.forEach(ccAddresss->{
            try {
                internetAddressList.add(new InternetAddress(ccAddresss));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        });
        return (Address[]) internetAddressList.toArray(addresses);
    }

}
