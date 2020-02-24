package com.chuenyee.service.api;

import com.chuenyee.service.FileFallbackService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@FeignClient(name = "fileServer",fallback = FileFallbackService.class)
public interface FileService {


	//上传文件
	@RequestMapping(value="/file/upload.do",consumes= MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST,produces ="application/json,charset=utf-8")
	public String  upload(@RequestParam Map<String,Object> request_map) throws Exception;

	@RequestMapping(value="/file/uploadBase64.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST,produces ="application/json,charset=utf-8" )
	public String uploadBase64(@RequestParam Map<String,Object> request_map) ;
}
