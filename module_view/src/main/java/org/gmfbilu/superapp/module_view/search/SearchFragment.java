package org.gmfbilu.superapp.module_view.search;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;
import org.gmfbilu.superapp.lib_base.view.flowLayout.FlowLayout;
import org.gmfbilu.superapp.lib_base.view.flowLayout.TagAdapter;
import org.gmfbilu.superapp.lib_base.view.flowLayout.TagFlowLayout;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.search.viewholder.SearchHotViewHolder;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment {

    private AppCompatEditText et_search;
    private TextView tv_goods;
    private TextView tv_shops;
    private TextView tv_local;
    private ImageView iv_bottom_line;
    private BaseSearchFragment[] mFragments;
    private int lastIndex = 0;
    private int screenWidth;

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;


    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment sSearchFragment = new SearchFragment();
        sSearchFragment.setArguments(args);
        return sSearchFragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        et_search = view.findViewById(R.id.et_search);
        view.findViewById(R.id.iv_clear_search_word).setOnClickListener(this);
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
        tv_goods = view.findViewById(R.id.tv_goods);
        tv_goods.setOnClickListener(this);
        tv_shops = view.findViewById(R.id.tv_shops);
        tv_shops.setOnClickListener(this);
        tv_local = view.findViewById(R.id.tv_local);
        tv_local.setOnClickListener(this);
        iv_bottom_line = view.findViewById(R.id.iv_bottom_line);

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_search;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_goods) {
            setIndexSelected(0);
        } else if (id == R.id.tv_shops) {
            setIndexSelected(1);
        } else if (id == R.id.tv_local) {
            setIndexSelected(2);
        } else if (id == R.id.iv_clear_search_word) {
            et_search.setText("");
        } else if (id == R.id.tv_search) {
            search();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        screenWidth = AppUtils.getScreenWidth();
        initRecyclerView();
        initFragment();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });
    }


    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBaseRecyclerView.setLayoutManager(layoutManager);
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new SearchHotViewHolder(parent);
            }
        });
        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(_mActivity);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv.setPadding(0, AppUtils.dp2px(20), 0, 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setTextColor(Color.parseColor("#999999"));
                tv.setText("热门搜索");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                LayoutInflater layoutInflater = _mActivity.getLayoutInflater();
                View inflate = layoutInflater.inflate(R.layout.module_view_recyclerview_inflate_footer_search_center_searchhistory, null);
                ImageView iv_clear_search_history = inflate.findViewById(R.id.iv_clear_search_history);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("点此处展示搜索无结果");
                strings.add("搜索历史词条超过十…");
                strings.add("两字");
                strings.add("三个字");
                strings.add("有四个字");
                strings.add("内部左右距10pt");
                strings.add("点此处展示搜索无结果");
                strings.add("搜索历史词条超过十…");
                strings.add("两字");
                strings.add("三个字");
                strings.add("有四个字");
                strings.add("内部左右距10pt");


                LayoutInflater layoutInflater = _mActivity.getLayoutInflater();
                View inflate = layoutInflater.inflate(R.layout.module_view_search_history, null);
                TagFlowLayout id_flowlayout = inflate.findViewById(R.id.id_flowlayout);
                id_flowlayout.setAdapter(new TagAdapter<String>(strings) {

                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        ConstraintLayout constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.module_view_search_history_flow, id_flowlayout, false);
                        TextView viewById = constraintLayout.findViewById(R.id.tv_history);
                        viewById.setText(s);
                        return constraintLayout;
                    }
                });
                id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        et_search.setText(strings.get(position));
                        return true;
                    }
                });
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                et_search.setText(mAdapter.getItem(position));
            }
        });
        ArrayList<String> strings = new ArrayList<>();
        strings.add("搜索历史词条最长十字");
        strings.add("搜索历史词条超过十…");
        strings.add("两字");
        strings.add("有四个字");
        strings.add("有四个字");
        strings.add("搜索历史词条最长十字");
        strings.add("搜索历史词条超过十…");
        strings.add("两字");
        strings.add("有四个字");
        strings.add("有四个字");
        mAdapter.addAll(strings);
    }


    private void initFragment() {
        GoodsFragment goodsFragment = GoodsFragment.newInstance();
        ShopsFragment shopsFragment = ShopsFragment.newInstance();
        LocalFragment localFragment = LocalFragment.newInstance();
        mFragments = new BaseSearchFragment[]{goodsFragment, shopsFragment, localFragment};
        FragmentTransaction ft = _mActivity.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, goodsFragment).commit();
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if (index == lastIndex) {
            return;
        }
        tv_goods.setTextColor(index == 0 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_shops.setTextColor(index == 1 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_local.setTextColor(index == 2 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        float curTranslationX = iv_bottom_line.getTranslationX();//初始坐标，每次移动屏幕宽度/3
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_line, "translationX", curTranslationX, index * screenWidth / 3);//绝对坐标
        animator.setDuration(300);
        animator.start();
        FragmentManager fragmentManager = _mActivity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(mFragments[lastIndex]);
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        lastIndex = index;
    }

    private void search() {
        String keyword = et_search.getText().toString();
        if (StringUtils.isEmpty(keyword)) {
            mBaseRecyclerView.setVisibility(View.VISIBLE);
            return;
        }
        mBaseRecyclerView.setVisibility(View.GONE);
        mFragments[lastIndex].search(lastIndex, keyword);
    }

}
