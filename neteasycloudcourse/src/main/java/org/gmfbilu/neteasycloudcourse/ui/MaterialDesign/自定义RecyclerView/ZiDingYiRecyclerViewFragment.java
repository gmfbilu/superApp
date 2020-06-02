package org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.自定义RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * https://www.jianshu.com/p/1806ed9737f6
 *
 * 父容器实现的接口，CoordinatorLayout实现了此接口
 * public interface NestedScrollingParent {
 * <p>
 * boolean onStartNestedScroll(@NonNull View child,@NonNull View target,@ScrollAxis int axes);
 * void onNestedScrollAccepted(@NonNull View child,@NonNull View target,@ScrollAxis int axes);
 * void onStopNestedScroll(@NonNull View target);
 * void onNestedScroll(@NonNull View target,int dxConsumed,int dyConsumed,int dxUnconsumed,int dyUnconsumed);
 * void onNestedPreScroll(@NonNull View target,int dx,int dy,@NonNull int[]consumed);
 * boolean onNestedFling(@NonNull View target,float velocityX,float velocityY,boolean consumed);
 * boolean onNestedPreFling(@NonNull View target,float velocityX,float velocityY);
 * int getNestedScrollAxes();
 * <p>
 * }
 * <p>
 * 子控件实现的接口，RecyclerView实现了此接口
 * public interface NestedScrollingChild {
 * <p>
 * void setNestedScrollingEnabled(boolean enabled);
 * boolean isNestedScrollingEnabled();
 * boolean startNestedScroll(@ScrollAxis int axes);
 * void stopNestedScroll();
 * boolean hasNestedScrollingParent();
 * boolean dispatchNestedScroll(int dxConsumed,int dyConsumed,
 * int dxUnconsumed,int dyUnconsumed,@Nullable int[]offsetInWindow);
 * boolean dispatchNestedPreScroll(int dx,int dy,@Nullable int[]consumed,@Nullable int[]offsetInWindow);
 * boolean dispatchNestedFling(float velocityX,float velocityY,boolean consumed);
 * boolean dispatchNestedPreFling(float velocityX,float velocityY);
 * <p>
 * }
 *
 */
public class ZiDingYiRecyclerViewFragment extends BaseFragment {

    private RecyclerView recyclerview;
    private FeedAdapter mFeedAdapter;
    private ConstraintLayout suspension_Bar;
    private ImageView suspension_iv;
    private FloatingActionButton fa;

    private int height;
    private int mCurrentPosition;

    public static ZiDingYiRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        ZiDingYiRecyclerViewFragment fragment = new ZiDingYiRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerview = view.findViewById(R.id.recyclerview);
        suspension_Bar = view.findViewById(R.id.suspension_Bar);
        suspension_iv = view.findViewById(R.id.suspension_iv);
        fa = view.findViewById(R.id.fa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(new FeedAdapter());
        recyclerview.setHasFixedSize(true);
        //监听recyclerview的滑动
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取悬浮条的高度
                height = suspension_Bar.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //调整悬浮条的高度
                //获取下一个item显示的位置进行调整
                View nextView = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (nextView != null) {
                    if (nextView.getTop() < height) {
                        //调整悬浮条的位置，负值就是往上移动
                        suspension_Bar.setTranslationY(nextView.getTop() - height);
                    } else {
                        //悬浮条的位置回到初始位置
                        suspension_Bar.setTranslationY(0);
                    }
                }
                //判断是否是否需要更新悬浮条中的内容
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    update();
                }
            }
        });
        update();
    }

    private void update() {
        String url = "";
        switch (mCurrentPosition % 3) {
            case 0:
                url = "http://n.sinaimg.cn/gd/transform/266/w640h426/20190319/xN4w-hukwxnv4651870.jpg";
                break;
            case 1:
                url = "http://n.sinaimg.cn/translate/w1080h721/20180212/7IKJ-fyrkuxt6100678.jpg";
                break;
            case 2:
                url = "http://news.xinhuanet.com/food/titlepic/129473020_1486619330222_title1n.jpg";
                break;
        }
        Glide.with(BaseApplication.mApplicationContext).load(url).into(suspension_iv);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui_materialdesign_zidingyirecyclerview;
    }

    @Override
    public void onClick(View v) {

    }
}
