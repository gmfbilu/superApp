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

public class ViewFragment extends SupportFragment {

    public static ViewFragment newInstance() {
        Bundle bundle = new Bundle();
        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        return viewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_new_feature_view, container, false);
        return view;
    }
}
