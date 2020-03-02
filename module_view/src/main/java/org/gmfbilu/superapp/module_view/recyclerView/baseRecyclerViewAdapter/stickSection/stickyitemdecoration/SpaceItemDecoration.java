package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.RecyclerViewAdapter;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    public SpaceItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            DividerHelper.drawBottomAlignItem(c, mDivider, child, params);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int type = parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(view));
        if (type != RecyclerViewAdapter.TYPE_DATA && type != RecyclerViewAdapter.TYPE_SMALL_STICKY_HEAD_WITH_DATA) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }

}
