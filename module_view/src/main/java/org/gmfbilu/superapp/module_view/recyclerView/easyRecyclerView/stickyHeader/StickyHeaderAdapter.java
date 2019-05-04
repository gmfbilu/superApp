

package org.gmfbilu.superapp.module_view.recyclerView.easyRecyclerView.stickyHeader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.StickyHeaderDecoration;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;


public class StickyHeaderAdapter implements StickyHeaderDecoration.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder> {


    private LayoutInflater mInflater;
    private ArrayList<String> data;

    public StickyHeaderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public long getHeaderId(int position) {
        String s = data.get(position);
        int i = Integer.parseInt(s);
        if (i < 10) {
            return 0;
        } else if (i == 10) {
            return 1;
        } else if (i < 15) {
            return 2;
        }else {
            return 3;
        }
       // return position / 3;
    }

    public void setData(ArrayList<String> d) {
        data = d;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.module_view_recyclerview_sticky_header_item_header, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.tv_name.setText("第" + getHeaderId(position) + "组");
    }


    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;

        public HeaderHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
