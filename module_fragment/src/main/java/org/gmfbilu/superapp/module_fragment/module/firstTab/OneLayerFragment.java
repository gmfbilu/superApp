package org.gmfbilu.superapp.module_fragment.module.firstTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_fragment.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class OneLayerFragment extends BaseFragment {

    private static final String ARG_MSG = "arg_msg";
    private TextView fragment_tv_message;
    private Message mag;


    public static OneLayerFragment newInstance(Message msg) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MSG, msg);
        OneLayerFragment fragment = new OneLayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mag = getArguments().getParcelable(ARG_MSG);
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        fragment_tv_message = view.findViewById(R.id.fragment_tv_message);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_tab_first_onelayer;
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        fragment_tv_message.setText(mag.mesage);
    }

    @Override
    public void onClick(View v) {

    }
}
