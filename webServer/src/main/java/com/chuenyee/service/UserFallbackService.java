package com.chuenyee.service;

import java.util.HashMap;
import java.util.Map;

import com.chuenyee.service.api.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;



@Component
public class UserFallbackService implements UserServer {

	Logger logger = LoggerFactory.getLogger(UserFallbackService.class);



	@Override
	public String Hello(@RequestParam("arg") String arg) {
		// TODO Auto-generated method stub
		return "error";
	}

	@Override
	public Map<String, Object> loginCl(Map<String,Object> request_map) {
		// TODO Auto-generated method stub
		logger.info("userService Fallback!");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> response_param = new HashMap<String, Object>();
		response_param.put("log_error", "系统正在维护中...");
		map.put("response_param", response_param);
		map.put("view", "login");
		return map;
	}

	@Override
	public Map<String, Object> regis(Map<String, Object> request_map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername(String accessToken) {
		// TODO Auto-generated method stub
		return "用户名获取失败！";
	}

	@Override
	public Map<String, Object> logOut(String accessToken) {
		return null;
	}

	@Override
	public String setHeadPortrait(String fileId, String accessToken) {
		return "用户系统正在维护...";
	}

	@Override
	public String getHeadPortrait(String accessToken) {
		return null;
	}




}
