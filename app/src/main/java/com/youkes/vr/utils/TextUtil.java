/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	public static String simplify(String textin){
		if(textin==null){
			return "";
		}
		String t=Html2Text.html2text(textin);
		t=t.trim();
		t=t.replaceAll("\\s+", " ").trim();
		
		if(t.length()>=240){
			t=t.substring(0,240);
		}
		
		return t;
		
	}

    public static String shortfy(String textin){
        if(textin==null){
            return "";
        }
        String t=Html2Text.html2text(textin);
        t=t.trim();
        t=t.replaceAll("\\s+", " ").trim();
        String[] tlist0=t.split("-|_");
        if(tlist0.length>=0){
            t=tlist0[0];
        }
        if(t.length()>=120){
            t=t.substring(0,120);
        }

        return t;

    }


    public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String strip(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("\n", " ")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String getBytesNumberText(int cnt) {
		if(cnt>1024*1024*1){
			double c=(double)cnt*10.0/(double)(1024*1024);
			int f=(int)Math.round(c);
			double d=f/10.0;
			return d+"MB";
		}

		if(cnt>1024*1){
			double c=(double)cnt*10.0/(double)(1024);
			int f=(int)Math.round(c);
			double d=f/10.0;
			return d+"KB";
		}
		return cnt+"B";
	}

	public static boolean isEmpty(String txt) {
		if(txt!=null&&!txt.equals("")){
			return false;
		}
		return true;
	}

	public static String toDistText(double dist) {

		if(dist>1){
			double dist2=((int)(dist*100))/100.0;
			return dist2+"km";
		}
		int d=(int)(dist*1000);
		return d+"m";

	}
}
