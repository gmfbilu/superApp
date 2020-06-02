package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.view.View;
import android.widget.ImageView;

import org.gmfbilu.neteasycloudcourse.R;

import androidx.annotation.NonNull;

public class ImageViewHolder extends MessageViewHolder {

    public ImageView image;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        image  = itemView.findViewById(R.id.iv_image);
    }

}
