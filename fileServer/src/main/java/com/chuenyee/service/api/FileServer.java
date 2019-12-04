package com.chuenyee.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.UUID;

public interface FileServer {

	public UUID upload(String stringBase64File, String format);

	public String upload(MultipartFile multipartFile, String format);

	public BufferedImage getIMG(String fileId);
}

