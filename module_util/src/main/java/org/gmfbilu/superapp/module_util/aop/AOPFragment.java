package org.gmfbilu.superapp.module_util.aop;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

import java.util.HashMap;
import java.util.Map;

public class AOPFragment extends BaseFragment {


    public static AOPFragment newInstance() {
        Bundle args = new Bundle();
        AOPFragment fragment = new AOPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_gotonext).setOnClickListener(this);
        view.findViewById(R.id.bt_click).setOnClickListener(this);
        view.findViewById(R.id.bt_async).setOnClickListener(this);
        view.findViewById(R.id.bt_trace).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_aop;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_gotonext) {
            gotoNextPage();
        } else if (id == R.id.bt_click) {
            Toast.makeText(getContext(), "有效点击", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.bt_async) {
            async();
        } else if (id == R.id.bt_trace) {
            trace();
        }
    }


    @Async
    private void async() {
        StringBuilder sb = new StringBuilder();
        sb.append("current thread=").append(Thread.currentThread().getId())
                .append("\r\n")
                .append("ui thread=")
                .append(Looper.getMainLooper().getThread().getId());
        Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
    }

    @CheckLogin()
    public void gotoNextPage() {
        //登录才会执行下列代码
        Toast.makeText(getContext(), "去下一个页面", Toast.LENGTH_SHORT).show();
    }

    @Trace
    private void trace() {
        for (int i = 0; i < 100000; i++) {
            Map map = new HashMap();
            map.put("name", "tony");
            map.put("age", "18");
            map.put("gender", "male");
        }
    }
}
