package com.gpmall.commons.tool.email.freeMarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.Constants;
import jodd.io.FileUtil;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Map;

/**
 * 咕泡学员
 * Administrator
 * 2019/8/31 0031
 * 19:37
 * 通过FreeMarker 获取模板数据，用于获取邮件模板
 */
public class FreeMarkerUtil {

    public static String getMailTextForTemplate(String templatePath, String filename, Map datas) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        //获取class下面的模板文件
        configuration.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getClass().getResource(
               "/"+templatePath).getPath()));
        Template template = configuration.getTemplate(filename,"utf-8");
        StringWriter out = new StringWriter();
        template.process(datas,out);
        return out.toString();
    }
}
