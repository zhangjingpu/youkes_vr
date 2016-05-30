/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.youkes.vr.R;
import com.youkes.vr.file.FileAccessor;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;


public class FileUtil {

	static public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];

				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 *
	 * @param root
	 * @param fileName
	 * @return
	 */
	public static String getMD5FileDir(String root, String fileName) {
		// FileAccessor.IMESSAGE_IMAGE + File.separator +
		// FileAccessor.getSecondLevelDirectory(fileNameMD5)+ File.separator;
		if (TextUtils.isEmpty(root)) {
			return null;
		}
		File file = new File(root);
		if (!file.exists()) {
			file.mkdirs();
		}

		File fullPath = new File(file,
				FileAccessor.getSecondLevelDirectory(fileName));
		if (!fullPath.exists()) {
			fullPath.mkdirs();
		}
		return fullPath.getAbsolutePath();
	}

	/**
	 * 转换成单位
	 *
	 * @param length
	 * @return
	 */
	public static String formatFileLength(long length) {
		if (length >> 30 > 0L) {
			float sizeGb = Math.round(10.0F * (float) length / 1.073742E+009F) / 10.0F;
			return sizeGb + " GB";
		}
		if (length >> 20 > 0L) {
			return formatSizeMb(length);
		}
		if (length >> 9 > 0L) {
			float sizekb = Math.round(10.0F * (float) length / 1024.0F) / 10.0F;
			return sizekb + " KB";
		}
		return length + " B";
	}

	/**
	 * 转换成Mb单位
	 *
	 * @param length
	 * @return
	 */
	public static String formatSizeMb(long length) {
		float mbSize = Math.round(10.0F * (float) length / 1048576.0F) / 10.0F;
		return mbSize + " MB";
	}

	/**
	 * 检查SDCARD是否可写
	 *
	 * @return
	 */
	public static boolean checkExternalStorageCanWrite() {
		try {
			boolean mouted = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
			if (mouted) {
				boolean canWrite = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath())
						.canWrite();
				if (canWrite) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 返回文件的图标
	 *
	 * @param fileName
	 * @return
	 */
	public static int getFileIcon(String fileName) {
		String fileType = fileName.toLowerCase();
		if (isDocument(fileType)) {
			return R.drawable.file_attach_doc;
		}
		if (isPic(fileType)) {
			return R.drawable.file_attach_img;
		}

		if (isCompresseFile(fileType)) {
			return R.drawable.file_attach_rar;
		}
		if (isTextFile(fileType)) {
			return R.drawable.file_attach_txt;
		}
		if (isPdf(fileType)) {
			return R.drawable.file_attach_pdf;
		}

		if (isPPt(fileType)) {
			return R.drawable.file_attach_ppt;
		}

		if (isXls(fileType)) {
			return R.drawable.file_attach_xls;
		}
		return R.drawable.file_attach_ohter;
	}

	/**
	 * 是否图片
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isPic(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".bmp") || lowerCase.endsWith(".png")
				|| lowerCase.endsWith(".jpg") || lowerCase.endsWith(".jpeg")
				|| lowerCase.endsWith(".gif");
	}

	/**
	 * 是否压缩文件
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isCompresseFile(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".rar") || lowerCase.endsWith(".zip")
				|| lowerCase.endsWith(".7z") || lowerCase.endsWith("tar")
				|| lowerCase.endsWith(".iso");
	}

	/**
	 * 是否音频
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isAudio(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".mp3") || lowerCase.endsWith(".wma")
				|| lowerCase.endsWith(".mp4") || lowerCase.endsWith(".rm");
	}

	/**
	 * 是否文档
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isDocument(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".doc") || lowerCase.endsWith(".docx")
				|| lowerCase.endsWith("wps");
	}

	/**
	 * 是否Pdf
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isPdf(String fileName) {
		return HelpUtils.nullAsNil(fileName).toLowerCase().endsWith(".pdf");
	}

	/**
	 * 是否Excel
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isXls(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".xls") || lowerCase.endsWith(".xlsx");
	}

	/**
	 * 是否文本文档
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isTextFile(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".txt") || lowerCase.endsWith(".rtf");
	}

	/**
	 * 是否Ppt
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isPPt(String fileName) {
		String lowerCase = HelpUtils.nullAsNil(fileName).toLowerCase();
		return lowerCase.endsWith(".ppt") || lowerCase.endsWith(".pptx");
	}

	/**
	 * decode file length
	 *
	 * @param filePath
	 * @return
	 */
	public static long decodeFileLength(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return 0;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return 0;
		}
		return file.length();
	}

	/**
	 * Gets the extension of a file name, like ".png" or ".jpg".
	 *
	 * @param uri
	 * @return Extension including the dot("."); "" if there is no extension;
	 *         null if uri was null.
	 */
	public static String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			// No extension.
			return "";
		}
	}

	/**
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean checkFile(String filePath) {
		if (TextUtils.isEmpty(filePath) || !(new File(filePath).exists())) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param filePath
	 * @param seek
	 * @param length
	 * @return
	 */
	public static byte[] readFileToByte(String filePath, int seek, int length) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		if (length == -1) {
			length = (int) file.length();
		}

		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			byte[] bs = new byte[length];
			randomAccessFile.seek(seek);
			randomAccessFile.readFully(bs);
			randomAccessFile.close();
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e(LogUtil.getLogUtilsTag(FileUtil.class),
					"readFromFile : errMsg = " + e.getMessage());
			return null;
		}
	}


	public static byte[] readFileToByte(String filePath) {
		return readFileToByte(filePath, 0, -1);
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileDir
	 * @param fileName
	 * @param buffer
	 * @return
	 */
	public static int copyFile(String fileDir, String fileName, byte[] buffer) {
		if (buffer == null) {
			return -2;
		}

		try {
			File file = new File(fileDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			File resultFile = new File(file, fileName);
			if (!resultFile.exists()) {
				resultFile.createNewFile();
			}
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(resultFile, true));
			bufferedOutputStream.write(buffer);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			return 0;

		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * 根据文件名和后缀 拷贝文件
	 *
	 * @param fileDir
	 * @param fileName
	 * @param ext
	 * @param buffer
	 * @return
	 */
	public static int copyFile(String fileDir, String fileName, String ext,
							   byte[] buffer) {
		return copyFile(fileDir, fileName + ext, buffer);
	}

	/**
	 * 根据后缀名判断是否是图片文件
	 *
	 * @param type
	 * @return 是否是图片结果true or false
	 */
	public static boolean isImage(String type) {
		if (type != null
				&& (type.equals("jpg") || type.equals("gif")
				|| type.equals("png") || type.equals("jpeg")
				|| type.equals("bmp") || type.equals("wbmp")
				|| type.equals("ico") || type.equals("jpe"))) {
			return true;
		}
		return false;
	}

	private Context context;
	private String SDPATH;
	private String FILESPATH;
	public FileUtil(Context context) {
		this.context = context;
		SDPATH = Environment.getExternalStorageDirectory().getPath() + "//";
		FILESPATH = this.context.getFilesDir().getPath() + "//";
	}
	/**
	 * 在SD卡上创建文件
	 *
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	/**
	 * 删除SD卡上的文件
	 *
	 * @param fileName
	 */
	public boolean delSDFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file == null || !file.exists() || file.isDirectory())
			return false;
		file.delete();
		return true;
	}
	/**
	 * 在SD卡上创建目录
	 *
	 * @param dirName
	 */
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 * 删除SD卡上的目录
	 *
	 * @param dirName
	 */
	public boolean delSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		return delDir(dir);
	}
	/**
	 * 修改SD卡上的文件或目录名
	 *
	 * @param fileName
	 */
	public boolean renameSDFile(String oldfileName, String newFileName) {
		File oleFile = new File(SDPATH + oldfileName);
		File newFile = new File(SDPATH + newFileName);
		return oleFile.renameTo(newFile);
	}
	/**
	 * 拷贝SD卡上的单个文件
	 *
	 * @param path
	 * @throws IOException
	 */
	public boolean copySDFileTo(String srcFileName, String destFileName)
			throws IOException {
		File srcFile = new File(SDPATH + srcFileName);
		File destFile = new File(SDPATH + destFileName);
		return copyFileTo(srcFile, destFile);
	}
	/**
	 * 拷贝SD卡上指定目录的所有文件
	 *
	 * @param srcDirName
	 * @param destDirName
	 * @return
	 * @throws IOException
	 */
	public boolean copySDFilesTo(String srcDirName, String destDirName)
			throws IOException {
		File srcDir = new File(SDPATH + srcDirName);
		File destDir = new File(SDPATH + destDirName);
		return copyFilesTo(srcDir, destDir);
	}
	/**
	 * 移动SD卡上的单个文件
	 *
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws IOException
	 */
	public boolean moveSDFileTo(String srcFileName, String destFileName)
			throws IOException {
		File srcFile = new File(SDPATH + srcFileName);
		File destFile = new File(SDPATH + destFileName);
		return moveFileTo(srcFile, destFile);
	}
	/**
	 * 移动SD卡上的指定目录的所有文件
	 *
	 * @param srcDirName
	 * @param destDirName
	 * @return
	 * @throws IOException
	 */
	public boolean moveSDFilesTo(String srcDirName, String destDirName)
			throws IOException {
		File srcDir = new File(SDPATH + srcDirName);
		File destDir = new File(SDPATH + destDirName);
		return moveFilesTo(srcDir, destDir);
	}
	/*
	 * 将文件写入sd卡。如:writeSDFile("test.txt");
	 */
	public FileOutputStream writeSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		return fos;
	}
	
	/*
	 * 在原有文件上继续写文件。如:appendSDFile("test.txt");
	 */
	public FileOutputStream appendSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		FileOutputStream fos = new FileOutputStream(file, true);
		return fos;
	}
	/*
	 * 从SD卡读取文件。如:readSDFile("test.txt");
	 */
	public FileInputStream readSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}
	/**
	 * 建立私有文件
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File creatDataFile(String fileName) throws IOException {
		File file = new File(FILESPATH + fileName);
		file.createNewFile();
		return file;
	}
	/**
	 * 建立私有目录
	 *
	 * @param dirName
	 * @return
	 */
	public File creatDataDir(String dirName) {
		File dir = new File(FILESPATH + dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 * 删除私有文件
	 *
	 * @param fileName
	 * @return
	 */
	public boolean delDataFile(String fileName) {
		File file = new File(FILESPATH + fileName);
		return delFile(file);
	}
	/**
	 * 删除私有目录
	 *
	 * @param dirName
	 * @return
	 */
	public boolean delDataDir(String dirName) {
		File file = new File(FILESPATH + dirName);
		return delDir(file);
	}
	/**
	 * 更改私有文件名
	 *
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public boolean renameDataFile(String oldName, String newName) {
		File oldFile = new File(FILESPATH + oldName);
		File newFile = new File(FILESPATH + newName);
		return oldFile.renameTo(newFile);
	}
	/**
	 * 在私有目录下进行文件复制
	 *
	 * @param srcFileName
	 *            ： 包含路径及文件名
	 * @param destFileName
	 * @return
	 * @throws IOException
	 */
	public boolean copyDataFileTo(String srcFileName, String destFileName)
			throws IOException {
		File srcFile = new File(FILESPATH + srcFileName);
		File destFile = new File(FILESPATH + destFileName);
		return copyFileTo(srcFile, destFile);
	}
	/**
	 * 复制私有目录里指定目录的所有文件
	 *
	 * @param srcDirName
	 * @param destDirName
	 * @return
	 * @throws IOException
	 */
	public boolean copyDataFilesTo(String srcDirName, String destDirName)
			throws IOException {
		File srcDir = new File(FILESPATH + srcDirName);
		File destDir = new File(FILESPATH + destDirName);
		return copyFilesTo(srcDir, destDir);
	}
	/**
	 * 移动私有目录下的单个文件
	 *
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 * @throws IOException
	 */
	public boolean moveDataFileTo(String srcFileName, String destFileName)
			throws IOException {
		File srcFile = new File(FILESPATH + srcFileName);
		File destFile = new File(FILESPATH + destFileName);
		return moveFileTo(srcFile, destFile);
	}
	/**
	 * 移动私有目录下的指定目录下的所有文件
	 *
	 * @param srcDirName
	 * @param destDirName
	 * @return
	 * @throws IOException
	 */
	public boolean moveDataFilesTo(String srcDirName, String destDirName)
			throws IOException {
		File srcDir = new File(FILESPATH + srcDirName);
		File destDir = new File(FILESPATH + destDirName);
		return moveFilesTo(srcDir, destDir);
	}
	/*
	 * 将文件写入应用私有的files目录。如:writeFile("test.txt");
	 */
	public OutputStream wirteFile(String fileName) throws IOException {
		OutputStream os = context.openFileOutput(fileName,
				Context.MODE_WORLD_WRITEABLE);
		return os;
	}
	
	/*
	 * 在原有文件上继续写文件。如:appendFile("test.txt");
	 */
	public OutputStream appendFile(String fileName) throws IOException {
		OutputStream os = context.openFileOutput(fileName, Context.MODE_APPEND);
		return os;
	}
	/*
	 * 从应用的私有目录files读取文件。如:readFile("test.txt");
	 */
	public InputStream readFile(String fileName) throws IOException {
		InputStream is = context.openFileInput(fileName);
		return is;
	}
	/**********************************************************************************************************/
	/*********************************************************************************************************/
	
	/**
	 * 删除一个文件
	 *
	 * @param file
	 * @return
	 */
	public boolean delFile(File file) {
		if (file.isDirectory())
			return false;
		return file.delete();
	}
	/**
	 * 删除一个目录（可以是非空目录）
	 *
	 * @param dir
	 */
	public boolean delDir(File dir) {
		if (dir == null || !dir.exists() || dir.isFile()) {
			return false;
		}
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				delDir(file);// 递归
			}
		}
		dir.delete();
		return true;
	}
	/**
	 * 拷贝一个文件,srcFile源文件，destFile目标文件
	 *
	 * @param path
	 * @throws IOException
	 */
	public boolean copyFileTo(File srcFile, File destFile) throws IOException {
		if (srcFile.isDirectory() || destFile.isDirectory())
			return false;// 判断是否是文件
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = fis.read(buf)) != -1) {
			fos.write(buf, 0, readLen);
		}
		fos.flush();
		fos.close();
		fis.close();
		return true;
	}
	/**
	 * 拷贝目录下的所有文件到指定目录
	 *
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public boolean copyFilesTo(File srcDir, File destDir) throws IOException {
		if (!srcDir.isDirectory() || !destDir.isDirectory())
			return false;// 判断是否是目录
		if (!destDir.exists())
			return false;// 判断目标目录是否存在
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "//"
						+ srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "//"
						+ srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}
		return true;
	}
	/**
	 * 移动一个文件
	 *
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws IOException
	 */
	public boolean moveFileTo(File srcFile, File destFile) throws IOException {
		boolean iscopy = copyFileTo(srcFile, destFile);
		if (!iscopy)
			return false;
		delFile(srcFile);
		return true;
	}
	/**
	 * 移动目录下的所有文件到指定目录
	 *
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public boolean moveFilesTo(File srcDir, File destDir) throws IOException {
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;
		}
		File[] srcDirFiles = srcDir.listFiles();
		for (int i = 0; i < srcDirFiles.length; i++) {
			if (srcDirFiles[i].isFile()) {
				File oneDestFile = new File(destDir.getPath() + "//"
						+ srcDirFiles[i].getName());
				moveFileTo(srcDirFiles[i], oneDestFile);
				delFile(srcDirFiles[i]);
			} else if (srcDirFiles[i].isDirectory()) {
				File oneDestFile = new File(destDir.getPath() + "//"
						+ srcDirFiles[i].getName());
				moveFilesTo(srcDirFiles[i], oneDestFile);
				delDir(srcDirFiles[i]);
			}
		}
		return true;
	}

	public static long fileSize(String path) {
		File f = new File(path);
		if(f==null||!f.exists()){
			return 0;
		}
		return f.length();
	}

	public static String fileSizeText(String path) {
		long fz=fileSize(path);
		if(fz>1024*1024*1024){
			double f=((fz*10)/(1024*1024*1024))/10.0;
			return f+"GB";
		}
		if(fz>1024*1024){
			double f=((fz*10)/(1024*1024))/10.0;
			return f+"MB";
		}
		if(fz>1024){
			double f=((fz*10)/(1024))/10.0;
			return f+"KB";
		}
		return fz+"B";

	}


	public static void deleteFile(String img) {
		File f=new File(img);
		if(f.exists()){
			f.delete();
		}
	}


	public static Intent openFile(String filePath){

		File file = new File(filePath);
		if(!file.exists()) return null;
		/* 取得扩展名 */
		String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
				end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
			return getAudioFileIntent(filePath);
		}else if(end.equals("3gp")||end.equals("mp4")){
			return getAudioFileIntent(filePath);
		}else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
				end.equals("jpeg")||end.equals("bmp")){
			return getImageFileIntent(filePath);
		}else if(end.equals("apk")){
			return getApkFileIntent(filePath);
		}else if(end.equals("ppt")){
			return getPptFileIntent(filePath);
		}else if(end.equals("xls")){
			return getExcelFileIntent(filePath);
		}else if(end.equals("doc")){
			return getWordFileIntent(filePath);
		}else if(end.equals("pdf")){
			return getPdfFileIntent(filePath);
		}else if(end.equals("chm")){
			return getChmFileIntent(filePath);
		}else if(end.equals("txt")){
			return getTextFileIntent(filePath,false);
		}else{
			return getAllIntent(filePath);
		}
	}

	//Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param ) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri,"*/*");
		return intent;
	}
	//Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param ) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri,"application/vnd.android.package-archive");
		return intent;
	}

	//Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent( String param ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	//Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	//Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent( String param ){

		Uri uri = Uri.parse(param ).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	//Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent( String param ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	//Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	//Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	//Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	//Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	//Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent( String param, boolean paramBoolean){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean){
			Uri uri1 = Uri.parse(param );
			intent.setDataAndType(uri1, "text/plain");
		}else{
			Uri uri2 = Uri.fromFile(new File(param ));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}
	//Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "application/pdf");
		return intent;

	}


	public static Intent getFileViewIntent(String path,String mimeType ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, mimeType);
		return intent;

	}



}

