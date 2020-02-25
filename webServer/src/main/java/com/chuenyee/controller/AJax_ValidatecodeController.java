package com.chuenyee.controller;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chuenyee.service.api.Validatecode;



@CrossOrigin
@Controller
public class AJax_ValidatecodeController {
	/*final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	String code = "";*/
	@Autowired
	Validatecode validatecode;
	
	@RequestMapping("/aJax_validatecode.do")
	public void validatecode(final HttpServletRequest request,
			final HttpServletResponse response)
			throws Exception {
//		Thread t = new Thread(new FutureTask(new Callable<V>() {
//			@Override
//			public V call() throws Exception {
//				// TODO Auto-generated method stub
//				
//				return validatecode.start(request, response);
//			}
//		}));
		validatecode.start(request, response);
	}


}


