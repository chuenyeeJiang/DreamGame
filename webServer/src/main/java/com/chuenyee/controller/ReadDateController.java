package com.chuenyee.controller;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@CrossOrigin
@Controller
public class ReadDateController {
	
	  Logger logger = LoggerFactory.getLogger(ReadDateController.class);
	
	  @RequestMapping("/hasLogin.do")
		public String hasLogin(HttpServletRequest request,HttpServletResponse response) throws Exception
		{
//		  System.out.println("/hasLogin.do");
//		    ModelAndView mv = new ModelAndView();
//		    
//	   boolean isLog = appuserService.isLog(request, response);
//		    
//			if(isLog==true)
//			{
//				mv.setViewName("WEB-INF/page/index.jsp");
//				return "index";
//			}
//			PrintWriter pw = response.getWriter();
//	        pw.print(1);
//	        pw.flush();
//	        pw.close();
		
			return null;
		}
	
    @RequestMapping("/readUsername.do")
	public void readUsername(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
    	logger.info("读取用户名！");
    	Cookie[] cookies = request.getCookies();
    	PrintWriter pw = response.getWriter();
    	logger.debug("request:"+request);
    	String username = null;
    	if(cookies!=null)
    	for(int i=0;i<cookies.length;i++)
    	{
    		if(cookies[i].getName().equals("username"))
    		{
    			logger.debug("getUsername");
    			username = cookies[i].getValue();
    		}
    	}
    	if(username!=null||username!=""){
		pw.print(username);
		pw.flush();
		pw.close();
    	}
	}
//    @RequestMapping("/readPassword.do")
//	public void readPassword(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{
//    	Cookie[] cookies = request.getCookies();
//    	PrintWriter pw = response.getWriter();
//    	
//    	for(int i=0;i<cookies.length;i++)
//    	{
//    		if(cookies[i].getName().equals("password"))
//    		{
//    			pw.print(cookies[i].getValue());
//    		}
//    	}
//    
//	}

    
}
