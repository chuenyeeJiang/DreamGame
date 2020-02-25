package com.chuenyee.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.chuenyee.service.api.ValidateBlockRange;


@Service
public class ValidateBlockRangeImpl implements ValidateBlockRange {
	List<BufferedImage> list;
	Map blockDataResult;
	BufferedImage oriImage;
	static Logger logger = LoggerFactory.getLogger(ValidateBlockRangeImpl.class);
	

	static String path  ;
	



	static{
	/*	try {
	
			path =	new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
			logger.info("path :"+path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@CrossOrigin
	public void start(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		init(request);

		PrintWriter pw = response.getWriter();
		pw.println(blockDataResult.get("y"));
		pw.flush();
		pw.close();
	}

	public void init(HttpServletRequest request) {
		// TODO Auto-generated method stub
		int randImgNum = new Random().nextInt(8) + 1;
		System.out.println("randImgNum:" + randImgNum);
		// 随机原图片路径
		/*path=path.replaceAll("\\\\", "//");
		String imgPath = path+"/static/img/randimg" + randImgNum + ".jpg";
		try {
			imgPath = URLDecoder.decode(imgPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("ValidateBlockRangeImpl======imgPath: "+imgPath);*/
		
		// 图片大小参考 前台div容器扣去右下边框宽度值
		/*cmd
		 非流形式获取图片
		ImageIcon imgeIcon = new ImageIcon(imgPath);
		*/
		/*
		 * 流形式
		 */
		int i;
		logger.info("ValidateBlockRangeImpl======this.getClass(): "+this.getClass()+"\n");
		InputStream iso = this.getClass().getResourceAsStream("/static/img/randimg" + randImgNum + ".jpg");
		logger.info("iso:"+iso+"\n");
	    byte[] bytes = new byte[900000];
	    try {
	    	while((i=iso.read(bytes))!=-1){
	    	System.out.println(i);
	    	}
			logger.info("bytes:"+bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon imgeIcon = new ImageIcon(bytes);
		
	/*	工具获取
	 * Toolkit toolKit = Toolkit.getDefaultToolkit();
		Image image = toolKit.createImage(this.getClass().getResource("classpath*:/static/img/randimg" + randImgNum + ".jpg"));
		ImageIcon imgeIcon = new ImageIcon(image);
		*/
		// 获取图片长宽
		int targetLength = imgeIcon.getIconWidth();
		int targetWidth = imgeIcon.getIconHeight();
		logger.info("ValidateBlockRangeImpl======targetLength: "+targetLength+",targetWidth: "+targetWidth+"\n");
		
		// 加载原图片
		oriImage = new BufferedImage(targetLength, targetWidth,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = oriImage.getGraphics();
		g2.drawImage(imgeIcon.getImage(), 0, 0, null);

		// 加工原图片
		blockDataResult = getBlockData(targetLength, targetWidth, 10, 50);
		list = cutByTemplate(oriImage, blockDataResult);

	}

	public void getValidateBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();

		ImageIO.write(list.get(0), "png", sos);
		/*
		 * os = new FileOutputStream(file); ImageIO.write(image, "jpg", os);
		 */
		System.out.println("ImageIO.write(image, 'jpg', os);\n");
		sos.flush();
		sos.close();
	}

	public void getValidateBlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();

		ImageIO.write(list.get(1), "png", sos);
		/*
		 * os = new FileOutputStream(file); ImageIO.write(image, "jpg", os);
		 */
		System.out.println("ImageIO.write(image, 'jpg', os);\n");
		sos.flush();
		sos.close();
	}

	public void getValidatePositionX(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		// 取整输出
		HttpSession session = request.getSession();
		int BlockX = (int) Math.ceil((Double) blockDataResult.get("BlockX"));
		session.setAttribute("BlockX", BlockX);
		pw.println(BlockX);
		pw.flush();
		pw.close();
	}

	public void getOriImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();

		ImageIO.write(oriImage, "png", sos);
		sos.flush();
		sos.close();
	}

