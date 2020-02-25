package com.chuenyee.service.api;


import java.util.Map;

import com.chuenyee.service.UserFallbackService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//,fallback=FallbackService.class
@FeignClient(name="userServer")
public interface UserServer {
	
	@RequestMapping(value="/user/hello",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	String Hello(@RequestParam("arg") String arg);

	@RequestMapping(value="/user/loginCl",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST)
	public Map<String, Object> loginCl(@RequestParam Map<String,Object> request_map) ;
	
	@RequestMapping(value="/user/regis.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public Map<String, Object> regis(@RequestParam Map<String,Object> request_map) ;
	
	@RequestMapping(value="/user/getUsername.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public String getUsername(@RequestParam("accessToken") String accessToken) ;

	@RequestMapping(value="/user/logOut.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public Map<String, Object> logOut(@RequestParam("accessToken") String accessToken) ;

	@RequestMapping(value="/user/setHeadPortrait.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public String setHeadPortrait(@RequestParam("fileId") String fileId,@RequestParam("accessToken") String accessToken) ;

	@RequestMapping(value="/user/getHeadPortrait.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public String getHeadPortrait(@RequestParam("accessToken") String accessToken) ;


}
