package com.chuenyee.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class LoginServer {
	public void remenberName(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie("username", username);
		response.addCookie(cookie);
	}

	public void forgetName(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("username")) {
					cookies[i].setValue(null);
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
	}

}
