package org.gmfbilu.neteasycloudcourse.架构师;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.架构师.eventbus3.EventBus3Fragment;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.SQLiteFragment;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.greenrobot.eventbus.EventBus;

public class ArchitectFragment extends BaseFragment {

    public static ArchitectFragment newInstance() {
        Bundle args = new Bundle();
        ArchitectFragment fragment = new ArchitectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_eventbus3).setOnClickListener(this);
        view.findViewById(R.id.bt_sqlite).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_architect;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.bt_eventbus3){
            EventBus.getDefault().postSticky("postSticky");
            start(EventBus3Fragment.newInstance());
        }else if (id==R.id.bt_sqlite){
            start(SQLiteFragment.newInstance());
        }
    }

}
