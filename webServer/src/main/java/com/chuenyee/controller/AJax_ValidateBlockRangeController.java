package com.chuenyee.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chuenyee.service.api.ValidateBlockRange;


@CrossOrigin
@Controller
public class AJax_ValidateBlockRangeController {
	@Autowired
	ValidateBlockRange validateBlockRange ;

	@RequestMapping("/aJax_validateImg.do")
	public void start(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化图片
		validateBlockRange.start(request, response);
	}

	/*
	 * //初始化 public void init(HttpServletRequest request) { int randImgNum = new
	 * Random().nextInt(8)+1; System.out.println("randImgNum:"+randImgNum);
	 * //随机原图片路径 String imgPath =
	 * request.getSession().getServletContext().getRealPath
	 * ("/img/randimg"+randImgNum+".jpg"); // 图片大小参考 前台div容器扣去右下边框宽度值 ImageIcon
	 * imgeIcon = new ImageIcon(imgPath);
	 * 
	 * // 获取图片长宽 int targetLength = imgeIcon.getIconWidth(); int targetWidth =
	 * imgeIcon.getIconHeight();
	 * 
	 * // 加载原图片 oriImage = new BufferedImage(targetLength, targetWidth,
	 * BufferedImage.TYPE_INT_ARGB); Graphics g2 = oriImage.getGraphics();
	 * g2.drawImage(imgeIcon.getImage(), 0, 0, null);
	 * 
	 * // 加工原图片 blockDataResult = getBlockData(targetLength, targetWidth, 10,
	 * 50); list = cutByTemplate(oriImage, blockDataResult);
	 * 
	 * }
	 */

	// 输出缺块背景
	@RequestMapping("/getValidateBack.do")
	public void getValidateBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		validateBlockRange.getValidateBack(request, response);
	}

	// 输出滑块
	@RequestMapping("/getValidateBlock.do")
	public void getValidateBlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		validateBlockRange.getValidateBlock(request, response);
	}

	// 输出拼图左边X位置
	@RequestMapping("/getValidatePositionX.do")
	public void getValidatePositionX(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		validateBlockRange.getValidatePositionX(request, response);
	}

	/*
	 * //验证
	 * 
	 * @RequestMapping("/checkValidate.do") public void
	 * checkValidate(HttpServletRequest request, HttpServletResponse
	 * response,String BlockX) throws Exception { PrintWriter pw =
	 * response.getWriter(); String session_BlockX = (String)
	 * request.getAttribute("BlockX"); if(BlockX.equals(session_BlockX)) {
	 * pw.println("1"); } else { pw.println("0"); }
	 * 
	 * }
	 */

	// 输出原图片
	@RequestMapping("/getOriImage.do")
	public void getOriImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		validateBlockRange.getOriImage(request, response);
	}

/*	
	 * 二维数组定位轮廓
	 * ------------------------------------------------------------------
	 * --------
	 
	public Map getBlockData(int imgWidth, int imgHeight, int r, int l) {
		return validateBlockRange.getBlockData(imgWidth, imgHeight, r, l);
	}

	
	 * 图片加工
	 * ----------------------------------------------------------------------
	 * ----
	 
	public List<BufferedImage> cutByTemplate(BufferedImage oriImage,
			Map blockDataResult) {
		return validateBlockRange.cutByTemplate(oriImage, blockDataResult);
	}*/

}
