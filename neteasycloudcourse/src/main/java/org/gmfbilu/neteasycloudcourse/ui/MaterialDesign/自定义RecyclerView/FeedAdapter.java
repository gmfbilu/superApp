package org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.自定义RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //承载每个子view的布局
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_feed, parent, false);
        return new FeedViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        String url = "";
        switch (position % 3) {
            case 0:
                url="http://n.sinaimg.cn/gd/transform/266/w640h426/20190319/xN4w-hukwxnv4651870.jpg";
                break;
            case 1:
                url="http://n.sinaimg.cn/translate/w1080h721/20180212/7IKJ-fyrkuxt6100678.jpg";
                break;
            case 2:
                url="http://news.xinhuanet.com/food/titlepic/129473020_1486619330222_title1n.jpg";
                break;
        }
        Glide.with(BaseApplication.mApplicationContext).load(url).into(holder.iv);
        Glide.with(BaseApplication.mApplicationContext).load(url).into(holder.iv_small);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private ImageView iv_small;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            iv_small = itemView.findViewById(R.id.iv_small);
        }
    }
}
