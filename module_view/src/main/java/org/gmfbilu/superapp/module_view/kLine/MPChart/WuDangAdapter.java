package org.gmfbilu.superapp.module_view.kLine.MPChart;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.List;


public class WuDangAdapter extends BaseSectionQuickAdapter<WuDangBean, BaseViewHolder> {



    public WuDangAdapter(int layoutResId, int sectionHeadResId, List<WuDangBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, WuDangBean item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, WuDangBean item) {
        TextView tv_One = helper.getView(R.id.tv_One);
        TextView tv_index = helper.getView(R.id.tv_index);
        TextView tv_num = helper.getView(R.id.tv_num);
        WuDang wuDang = item.t;
        tv_One.setText(wuDang.one);
        tv_index.setText(wuDang.two);
        tv_num.setText(wuDang.three);

    }
}
