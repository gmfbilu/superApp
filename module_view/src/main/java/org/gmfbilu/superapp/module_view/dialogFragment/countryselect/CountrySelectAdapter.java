package org.gmfbilu.superapp.module_view.dialogFragment.countryselect;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.List;

public class CountrySelectAdapter extends BaseSectionQuickAdapter<CountrySection, BaseViewHolder> {


    public CountrySelectAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }


    @Override
    protected void convertHead(BaseViewHolder helper, CountrySection item) {
        TextView tv_group = helper.getView(R.id.tv_group);
        tv_group.setText(item.header);
    }


    @Override
    protected void convert(BaseViewHolder helper, CountrySection item) {
        Country country = (Country) item.t;
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_phone = helper.getView(R.id.tv_phone);
        tv_name.setText(country.name);
        tv_phone.setText(country.phone);
    }
}
