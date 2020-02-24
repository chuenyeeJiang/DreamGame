package com.chuenyee.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.chuenyee.pojo.User;
import com.chuenyee.service.api.FileService;
import com.chuenyee.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class UserLoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	private Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@CrossOrigin
	@RequestMapping(value = "/loginCl",method = RequestMethod.POST)
	public Map<String, Object> userLog(
			@RequestParam Map<String, Object> request_map, HttpServletResponse response) throws Exception {
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
		ModelAndView mv = userService.login(request_map, loginView, reView);

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

        Cookie cookie = new Cookie("accessToken", (String) mv.getModelMap().get("accessToken"));
        response.addCookie(cookie);
		return response_map;
	}

	// @RequestMapping(value="/login")
	// public String GotoLogin(){
	// return "login";
	// }

	@CrossOrigin
	@RequestMapping(value = "/getUsername.do")
	public String getUsername(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
			throws Exception {
		String accessToken = null;
		logger.info("getUsername.do================run");
        accessToken = getAccessToken(request,response,map);
        logger.info("accessToken:"+accessToken);
		String username = userService.getUsernameByRedis(accessToken);
		logger.info("getUsername.do================get username");
		return username;
	}

	@CrossOrigin
	@RequestMapping(value = "/getUsername.do",method = RequestMethod.GET)
	public String getUsername2(String accessToken)
			throws Exception {
		logger.info("getUsername.do================run");
		logger.info("accessToken:"+accessToken);
		String username = userService.getUsernameByRedis(accessToken);
		logger.info("getUsername.do================get username");
		return username;
	}

	public String getAccessToken(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws IOException {
		logger.info("getusername");
		// return restTemplate.getForObject("", responseType)
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		String username = null;
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("accessToken"))
			{
				accessToken = cookies[i].getValue();
			}
		}
		logger.info("get accessToken:"+accessToken);
	    return accessToken;

	}

	@CrossOrigin
	@RequestMapping(value = "/hello")
	public String Hello(@RequestParam("arg") String arg) {
		return arg;
	}

	@CrossOrigin
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
		ModelAndView mv = userService.logout(accessToken,loginView);

		/*
		 * 打包返回信息
		 */
		Map<String, Object> response_map = new HashMap<String, Object>();
		response_map.put("view", loginView);
		response_map.put("response_param", mv.getModelMap());

		return response_map;
	}


	@CrossOrigin
	@RequestMapping(value = "/regis.do",method = RequestMethod.POST)
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
		String reView = "/index";
		String requestView = "/regis";

		/*
		 * 请求服务
		 */
		ModelAndView mv = userService
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

	@CrossOrigin
	@RequestMapping("/setHeadPortrait.do")
	public String setHeadPortrait(String fileId,String accessToken){
		try {
		User user = new User();
		user.setHeadportrait(fileId);
		String username = userService.getUsernameByRedis(accessToken);
		String id = userService.getIdByUsernmae(username);
		user.setId(id);
		userService.saveHeadPortrait(user);
		} catch (Exception e) {
			return  "更新失败,错误：" + e.getMessage();
		}
		return "更新成功";
	}

	@CrossOrigin
	@RequestMapping("/getHeadPortrait.do")
	public String getHeadPortrait(String accessToken){
		String username = null;
		try {
			username = userService.getUsernameByRedis(accessToken);
			return userService.getHeadPortrait(username);
		} catch (Exception e) {
			return null;
		}

	}



}