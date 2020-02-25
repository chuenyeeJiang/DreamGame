package com.chuenyee.service;

import java.util.HashMap;
import java.util.Map;

import com.chuenyee.service.api.FileServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FileFallbackServer implements FileServer {

    Logger logger = LoggerFactory.getLogger(FileFallbackServer.class);


    @Override
    public String upload(@RequestParam MultipartFile local_file) {
        logger.info("FileServer    fallback!");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("error", "系统正在维护中...");
        map.put("response_param", response_param);
        map.put("view", "404.html");
        return "error";
    }

    @Override
    public String uploadBase64(@RequestParam("strBase64") String strBase64) {
        logger.info("FileServer    fallback!");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("error", "系统正在维护中...");
        map.put("response_param", response_param);
        map.put("view", "404.html");
        return "error";
    }
}
