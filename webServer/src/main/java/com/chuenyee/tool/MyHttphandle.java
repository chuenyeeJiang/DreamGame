package com.chuenyee.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyHttphandle {
    String projectName  = "store";

    public String getFileServerURL(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://127.0.0.1:9000/eureka/apps/store-fileServer")
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
                return "http://"+MyJsonHandle.getResult(root,"application.instance.hostName")+":"+MyJsonHandle.getResult(root,"application.instance.port.$")+"/"+projectName+"/uploadHeadPortrait.do";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getURL(String host,String port,String projectName,String  serviceName,String requestMapper){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+host+":"+port+"/eureka/apps/"+serviceName)
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
                return "http://"+MyJsonHandle.getResult(root,"application.instance.hostName")+":"+MyJsonHandle.getResult(root,"application.instance.port.$")+"/"+projectName+"/"+requestMapper;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
