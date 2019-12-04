package com.chuenyee.controller;

import com.alibaba.fastjson.JSONObject;
import com.chuenyee.service.FileServerImpl;
import com.chuenyee.service.api.FileServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Deprecated
@RestController
public class HeadPortraitController {

    private Logger logger = LoggerFactory.getLogger(HeadPortraitController.class);

    FileServer fileServer = new FileServerImpl();
    // 上传头像
    //@RequestMapping(value = "/uploadHeadPortrait.do")
    @RequestMapping(value = "uploadHeadPortrait.do",method = RequestMethod.POST)
    public Map<String, Object> uploadHeadPortrait(@RequestParam Map<String,Object> request_map) {
        logger.info("上传头像");

        logger.debug("request_map:"+request_map);


        /*
         解包
         */
        String str_base64 = (String) request_map.get("str_bytefile");
        logger.debug("str_base64:"+str_base64);
/*
        int x = (Integer) request_map.get("str_x");
        int y = (Integer) request_map.get("str_y");
        int w = (Integer) request_map.get("str_w");
        int h = (Integer) request_map.get("str_h");
        int previewW = (Integer) request_map.get("str_previewW");
        int previewH = (Integer) request_map.get("str_previewH");*/

     /*   ImageIcon imgIcon = new ImageIcon();
        BufferedImage image = 	Preview = new BufferedImage(imgIcon.getIconWidth(),
                imgIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);*/


        // 原图放大
      /*  x = x * Preview.getWidth() / previewW;

        y = y * Preview.getHeight() / previewH;

        w = w * Preview.getWidth() / previewW;

        h = h * Preview.getHeight() / previewH;*/

        /*
           上传头像
        */
       // String  file_path = fileServer.upload_strbase64(str_base64,"jpg");

        /*
           存储用户头像信息
         */


        /*
         * 打包返回信息
         */
        Map<String, Object> response_map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
     //   response_param.put("file_path",file_path);
       // response_param.put("fileId",fileId);
        response_map.put("response_param", response_param);
        response_map.put("view", "login");

        return response_map;
    }

}
