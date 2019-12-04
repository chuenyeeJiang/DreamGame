package com.chuenyee.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileServer {

	public UUID upload(String stringBase64File, String format);

	public UUID upload(MultipartFile multipartFile, String format);
}

