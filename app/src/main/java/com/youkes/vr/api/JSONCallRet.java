/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.api;

public class JSONCallRet {
	
	public int status=0;
	public String api="";
	public String message="";
	public String accessKey="";
	public String content="";

	public JSONCallRet(String api,int status,String message,String accessKey){
		this.api=api;
		this.status=status;
		this.message=message;
		this.accessKey=accessKey;
		
	}

	public JSONCallRet(String api,int status,String message,String accessKey, String content){

		this.api=api;
		this.status=status;
		this.message=message;
		this.accessKey=accessKey;
		this.content=content;

	}

}

