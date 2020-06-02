package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewFragment extends BaseFragment {


    private RecyclerView recyclerview;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    public static RecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerview = view.findViewById(R.id.recyclerview);
        view.findViewById(R.id.bt_recyclerview_animator).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui_supplementcourse_recyclerview;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_recyclerview_animator) {
            String time = Calendar.getInstance().getTime().toLocaleString();
            messageList.add(1,new ImageMessage(time, R.mipmap.lib_base_camera_ic_start));
            messageAdapter.notifyItemChanged(1);
            recyclerview.setItemAnimator(new FadeInItemAnimator());
        }
    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        messageList = new ArrayList<>(10);
        String time = Calendar.getInstance().getTime().toLocaleString();
        for (int i = 0; i < 10; i++) {
            if (i < 3 || i == 6 || i == 9 ) {
                TextMessage textMessage = new TextMessage(time, "我是文本消息" + i);
                messageList.add(textMessage);
            } else {
                ImageMessage imageMessage = new ImageMessage(time, R.mipmap.ic_launcher);
                messageList.add(imageMessage);
            }
        }
        messageAdapter = new MessageAdapter(messageList);
        recyclerview.setAdapter(messageAdapter);
        //第三个参数为false表示从上往下滑或者从左往右滑，反之亦然
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SimpleItemDecoration());
        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                LoggerUtil.d(position);
            }
        });
    }
}
