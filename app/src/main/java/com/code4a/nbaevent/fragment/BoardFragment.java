package com.code4a.nbaevent.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.code4a.nbaevent.R;
import com.code4a.nbaevent.adapter.CommonAdapter;
import com.code4a.nbaevent.adapter.ViewHolder;
import com.code4a.simpleapi.simple.ContentBoard;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

/**
 * Created by code4a on 2017/7/11.
 */

public class BoardFragment extends LazyFragment {

    public static final String B_DATA = "data";

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    ContentBoard.DataBeanX.DataBean data;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.view_board);
        data = (ContentBoard.DataBeanX.DataBean) getArguments().getSerializable(B_DATA);
        //ViewPager,Indicator
        ViewPager viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.view_content, container, false);
            }
            initGridView(position, (GridView)convertView);
            return convertView;
        }

        private void initGridView(int position, final GridView convertView) {
            if(data != null && data.getList() != null){
                List<ContentBoard.DataBeanX.DataBean.ListBean> listBean = data.getList().get(position);
                CommonAdapter<ContentBoard.DataBeanX.DataBean.ListBean> commonAdapter = new CommonAdapter<ContentBoard.DataBeanX.DataBean.ListBean>(getApplicationContext(), listBean, R.layout.view_content_item) {
                    @Override
                    public void convert(ViewHolder helper, ContentBoard.DataBeanX.DataBean.ListBean item) {
                        helper.setText(R.id.text, item.getName());
                        ImageView imageView = helper.getView(R.id.image);
                        Glide.with(BoardFragment.this)
                                .load(item.getIcon())
                                .into(imageView);
                    }
                };
                convertView.setAdapter(commonAdapter);
            }
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }
    };
}
