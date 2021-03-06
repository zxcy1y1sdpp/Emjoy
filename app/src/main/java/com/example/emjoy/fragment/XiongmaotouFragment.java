package com.example.emjoy.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.emjoy.R;
import com.example.emjoy.adapter.RecyclerGridAdapter;
import com.example.emjoy.bean.ImagesInfo;
import com.example.emjoy.widget.SpacesItemDecoration;

public class XiongmaotouFragment extends Fragment implements OnRefreshListener{
    private static final String TAG = " XiongmaotouFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    private SwipeRefreshLayout srl_4; // 声明一个下拉刷新布局对象
    private RecyclerView rv_4; // 声明一个循环视图对象
    private RecyclerGridAdapter mAdapter; // 声明一个普通网格适配器对象
    private ArrayList<ImagesInfo> mAllArray; // 熊猫头表情队列

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment生成视图对象
        mView = inflater.inflate(R.layout.fragment, container, false);
        // 从布局文件中获取名叫srl的下拉刷新布局
        srl_4 = mView.findViewById(R.id.srl);
        // 设置srl_4的下拉刷新监听器
        srl_4.setOnRefreshListener(this);
        // 设置srl_4的下拉变色资源数组
        srl_4.setColorSchemeResources(
                R.color.red, R.color.orange, R.color.green, R.color.blue);
        // 从布局文件中获取名叫rv的循环视图
        rv_4 = mView.findViewById(R.id.rv);
        // 创建一个垂直方向的普通网格布局管理器
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        // 设置循环视图的布局管理器
        rv_4.setLayoutManager(manager);
        // 获取默认的熊猫头表情信息队列
        mAllArray = ImagesInfo.getDefault(3); //**
        // 构建一个熊猫头表情列表的普通网格适配器
        mAdapter = new RecyclerGridAdapter(mContext, mAllArray);
        // 设置普通网格列表的点击监听器
        mAdapter.setOnItemClickListener(mAdapter);
        // 设置普通网格列表的长按监听器
        mAdapter.setOnItemLongClickListener(mAdapter);
        // 给rv_4设置熊猫头表情普通网格适配器
        rv_4.setAdapter(mAdapter);
        // 设置rv_4的默认动画效果
        rv_4.setItemAnimator(new DefaultItemAnimator());
        // 给rv_4添加列表项之间的空白装饰
        rv_4.addItemDecoration(new SpacesItemDecoration(3));

        return mView;
    }

    // 一旦在下拉刷新布局内部往下拉动页面，就触发下拉监听器的onRefresh方法
    public void onRefresh() {
        // 延迟若干秒后启动刷新任务
        mHandler.postDelayed(mRefresh, 2000);
    }

    private Handler mHandler = new Handler(); // 声明一个处理器对象
    // 定义一个刷新任务
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            // 结束下拉刷新布局的刷新动作
            srl_4.setRefreshing(false);
            // 更新熊猫头表情信息队列
            for (int i = mAllArray.size() - 1, count = 0; count < 5; count++) {
                ImagesInfo item = mAllArray.get(i);
                mAllArray.remove(i);
                mAllArray.add(0, item);
            }
            // 通知适配器的列表数据发生变化
            mAdapter.notifyDataSetChanged();
            // 让循环视图滚动到第一项所在的位置
            rv_4.scrollToPosition(0);
        }
    };


}

