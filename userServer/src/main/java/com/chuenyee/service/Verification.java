package com.chuenyee.service;

public class Verification {

	public boolean compare(String str1,String str2){
		if(str1.equals(str2)){
		   return true;
		}
		else {
		   return false;
		}
	}
	
	public boolean toLowerCase(String str1,String str2){
		if(str1.equalsIgnoreCase(str2)){
		   return true;
		}
		else {
		   return false;
		}
	}
	
	public boolean isNull(String str1){
		if(str1==null||str1.equals("")){
		   return true;
		}
		else {
		   return false;
		}
	}
	
	
	
	
}
