/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.youkes.vr.R;
import com.youkes.vr.utils.DensityUtil;
import com.youkes.vr.utils.LogUtil;

/**
 * 主界面Tab容器
 */
public class CCPLauncherUITabView extends RelativeLayout implements
		View.OnClickListener {

	/**
	 * Show that the main tab at first.
	 */
	public static final int TAB_VIEW_1 = 0;

	/**
	 * Show that the second tab view
	 */
	public static final int TAB_VIEW_2 = 1;

	/**
	 * Show that the third list .
	 */
	public static final int TAB_VIEW_3 = 2;

	public static final int TAB_VIEW_4 = 3;

	public static final int TAB_VIEW_5 = 4;

	public static final int TAB_VIEW_6 = 5;

	private TabViewHolder tabShare;

	private TabViewHolder tabRec =null;

	/**
	 * The holder for main TabView
	 */
	private TabViewHolder tabMsg;

	/**
	 * The holder for Second TabView
	 */
	private TabViewHolder tabFriend;

	/**
	 * The holder for third TabView
	 */
	private TabViewHolder tabDiscover;

	private TabViewHolder tabMe;

	/**
	 * Follow the label moved slowly
	 */
	private Bitmap mIndicatorBitmap;

	/**
     *
     */
	private Matrix mMatrix = new Matrix();

	/**
	 * Slide unit
	 */
	private int mTabViewBaseWidth;

	/**
	 * The current label location, is the need to move the index
	 */
	private int mCurrentSlideIndex;

	/**
     *
     */
	private ImageView mSlideImage;

	/**
	 * UITableView items.
	 */
	// private String[] mItems;

	/**
	 * UITableView item size.
	 */
	// private int mItemSize;

	/**
	 * tab view click.
	 */
	private long mClickTime = 0L;

	/**
	 * Listener used to dispatch click events.
	 */
	private OnUITabViewClickListener mListener;

	/**
     *
     */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			LogUtil.v(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
					"onMainTabClick");
			if (mListener != null) {
				mListener.onTabClick(TAB_VIEW_1);
			}
		}

	};

	/**
	 * @param context
	 */
	public CCPLauncherUITabView(Context context) {
		super(context);

		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CCPLauncherUITabView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CCPLauncherUITabView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		init();
	}

	/**
	 * 初始化三个Tab 视图
	 */
	private void init() {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setId(R.id.main_tab_root);
		layout.setOrientation(LinearLayout.HORIZONTAL);

		addView(layout, new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		ImageView imageView = new ImageView(getContext());
		imageView.setImageMatrix(mMatrix);
		imageView.setScaleType(ImageView.ScaleType.MATRIX);
		imageView.setId(R.id.main_tab_navigation_img);
		LayoutParams imageViewLayoutParams = new LayoutParams(
				-1, DensityUtil.fromDPToPix(getContext(), 3));
		imageViewLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				R.id.main_tab_root);
		addView(mSlideImage = imageView, imageViewLayoutParams);


		 tabRec = createTabView(TAB_VIEW_1);
		tabRec.tabView.setText(R.string.main_recommend);
		LinearLayout.LayoutParams recGroupLayoutParams = new LinearLayout.LayoutParams(
				0, getResources().getDimensionPixelSize(
				R.dimen.DefaultTabbarHeight));
		recGroupLayoutParams.weight = 1.0F;
		layout.addView(tabRec.tabView, recGroupLayoutParams);



		tabShare = createTabView(TAB_VIEW_2);
		tabShare.tabView.setText(R.string.main_activities);
		LinearLayout.LayoutParams shLayoutParams = new LinearLayout.LayoutParams(
				0, getResources().getDimensionPixelSize(
				R.dimen.DefaultTabbarHeight));
		recGroupLayoutParams.weight = 1.0F;
		layout.addView(tabShare.tabView, recGroupLayoutParams);


		// TabView dial
		TabViewHolder tabViewMain = createTabView(TAB_VIEW_3);
		tabViewMain.tabView.setText(R.string.main_msg);
		LinearLayout.LayoutParams enbGroupLayoutParams = new LinearLayout.LayoutParams(
				0, getResources().getDimensionPixelSize(
						R.dimen.DefaultTabbarHeight));
		enbGroupLayoutParams.weight = 1.0F;
		layout.addView(tabViewMain.tabView, enbGroupLayoutParams);
		tabMsg = tabViewMain;

		// TabView communication
		TabViewHolder tabViewSecond = createTabView(TAB_VIEW_4);
		tabViewSecond.tabView.setText(R.string.main_friend);
		LinearLayout.LayoutParams secondLayoutParams = new LinearLayout.LayoutParams(
				0, getResources().getDimensionPixelSize(
						R.dimen.DefaultTabbarHeight));
		secondLayoutParams.weight = 1.0F;
		layout.addView(tabViewSecond.tabView, secondLayoutParams);
		tabFriend = tabViewSecond;

		// TabView contacts
		TabViewHolder tabViewContacts = createTabView(TAB_VIEW_5);
		tabViewContacts.tabView.setText(R.string.main_discover);
		LinearLayout.LayoutParams contactsLayoutParams = new LinearLayout.LayoutParams(
				0, getResources().getDimensionPixelSize(
						R.dimen.DefaultTabbarHeight));
		contactsLayoutParams.weight = 1.0F;
		layout.addView(tabViewContacts.tabView, contactsLayoutParams);
		tabDiscover = tabViewContacts;

		tabMe = createTabView(TAB_VIEW_6);
		tabMe.tabView.setText(R.string.main_my);
		LinearLayout.LayoutParams param4 = new LinearLayout.LayoutParams(0,
				getResources().getDimensionPixelSize(
						R.dimen.DefaultTabbarHeight));
		param4.weight = 1.0F;
		layout.addView(tabMe.tabView, param4);

	}

	/**
	 * Register a callback to be invoked when this UITabView is clicked.
	 * 
	 * @param l
	 *            The callback that will run
	 */
	public void setOnUITabViewClickListener(OnUITabViewClickListener l) {
		mListener = l;
	}

	/**
	 * Set a list of items to be displayed in the UITableView as the content,
	 * you will be notified of the selected item via the supplied listener. This
	 * should be an array type i.e. R.array.foo
	 * 
	 * @param itemsId
	 */
	public final void setTabViewItems(int itemsId) {
		// mItems = getResources().getStringArray(itemsId);
		// mItemSize = mItems.length;
	}

	/**
	 *
	 * @param index
	 */
	public final void setTabViewText(int index, int resid) {
		switch (index) {
		case TAB_VIEW_1:

			tabMsg.tabView.setText(resid);
			break;
		case TAB_VIEW_2:
			tabFriend.tabView.setText(resid);
			break;

		case TAB_VIEW_3:
			tabDiscover.tabView.setText(resid);
			break;
		case TAB_VIEW_4:
			tabMe.tabView.setText(resid);
			break;

		default:
			break;
		}
	}

	/**
	 * create new TabView.
	 * 
	 * @param index
	 * @return
	 */
	public TabViewHolder createTabView(int index) {
		TabViewHolder tabViewHolder = new TabViewHolder();
		tabViewHolder.tabView = new CCPTabView(getContext(), index);
		tabViewHolder.tabView.setTag(Integer.valueOf(index));
		tabViewHolder.tabView.setOnClickListener(this);
		return tabViewHolder;
	}

	public final void resetTabViewDesc() {
		if (tabMsg == null || tabFriend == null
				|| tabDiscover == null || tabMe == null) {
			return;
		}

		tabMsg.tabView.notifyChange();
		tabFriend.tabView.notifyChange();
		tabDiscover.tabView.notifyChange();
	}

	@Override
	public void onClick(View v) {
		mClickTime = System.currentTimeMillis();
		int intValue = ((Integer) v.getTag()).intValue();
		if (dosomeTab(intValue)) return;


		mCurrentSlideIndex = intValue;
		if (mListener != null) {

				mListener.onTabClick(intValue);

			mHandler.sendEmptyMessageDelayed(0, 300L);
		}

	}

	private boolean dosomeTab(int intValue) {
		if ((mCurrentSlideIndex == intValue) && (intValue == TAB_VIEW_2)
				&& (System.currentTimeMillis() - mClickTime <= 300L)) {
			LogUtil.v(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
					"onMainTabDoubleClick");
			mHandler.removeMessages(0);
			// Processing double click
			mClickTime = System.currentTimeMillis();
			mCurrentSlideIndex = intValue;
			return true;
		}

		if (mListener != null) {
			if (intValue != TAB_VIEW_1
					|| mCurrentSlideIndex != TAB_VIEW_2) {
				mClickTime = System.currentTimeMillis();
				mCurrentSlideIndex = intValue;
				mListener.onTabClick(intValue);
				return true;
			}
			mHandler.sendEmptyMessageDelayed(0, 300L);
		}
		return false;
	}

	/**
     *
     */
	public final void doSentEvents() {
		if (tabFriend == null || tabMsg == null) {
			return;
		}

		tabMsg.tabView.notifyChange();
		tabFriend.tabView.notifyChange();
		tabDiscover.tabView.notifyChange();
	}

	/**
	 * 移动
	 * 
	 * @param start
	 *            开始位置
	 * @param distance
	 *            移动距离
	 */
	public final void doTranslateImageMatrix(int start, float distance) {
		mMatrix.setTranslate(mTabViewBaseWidth * (start + distance), 0.0F);
		mSlideImage.setImageMatrix(mMatrix);
	}

	/**
	 * Set the TabView to operate
	 * 
	 * @param visibility
	 */
	public final void setUnreadDotVisibility(boolean visibility) {

	}

	/**
	 * Update the interface number of unread
	 * 
	 * @param unreadCount
	 */
	public final void updateMsgTabUnread(int unreadCount) {

		if (unreadCount > 0) {
			if (unreadCount > 99) {
				tabMsg.tabView.setUnreadTips(getResources().getString(
						R.string.unread_count_overt_100));
				return;
			}
			tabMsg.tabView.setUnreadTips(String.valueOf(unreadCount));
			return;
		}
		tabMsg.tabView.setUnreadTips(null);
	}

	public final void updateSecondTabUnread(int unreadCount) {
		LogUtil.d(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
				"updateSecondTabUnread unread count " + unreadCount);
		if (unreadCount > 0) {
			if (unreadCount > 99) {
				tabFriend.tabView.setUnreadTips(getResources().getString(
						R.string.unread_count_overt_100));
				return;
			}
			tabFriend.tabView.setUnreadTips(String.valueOf(unreadCount));
			return;
		}
		tabFriend.tabView.setUnreadTips(null);
	}

	/**
	 * Update the interface number of unread
	 * 
	 * @param unreadCount
	 */
	public final void updateContactsTabUnread(int unreadCount) {
		LogUtil.d(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
				"updateContactsTabUnread unread count " + unreadCount);
		if (unreadCount > 0) {
			if (unreadCount > 99) {
				tabDiscover.tabView.setUnreadTips(getResources().getString(
						R.string.unread_count_overt_100));
				return;
			}
			tabDiscover.tabView.setUnreadTips(String.valueOf(unreadCount));
			return;
		}
		tabDiscover.tabView.setUnreadTips(null);
	}

	/**
	 * Change the selected TabView color
	 * 
	 * @param index
	 */
	@SuppressLint("ResourceAsColor")
	public final void doChangeTabViewDisplay(int index) {
		mCurrentSlideIndex = index;
		switch (index) {

			case TAB_VIEW_1:
				tabRec.tabView.setTextColor(getResources().getColorStateList(
						R.color.green_btn_color_normal));


				tabMsg.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));

				tabShare.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));

				tabFriend.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));
				tabDiscover.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));

				tabMe.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));
				break;

			case TAB_VIEW_2:
				tabShare.tabView.setTextColor(getResources().getColorStateList(
						R.color.green_btn_color_normal));
				tabRec.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));


				tabMsg.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));


				tabFriend.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));
				tabDiscover.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));

				tabMe.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));
				break;

		case TAB_VIEW_3:
			tabShare.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			tabMsg.tabView.setTextColor(getResources().getColorStateList(
					R.color.green_btn_color_normal));

			tabRec.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));

			tabFriend.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));
			tabDiscover.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));

			tabMe.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			break;

		case TAB_VIEW_4:
			tabShare.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			tabRec.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));


			tabMsg.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			tabFriend.tabView.setTextColor(getResources()
					.getColorStateList(R.color.green_btn_color_normal));
			tabDiscover.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));
			tabMe.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			break;
		case TAB_VIEW_5:
			tabShare.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			tabRec.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));

			tabMsg.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			tabFriend.tabView.setTextColor(getResources()
					.getColorStateList(R.color.launcher_tab_text_selector));
			tabDiscover.tabView.setTextColor(getResources()
					.getColorStateList(R.color.green_btn_color_normal));
			tabMe.tabView.setTextColor(getResources().getColorStateList(
					R.color.launcher_tab_text_selector));
			break;
			

			case TAB_VIEW_6:
				tabShare.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));
				tabRec.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));

				tabMsg.tabView.setTextColor(getResources().getColorStateList(
						R.color.launcher_tab_text_selector));
				tabFriend.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));
				tabDiscover.tabView.setTextColor(getResources()
						.getColorStateList(R.color.launcher_tab_text_selector));
				tabMe.tabView.setTextColor(getResources().getColorStateList(
						R.color.green_btn_color_normal));
				break;
		default:
			break;
		}
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		LogUtil.d(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
				"on layout, width " + (r - l));
		int width = mTabViewBaseWidth = ((r - l) / 4);

		if (mIndicatorBitmap == null
				|| mIndicatorBitmap.getWidth() != mTabViewBaseWidth) {

			int from = -1;
			if (mIndicatorBitmap != null) {
				from = mIndicatorBitmap.getWidth();
			}
			LogUtil.d(LogUtil.getLogUtilsTag(CCPLauncherUITabView.class),
					"sharp width changed, from " + from + " to " + width);

			mIndicatorBitmap = Bitmap.createBitmap(width,
					DensityUtil.fromDPToPix(getContext(), 1),
					Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(mIndicatorBitmap);
			//canvas.drawColor(getResources().getColor(R.color.green_btn_color_normal));
			canvas.drawColor(getResources().getColor(R.color.transparent));
			doTranslateImageMatrix(mCurrentSlideIndex, 0.0F);
			mSlideImage.setImageBitmap(mIndicatorBitmap);
			doChangeTabViewDisplay(mCurrentSlideIndex);
		}

	}

	/**
	 * @author Jorstin Chan
	 * @date 2014-4-26
	 * @version 1.0
	 */
	public class TabViewHolder {

		CCPTabView tabView;
	}

	/**
	 * Interface definition for a callback to be invoked when a UITabView is
	 * clicked.
	 */
	public abstract interface OnUITabViewClickListener {

		/**
		 * Called when a UITabView has been clicked.
		 * 
		 * @param tabIndex
		 *            index of UITabView
		 */
		public abstract void onTabClick(int tabIndex);
	}

}
