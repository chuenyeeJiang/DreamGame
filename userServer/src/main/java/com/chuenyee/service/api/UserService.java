package com.chuenyee.service.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.pojo.User;


public interface UserService {
	/* void insert(User user) throws Exception; */

	/* boolean deleteUser(int userid) throws Exception; */

	// 用戶信息修改
	boolean updateUser(User user) throws Exception;

	ModelAndView tabQuery(HttpServletRequest request,HttpServletResponse response,Integer pn,String reView);
	  
	/* User findUserById(int userid) throws Exception; */

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
	User getUser(String username) throws Exception;

	
	//判断是否已经登陆
	boolean isLog(HttpServletRequest request,HttpServletResponse response) throws Exception;
	

	// 用户列表
	List<User> getUsers() throws Exception;
	
	//redis获取用户名
	public String getUsernameByRedis(String accessToken)  throws Exception;
	
	//获取用户 头像
	public String getHeadPortrait(String name) throws Exception;
	
	//存储用户 头像
	public Boolean saveHeadPortrait(User user) throws Exception;

	//获取用户名Id
	public String getIdByUsernmae(String username ) throws  Exception;
}
