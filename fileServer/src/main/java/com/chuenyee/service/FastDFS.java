package com.chuenyee.service;

import com.chuenyee.service.api.FastDFSServer;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FastDFS implements FastDFSServer {
    private Logger logger = LoggerFactory.getLogger(FastDFS.class);
    public String upload(String local_filename) {
        try {
            logger.debug("local_filename: " + local_filename);
            System.out.println("local_filename=" +local_filename);
            ClientGlobal.initByProperties("FastDFS.properties");
          //  System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
           // System.out.println("charset=" + ClientGlobal.g_charset);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", local_filename);
            String fileId = client.upload_file1(local_filename, "txt", metaList);
            System.out.println("upload success. file id is: " + fileId);
            trackerServer.close();
            return fileId;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public FileInfo query(String fileId) {
        try {
            ClientGlobal.initByProperties("FastDFS.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            FileInfo fileInfo = client.query_file_info1(fileId);

            System.out.println("query success.file info is: " + fileInfo);

            NameValuePair[] metadata1 = client.get_metadata1("group1/M00/00/00/rBN54Fy8E8KAMvEpAAAAAeXVvg0457.txt");
            System.out.println("query success.metadata1 name is: " + metadata1[0].getName()+" value:"+metadata1[0].getValue());

            trackerServer.close();
            return fileInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void download() {
        try {
            ClientGlobal.initByProperties("FastDFS.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            FileInfo fileInfo = client.query_file_info1("group1/M00/00/00/rBN54Fy8E8KAMvEpAAAAAeXVvg0457.txt");

            System.out.println("query success.file info is: " + fileInfo);
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
