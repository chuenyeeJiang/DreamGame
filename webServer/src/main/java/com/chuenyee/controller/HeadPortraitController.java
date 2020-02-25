package com.chuenyee.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.chuenyee.service.api.FileServer;
import com.chuenyee.service.api.HeadPortrait;
import com.chuenyee.service.api.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;



@Controller
public class    HeadPortraitController {
	Logger logger = LoggerFactory.getLogger(HeadPortraitController.class);

	@Autowired
	FileServer fileServer;
   // HeadPortrait headPortrait;

	@Autowired
	UserServer userServer;
   @Autowired
   HeadPortrait headPortrait;
	// 上传头像
	@RequestMapping(value="/Controller_uploadHeadPortrait.do")
	public Map<String, Object> uploadHeadPortrait(@RequestParam MultipartFile local_file,Map<String,Object> map) throws IOException {
		// return restTemplate.getForObject("", responseType)
//		logger.info("web HeadPortraitController=====================uploadHeadPortrait");
//		//图片转码
//		logger.debug("local_file："+local_file);
//		byte[] local_fileBytes = local_file.getBytes();
//		logger.debug("byte:"+local_fileBytes);
//		String str_bytefile = base64Encode(local_fileBytes);
//		logger.debug("base64:"+str_bytefile);
//		request_map.put("str_bytefile",str_bytefile);


		/*
		 * 请求服务
		 */

/*		try {
			HttpBuffer.Wirter(local_file.getBytes(),myHttphandle.getFileServerURL());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//Map<String, Object> response_map = headPortrait.upload(request_map);
		fileServer.upload(local_file);

/*		*//*
		 * 响应信息
		 *//*
		//  return	(String) response_map.get("view");
		if(response_map.get("response_param")!=null){
			//信息返回
			map.putAll((Map<String, Object>) response_map.get("response_param"));
		}
		logger.debug("map："+map);*/
		return	map;
	}

	// 上传头像
	@RequestMapping("/tailoringToBufferedImage.do")
	public BufferedImage tailoringToBufferedImage(HttpServletRequest request,
								   HttpServletResponse response, MultipartFile file, String x,
								   String y, String w, String h, String previewW, String previewH) throws Exception {
	   return headPortrait.tailoringToBufferedImage(request, response, file, x, y, w, h,previewW,previewH);
	}

	@RequestMapping("/tailoringToBase64.do")
	public String tailoringToBase64(HttpServletRequest request,
								   HttpServletResponse response, MultipartFile file, String x,
								   String y, String w, String h, String previewW, String previewH) throws Exception {
		return headPortrait.tailoringToBase64(request, response, file, x, y, w, h,previewW,previewH);
	}


	@ResponseBody
	@RequestMapping("/tailorAndUploadFile.do")
	public String tailorAndUploadFile(HttpServletRequest request,
									HttpServletResponse response, MultipartFile localFile, String x,
									String y, String w, String h, String previewW, String previewH) throws Exception {
		String strBase64 = headPortrait.tailoringToBase64(request, response, localFile, x, y, w, h,previewW,previewH);
		logger.info("上传头像");
//		Map<String,Object> requestMap = new HashMap<>();
//		requestMap.put("strBase64",strBase64);
		String fileId = fileServer.uploadBase64(strBase64);

		//获取accessToken
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("accessToken"))
			{
				accessToken = cookies[i].getValue();
			}
		}

		String result = userServer.setHeadPortrait(fileId,accessToken);
		return	result;
	}


	// 获取头像
//	@RequestMapping("/getHeadPortrait.do")
//	public void getHeadPortrait(String id) throws Exception {
//		//跳转视图
//
//		 //headPortrait.getHeadPortrait(request, response);
//
//	}

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
