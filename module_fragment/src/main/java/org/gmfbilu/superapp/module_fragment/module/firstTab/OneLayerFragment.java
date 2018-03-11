package org.gmfbilu.superapp.module_fragment.module.firstTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.module_fragment.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class OneLayerFragment extends SupportFragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_first_onelayer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fragment_tv_message = view.findViewById(R.id.fragment_tv_message);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        fragment_tv_message.setText(mag.mesage);
    }
}
