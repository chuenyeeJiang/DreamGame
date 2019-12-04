package com.chuenyee.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyHttphandle {
    static String projectName  = "store";

    public static String getUserName(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://127.0.0.1:9000/eureka/apps/store-userServer")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String str = response.body().string();
                str=str.replace("[", "");
                str=str.replace("]", "");
                System.out.println(str);
                JSONObject root = JSON.parseObject(str);
                MyJsonHandle.debug(root,false);
                return "http://"+MyJsonHandle.getResult(root,"application.instance.hostName")+":"+MyJsonHandle.getResult(root,"application.instance.port.$")+"/"+projectName+"/getUsername.do";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
