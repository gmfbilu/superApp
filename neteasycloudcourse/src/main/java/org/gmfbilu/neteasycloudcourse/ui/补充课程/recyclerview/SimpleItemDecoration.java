package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {


    private Drawable divider = BaseApplication.mApplicationContext.getResources().getDrawable(R.drawable.recyclerview_decoration);

    /**
     * 绘制再内容外面
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        //默认第一个最上面和最后一个最下面不显示
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + 1;//间隔线的高度，1个像素
            int left = child.getLeft() + AppUtils.dp2px(15);//距离左边15dp
            int right = child.getRight() - AppUtils.dp2px(15);//距离右边15dp
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }


    /**
     * 会绘制在内容上
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 修改item的边距
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //最后一个item底线的高度
        //outRect.bottom += 10;
    }
}
