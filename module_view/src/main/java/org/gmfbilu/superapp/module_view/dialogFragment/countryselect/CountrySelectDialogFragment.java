package org.gmfbilu.superapp.module_view.dialogFragment.countryselect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.view.dialogFragment.BaseDialogFragment;
import org.gmfbilu.superapp.lib_base.view.dialogFragment.DialogFragmentViewHolder;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CountrySelectDialogFragment extends BaseDialogFragment implements IndexBar.OnIndexChangedListener {

    private AppCompatEditText et_search;
    private IndexBar ibIndicator;
    private TextView tvIndicator;
    private RecyclerView recyclerView;
    private CountrySelectAdapter countrySelectAdapter;
    private ArrayList<CountrySection> mData = new ArrayList<>();


    public static CountrySelectDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        CountrySelectDialogFragment dialog = new CountrySelectDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public int intLayoutId() {
        return R.layout.module_view_dialogfragment_country_select;
    }

    @Override
    public void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {
        holder.setOnClickListener(R.id.iV_close, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        et_search = holder.getView(R.id.et_search);
        ibIndicator = holder.getView(R.id.ib_indicator);
        tvIndicator = holder.getView(R.id.tv_indicator);
        recyclerView = holder.getView(R.id.recyclerView);
        ibIndicator.setOnIndexChangedListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        countrySelectAdapter = new CountrySelectAdapter(R.layout.module_view_recyclerview_item_country_select, R.layout.module_view_recyclerview_item_country_select_head, null);
        //DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, AppUtils.dp2px(1f), AppUtils.dp2px(14f), AppUtils.dp2px(14f));
        //itemDecoration.setDrawLastItem(false);
        //CountrySelectDecoration countrySelectDecoration = new CountrySelectDecoration(AppUtils.dp2px(4f),AppUtils.dp2px(4f),AppUtils.dp2px(4f),AppUtils.dp2px(4f));
        //recyclerView.addItemDecoration(itemDecoration);
        countrySelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CountrySection item = (CountrySection) adapter.getItem(position);
                if (!item.isHeader) {
                    Country country = item.t;
                    LoggerUtil.d(country.name);
                }else {
                    LoggerUtil.d(item.header);
                }
            }
        });
        recyclerView.setAdapter(countrySelectAdapter);
        Single.create(new SingleOnSubscribe<ArrayList<CountrySection>>() {
            @Override
            public void subscribe(SingleEmitter<ArrayList<CountrySection>> emitter) throws InterruptedException {
                //只能发射一条单一的数据
                Thread.sleep(200);
                emitter.onSuccess(getData());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<CountrySection>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<CountrySection> integer) {
                        countrySelectAdapter.addData(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private ArrayList<CountrySection> getData() {
        mData.add(new CountrySection(true, "推荐"));
        mData.add(new CountrySection(new Country("中国", "+086")));
        mData.add(new CountrySection(new Country("香港", "+000")));
        mData.add(new CountrySection(new Country("台湾", "+111")));
        mData.add(new CountrySection(new Country("澳门", "+0")));
        mData.add(new CountrySection(true, "A"));
        mData.add(new CountrySection(new Country("美国", "+1")));
        mData.add(new CountrySection(new Country("日本", "+2")));
        mData.add(new CountrySection(new Country("德国", "+086")));
        mData.add(new CountrySection(true, "B"));
        mData.add(new CountrySection(new Country("英国", "+086")));
        mData.add(new CountrySection(new Country("肯尼亚", "+086")));
        mData.add(new CountrySection(true, "C"));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(true, "D"));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(true, "E"));
        mData.add(new CountrySection(new Country("美国", "+1")));
        mData.add(new CountrySection(new Country("日本", "+2")));
        mData.add(new CountrySection(new Country("德国", "+086")));
        mData.add(new CountrySection(true, "F"));
        mData.add(new CountrySection(new Country("英国", "+086")));
        mData.add(new CountrySection(new Country("肯尼亚", "+086")));
        mData.add(new CountrySection(true, "G"));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(true, "H"));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(true, "I"));
        mData.add(new CountrySection(new Country("美国", "+1")));
        mData.add(new CountrySection(new Country("日本", "+2")));
        mData.add(new CountrySection(new Country("德国", "+086")));
        mData.add(new CountrySection(true, "J"));
        mData.add(new CountrySection(new Country("英国", "+086")));
        mData.add(new CountrySection(new Country("肯尼亚", "+086")));
        mData.add(new CountrySection(true, "K"));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(true, "M"));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(true, "N"));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(true, "O"));
        mData.add(new CountrySection(new Country("长沙", "+086")));
        mData.add(new CountrySection(new Country("津巴布韦", "+086")));
        mData.add(new CountrySection(new Country("韩国", "+086")));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(true, "P"));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        mData.add(new CountrySection(true, "M"));
        mData.add(new CountrySection(new Country("武汉", "+086")));
        return mData;
    }

    @Override
    public void onIndexChanged(String index, boolean isDown) {
        LoggerUtil.d(index);
        int position = -1;
        for (CountrySection countrySection : mData) {
            if (TextUtils.equals(countrySection.header, index)) {
                position = mData.indexOf(countrySection);
                break;
            }
        }
        if (position != -1) {
            recyclerView.scrollToPosition(position);
        }
        tvIndicator.setText(index);
        tvIndicator.setVisibility(isDown ? View.VISIBLE : View.GONE);
    }
}
