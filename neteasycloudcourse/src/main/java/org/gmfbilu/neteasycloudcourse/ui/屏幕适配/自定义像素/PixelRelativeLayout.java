package org.gmfbilu.neteasycloudcourse.ui.屏幕适配.自定义像素;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 自定义像素适配，缺点是所有的容器控件都需要自定义
 * 此自定义布局中的子控件直接使用px即可
 */
public class PixelRelativeLayout extends RelativeLayout {

    private boolean flag;

    public PixelRelativeLayout(Context context) {
        super(context);
    }

    public PixelRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PixelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!flag) {
            flag = true;
            //只执行一次
            float scaleX = PixelShiPeiUtil.getInstance(getContext()).getHorizontalScale();
            float scaleY = PixelShiPeiUtil.getInstance(getContext()).getVerticalScale();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin = (int) (params.leftMargin * scaleX);
                params.rightMargin = (int) (params.rightMargin * scaleX);
                params.topMargin = (int) (params.topMargin * scaleY);
                params.bottomMargin = (int) (params.bottomMargin * scaleY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
