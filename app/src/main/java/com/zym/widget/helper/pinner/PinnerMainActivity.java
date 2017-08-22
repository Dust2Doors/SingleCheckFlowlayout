package com.zym.widget.helper.pinner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.zym.widget.helper.R;
import com.zym.widget.helper.adapter.CommonAdapter;
import com.zym.widget.helper.adapter.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by zym on 2017/8/22.
 */

public class PinnerMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView tvTopBar;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PinnerItem> mList;
    private int mTopBarHeight = 0;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pinner);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTopBar = (TextView) findViewById(R.id.tvTitle);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initRecycleView();
        getHistoryList();
    }

    private void initRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取上浮title的高度
                mTopBarHeight = tvTopBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //当滑动的时候获取当前滑动的下一个item的视图
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition+1);
                if (view == null) {
                    return;
                }
                //当view距离手机顶端的top值小于等于浮动title的高度的时候
                if (mList.get(mCurrentPosition+1).showMonth) {
                    //浮动标题的高度-view距离top的距离，计算出view和浮动层的交集部分，
                    //然后设置浮动view的top值，保证滑动的view始终是在浮动层的下方
                    tvTopBar.setY(-(mTopBarHeight - view.getTop()));
                }
//                } else {
//                    tvTopBar.setY(0);
//                }
                if (!mList.get(mCurrentPosition).showMonth) {
                    tvTopBar.setY(0);
                }
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    tvTopBar.setY(0);
                    tvTopBar.setText(mList.get(mCurrentPosition).month);
                }
            }
        });
    }
    private void getHistoryList() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            // TODO: 2017/8/22
            PinnerItem item;
            if (i < 4) {
                item = new PinnerItem("周五", "" + i % 12 + i, 584d, "天天 支付宝支付", "3月" );
            } else if (i < 10) {
                item = new PinnerItem("周三", "" + i % 12 + i, 584d, "天天 支付宝支付", "4月" );
            } else if (i < 15) {
                item = new PinnerItem("周二", "" + i % 12 + i, 584d, "天天 支付宝支付", "5月" );
            }else{
                item = new PinnerItem("周一", "" + i % 12 + i, 584d, "天天 支付宝支付", "6月" );
            }
            if (i == 0 ||i ==4 || i == 10 || i == 15) {
                item.showMonth = true;
            }else{
                item.showMonth = false;
            }
            mList.add(item);
        }
        tvTopBar.setText(mList.get(0).month);
        mRecyclerView.setAdapter(new CommonAdapter<PinnerItem>(R.layout.pinner_item, mList) {

            @Override
            protected void convert(CommonViewHolder commonViewHolder, PinnerItem billItem) {
                commonViewHolder.setVisibility(R.id.tvTitle, billItem.showMonth ? View.VISIBLE : View.GONE)
                        .setVisibility(R.id.separator,billItem.showMonth?View.GONE:View.VISIBLE)
                        .setText(R.id.type, billItem.week)
                        .setText(R.id.data, billItem.date)
                        .setText(R.id.tvTitle,billItem.month)
                        .setText(R.id.desc, billItem.desc);
                if (billItem.money > 0) {
                    commonViewHolder.getView(R.id.money).setEnabled(false);
                    commonViewHolder.setText(R.id.money, "+" + billItem.money);
                }else{
                    commonViewHolder.getView(R.id.money).setEnabled(true);
                    commonViewHolder.setText(R.id.money, "-" + billItem.money);
                }
            }
        });
    }
}
