package com.chuenyee.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chuenyee.tool.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.chuenyee.service.api.Validatecode;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Service
public class ValidatecodeImpl implements Validatecode{
	//日志
	Logger logger = LoggerFactory.getLogger(ValidatecodeImpl.class);
	
	// 验证码字码范围
	final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

	// 4字码
	String code = "";

	// 验证码图片
	BufferedImage image;

	// 画笔
	Graphics2D g2;

	// 宽度
	int w = 130;

	// 高度
	int h = 40;

	// 随机数
	Random rand = new Random();
	Random random = new Random();

	// 字码数据
	char[] chars = { ' ', ' ', ' ', ' ' };
	
	// redis
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// 开始
	public void start(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		init();
		generateCode();
		drawBackgroundColor();
		drawLines();
		drawNoise();
		drawCode();
		
		/*
		 * 存入redis
		 */
		redisTemplate.opsForValue().set("validatecode", code);
		logger.debug("validatecode:"+code);
		
		outputImage(request,response);
	
	}

	// 初始化
	public void init() {
		/*
		 * 生成验证码图片
		 */
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		g2 = image.createGraphics();

		// 底色（边框上色）
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, w, h);
	}

	// 生成4个随机字码
	public String generateCode() {
		/*
		 * 生成4个随机码
		 */
		code = "";
		for (int i = 0; i < 4; i++) {
			chars[i] = VERIFY_CODES
					.charAt(rand.nextInt(VERIFY_CODES.length() - 1));
			code += chars[i];
		}

		return code;
	}

	// 背景上色
	public void drawBackgroundColor() {
		// 随机浅色调背景色
		Color c = getRandColor(200, 250);
		g2.setColor(c);
		g2.fillRect(0, 2, w, h - 4);
	}

	// 线条干扰
	public void drawLines() {
		// 线条干扰
		g2.setColor(getRandColor(160, 200));
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(w - 1);
			int y = random.nextInt(h - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g2.drawLine(x, y, x + xl + 40, y + yl + 20);
		}
	}

	// 噪点干扰
	public void drawNoise() {
		// 噪点干扰
		float yawpRate = 0.05f;
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}
	}

	// 字码抖斜干扰,画字码
	public void drawCode() {
		// 画字码
		g2.setColor(getRandColor(100, 160));
		int fontSize = h - 4;
		Font font = new Font("Algerian", Font.ITALIC, fontSize);
		g2.setFont(font);
		for (int i = 0; i < 4; i++) {
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(
					Math.PI / 4 * rand.nextDouble()
							* (rand.nextBoolean() ? 1 : -1), (w / 4) * i
							+ fontSize / 2, h / 2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1, ((w - 10) / 4) * i + 5, h / 2 + fontSize
					/ 2 - 10);
		}
	}

	// 输出
	public void outputImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 输出
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "jpeg", sos);
		sos.flush();
		sos.close();
	}
/*
 * ----------------------------TOOD---------------------------------------
 */
	//随机浅色
	  public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	//随机深色
	public int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}
    
	//随机色
	public int[] getRandomRgb() {
		Random random = new Random();
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}


	//验证
	public Map<String,Object> verify(String validatecode){
        Map<String,Object> map = new HashMap<String,Object>();

		// 获取正确验证码
		String str_validatecode = (String) redisTemplate.opsForValue().get("validatecode");
		Verification verification = new Verification();
		//验证
			if(verification.isNull(validatecode)){
                map.put("log_error", "验证码不能为空!");
				logger.debug("验证码不能为空!");
				return map;
			}

			if(!verification.toLowerCase(str_validatecode, validatecode)){
                map.put("log_error", "验证码错误!");
				logger.debug("验证码错误!");
				return map;
			}

		return map;
	}
}
