/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.youkes.vr.MainApp;
import com.youkes.vr.R;
import com.youkes.vr.utils.LogUtil;


import java.util.ArrayList;


public class EmojiApapter extends BaseAdapter {


	ArrayList<CCPEmoji> emojis;
	
	LayoutInflater mInflater;
	//List<String> faceList =new ArrayList<String>();
    public EmojiApapter(Context context) {
    	
    	mInflater = LayoutInflater.from(context);
		//faceList =ExpressionUtil.initFaces(MainApp.getContext());

		init();

	}

	private void init() {
		//ToastUtil.showMessage(faceList.size() + ":faces");
	}


	@Override
	public int getCount() {

		if(emojis != null && emojis.size() > 0) {
			return this.emojis.size() + 1;
		}
		
		return 0;
	}


	@Override
	public Object getItem(int position) {
		
		if(emojis != null && (position <= (emojis.size() - 1)))  {
			return emojis.get(position);
		}
		
		return null;
	}


	@Override
	public long getItemId(int position) {

		return position;
	}
	

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null || convertView.getTag() == null ) {
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.emoji_item, null);
            viewHolder.emoji_icon=(ImageView)convertView.findViewById(R.id.emoji_id);
            //viewHolder.emoji_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        
        
        if(getCount() - 1 == position) {
    		viewHolder.emoji_icon.setImageResource(R.drawable.emoji_del_selector);
        } else {
			Bitmap bitmap=MainApp.getInstance().getFaceBitmap(getFacePath(position));
			if(bitmap!=null) {
				viewHolder.emoji_icon.setImageBitmap(bitmap);
			}

			/*
        	CCPEmoji emoji=(CCPEmoji) getItem(position);
        	if(emoji != null) {


        		if(emoji.getId() == R.drawable.emoji_del_selector) {
        			convertView.setBackgroundDrawable(null);
        			viewHolder.emoji_icon.setImageResource(emoji.getId());
        		} else if(TextUtils.isEmpty(emoji.getEmojiDesc())) {
        			convertView.setBackgroundDrawable(null);
        			viewHolder.emoji_icon.setImageDrawable(null);
        		} else {
        			viewHolder.emoji_icon.setTag(emoji);
        			viewHolder.emoji_icon.setImageResource(emoji.getId());
        		}
        	}
        	*/
        }



        return convertView;
    }

	public String getFacePath(int position) {
		if(emojisPath != null && (position <= (emojisPath.size() - 1)))  {
			return emojisPath.get(position);
		}

		return null;
	}

	class ViewHolder {

        public ImageView emoji_icon;
    }
    
    public void release() {
    	if(emojis != null) {
    		emojis.clear();
    		emojis = null;
    	}
    	mInflater = null;
    }
    
    /**
     * @param emojis
     */
    public void updateEmoji(ArrayList<CCPEmoji> emojis) {
    	this.emojis = emojis;
    	if(this.emojis == null) {
    		emojis = new ArrayList<CCPEmoji>();
    		LogUtil.e(LogUtil.getLogUtilsTag(EmoticonUtil.class), "EmojiApapter.updateEmoji get emoji list fail, new one");
    	}
    	notifyDataSetChanged();
    }


	ArrayList<String> emojisPath;
	public void updateEmojiPath(ArrayList<String> emojis) {
		this.emojisPath = emojis;
		if(this.emojisPath == null) {
			emojisPath = new ArrayList<String>();
			LogUtil.e(LogUtil.getLogUtilsTag(EmoticonUtil.class), "EmojiApapter.updateEmoji get emoji list fail, new one");
		}
		notifyDataSetChanged();
	}

}