package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.RecyclerViewAdapter;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.RecyclerViewHolder;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.StickyHeadEntity;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration.FullSpanUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 直接继承BaseMultiItemQuickAdapter单独实现一个适配器的写法
 */
public class StockAdapter extends RecyclerViewAdapter<StockEntity.StockInfo, StickyHeadEntity<StockEntity.StockInfo>> implements CompoundButton.OnCheckedChangeListener{

    public final static int TYPE_HEAD = 4;

    public StockAdapter(List<StickyHeadEntity<StockEntity.StockInfo>> data) {
        super(data);
    }


    /**
     * 用于处理GridLayoutManager和StaggeredGridLayoutManager模式下的头部使之占满一行
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, TYPE_STICKY_HEAD);
    }

    /**
     * 用于处理GridLayoutManager和StaggeredGridLayoutManager模式下的头部使之占满一行
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, TYPE_STICKY_HEAD);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            //最上面的头部，类似于Header
            case TYPE_HEAD:
                return R.layout.module_view_recyclerview_item_stock_head;
            //Stick Header,固定的Header
            case TYPE_STICKY_HEAD:
                return R.layout.module_view_recyclerview_item_stock_sticky_head;
            //Item 布局
            case TYPE_DATA:
                return R.layout.module_view_recyclerview_item_stock_data;
        }
        return 0;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int viewType, int position, StockEntity.StockInfo item) {
        int type = holder.getItemViewType();
        switch (type) {
            case TYPE_STICKY_HEAD:
                CheckBox checkBox = holder.getCheckBox(R.id.checkbox);
                checkBox.setTag(position);
                checkBox.setOnCheckedChangeListener(this);
                checkBox.setChecked(item.check);
                holder.setText(R.id.tv_stock_name, item.stickyHeadName);
                break;

            case TYPE_DATA:
                setData(holder, item);
                break;
        }
    }

    private void setData(RecyclerViewHolder holder, StockEntity.StockInfo item) {
        final String stockNameAndCode = item.stock_name + "\n" + item.stock_code;
        SpannableStringBuilder ssb = new SpannableStringBuilder(stockNameAndCode);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#a4a4a7")), item.stock_name.length(), stockNameAndCode.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new AbsoluteSizeSpan(dip2px(holder.itemView.getContext(), 13)), item.stock_name.length(), stockNameAndCode.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.setText(R.id.tv_stock_name_code, ssb).setText(R.id.tv_current_price, item.current_price)
                .setText(R.id.tv_rate, (item.rate < 0 ? String.format("%.2f", item.rate) : "+" + String.format("%.2f", item.rate)) + "%");
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = (Integer) buttonView.getTag();
        mData.get(pos).getData().check = isChecked;
    }

}
