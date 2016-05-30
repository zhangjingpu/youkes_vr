/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.face;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.youkes.vr.utils.StringUtils;

public class ExpressionUtil {

	public static SpannableStringBuilder parse(Context mContext, String content, int itemWidth, int itemHeight) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "\\[[^\\]]+\\]";//[图片]

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {

			String tempText = m.group();
			if (tempText.equals("[图片]")) {
				continue;
			}

			String png = tempText.substring("[".length(), tempText.length() - "]".length());
			try {

				ImageSpan ispan = new ImageSpan(mContext, BitmapFactory.decodeStream(mContext.getAssets().open(png)));
				ispan.getDrawable().setBounds(0, 0, itemWidth, itemHeight);
				sb.setSpan(ispan, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		return sb;
	}

	public static String parseSimple(String content) {

		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "\\[[^\\]]+\\]";//[图片]
		String ret=content.replaceAll(regex,"");
		if(StringUtils.isEmpty(ret)){
			return "[表情]";
		}
		return ret;

	}


	public static SpannableStringBuilder prase2(Context mContext,final TextView gifTextView,String content,int itemWidth,int itemHeight) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "\\[[^\\]]+\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring("[p/_".length(), tempText.length()- ".png]".length());
				String gif = "g/" + num + ".gif";


				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif
				 * 否则说明gif找不到，则显示png\\[[^\\]]+\\]
				 * */
				InputStream is = mContext.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,itemWidth,itemHeight,new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("[".length(),tempText.length() - "]".length());
				try {

					ImageSpan ispan=new ImageSpan(mContext, BitmapFactory.decodeStream(mContext.getAssets().open(png)));
					ispan.getDrawable().setBounds(0,0,itemWidth,itemHeight);
					sb.setSpan(ispan, m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	}
	
	public static SpannableStringBuilder getFace(Context mContext,String png,int itemWidth,int itemHeight) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		try {
			/**
			 * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
			 * 所以这里对这个tempText值做特殊处理
			 * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
			 * */
			String tempText = "[" + png + "]";
			sb.append(tempText);

			ImageSpan imgspan=new ImageSpan(mContext, BitmapFactory
					.decodeStream(mContext.getAssets().open(png)));
			imgspan.getDrawable().setBounds(0,0,itemWidth,itemHeight);
			sb.setSpan(imgspan
					, sb.length()
							- tempText.length(), sb.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;
	}




	/**
	 * 向输入框里添加表情
	 * */
	public static void insert(EditText input,CharSequence text) {
		int iCursorStart = Selection.getSelectionStart((input.getText()));
		int iCursorEnd = Selection.getSelectionEnd((input.getText()));
		if (iCursorStart != iCursorEnd) {
			((Editable) input.getText()).replace(iCursorStart, iCursorEnd, "");
		}
		int iCursor = Selection.getSelectionEnd((input.getText()));
		((Editable) input.getText()).insert(iCursor, text);
	}

	/**
	 * 删除图标执行事件
	 * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
	 * */
	public static void delete(EditText input) {
		if (input.getText().length() != 0) {
			int iCursorEnd = Selection.getSelectionEnd(input.getText());
			int iCursorStart = Selection.getSelectionStart(input.getText());
			if (iCursorEnd > 0) {
				if (iCursorEnd == iCursorStart) {
					if (isDeletePng(input,iCursorEnd)) {
						String st = "[p/_000.png]";
						((Editable) input.getText()).delete(
								iCursorEnd - st.length(), iCursorEnd);
					} else {
						((Editable) input.getText()).delete(iCursorEnd - 1,
								iCursorEnd);
					}
				} else {
					((Editable) input.getText()).delete(iCursorStart,
							iCursorEnd);
				}
			}
		}
	}

	/**
	 * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
	 * **/
	public static boolean  isDeletePng(EditText input,int cursor) {
		String st = "[p/_000.png]";
		String content = input.getText().toString().substring(0, cursor);
		if (content.length() >= st.length()) {
			String checkStr = content.substring(content.length() - st.length(), content.length());
			String regex = "\\[[^\\]]+\\]";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(checkStr);
			return m.matches();
		}
		return false;
	}
	

	
	/**
	 * 根据表情数量以及GridView设置的行数和列数计算Pager数量
	 * @return
	 */
	public  static int getPagerCount(int listsize,int columns,int rows ) {
		return listsize % (columns * rows - 1) == 0 ? listsize / (columns * rows - 1): listsize / (columns * rows - 1) + 1;
	}

	private static ArrayList<String> facesList=null;
	/**
	 * 初始化表情列表staticFacesList
	 */
	public static  ArrayList<String> initFaces(Context context) {
		if(facesList!=null){
			return facesList;
		}
		try {
			facesList = new ArrayList<String>();
			String[] faces = context.getAssets().list("p");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for (int i = 0; i < faces.length; i++) {
				facesList.add(faces[i]);
			}
			//去掉删除图片
			facesList.remove("del.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facesList;
	}

	public static List<String> initStaticFaces2(Context context) {
		List<String> facesList=null;
		try {
			facesList = new ArrayList<String>();
			String[] faces = context.getAssets().list("p");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for (int i = 0; i < faces.length; i++) {
				facesList.add(faces[i]);
			}
			//去掉删除图片
			facesList.remove("del.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facesList;
	}

}
