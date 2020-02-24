package com.chuenyee.controller;


import com.chuenyee.service.FastDFS;
import com.chuenyee.service.FileServerImpl;
import com.chuenyee.service.api.FileServer;
import com.chuenyee.until.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@CrossOrigin
@RestController
public class FileUploadController {

    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    FileServer fileServer = new FileServerImpl();
    // 上传文件 str_base64
    @RequestMapping(value = "uploadBase64.do", method = RequestMethod.POST)
    public String FileUploadStrbase64(@RequestParam("strBase64") String strBase64) {
        logger.info("===============文件上传");


        /*
        跳转页面
         */
        //   String view ;

        /*
         解包
         */
       // String strBase64 = (String) requestMap.get("strBase64");
        logger.debug("str_base64:" + strBase64);

        /*
          调用服务 上传头像
        */
        String result = fileServer.upload(strBase64, ConfigUtil.getProperty("file.format"));

        logger.debug("getresult:" + result);


        /*
         * 打包返回信息
         */
//        Map<String, Object> response_map = new HashMap<String, Object>();
//        Map<String, Object> response_param = new HashMap<String, Object>();
//        response_param.put("result", result);
//        response_map.put("response_param", response_param);
//        response_map.put("view", "login");

        return result;
    }

    // 上传文件
    @CrossOrigin
    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile localFile) {
        logger.info("===============文件上传");

        logger.debug("request_map:" + localFile);

        /*
        跳转页面
         */
        //   String view ;

        /*
         解包
         */
        /* request_map.get("str_bytefile");*/

        /*
          调用服务 上传头像
        */
        String result = fileServer.upload(localFile, ConfigUtil.getProperty("file.format"));

        /*
         * 打包返回信息
         */
  /*      Map<String, Object> response_map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("uploadMessge",uploadMessge);
        response_map.put("response_param", response_param);
        response_map.put("view", "login");*/

        return result;
    }

    //图片输出
    @CrossOrigin
    @RequestMapping(value = "showPNG.do", method = RequestMethod.GET)
    public void showPNG(String fileId, HttpServletResponse response) {
        // 输出文件
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        BufferedImage img = fileServer.getIMG(fileId);

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            ImageIO.write(img, "PNG", sos);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sos.flush();
                sos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*    public Result uploadtofastdfs(String filename, byte[] data) {
        PictureResult pictureResult = new PictureResult();
        try {
            FastDFS client = new FastDFSClient("classpath:fdfs_client.conf");
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            String url = client.uploadFile(data, extName);
            pictureResult.setUrlDB(url);
            pictureResult.setUrl(imageServerUrl + url);
            System.out.println("-------文件上传成功！-------------");
            System.out.println(pictureResult.getUrl());
            Result<PictureResult> result = new Result(0, "上传成功", pictureResult);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(1, "上传失败");
    }*/

}
