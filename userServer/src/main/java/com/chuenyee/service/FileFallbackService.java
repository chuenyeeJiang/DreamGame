package com.chuenyee.service;

import java.util.HashMap;
import java.util.Map;

import com.chuenyee.service.api.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class FileFallbackService implements FileService {

    Logger logger = LoggerFactory.getLogger(FileFallbackService.class);


    @Override
    public String upload(@RequestParam Map<String,Object> request_map) {
        logger.info("HeadPortraitServer Fallback!");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("error", "系统发生错误，请联系站长:18302059427");
        map.put("response_param", response_param);
        map.put("view", "error.html");
        return "error";
    }

    @Override
    public String uploadBase64(Map<String, Object> request_map) {
        logger.info("HeadPortraitServer Fallback!");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("error", "系统发生错误，请联系站长:18302059427");
        map.put("response_param", response_param);
        map.put("view", "error.html");
        return "error";
    }
}
