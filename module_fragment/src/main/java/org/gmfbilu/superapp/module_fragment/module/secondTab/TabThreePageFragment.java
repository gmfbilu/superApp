package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.superapp.module_fragment.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class TabThreePageFragment extends SupportFragment {

    public static TabThreePageFragment newInstance() {
        Bundle args = new Bundle();
        TabThreePageFragment fragment = new TabThreePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_second_pager_three, container, false);
        return view;
    }

}
