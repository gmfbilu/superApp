package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.multipleItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeSearchItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;


    public HomeSearchItemDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#525252"));
    }

    /**
     * 设置间隔的大小的，修改ourRect这个参数即可，里边有left，right，top，bottom属性
     * 举例,如下，第一个item，有个top 30，那么你看到的效果就是第一个item距离上边有30个间隔
     * <p>
     * 主要作用就是给item的四周加上边距，实现的效果类似于margin，将item的四周撑开一些距离，在撑开这些距离后，我们就可以利用上面的onDraw函数，在这个距离上进行绘图了
     *
     * @param outRect outRect就是表示在item的上下左右所撑开的距离
     * @param view    是指当前Item的View对象
     * @param parent  是指RecyclerView 本身
     * @param state   通过State可以获取当前RecyclerView的状态，也可以通过State在RecyclerView各组件间传递参数
     *                <p>
     *                outRect 中的 top、left、right、bottom四个点，并不是普通意义的坐标点，而是指的在Item上、左、右、下各撑开的距离，这个值默认是0
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        try {
            int pos = parent.getChildAdapterPosition(view);
            int type = Objects.requireNonNull(parent.getAdapter()).getItemViewType(pos);
            if (type == BaseQuickAdapter.EMPTY_VIEW) {
                // 排除空布局
                return;
            } else if (type == BaseQuickAdapter.FOOTER_VIEW) {
                return;
            } else if (type == BaseQuickAdapter.HEADER_VIEW) {
                return;
            } else if (type == BaseQuickAdapter.LOADING_VIEW) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        outRect.bottom = 2;
    }


    /**
     * 这里就是来操作上边弄的间隔的，默认间隔就是个空白
     *
     * @param c      是指通过getItemOffsets撑开的空白区域所对应的画布，通过这个canvas对象，可以在getItemOffsets所撑出来的区域任意绘图
     * @param parent 指RecyclerView 本身
     * @param state  通过State可以获取当前RecyclerView的状态，也可以通过State在RecyclerView各组件间传递参数
     *
     * onDraw绘制的内容是显示在itemView的下层
     * onDrawOver绘制在itemView的上层
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //屏幕可见的数量，软键盘挡住了(不可见)，也包括头布局和尾部局，所以要剔除
        int childCount = parent.getChildCount();
        if (childCount > 0) {
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            for (int i = 0; i < childCount; i++) {
                if (i == childCount - 1) {
                    break;
                }
                View child = parent.getChildAt(i);
                int pos = parent.getChildAdapterPosition(child);
                int type = Objects.requireNonNull(parent.getAdapter()).getItemViewType(pos);
                if (i == childCount - 2) {
                    View child1 = parent.getChildAt(i + 1);
                    int pos1 = parent.getChildAdapterPosition(child1);
                    int type1 = Objects.requireNonNull(parent.getAdapter()).getItemViewType(pos1);
                    if (type1 != type) {
                        //倒数第一个和倒数第二个类型不一致，可能加了footer
                        break;
                    }
                }


                int left = manager.getLeftDecorationWidth(child);//0
                int top = manager.getTopDecorationHeight(child);//0
                int right = manager.getRightDecorationWidth(child);//0
                int bottom = manager.getBottomDecorationHeight(child);//1dp
                //在超出getItemOffsets函数所设定的outRect范围的部分将是不可见的。这是因为在整个绘制流程中，是先调用ItemDecoration的onDraw函数，然后再调用Item的onDraw函数，最后调用ItemDecoration的onDrawOver函数。
                //所以在ItemDecoration的onDraw函数中绘制的内容，当超出边界时，会被Item所覆盖。但是因为最后才调用ItemDecoration的OnDrawOver函数，所以在onDrawOver中绘制的内容就不受outRect边界的限制，可以覆盖Item的区域显示
                //ItemDecoration与Item的绘制顺序为：decoration 的 onDraw->item的 onDraw->decoration 的 onDrawOver，这三者是依次发生的。
                //所以，onDrawOver 是绘制在最上层的，所以它的绘制位置并不受限制
                //所以利用 onDrawOver 可以做很多事情，例如为 RecyclerView 整体顶部绘制一个蒙层、超出itemDecoration的范围绘制图像
                c.drawRect(AppUtils.dp2px(15), child.getTop() + child.getHeight(), child.getRight() - AppUtils.dp2px(15), child.getTop() + child.getHeight() + bottom, mPaint);
                LoggerUtil.e(child.getHeight()+" ,"+child.getWidth()+" ,"+left+" ,"+top+" ,"+right+" ,"+bottom+" ,"+child.getLeft()+" ,"+child.getTop()+" ,"+child.getRight()+" ,"+child.getBottom());
                //child.getHeight()为对应Item的高度，当然不包括ItemDecoration的高度
                //child.getWidth()为对应Item的宽度
                //left对应Item的ItemDecoration种outRect.left(getItemOffsets中设置)，top，right，bottom也类似
                //child.getLeft()为对应Item左边距离RecyclerView左边的距离，child.getTop()为对应Item顶部距离RecyclerView顶部的距离，child.getRight()为对应Item右边距离RecyclerView左边的宽度，child.getBottom()为对应Item底部距离RecyclerView顶部的距离
            }
        }

    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
