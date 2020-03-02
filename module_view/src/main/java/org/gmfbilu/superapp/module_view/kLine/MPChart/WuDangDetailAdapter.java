package org.gmfbilu.superapp.module_view.kLine.MPChart;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.List;

import androidx.annotation.Nullable;

public class WuDangDetailAdapter extends BaseQuickAdapter<WuDangDetail, BaseViewHolder> {


    public WuDangDetailAdapter(int layoutResId, @Nullable List<WuDangDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WuDangDetail item) {
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_index = helper.getView(R.id.tv_index);
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_time.setText(item.time);
        tv_index.setText(item.index);
        tv_num.setText(item.num);
    }
}
