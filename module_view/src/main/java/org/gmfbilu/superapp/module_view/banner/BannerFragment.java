package org.gmfbilu.superapp.module_view.banner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class BannerFragment extends BaseFragment {

    private MZBannerView banner_normal;
    private MZBannerView banner_meizu;
    public static final int[] BANNER = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3, R.mipmap.banner4, R.mipmap.banner5};

    public static BannerFragment newInstance() {
        Bundle args = new Bundle();
        BannerFragment fragment = new BannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        banner_normal = view.findViewById(R.id.banner_normal);
        banner_meizu = view.findViewById(R.id.banner_meizu);
        initBanner();
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bannar;
    }

    @Override
    public void onClick(View v) {

    }

    private void initBanner() {
        List<Integer> list = new ArrayList<>();
        for (int value : BANNER) {
            list.add(value);
        }
        banner_normal.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Integer integer = list.get(i);
                ToastUtil.show(integer + "");
            }
        });
        banner_normal.setPages(list, (MZHolderCreator<BannerViewHolder>) BannerViewHolder::new);


        banner_meizu.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Integer integer = list.get(i);
                ToastUtil.show(integer + "");
            }
        });
        banner_meizu.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LoggerUtil.e("----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                LoggerUtil.e("addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner_meizu.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        //banner_meizu.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        //banner_meizu.setIndicatorPadding(10,0,0,150);
        banner_meizu.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.module_view_banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner_normal.pause();
        banner_meizu.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner_normal.start();
        banner_meizu.start();
    }
}
