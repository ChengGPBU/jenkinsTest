package com.heytea.fragment.example;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.heytea.R;
import com.heytea.activity.FragmentActivity;
import com.heytea.adapter.BaseRecyclerAdapter;
import com.heytea.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.Arrays;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * 使用示例-空布页面
 * A simple {@link Fragment} subclass.
 */
public class EmptyLayoutExampleFragmentInner extends Fragment implements AdapterView.OnItemClickListener, OnRefreshListener {

    private View mEmptyLayout;
    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private static boolean mIsNeedDemo = true;
    private BaseRecyclerAdapter<EmptyLayoutExampleFragment.Item> mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example_emptylayout_inner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        final Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.FixedBehind).setPrimaryColorId(R.color.colorPrimary).setAccentColorId(android.R.color.white));
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<EmptyLayoutExampleFragment.Item>(new ArrayList<EmptyLayoutExampleFragment.Item>(), simple_list_item_2,EmptyLayoutExampleFragmentInner.this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, EmptyLayoutExampleFragment.Item model, int position) {
                holder.text(android.R.id.text1, model.name());
                holder.text(android.R.id.text2, model.name);
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });

        mEmptyLayout = root.findViewById(R.id.empty);

        ImageView image = root.findViewById(R.id.empty_image);
        image.setImageResource(R.drawable.ic_empty);

        TextView empty = root.findViewById(R.id.empty_text);
        empty.setText("暂无数据下拉刷新");

        /*主动演示刷新*/
        if (mIsNeedDemo) {
            mRefreshLayout.getLayout().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mIsNeedDemo) {
                        mRefreshLayout.autoRefresh();
                    }
                }
            }, 3000);
            mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
                @Override
                public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                    mIsNeedDemo = false;
                }
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.finishLoadMore(2000);
                }
            });
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRefreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.refresh(Arrays.asList(EmptyLayoutExampleFragment.Item.values()));
                mRefreshLayout.finishRefresh();
                mEmptyLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EmptyLayoutExampleFragment.Item item = EmptyLayoutExampleFragment.Item.values()[position];
        if (Activity.class.isAssignableFrom(item.clazz)) {
            startActivity(new Intent(getContext(), item.clazz));
        } else if (Fragment.class.isAssignableFrom(item.clazz)) {
            FragmentActivity.start(this, item.clazz);
        }
    }
}
