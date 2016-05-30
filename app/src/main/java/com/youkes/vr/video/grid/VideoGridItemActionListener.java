/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.video.grid;


import com.youkes.vr.video.VideoItem;

public interface VideoGridItemActionListener {

	void onShareCommentClick(VideoItem doc);
	void onShareLoveClick(VideoItem doc);
	void onDeleteDoc(VideoItem doc);
	
}

