/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import com.youkes.vr.R;

public class PathUtil {

	public static String getFileName(String path) {

		if (path == null) {
			return "";
		}
		int lastIndexOf = path.lastIndexOf("/");
		if (lastIndexOf >= 0) {
			return path.substring(lastIndexOf+1);
		}
		return "";

	}

	public static String getFileNameWithoutExt(String path) {
		String n= getFileName(path);
		int lastIndexOf = n.lastIndexOf(".");
		if (lastIndexOf > 0) {
			return n.substring(0,lastIndexOf);
		}
		return n;

	}


	public static String getFileExt(String path) {
		if (path == null) {
			return "";
		}
		int lastIndexOf = path.lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return path.substring(lastIndexOf+1);
		}
		return "";
	}


	public static int getPathExtRes(String imgPath) {
		String ext=getFileExt(imgPath).toLowerCase();
		if(ext.equals("pdf")){
			return R.drawable.pdf;
		}

		if(ext.equals("doc")||ext.equals("docx")){
			return R.drawable.word;
		}

		if(ext.equals("ppt")){
			return R.drawable.file_attach_ppt;
		}

		if(ext.equals("xls")||ext.equals("xlsx")){
			return R.drawable.file_attach_xls;
		}

		if(ext.equals("jpg")||ext.equals("jpeg")||ext.equals("png")||ext.equals("gif")){
			return R.drawable.file_attach_img;
		}

		return R.drawable.file_attach_txt;

	}
}
