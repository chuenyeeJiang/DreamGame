package com.chuenyee.controller;


import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.service.api.AppuserService;

@RestController
public class AppuserLoginController {

	@Autowired
	private AppuserService appuserService;

	private Logger logger = LoggerFactory.getLogger(AppuserLoginController.class);

	@RequestMapping(value = "/loginCl")
	public Map<String, Object> userLog(
			@RequestParam Map<String, Object> request_map) throws Exception {
		logger.info("================登录验证");

		// 跳转页面
		/* reView = "WEB-INF/page/index.jsp"; */
		/*
		 * 指定跳转视图
		 */
		String loginView = "login";
		String reView = "index";

		/*
		 * 调用登陆验证
		 */
		ModelAndView mv = appuserService.login(request_map, loginView, reView);

		logger.info("reView: " + mv.getViewName());

		/*
		 * UTF-8转码
		 */
		reView = new String(mv.getViewName().getBytes("ISO-8859-1"), "UTF-8");
		logger.debug("=====reView: " + reView);

		/*
		 * 打包返回信息
		 */
		Map<String, Object> response_map = new HashMap<String, Object>();
		response_map.put("view", reView);
		response_map.put("response_param", mv.getModelMap());

		return response_map;
	}

	// @RequestMapping(value="/login")
	// public String GotoLogin(){
	// return "login";
	// }

	@RequestMapping(value = "/getUsername.do")
	public String getUsername(@RequestParam String accessToken)
			throws Exception {
		logger.info("getUsername.do================run");
		String username = appuserService.getusernameByRedis(accessToken);
		logger.info("getUsername.do================get username");
		return username;
	}

	@RequestMapping(value = "/hello")
	public String Hello(@RequestParam("arg") String arg) {
		return arg;
	}

	@RequestMapping("/logOut.do")
	public Map<String, Object> userLogout(@RequestParam String accessToken)  {
		logger.info("=====================注销");
		/*
		   定义视图
		 */
		String loginView = "login";

		/*
		 * 调用服务
		 */
		ModelAndView mv = appuserService.logout(accessToken,loginView);

		/*
		 * 打包返回信息
		 */
		Map<String, Object> response_map = new HashMap<String, Object>();
		response_map.put("view", loginView);
		response_map.put("response_param", mv.getModelMap());

		return response_map;
	}

	@RequestMapping("/Regis.do")
	public Map<String, Object> userRegis(
			@RequestParam Map<String, Object> request_map) throws Exception {
		/*
		 * UTF-8转码
		 */
		String regisUsername = null;
		regisUsername = (String) request_map.get("regisUsername");
		regisUsername = new String(regisUsername.getBytes("ISO-8859-1"),
				"UTF-8");

		/*
		 * 指定跳转视图
		 */
		String reView = "success";
		String requestView = "regis";

		/*
		 * 请求服务
		 */
		ModelAndView mv = appuserService
				.regis(request_map, requestView, reView);

		/*
		 * UTF-8转码
		 */
		reView = new String(mv.getViewName().getBytes("ISO-8859-1"), "UTF-8");
		logger.debug("=====reView: " + reView);

		/*
		 * 打包返回信息
		 */
		Map<String, Object> response_map = new HashMap<String, Object>();
		response_map.put("view", reView);
		response_map.put("response_param", mv.getModelMap());
		return response_map;
	}
	

}