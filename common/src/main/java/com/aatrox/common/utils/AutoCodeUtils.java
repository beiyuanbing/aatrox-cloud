package com.aatrox.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Map;

/**
 * 自动生成的工具
 */
public class AutoCodeUtils {
    public static String readResource(String resourceUri) throws IOException {
        Resource resource = new ClassPathResource(resourceUri);
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }

    public static String replacePlaceHolder(String context, Map<String, String> param) {
        if (param != null && param.size() > 0) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                context = context.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return context;
    }

    public static void makeFile(String parentPath, String fileName, String content) throws IOException {
        String filePath = parentPath + File.separator + fileName;
        File file = new File(filePath);
        file.createNewFile();
        OutputStreamWriter out = new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8");
        out.write(content);
        out.close();
    }

    public static void deleteFile(String parentPath, String fileName) throws IOException {
        String filePath = parentPath + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
