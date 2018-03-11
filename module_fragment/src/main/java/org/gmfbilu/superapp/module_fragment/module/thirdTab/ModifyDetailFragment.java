package org.gmfbilu.superapp.module_fragment.module.thirdTab;

import android.os.Bundle;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class ModifyDetailFragment extends SupportFragment {
    private static final String ARG_TITLE = "arg_title";

    public static ModifyDetailFragment newInstance(String title) {
        Bundle args = new Bundle();
        ModifyDetailFragment fragment = new ModifyDetailFragment();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }
}
