package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.simple;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class SimpleAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {

    public SimpleAdapter(ArrayList<String> data) {
        super(R.layout.module_view_recyclerview_item_bras_simple,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
    }

}
