package com.heytea.fragment.example;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.heytea.R;
import com.heytea.adapter.BaseRecyclerAdapter;
import com.heytea.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlexboxLayoutManagerFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flexbox_layout_manager, container, false);
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

        FlexboxLayoutManager manager = new FlexboxLayoutManager(root.getContext());
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(DensityUtil.dp2px(2.5f),DensityUtil.dp2px(2.5f),DensityUtil.dp2px(2.5f),DensityUtil.dp2px(2.5f));

        recyclerView.setAdapter(new BaseRecyclerAdapter<Object>(Arrays.asList(new Object[100]),0) {

            Random random = new Random();

            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Object model, int position) {

                int[] colorIds = {
                        R.color.colorPrimaryDark,
                        android.R.color.holo_green_dark,
                        android.R.color.holo_red_dark,
                        android.R.color.holo_orange_dark,
                };

                holder.itemView.setBackgroundResource(colorIds[random.nextInt(colorIds.length)]);
                holder.itemView.setMinimumWidth(DensityUtil.dp2px(70f + random.nextInt(70)));
            }
            @NonNull
            @Override
            public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FlexboxLayoutManager.LayoutParams lp = new FlexboxLayoutManager.LayoutParams(-2,-2);
                lp.setFlexGrow(1);
                lp.bottomMargin = DensityUtil.dp2px(2.5f);
                lp.topMargin = DensityUtil.dp2px(2.5f);
                lp.leftMargin = DensityUtil.dp2px(2.5f);
                lp.rightMargin = DensityUtil.dp2px(2.5f);
                ImageView imageVIew = new ImageView(parent.getContext());
                imageVIew.setMinimumWidth(DensityUtil.dp2px(90f));
                imageVIew.setMinimumHeight(DensityUtil.dp2px(90f));
                imageVIew.setLayoutParams(lp);
                return new SmartViewHolder(imageVIew, mListener);
            }
        });
    }


}
