package com.chuenyee.service.api;


import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ValidateBlockRange { 
	//开始
	public void start(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
    //初始化
	public void init(HttpServletRequest request);

	//获取缺口背景
	public void getValidateBack(HttpServletRequest request,
		HttpServletResponse response) throws Exception;

	//获取滑块
	public void getValidateBlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	//获取拼图左边X位置
	public void getValidatePositionX(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	//获取原图
    public void getOriImage(HttpServletRequest request,
				HttpServletResponse response) throws Exception;


	/*
	 * 二维数组定位轮廓
	 * ------------------------------------------------------------------
	 * --------
	 */
	public Map getBlockData(int imgWidth, int imgHeight, int r, int l);

	/*
	 * 图片加工
	 * ----------------------------------------------------------------------
	 * ----
	 */
	public List<BufferedImage> cutByTemplate(BufferedImage oriImage,
			Map blockDataResult);


}
