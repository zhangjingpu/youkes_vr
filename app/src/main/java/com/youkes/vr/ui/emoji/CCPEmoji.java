
/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui.emoji;

public class CCPEmoji {

    /**
     * Expression corresponding resource picture ID
     */
    private int id;

    /**
     * Expression resources corresponding text description
     */
    private String EmojiDesc;

    /**
     * File name expression resources
     */
    private String EmojiName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

	public String getEmojiDesc() {
		return EmojiDesc;
	}

	public void setEmojiDesc(String emojiDesc) {
		EmojiDesc = emojiDesc;
	}

	public String getEmojiName() {
		return EmojiName;
	}

	public void setEmojiName(String emojiName) {
		EmojiName = emojiName;
	}

    
}

