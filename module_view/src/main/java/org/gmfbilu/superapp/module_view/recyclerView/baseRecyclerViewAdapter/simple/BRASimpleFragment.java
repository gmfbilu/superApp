package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.simple;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BRASimpleFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SimpleAdapter mSimpleAdapter;

    public static BRASimpleFragment newInstance() {
        Bundle bundle = new Bundle();
        BRASimpleFragment braSimpleFragment = new BRASimpleFragment();
        braSimpleFragment.setArguments(bundle);
        return braSimpleFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_simple;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        //如果Item的高度一致，可以设置setHasFixedSize(true)，这个方法的意义就是当调用onItemRangeChanged()、onItemRangeInserted()、onItemRangeRemoved()、onItemRangeMoved()这几个方法时，可以避免重复的requestLayout，节省资源
        recyclerView.setHasFixedSize(true);
        //如果不要求动画，把默认动画关闭来提升效率
        //((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mSimpleAdapter = new SimpleAdapter(R.layout.module_view_recyclerview_item_bras_simple, null);
        //嵌套recycleView的情况下需要使用你使用 adapter. setOnItemClickListener 来设置点击事件,如果使用recycleView.addOnItemTouchListener会累计添加的
        mSimpleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show("onItemClick：点击了第" + position + "个");
            }
        });
        //Item子控件的点击事件,在adapter中绑定,如果有header的话需要处理一下position加上 headerlayoutcount
        mSimpleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int headerLayoutCount = adapter.getHeaderLayoutCount();
                position = position + headerLayoutCount;
                String item = (String) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.tweetName) {
                    ToastUtil.show("onItemChildClick：点击了第" + position + "--->" + item);
                }
                //获取其他子控件
                //adapter.getViewByPosition(recyclerView, );
            }
        });
        //Item子控件的长按事件,在adapter中绑定,如果有header的话需要处理一下position加上 headerlayoutcount
        mSimpleAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                int headerLayoutCount = adapter.getHeaderLayoutCount();
                position = position + headerLayoutCount;
                String item = (String) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.tweetDate) {
                    ToastUtil.show("onItemChildLongClick：点击了第" + position + "--->" + item);
                }
                return false;
            }
        });
        //开启动画(默认为渐显效果),默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        mSimpleAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //动画默认只执行一次,如果想重复执行可设置
        mSimpleAdapter.isFirstOnly(false);
        //自定义动画
        mSimpleAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[0];
            }
        });
        recyclerView.setAdapter(mSimpleAdapter);
        //模拟网络请求，实际上是数据成功后再去初始化recyclerView
        Single.create(new SingleOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(SingleEmitter<ArrayList<String>> emitter) throws InterruptedException {
                //只能发射一条单一的数据
                Thread.sleep(200);
                emitter.onSuccess(getStringData(10));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<String> integer) {
                        mSimpleAdapter.addData(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private ArrayList<String> getStringData(int i) {
        ArrayList<String> data = new ArrayList<>();
        for (int i1 = 0; i1 < i; i1++) {
            data.add("" + i);
        }
        return data;
    }
}
