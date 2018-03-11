package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class SecondTabPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    public SecondTabPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TabOnePageFragment.newInstance();
        } else if (position == 1) {
            return TabTwoPageFragment.newInstance();
        } else {
            return TabThreePageFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
