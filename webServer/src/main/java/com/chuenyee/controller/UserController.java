package com.chuenyee.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chuenyee.service.api.Validatecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.chuenyee.service.LoginServer;
import com.chuenyee.service.api.UserServer;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@Controller
public class UserController extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserServer userServer;
	
	@Autowired
	LoginServer loginServer;

    @Autowired
    Validatecode validatecode;
	
	// redis
		@Autowired
		private RedisTemplate<String, Object> redisTemplate;

	@RequestMapping("/web/hello/{arg}")
	public String Hello(@PathVariable("arg") String arg) {
		logger.info(arg);
		System.out.println("====================arg:" + arg);
		// return restTemplate.getForObject("", responseType)
		return userServer.Hello(arg);
	}

	@RequestMapping(value="/loginCl",method=RequestMethod.POST)
	public String loginCl(HttpServletRequest request,
			HttpServletResponse response,
		    @RequestParam  Map<String,Object> request_map,Map<String,Object> map,String str_validatecode) throws UnsupportedEncodingException {
		// return restTemplate.getForObject("", responseType)
		logger.debug("web UserController=====================loginCl");
		
		//打包请求
		logger.debug("request_map.keySet():"+request_map.keySet());

		//验证码验证
        validatecode.verify(str_validatecode);


		//记住用户
		if(request_map.get("remenberUsername")!=null){
		loginServer.remenberName(
				response,
				(String)request_map.get("username")
				);
		}else {
			loginServer.forgetName(request,response);
		}
		
		long past = System.currentTimeMillis();
		Map<String, Object> response_map = userServer.loginCl(request_map);
		String accessToken = null;
		if(response_map!=null)
		if(response_map.get("response_param")!=null){
		map.putAll((Map<String, Object>)response_map.get("response_param"));
		accessToken = (String) ((Map<String, Object>) response_map.get("response_param")).get("accessToken");
		}
		Cookie cookie = new Cookie("accessToken", accessToken);
		response.addCookie(cookie);
		
		System.out.println("set accessToken:"+accessToken);
		logger.debug("set accessToken:"+accessToken);
		
		logger.debug("map.keySet():"+map.keySet());
		return	"redirect:http://www.chuenyee.com/"+(String) response_map.get("view")+".html";
	}
	
	@RequestMapping(value="/regis.do",method=RequestMethod.POST)
	public String Regis(HttpServletRequest request,
			HttpServletResponse response,
		    @RequestParam  Map<String,Object> request_map,Map<String,Object> map) throws UnsupportedEncodingException {
		// return restTemplate.getForObject("", responseType)
		logger.info("web UserController=====================regis");
		
		/*
		 * 打包请求
		 */
		logger.debug("request_map.keySet():"+request_map.keySet());
		
		
		/*
		 * 请求服务
		 */
		Map<String, Object> response_map = userServer.regis(request_map);
		
		/*
		 * 响应信息
		 */
		
		//登陆令牌
		String accessToken = null;
		if(response_map!=null)
		if(response_map.get("response_param")!=null){
	    //信息返回
		map.putAll((Map<String, Object>) response_map.get("response_param"));
		// sso登陆令牌
		accessToken = (String) ((Map<String, Object>) response_map.get("response_param")).get("accessToken");
		}
		Cookie cookie = new Cookie("accessToken", accessToken);
		response.addCookie(cookie);
		

		logger.debug("set accessToken:"+accessToken);
		
		logger.debug("map.keySet():"+map.keySet());
		return	"redirect:http://www.chuenyee.com/login/success.html";
	}

	@RequestMapping(value="/logOut.do")
	public String logOut(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map)
	{
		/*
		   获取令牌
		 */
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("accessToken"))
			{
				cookies[i].setValue(null);
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
		Map<String, Object> response_map = userServer.logOut(accessToken);

		if(response_map!=null){
			if(response_map.get("response_param")!=null){
				map.putAll((Map<String, Object>)response_map.get("response_param"));
			}
		}
		//return	(String) response_map.get("view");
		return	"redirect:http://www.chuenyee.com/"+(String) response_map.get("view")+".html";
	}
	
	
	@RequestMapping("/getUsername.do")
	public  String getusername(HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws IOException {
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
        if(accessToken==null||accessToken==""){
		return null;
    	}else{
    		PrintWriter pw = response.getWriter();
    		logger.info("getUsername.do================get pw");
    		username = userServer.getUsername(accessToken);
            pw.print(username);
        	logger.info("getUsername.do================pw.print");
            pw.flush();
        	logger.info("getUsername.do================pw.flush");
            pw.close();
    		return null;
    	}
	}

	@ResponseBody
    @RequestMapping("/getHeadPortrait.do")
    public String getheadPortrait(HttpServletRequest request) {
        //获取accessToken
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        for(int i=0;i<cookies.length;i++)
        {
            if(cookies[i].getName().equals("accessToken"))
            {
                accessToken = cookies[i].getValue();
            }
        }
        // return restTemplate.getForObject("", responseType)
        return userServer.getHeadPortrait(accessToken);
    }
//	@RequestMapping("/getUsername.do")
//	public String getusername(String accessToken)
//	{
//		logger.info("getUsername.do===============run");
//		logger.info("get accessToken:" + accessToken);
//		String username = null;
//		if (accessToken == null || accessToken == "") {
//			return null;
//		} else {
//			username = userServer.getUsername(accessToken);
//		}
//		return username;
//	}

//	@RequestMapping("/updateHeadPortrait.do")
//	public Map<String, Object> uploadHeadPortrait(String fileId ){
//		logger.info("uploadHeadPortrait.do===============run");
//		return  userServer.setheadPortrait(fileId);
//	}
}
