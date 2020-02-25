package com.chuenyee.service.api;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Validatecode {
	public void start(HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	// 初始化
	public void init();

	// 生成4个随机字码
	public String generateCode();

	// 背景上色
	public void drawBackgroundColor();

	// 线条干扰
	public void drawLines();

	// 噪点干扰
	public void drawNoise();

	// 字码抖斜干扰,画字码
	public void drawCode();

	// 输出
	public void outputImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/*
	 * ----------------------------TOOD---------------------------------------
	 */
	// 随机浅色
	Color getRandColor(int fc, int bc);

	// 随机深色
	int getRandomIntColor();

	// 随机色
	int[] getRandomRgb();

	/*
	* -----------------------------验证---------------------------------------
	* */
//	public boolean verificationcodeVerification(){
//
//	}
	public Map<String,Object> verify(String validatecode);

}
