package com.chuenyee.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.chuenyee.service.api.Encryption;


public class JcParseMD5 implements Encryption{ 
	  
	  /**
	   * @param str 
	   * @return  
	   * @Description: 32λСдMD5 
	   */
	  public String parseStrToMd5L32(String str){ 
	    String reStr = null;
	    try{
	      MessageDigest md5 = MessageDigest.getInstance("MD5");
	      byte[] bytes = md5.digest(str.getBytes()); 
	      StringBuffer stringBuffer = new StringBuffer();
	      for(byte b : bytes){ 
	        int bt = b & 0xff;
	        if(bt < 16){
	          stringBuffer.append(0);
	        } 
	        stringBuffer.append(Integer.toHexString(bt));
	      }
	      reStr = stringBuffer.toString(); 
	    }catch(NoSuchAlgorithmException e) { 
	      e.printStackTrace();
	    }
	    return reStr;
	  }
}