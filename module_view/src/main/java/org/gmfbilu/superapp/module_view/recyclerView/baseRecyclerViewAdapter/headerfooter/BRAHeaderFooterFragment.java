package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.headerfooter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class BRAHeaderFooterFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private HeaderFooterAdapter mHeaderFooterAdapter;

    public static BRAHeaderFooterFragment newInstance() {
        Bundle bundle = new Bundle();
        BRAHeaderFooterFragment braSimpleFragment = new BRAHeaderFooterFragment();
        braSimpleFragment.setArguments(bundle);
        return braSimpleFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_header_footer;
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
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mHeaderFooterAdapter = new HeaderFooterAdapter(R.layout.module_view_recyclerview_item_bras_simple, null);
        recyclerView.setAdapter(mHeaderFooterAdapter);
        mHeaderFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int headerLayoutCount = mHeaderFooterAdapter.getHeaderLayoutCount();
                int footerLayoutCount = mHeaderFooterAdapter.getFooterLayoutCount();
                //这个框架直接过滤的值，Adapter.getItem(position)不管有没有Header都会得到正确的值
                //Adapter.getHeaderLayoutCount()和Adapter.getFooterLayoutCount()都一直是1，why?
                LoggerUtil.d("Header有" + headerLayoutCount + "个，Foot有" + footerLayoutCount + "个，点击的是第" + position + "，值是" + mHeaderFooterAdapter.getItem(position));
            }
        });

        mHeaderFooterAdapter.addData(getData());

        //inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        //如果root为null，attachToRoot将失去作用，设置任何值都没有意义
        //如果root不为null，attachToRoot设为true，则会给加载的布局文件的指定一个父布局，即root
        //如果root不为null，attachToRoot设为false，则会将布局文件最外层的所有layout属性进行设置，当该view被添加到父view当中时，这些layout属性会自动生效
        //在不设置attachToRoot参数的情况下，如果root不为null，attachToRoot参数默认为true
        View headerOne = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_bra, null);
        mHeaderFooterAdapter.addHeaderView(headerOne, 0);
        TextView tv_header = headerOne.findViewById(R.id.tv_header);
        tv_header.setText("deam ,i am header one");

        View headerTwo = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_bra, null);
        mHeaderFooterAdapter.addHeaderView(headerTwo, 1);
        TextView tv_header1 = headerTwo.findViewById(R.id.tv_header);
        tv_header1.setText("deam ,i am header two");

        View footOne = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_bra, null);
        mHeaderFooterAdapter.addFooterView(footOne, 0);
        TextView tv_header2 = footOne.findViewById(R.id.tv_header);
        tv_header2.setText("deam ,i am foot one");

        View footTwo = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_bra, null);
        mHeaderFooterAdapter.addFooterView(footTwo, 1);
        TextView tv_header3 = footTwo.findViewById(R.id.tv_header);
        tv_header3.setText("deam ,i am foot two");
    }

    private ArrayList<String> getData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }
        return strings;
    }


}
