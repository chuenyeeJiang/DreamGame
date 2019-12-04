package com.chuenyee.service.api;

import org.csource.fastdfs.*;

public interface FastDFSServer {


	public String upload(String local_filename);

	public FileInfo query(String fileId);

	//public void download();
}
