package com.chuenyee.service.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;



public interface HeadPortrait {
	// 上传头像
	public BufferedImage tailoringToBufferedImage(HttpServletRequest request,
								   HttpServletResponse response, MultipartFile file, String x, String y, String w, String h, String previewW, String previewH) throws Exception;

	public String tailoringToBase64(HttpServletRequest request,
												  HttpServletResponse response, MultipartFile file, String x, String y, String w, String h, String previewW, String previewH) throws Exception;

}
