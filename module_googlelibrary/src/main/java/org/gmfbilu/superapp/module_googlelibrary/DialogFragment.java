package org.gmfbilu.superapp.module_googlelibrary;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class DialogFragment extends BaseFragment {


    public static DialogFragment newInstance() {
        Bundle args = new Bundle();
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_googlelibrary_fragment_dialogfragment;
    }

    @Override
    public void onClick(View v) {

    }
}
