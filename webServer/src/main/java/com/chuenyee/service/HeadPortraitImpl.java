package com.chuenyee.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chuenyee.service.api.HeadPortrait;


@Service
public class HeadPortraitImpl implements HeadPortrait {
	//日志文件
	Logger logger = LoggerFactory.getLogger(HeadPortraitImpl.class);
	// 上传头像
	public BufferedImage  tailoringToBufferedImage(HttpServletRequest request,
			HttpServletResponse response, MultipartFile local_file, String str_x,
			String str_y, String str_w, String str_h, String str_previewW,
			String str_previewH) throws Exception {
		logger.debug("===tailoringToBufferedImage====");
		response.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		logger.debug("2");
//		PrintWriter pw = response.getWriter();
        BufferedImage orgImg = ImageIO.read(local_file.getInputStream());
		// 参数转整
		int x = Integer.parseInt(str_x);
		int y = Integer.parseInt(str_y);
		int w = Integer.parseInt(str_w);
		int h = Integer.parseInt(str_h);
		int previewW = Integer.parseInt(str_previewW);
		int previewH = Integer.parseInt(str_previewH);
		logger.debug("x："+x+"y:"+y+"w:"+w+"h:"+h+"previewW:"+previewW+"previewH:"+previewH);

		System.out.println("bufferedImage.getWidth()" + orgImg.getWidth());
		System.out.println("bufferedImage.getHeight()" + orgImg.getHeight());
		// 原图放大
		x = x * orgImg.getWidth() / previewW;

		y = y * orgImg.getHeight() / previewH;

		w = w * orgImg.getWidth() / previewW;

		h = h * orgImg.getHeight() / previewH;


		/*
		 * 截图
		 */
		BufferedImage bufferedImage = new BufferedImage(w, h,
				BufferedImage.TYPE_4BYTE_ABGR);

		int imgWidth = orgImg.getWidth();
		int imgHeight = orgImg.getHeight();
		int r = w / 2;
		int r2 = r * r;
		// 二维定位抠图点
		int[][] data = new int[imgWidth][imgHeight];
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				double d1 = Math.pow(i - (x + w / 2), 2)
						+ Math.pow(j - (y + h / 2), 2);
			/*	// 获取拼块面积数组
				if (d1 < r2) {
					data[i][j] = 1;
				} else {
					data[i][j] = 0;
				}*/
				data[i][j] = 1;
			}
		}

		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				if (data[i][j] == 1) {
					bufferedImage.setRGB(i - x, j - y, orgImg.getRGB(i, j));
				}
			}
		}

		// 输出文件
//		FileOutputStream outfile = new FileOutputStream(path + "/" + username
//				+ "/HeadPortrait/" + date + ".jpg");
//		ImageOutputStream i = ImageIO.createImageOutputStream(outfile);
//
//		ImageIO.write(bufferedImage, "png", i);
//
//		System.out.println("ImageIO.write(image, 'jpg', os);");
//
//		i.flush();
//		i.close();
//		outfile.flush();
//		outfile.close();
//
//		pw.write("更新成功!");
        return bufferedImage;
		/*
		 * request.getRequestDispatcher("forward:/"+reView).forward(request,
		 * response);
		 */
	}
    public String  tailoringToBase64(HttpServletRequest request,
                                             HttpServletResponse response, MultipartFile local_file, String str_x,
                                             String str_y, String str_w, String str_h, String str_previewW,
                                             String str_previewH) throws Exception {
		logger.debug("tailoringToBase64");
	    BufferedImage bufferedImage  = tailoringToBufferedImage(request,response,local_file,str_x,str_y,str_w,str_h,str_previewW,str_previewH);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayInputStream);
        byte[] local_fileBytes = byteArrayInputStream.toByteArray();
        logger.debug("byte:"+local_fileBytes);
        String str_bytefile = base64Encode(local_fileBytes);
        logger.debug("base64:"+str_bytefile);

        return str_bytefile;
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
