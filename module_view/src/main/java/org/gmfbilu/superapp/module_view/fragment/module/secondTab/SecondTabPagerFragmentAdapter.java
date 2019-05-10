package org.gmfbilu.superapp.module_view.fragment.module.secondTab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
