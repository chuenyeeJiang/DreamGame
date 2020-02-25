package com.chuenyee.service.api;


import com.chuenyee.service.FileFallbackServer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(name="fileServer",fallback= FileFallbackServer.class)
public interface FileServer {
	@RequestMapping(value="/file/upload.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST,produces ="text/html,charset=utf-8" )
	public String upload(@RequestParam("localFile") MultipartFile localFile) ;

	@RequestMapping(value="/file/uploadBase64.do",method=RequestMethod.POST )
	public String uploadBase64(@RequestParam("strBase64") String strBase64) ;
}
