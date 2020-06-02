package org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.自定义RecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior {

    private Interpolator Inter;
    private boolean isRunning;

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Inter = new AccelerateDecelerateInterpolator();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只有返回true后续的事件才会触发，此案例中只需要Y轴方向滑动
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL; //垂直滚动
    }

    /**
     * @param coordinatorLayout 父布局
     * @param child             当前Behavior设置给谁，谁就是child
     * @param target
     * @param dxConsumed        x方向消费了多少
     * @param dyConsumed
     * @param dxUnconsumed      x方向还有多少没有消费
     * @param dyUnconsumed
     * @param type
     * @param consumed
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        if (dyConsumed > 0 && !isRunning && child.getVisibility() == View.VISIBLE) {
            //上滑，缩小隐藏动画
            ViewCompat.animate(child).alpha(0).scaleX(0).scaleY(0).setInterpolator(Inter).setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    isRunning = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    isRunning = false;
                    child.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(View view) {
                    isRunning = false;
                }
            }).setDuration(150).start();
        } else if (dyConsumed < 0 && !isRunning && child.getVisibility() == View.INVISIBLE) {
            child.setVisibility(View.VISIBLE);
            //下滑，放大显示动画
            ViewCompat.animate(child).alpha(1).scaleX(1).scaleY(1).setInterpolator(Inter).setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    isRunning = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    isRunning = false;
                }

                @Override
                public void onAnimationCancel(View view) {
                    isRunning = false;
                }
            }).setDuration(150).start();
        }
    }
}
