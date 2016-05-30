/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtil {

	private static String charCode = "UTF-8";
	private static String HmacSHA1 = "HmacSHA1";
	public static final String HMAC_SHA1 = "HmacSHA1";
	public static final String encName_MD5 = "MD5";
	public static final String encName_SHA1 = "SHA-1";


	/**
	 * 适用于上G大的文件
	 */
	public static String getFileSha1(String path) {
		FileInputStream in =null;
		try {
			File file=new File(path);
			 in = new FileInputStream(file);

			MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
			byte[] buffer = new byte[1024 * 1024 * 10];
			int len = 0;
			while ((len = in.read(buffer)) >0) {
				//该对象通过使用 update（）方法处理数据
				messagedigest.update(buffer, 0, len);
			}



			//对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
			return byte2hex(messagedigest.digest());
		}   catch (NoSuchAlgorithmException e) {
			//NQLog.e("getFileSha1->NoSuchAlgorithmException###", e.toString());
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {

			//NQLog.e("getFileSha1->OutOfMemoryError###", e.toString());
			e.printStackTrace();
			throw e;
		}catch (IOException e){

		}
		finally{
			try {

				if(in!=null) {
					in.close();
				}

			}catch (Exception e){

			}
		}
		return null;
	}


	private static String byte2hex(byte[] digest) {
		BigInteger bigInt = new BigInteger(1, digest);
		return bigInt.toString(16);
	}


	public static String hmacSHA1(String source, String secretKey) {
		if (source == null || source.equals("")) {
			return "";
		}

		try {
			String secret = "";
			byte[] data = source.getBytes(charCode);
			byte[] key = secretKey.getBytes(charCode);

			SecretKeySpec signingKey = new SecretKeySpec(key, HmacSHA1);
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data);
			secret = bytesToHexString(rawHmac);
			if (secret == null || "".equals(secret)) {
				secret = source;
			}
			return secret;
		} catch (Exception e) {
			return "";
		}
	}

	private static String encryptHash(String source, String secretType) {
		try {

			MessageDigest md = MessageDigest.getInstance(secretType);
			byte[] digest = md.digest(source.getBytes(charCode));
			return bytesToHexString(digest);
			// return hexstring(secret, digest);
		} catch (Exception e) {
			return "";
		}

	}

	public static String md5Hash(String source) {
		return encryptHash(source, "MD5");
	}

	public static String sha1Hash(String source) {
		return encryptHash(source,"SHA-1");
	}

	private static String hexstring(byte[] digest) {
		String secret = "";
		String str = "";
		String tempStr = "";
		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return secret;
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String getUploadSha1(File file) {
		try {
			return getUploadSha1_(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getUploadSha1(byte[] file) {
		try {
			return getUploadSha1_(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String getUploadSha1_(byte[] file) throws IOException,
			NoSuchAlgorithmException {

		InputStream in = new ByteArrayInputStream(file);// file.getInputStream();
		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

		byte[] buffer = new byte[1024 * 1024 * 10];
		int len = 0;

		while ((len = in.read(buffer)) > 0) {
			// 该对象通过使用 update（）方法处理数据
			messagedigest.update(buffer, 0, len);
		}

		// 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest
		// 对象被重新设置成其初始状态。
		return bytesToHexString(messagedigest.digest());
	}

	private static String getUploadSha1_(File file) throws IOException,
			NoSuchAlgorithmException {

		InputStream in = new FileInputStream(file);// file.getInputStream();
		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

		byte[] buffer = new byte[1024 * 1024 * 10];
		int len = 0;

		while ((len = in.read(buffer)) > 0) {
			// 该对象通过使用 update（）方法处理数据
			messagedigest.update(buffer, 0, len);
		}

		in.close();
		// file.
		// 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest
		// 对象被重新设置成其初始状态。
		return bytesToHexString(messagedigest.digest());
	}

	public static String getUploadSha1(InputStream in) {
		try {
			return getUploadSha1_(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getUploadSha1_(InputStream in) throws IOException,
			NoSuchAlgorithmException {

		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

		byte[] buffer = new byte[1024 * 1024 * 10];
		int len = 0;

		while ((len = in.read(buffer)) > 0) {
			// 该对象通过使用 update（）方法处理数据
			messagedigest.update(buffer, 0, len);
		}

		in.close();
		// file.
		// 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest
		// 对象被重新设置成其初始状态。
		return bytesToHexString(messagedigest.digest());
	}

	public static String getUploadSha1Bytes(byte[] bytes) {
		try {
			return getUploadSha1Bytes_(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getUploadSha1Bytes_(byte[] bytes) throws IOException,
			NoSuchAlgorithmException {

		InputStream in = new ByteArrayInputStream(bytes);

		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

		byte[] buffer = new byte[1024 * 1024 * 10];
		int len = 0;

		while ((len = in.read(buffer)) > 0) {
			// 该对象通过使用 update（）方法处理数据
			messagedigest.update(buffer, 0, len);
		}

		in.close();
		// 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest
		// 对象被重新设置成其初始状态。
		return bytesToHexString(messagedigest.digest());
	}

}
