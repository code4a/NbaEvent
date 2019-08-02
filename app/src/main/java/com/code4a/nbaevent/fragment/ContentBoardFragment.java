package com.code4a.nbaevent.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.code4a.nbaevent.R;
import com.code4a.retrofitutil.rx.RxSubscriber;
import com.code4a.simpleapi.SimpleManager;
import com.code4a.simpleapi.simple.ContentBoard;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by code4a on 2017/7/11.
 */

public class ContentBoardFragment extends LazyFragment {

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.view_content_board);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final TextView loadError = (TextView) findViewById(R.id.load_error);
        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.fragment_tabmain_indicator);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.BLUE, 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.BLUE, Color.GRAY).setSize(selectSize, unSelectSize));
        viewPager.setOffscreenPageLimit(4);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        final ContentBoardAdapter adapter = new ContentBoardAdapter(getChildFragmentManager());
        indicatorViewPager.setAdapter(adapter);

        SimpleManager.getCode4aApi().getContentBoard(new RxSubscriber<ContentBoard>() {
            @Override
            public void onSuccess(ContentBoard contentBoard) {
                loadError.setVisibility(View.GONE);
                adapter.setData(contentBoard);
            }

            @Override
            public void onFailed(Object obj) {
                loadError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onException(Throwable e) {
                progressBar.setVisibility(View.GONE);
                loadError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private class ContentBoardAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        ContentBoard contentBoard;

        public ContentBoardAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void setData(ContentBoard contentBoard) {
            this.contentBoard = contentBoard;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return contentBoard == null ? 0 : contentBoard.getTotal();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            if (contentBoard != null && contentBoard.getData() != null) {
                List<ContentBoard.DataBeanX> data = contentBoard.getData();
                textView.setText(data.get(position).getCategory());
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            BoardFragment mainFragment = new BoardFragment();
            if (contentBoard != null && contentBoard.getData() != null) {
                Bundle bundle = new Bundle();
                ContentBoard.DataBeanX dataBeanX = contentBoard.getData().get(position);
                if (dataBeanX != null) {
                    bundle.putSerializable(BoardFragment.B_DATA, dataBeanX.getData());
                    mainFragment.setArguments(bundle);
                }
            }
            return mainFragment;
        }
    }
}
