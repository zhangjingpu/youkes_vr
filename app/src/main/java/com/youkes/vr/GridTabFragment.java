/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youkes.vr.ui.BaseFragment;
import com.youkes.vr.ui.ColumnHorizontalScrollView;
import com.youkes.vr.widget.swipegridview.SwipeGridView;

import java.util.ArrayList;

/**
 * Created by xuming on 2016/4/20.
 */
public class GridTabFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return this.resId;
    }
    protected SwipeRefreshLayout swipeLayout;
    protected SwipeGridView mListView;
    protected ProgressBar mProgressBar;

    int columnSelectIndex=0;
    int columnSelectIndex1=0;
    protected int resId = R.layout.grid_tab_list;

    public GridTabFragment() {

    }



    LinearLayout tab2_layout=null;
    LinearLayout tab1_layout=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayoutId(), container, false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mListView = (SwipeGridView) rootView.findViewById(R.id.listview);
        mListView.setNumColumns(2);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        shade_left1=(ImageView)rootView.findViewById(R.id.shade_left_1);
        shade_right1=(ImageView)rootView.findViewById(R.id.shade_right_1);
        ll_more_columns1=(LinearLayout)rootView.findViewById(R.id.ll_more_columns_1);
        rl_column1=(RelativeLayout)rootView.findViewById(R.id.rl_column_1);
        button_more_columns1=(ImageView)rootView.findViewById(R.id.button_more_columns_1);
        mColumnHorizontalScrollView1=(ColumnHorizontalScrollView)rootView.findViewById(R.id.mColumnHorizontalScrollView_1);
        mRadioGroup_content1=(LinearLayout)rootView.findViewById(R.id.mRadioGroup_content_1);
        tab2_layout=(LinearLayout)rootView.findViewById(R.id.tab2_layout);
        tab1_layout=(LinearLayout)rootView.findViewById(R.id.tab1_layout);

        shade_left=(ImageView)rootView.findViewById(R.id.shade_left);
        shade_right=(ImageView)rootView.findViewById(R.id.shade_right);
        ll_more_columns=(LinearLayout)rootView.findViewById(R.id.ll_more_columns);
        rl_column=(RelativeLayout)rootView.findViewById(R.id.rl_column);
        button_more_columns=(ImageView)rootView.findViewById(R.id.button_more_columns);
        mColumnHorizontalScrollView=(ColumnHorizontalScrollView)rootView.findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content=(LinearLayout)rootView.findViewById(R.id.mRadioGroup_content);


        initTabCol0(rootView);
        initTabCol1();

        return rootView;

    }

    ColumnHorizontalScrollView mColumnHorizontalScrollView1=null;
    protected LinearLayout mRadioGroup_content1;
    protected ImageView shade_left1;
    protected ImageView shade_right1;
    protected LinearLayout ll_more_columns1;
    protected RelativeLayout rl_column1;
    protected ImageView button_more_columns1;
    ArrayList<ChannelItem> channels1=new ArrayList<ChannelItem>();

    protected void initTabCol1() {

        mRadioGroup_content1.removeAllViews();
        //no more button
        button_more_columns1.setVisibility(View.GONE);

        channels=getChannelList();
        if(channels.size()==0){
            tab2_layout.setVisibility(View.GONE);
            return;
        }

        ArrayList<ChannelItem> channelList1=getChannelList1(channels.get(columnSelectIndex).getName());
        int count = channelList1.size();
        if(count==0){
            tab2_layout.setVisibility(View.GONE);
            return;
        }
        tab2_layout.setVisibility(View.VISIBLE);
        int mScreenWidth = (int)MainApp.getInstance().getScreenWidth();
        int mItemWidth = mScreenWidth / getChannelScreenCount1();
        mColumnHorizontalScrollView1.setParam(getActivity(), mScreenWidth, mRadioGroup_content1, shade_left1,
                shade_right1, ll_more_columns1, rl_column1);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            // TextView localTextView = (TextView)
            // mInflater.inflate(R.layout.column_radio_item, null);
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setTextAppearance(getActivity(), R.style.top1_category_scroll_view_item_text);

            //columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);

            columnTextView.setId(i);
            columnTextView.setText(channelList1.get(i).getName());
            columnTextView.setSingleLine(true);
            columnTextView.setTextColor(getResources().getColorStateList(
                    R.color.top_category_scroll_text_color_orange));
            if (columnSelectIndex1 == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content1.getChildCount(); i++) {
                        View localView = mRadioGroup_content1.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            //mViewPager.setCurrentItem(i);
                            onMenuTab1Selected(i);
                        }
                    }
                }
            });

            mRadioGroup_content1.addView(columnTextView, i, params);

        }


    }

    protected void onMenuTab1Selected(int i) {

    }

    public ArrayList<ChannelItem> getChannelList1(String s){

        return new ArrayList<ChannelItem>();
    }

    ColumnHorizontalScrollView mColumnHorizontalScrollView=null;
    protected LinearLayout mRadioGroup_content;
    protected ImageView shade_left;
    protected ImageView shade_right;
    protected LinearLayout ll_more_columns;
    protected RelativeLayout rl_column;
    protected ImageView button_more_columns;
    ArrayList<ChannelItem> channels=new ArrayList<ChannelItem>();
    public ArrayList<ChannelItem> getChannelList(){
        return new ArrayList<ChannelItem>();
    }



    private void initTabCol0(View rootView) {

        mRadioGroup_content.removeAllViews();

        //no more button
        button_more_columns.setVisibility(View.GONE);


        channels=getChannelList();
        int count = channels.size();
        if(count>1){
            tab1_layout.setVisibility(View.VISIBLE);
        }else{
            tab1_layout.setVisibility(View.GONE);
        }

        int mScreenWidth = (int)MainApp.getInstance().getScreenWidth();
        int mItemWidth = mScreenWidth / 8;
        mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left,
                shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            // TextView localTextView = (TextView)
            // mInflater.inflate(R.layout.column_radio_item, null);
            TextView columnTextView = new TextView(getActivity());
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setTextAppearance(getActivity(), R.style.top1_category_scroll_view_item_text);

            //columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);

            columnTextView.setId(i);
            columnTextView.setText(channels.get(i).getName());
            columnTextView.setSingleLine(true);
            columnTextView.setTextColor(getResources().getColorStateList(
                    R.color.top_category_scroll_text_color_orange));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            columnSelectIndex=i;
                            //mViewPager.setCurrentItem(i);
                            onMenuTab0Selected(i);
                        }
                    }
                }
            });

            mRadioGroup_content.addView(columnTextView, i, params);

        }
    }


    protected void onMenuTab0Selected(int i) {
        columnSelectIndex=i;
    }


    public int getChannelScreenCount1() {
        return 8;
    }
}
