package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * 插入动画
 */
public class FadeInItemAnimator extends SimpleItemAnimator {

    private List<RecyclerView.ViewHolder> pendingAddViewHolderList = new ArrayList<>();
    private List<RecyclerView.ViewHolder> addAnimationViewHolderList = new ArrayList<>();

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        View view = holder.itemView;
        ViewCompat.setAlpha(view, 0);
        pendingAddViewHolderList.add(holder);
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }

    @Override
    public void runPendingAnimations() {
        for (int i = 0; i < pendingAddViewHolderList.size(); i++) {
            RecyclerView.ViewHolder viewHolder = pendingAddViewHolderList.get(i);
            addAnimationViewHolderList.add(viewHolder);
            View view = viewHolder.itemView;
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(view);
            animator.alpha(1).setDuration(1000).setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    animator.setListener(null);
                    ViewCompat.setAlpha(view, 1);
                    dispatchAddFinished(viewHolder);
                    addAnimationViewHolderList.remove(viewHolder);
                    if (!isRunning()) {
                        dispatchAnimationsFinished();
                    }
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
        }
        pendingAddViewHolderList.clear();
    }

    @Override
    public void endAnimation(@NonNull RecyclerView.ViewHolder item) {
        View view = item.itemView;
        ViewCompat.animate(view).cancel();
        for (RecyclerView.ViewHolder viewHolder : pendingAddViewHolderList) {
            if (viewHolder == item) {
                pendingAddViewHolderList.remove(viewHolder);
                ViewCompat.setAlpha(view, 1);
                dispatchAddFinished(item);
            }
        }
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    @Override
    public void endAnimations() {
        for (RecyclerView.ViewHolder viewHolder : pendingAddViewHolderList) {
            View view = viewHolder.itemView;
            pendingAddViewHolderList.remove(viewHolder);
            ViewCompat.setAlpha(view, 1);
            dispatchAddFinished(viewHolder);
        }

        for (RecyclerView.ViewHolder viewHolder : addAnimationViewHolderList) {
            View view = viewHolder.itemView;
            ViewCompat.animate(view).cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return !pendingAddViewHolderList.isEmpty() || !addAnimationViewHolderList.isEmpty();
    }

}
