package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.section;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class BRASectionFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SectionAdapter mSectionAdapter;

    public static BRASectionFragment newInstance() {
        Bundle args = new Bundle();
        BRASectionFragment fragment = new BRASectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mSectionAdapter = new SectionAdapter(R.layout.module_view_recyclerview_item_bras_simple, R.layout.module_view_recyclerview_item_simple, null);
        mSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SectionBean sectionBean = mSectionAdapter.getData().get(position);
                if (sectionBean.isHeader) {

                } else {

                }
                LoggerUtil.d("position:" + position + ", " + sectionBean);

            }
        });
        recyclerView.setAdapter(mSectionAdapter);
        mSectionAdapter.addData(getData());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_section;
    }

    @Override
    public void onClick(View v) {

    }

    private ArrayList<SectionBean> getData() {
        ArrayList<SectionBean> sectionBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SectionBean sectionBean;
            if (i % 3 == 0) {
                sectionBean = new SectionBean(true, "" + i);
            } else {
                Video video = new Video();
                video.img="http://www.hi.chinanews.com/photo/2019/1209/U57P16T10D109858F274DT20191209152353.jpg";
                sectionBean = new SectionBean(video);
            }
            sectionBeans.add(sectionBean);
        }
        return sectionBeans;
    }
}
