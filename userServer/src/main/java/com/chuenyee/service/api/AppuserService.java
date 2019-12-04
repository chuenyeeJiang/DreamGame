package com.chuenyee.service.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.pojo.Appuser;


public interface AppuserService {
	/* void insert(Appuser appuser) throws Exception; */

	/* boolean deleteAppuser(int appuserid) throws Exception; */

	// 用戶信息修改
	boolean updateAppuser(Appuser appuser) throws Exception;

	ModelAndView tabQuery(HttpServletRequest request,HttpServletResponse response,Integer pn,String reView);
	  
	/* Appuser findAppuserById(int appuserid) throws Exception; */

	// 登录
	/* String login(String username) throws Exception; */
	// 登录
	ModelAndView login(Map<String,Object> request_map,String loginView,String reView) throws Exception;

	// 注销
	ModelAndView logout(String accessToken,String loginView);

	// 注册
	ModelAndView regis(Map<String,Object> request_map,String requestView,String reView)
			throws Exception;

	// 验证用户是否存在,并获取用户
	Appuser getUser(String username) throws Exception;

	
	//判断是否已经登陆
	boolean isLog(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	//获取用户权限
	int getGrade(String username) throws Exception;
	
	// 用户列表
	List<Appuser> getUsers() throws Exception;
	
	//redis获取用户名
	public String getusernameByRedis(String accessToken)  throws Exception;
	
	//获取用户 头像
	public String getheadPortrait(String username) throws Exception;
	
	//存储用户 头像
	public Boolean saveheadPortrait(Appuser appuser) throws Exception;
}
