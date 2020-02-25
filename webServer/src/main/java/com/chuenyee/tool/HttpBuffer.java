package com.chuenyee.tool;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class HttpBuffer {
    static Logger logger = LoggerFactory.getLogger(HttpBuffer.class);

    public static boolean Wirter(byte[] bytes,String url){
        try {
            logger.debug("HttpBuffer.wirter");
            String fileBytes = null;
            fileBytes = base64Encode(bytes);
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                JSONObject params = new JSONObject();
                params.put("fileBytes", fileBytes);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.getOutputStream().write(params.toString().getBytes());
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            return false;
        }
            return true;
    }
    private static String base64Encode(byte[] content) {
        Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
        String str = encoder.encodeToString(content);
        return str;

        /**
         char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
         String str = new String(chars);

         return str;
         */
    }

}