	public Map getBlockData(int imgWidth, int imgHeight, int r, int l) {
		// TODO Auto-generated method stub
		imgWidth = imgWidth - 10;
		imgHeight = imgHeight - 10;

		// 定义二维坐标,1为抠图坐标
		int[][] data = new int[imgWidth][imgHeight];

		// 半径平方
		int r2 = r * r;

		// 定义拼块中心位置
		int x = (int) (Math.round(l / 1.8 + r) + 10 + new Random()
				.nextInt((int) (imgWidth - 2 * (Math.round(l / 1.8 + r) + 10)))), y = (int) (Math
				.round(l / 1.8 + r) + 10 + new Random()
				.nextInt((int) (imgHeight - 2 * (Math.round(l / 1.8 + r) + 10))));

		// 拼块图左边位置
		double BlockX = x - l / 1.8 - r;

		// 定义随机缺块凹或者缺口凸
		int lackOrExcess = 1;
		/* new Random().nextInt(2); */
		int lackOrExcess2 = new Random().nextInt(2);

		// 定义凹凸偏移
		int Xoffset = -1;
		/* (int) Math.pow(-1, new Random().nextInt(2)); */
		int Yoffset = (int) Math.pow(-1, new Random().nextInt(2));

		// 定义圆心位置
		double ovalX = x + Xoffset * l / 1.8;
		double ovalY = y + Yoffset * l / 1.8;

		// 随机正方形边位置
		double l_x = x + Xoffset * l / 2;
		double l_y = y + Yoffset * l / 2;

		// 二维定位抠图点
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				double d1 = Math.pow(i - ovalX, 2) + Math.pow(j - y, 2);
				double d2 = Math.pow(i - x, 2) + Math.pow(j - ovalY, 2);
				// 获取拼块面积数组
				if ((i > x - l / 2 && i < x + l / 2)
						&& (j > y - l / 2 && j < y + l / 2)) {
					data[i][j] = 1;
					if ((i < x - l / 2 + 3 || i > x + l / 2 - 3)
							|| (j < y - l / 2 + 3 || j > y + l / 2 - 3)) {
						data[i][j] = 2;
					}

				} else {
					data[i][j] = 0;
				}
				if (d1 < r2) {

					if (lackOrExcess == 0) {
						data[i][j] = 0;
						if (d1 > r2 - 50
								&& ((Xoffset == -1 && i > l_x) || (Xoffset == 1 && i < l_x))) {
							data[i][j] = 2;
						}
					} else {
						data[i][j] = 1;
						if (d1 > r2 - 50
								&& ((Xoffset == -1 && i < l_x) || (Xoffset == 1 && i > l_x))) {
							data[i][j] = 2;
						}
					}
				}
				if (d2 < r2) {

					if (lackOrExcess2 == 0) {
						data[i][j] = 0;
						// 黄色边框
						if (d2 > r2 - 50
								&& ((Yoffset == -1 && j > l_y) || (Yoffset == 1 && j < l_y))) {

							data[i][j] = 2;
						}
					} else {
						data[i][j] = 1;
						if (d2 > r2 - 50
								&& ((Yoffset == -1 && j < l_y) || (Yoffset == 1 && j > l_y))) {

							data[i][j] = 2;
						}
					}

				}

			}
		}
		Map map = new HashMap();
		map.put("data", data);
		map.put("x", x);
		map.put("y", y);
		map.put("l", l);
		map.put("r", r);
		map.put("BlockX", BlockX);
		return map;
	}

	public List<BufferedImage> cutByTemplate(BufferedImage oriImage,
			Map blockDataResult) {
		// TODO Auto-generated method stub
		// 误差处理
		int w = oriImage.getWidth() - 10;
		int h = oriImage.getHeight() - 10;

		// 获取二维轮廓
		int[][] templateImage = (int[][]) blockDataResult.get("data");

		// 获取位置
		int x = (Integer) blockDataResult.get("x");
		int y = (Integer) blockDataResult.get("y");

		// 获取正方形边长
		int l = (Integer) blockDataResult.get("l");

		// 获取圆半径
		int r = (Integer) blockDataResult.get("r");

		// 调节亮度 非渐变默认值
		int blight = 80;

		// 渐变色亮度偏移
		int blightffset = 0;

		// 剪切-抠图位置偏移
		int Xoffset = x - (int) (Math.round(l / 1.8)) - r;
		int Yoffset = y - (int) (Math.round(l / 1.8)) - r;

		// 剪切块 图片大小
		int b_w = (int) (Math.round(1.2 * l)) + 2 * r + 10;
		int b_h = (int) (Math.round(1.2 * l)) + 2 * r + 10;

		// 结果图 targetImage——缺块背景 blocksImage——剪切块
		BufferedImage targetImage = new BufferedImage(oriImage.getWidth(),
				oriImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage blocksImage = new BufferedImage(b_w, b_h,
				BufferedImage.TYPE_INT_ARGB);

		// 绘制被抠图
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				targetImage.setRGB(i, j, oriImage.getRGB(i, j));

				if (templateImage[i][j] > 0) {
					Color c = new Color(oriImage.getRGB(i, j));

					// 渐变
					/*
					 * blight = Math.abs(j-y)+Math.abs(i-x) - blightffset;
					 */

					// 渐变加强
					blight = 2 * (Math.abs(j - y) + Math.abs(i - x) - blightffset);

					// 调暗
					int red = c.getRed() - blight;
					if (red > 255)
						red = 255;
					if (red < 0)
						red = 0;
					int g = c.getGreen() - blight;
					if (g > 255)
						g = 255;
					if (g < 0)
						g = 0;
					int b = c.getBlue() - blight;
					if (b > 255)
						b = 255;
					if (b < 0)
						b = 0;
					Color new_c = new Color(red, g, b);
					int rgb_ori = new_c.getRGB();

					targetImage.setRGB(i, j, rgb_ori);
					/* blocksImage.setRGB(i, j, oriImage.getRGB(i, j)); */
				}
			}
		}
		int j = 0;
		int i;
		// 剪切块
		for (i = Xoffset; i < Xoffset + b_w - 15; i++) {
			for (j = Yoffset; j < Yoffset + b_h - 15; j++) {
				if (templateImage[i][j] > 0) {
					blocksImage.setRGB(i - Xoffset, j - Yoffset,
							oriImage.getRGB(i, j));
				}
				if (templateImage[i][j] == 2) {
					blocksImage.setRGB(i - Xoffset, j - Yoffset, new Color(255,
							255, 0).getRGB());
				}
			}
		}

		List<BufferedImage> list = new ArrayList<BufferedImage>();
		list.add(targetImage);
		list.add(blocksImage);
		return list;
	}

}
