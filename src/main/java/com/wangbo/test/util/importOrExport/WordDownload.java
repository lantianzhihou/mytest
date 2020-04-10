package com.wangbo.test.util.importOrExport;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordDownload {

    private static Configuration freemarkerConfig;

    static {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_27);
        freemarkerConfig.setEncoding(Locale.getDefault(), "UTF-8");
    }

    public void getDocList(HttpServletResponse response, Integer baseId) {
        Map<String, Object> dataMap = gennerateDataMap(baseId);
        String templateName = "docRedressDuty.xml";
        String downloadFileName = "社区矫正责任书.doc";
        generateTemplateToBrowser(dataMap, templateName, downloadFileName, response);
    }

    private Map<String, Object> gennerateDataMap(Integer baseId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "lily");
        return dataMap;
    }

    private void generateTemplateToBrowser(Map<String, Object> datas, String tmeplate,
            String fileName, HttpServletResponse response) {
        // 按模板生成html文档
        freemarkerConfig.setTemplateLoader(new URLTemplateLoader() {
            @Override
            protected URL getURL(String arg0) {
                return WordDownload.class.getResource("/" + tmeplate);
            }
        });
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Writer out = null;
        FileOutputStream fos = null;
        try {
            Template temp = freemarkerConfig.getTemplate(tmeplate);
            out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            // 执行模板替换
            temp.process(datas, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
