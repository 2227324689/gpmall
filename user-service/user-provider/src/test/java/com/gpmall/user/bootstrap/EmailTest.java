package com.gpmall.user.bootstrap;

import com.gpmall.commons.tool.email.DefaultEmailSender;
import com.gpmall.commons.tool.email.MailData;
import com.gpmall.commons.tool.email.emailConfig.EmailConfig;
import com.gpmall.commons.tool.email.freeMarker.FreeMarkerUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 咕泡学员
 * Administrator
 * 2019/8/26 0026
 * 21:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath://application.yml")
public class EmailTest {
    @Autowired
    DefaultEmailSender defaultEmailSender;
    @Autowired
    EmailConfig emailConfig;
    @Test
    public void sendMail(){
        try {
            for (int i=0;i<=1;i++) {
                MailData mailData = new MailData();
                mailData.setToAddresss(Arrays.asList("shuaike@chinasie.com"));
                mailData.setSubject("文件地址");
                Map<String,Object> viewObj  = new HashMap<>();
                viewObj.put("url","http://www.xxxxx.ddcom/register");
                viewObj.put("title","激活邮件");
                mailData.setDataMap(viewObj);
                mailData.setFileName("activeRegisterInfoHtmlTemplate.html");

//                mailData.setContent("<p>list是一个集合，也就是一个容器。<br>\n" +
//                        "<code>null == list</code>判断是否有这个容器，<br>\n" +
//                        "而<code>list.size() ==0</code>判断这个容器有没有东西，<br>\n" +
//                        "两者是不一样的意思</p>\n" +
//                        "<p>而<code>list.size() ==0</code>与<code>list.isEmpty()</code>没有区别</p>\n" +
//                        "<p>容器相当于水杯，如果水杯都没有，水是绝对不会有的，如果没有水，水杯有没有就不清楚了，但有水，肯定有水杯。就这个道理。</p>\n" +
//                        "<p>出错例子：<br>\n" +
//                        "usql.append(\"select unit_name from jhc_ut_unit where unit_oid = \"+wageserviceunit);</p>\n" +
//                        "<pre><code>          List&lt;String&gt; uList = DaoUtil.findWithSQL(usql.toString());  \n" +
//                        "          dto.setWageServiceUnit(uList.get(0));\n" +
//                        "     \n" +
//                        "         }  \n" +
//                        "</code></pre>\n" +
//                        "<p>问题出在uList.get(0) 这样写是无形中默认uList这个集合是一定存在的，也就是一定有杯子的。如果uList这个集合为空，也就是要查的数据表中没有查不出东西时，这种情况就是没有杯子的情况，就会报异常的了。<br>\n" +
//                        "修改后：<br>");
                mailData.setAttachFileNames(new Vector<>(Arrays.asList("D:\\testmain.txt","D:\\TEST.txt")));
                defaultEmailSender.sendHtmlMailUseTemplate(mailData);
                Thread.sleep(2000);
                System.out.println("发送" + i + "邮件");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
