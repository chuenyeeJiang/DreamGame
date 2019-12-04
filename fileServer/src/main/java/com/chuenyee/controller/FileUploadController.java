package com.chuenyee.controller;


import com.chuenyee.service.FastDFS;
import com.chuenyee.service.FileServerImpl;
import com.chuenyee.service.api.FileServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class FileUploadController {

    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    FileServer fileServer = new FileServerImpl();
    // 上传文件 str_base64
    @RequestMapping(value = "FileUpload_strbase64.do",method = RequestMethod.POST)
    public Map<String, Object> FileUpload_strbase64(@RequestParam Map<String,Object> request_map) {
        logger.info("===============文件上传");

        logger.debug("request_map:"+request_map);

        /*
        跳转页面
         */
     //   String view ;

        /*
         解包
         */
        String str_base64 = (String) request_map.get("str_bytefile");
        logger.debug("str_base64:"+str_base64);

        /*
          调用服务 上传头像
        */
            UUID result = fileServer.upload(str_base64,"jpg");


        /*
         * 打包返回信息
         */
        Map<String, Object> response_map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("result",result);
        response_map.put("response_param", response_param);
        response_map.put("view", "login");

        return response_map;
    }

    // 上传文件
    @RequestMapping(value = "FileUpload.do",method = RequestMethod.POST)
    public UUID FileUpload(@RequestParam MultipartFile local_file) {
        logger.info("===============文件上传");

        logger.debug("request_map:"+local_file);

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
        UUID  result = fileServer.upload(local_file,"jpg");


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
