package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.view.dialog.DialogHelper;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.KLineFragment;
import org.gmfbilu.superapp.module_view.kLine.MPChart.SimpleFragmentPagerAdapter;
import org.gmfbilu.superapp.module_view.shape.ShapeFragment;
import org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.datePicker.DoubleDatePicker;
import org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.datePicker.SingleDataPicker;
import org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.locationLinkage.WheelView;
import org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.search.SearchAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

/**
 * 卡片式布局,继承自framelayout
 * app:cardBackgroundColor="@color/colorPrimary"，设置背景色
 * app:cardPreventCornerOverlap="true"，取消5.0以下版本的padding
 * app:cardUseCompatPadding="true"，保证5.0以上的版本和低版本一致
 * 点击效果
 * android:clickable="true",
 * android:foreground="?attr/selectableItemBackground"
 * app:contentPadding="16dp"，设置padding
 * app:cardCornerRadius="2dp"，圆角效果
 * app:cardElevation="2dp"，Z轴阴影
 */
public class Switch_CheckBox_ListPreferenceFragment extends BaseFragment {

    private Toolbar toolbar;

    private RadioGroup rg;
    private ViewPager vp_rg;


    public static Switch_CheckBox_ListPreferenceFragment newInstance() {
        Bundle args = new Bundle();
        Switch_CheckBox_ListPreferenceFragment fragment = new Switch_CheckBox_ListPreferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        toolbar = view.findViewById(R.id.module_googlelibrary_toolbar);
        view.findViewById(R.id.bt_singleSelected).setOnClickListener(this);
        view.findViewById(R.id.bt_multiSelected).setOnClickListener(this);
        view.findViewById(R.id.bt_progress).setOnClickListener(this);
        view.findViewById(R.id.bt_progress2).setOnClickListener(this);
        view.findViewById(R.id.bt_DatePicker).setOnClickListener(this);
        view.findViewById(R.id.bt_singleDatePicker).setOnClickListener(this);
        view.findViewById(R.id.bt_doubleDatePicker).setOnClickListener(this);
        view.findViewById(R.id.bt_locationLinkage).setOnClickListener(this);
        view.findViewById(R.id.bt_search).setOnClickListener(this);
        view.findViewById(R.id.bt_TimePicker).setOnClickListener(this);
        view.findViewById(R.id.bt_BottomSheetDialog).setOnClickListener(this);
        view.findViewById(R.id.bt_FullscreenDialog).setOnClickListener(this);
        view.findViewById(R.id.bt_AlertDialog).setOnClickListener(this);
        view.findViewById(R.id.CardView).setOnClickListener(this);
        rg = view.findViewById(R.id.rg);
        vp_rg = view.findViewById(R.id.vp_rg);
        radioGroup();
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_switch_checkbox_listpreference;
    }


    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        toolbar.setTitle("Switch,Check Box,List Preference!");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_singleSelected) {
            String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_choice_array);
            int itemSelected = 0;
            new AlertDialog.Builder(_mActivity)
                    .setTitle("")
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(_mActivity, singleChoiceItems[i], Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        } else if (id == R.id.bt_multiSelected) {
            String[] multiChoiceItems = getResources().getStringArray(R.array.dialog_choice_array);
            boolean[] checkedItems = {false, false, false, false, false};
            new AlertDialog.Builder(_mActivity)
                    .setTitle("MultiSelected")
                    .setPositiveButton("ok", null)
                    .setMultiChoiceItems(multiChoiceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            Toast.makeText(_mActivity, which + "", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        } else if (id == R.id.bt_progress) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("进度条对话框...");
            progressDialog.show();
        } else if (id == R.id.bt_progress2) {
            final ProgressDialog horizontalProgressDialog = new ProgressDialog(getContext());
            horizontalProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            horizontalProgressDialog.setMessage("Progress dialog...");
            horizontalProgressDialog.setCancelable(false);
            horizontalProgressDialog.setMax(100);
            horizontalProgressDialog.show();
            new Thread(new Runnable() {
                int progress = 0;

                @Override
                public void run() {
                    while (progress <= 100) {
                        horizontalProgressDialog.setProgress(progress);
                        if (progress == 100) {
                            horizontalProgressDialog.dismiss();
                        }
                        try {
                            Thread.sleep(35);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progress++;
                    }
                }
            }).start();
        } else if (id == R.id.bt_DatePicker) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                    //Toast.makeText(_mActivity, date, Toast.LENGTH_SHORT).show();
                    Toast.makeText(_mActivity, "year=" + year + ", monthOfYear=" + (monthOfYear + 1) + ", dayOfMonth=" + dayOfMonth, Toast.LENGTH_SHORT).show();

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else if (id == R.id.bt_singleDatePicker) {
            SingleDataPicker singleDataPicker = new SingleDataPicker(_mActivity);
            singleDataPicker.show();
            singleDataPicker.addOnDataSelectedListener(new SingleDataPicker.onDataSelectedListener() {
                @Override
                public void onDataSelected(String time) {
                    ToastUtil.show(time);
                }
            });

        } else if (id == R.id.bt_doubleDatePicker) {
            DoubleDatePicker doubleDatePicker = new DoubleDatePicker(_mActivity, true);
            doubleDatePicker.show();
            doubleDatePicker.addOnDataSelectedListener(new DoubleDatePicker.onDataSelectedListener() {
                @Override
                public void onDataSelected(String leftTime, String rightTime) {
                    ToastUtil.show(leftTime + " , " + rightTime);
                }
            });
        } else if (id == R.id.bt_locationLinkage) {
            locationLinkage();
        } else if (id == R.id.bt_search) {
            search();
        } else if (id == R.id.bt_TimePicker) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, i1);
                    String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                    Toast.makeText(_mActivity, time, Toast.LENGTH_SHORT).show();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        } else if (id == R.id.bt_BottomSheetDialog) {
            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(_mActivity);
            View dialogView = _mActivity.getLayoutInflater().inflate(R.layout.module_view_dialog_bottom_sheet, null);
            mBottomSheetDialog.setContentView(dialogView);
            mBottomSheetDialog.show();
        } else if (id == R.id.bt_FullscreenDialog) {
            final Dialog fullscreenDialog = new Dialog(_mActivity, R.style.DialogFullscreen);
            fullscreenDialog.setContentView(R.layout.module_view_dialog_fullscreen);
            fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close).setOnClickListener(v1 -> fullscreenDialog.dismiss());
            fullscreenDialog.show();
        } else if (id == R.id.bt_AlertDialog) {
            String[] item = {"游戏", "运动", "电影", "旅游", "看书"};
            AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity);
            builder.setTitle("请选择");
            builder.setItems(item, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ToastUtil.show("选择了" + item[which]);
                }
            });
            // 取消可以不添加
            //builder.setNegativeButton("取消",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }


    private void locationLinkage() {
        ArrayList<String> mProvinceList = new ArrayList<>();
        Map<Integer, ArrayList<String>> mcity = new HashMap<Integer, ArrayList<String>>();
        for (int i = 0; i < 26; i++) {
            mProvinceList.add("A" + i);
            ArrayList<String> mCityList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                mCityList.add("B" + j);
            }
            mcity.put(i, mCityList);
        }

        DialogHelper calDialog = new DialogHelper(_mActivity);
        View layout = calDialog.setLocationLinkageLayout(R.layout.module_view_dialog_location_linkage);
        TextView tv_location = layout.findViewById(R.id.tv_location);
        WheelView wheelViewProvince = layout.findViewById(R.id.wheelViewProvince);
        WheelView wheelViewCity = layout.findViewById(R.id.wheelViewCity);
        layout.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calDialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity, wheelViewProvince.getSelectedText() + wheelViewCity.getSelectedText(), Toast.LENGTH_SHORT).show();
                calDialog.dismiss();
            }
        });
        wheelViewProvince.setData(mProvinceList);
        wheelViewProvince.setDefault(0);
        wheelViewCity.setData(mcity.get(0));
        wheelViewCity.setDefault(0);
        tv_location.setText(mProvinceList.get(0) + mcity.get(0).get(0));
        wheelViewProvince.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wheelViewCity.refreshData(mcity.get(id));
                        wheelViewCity.setDefault(0);
                        tv_location.setText(text + wheelViewCity.getSelectedText());
                    }
                });
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        wheelViewCity.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_location.setText(wheelViewProvince.getSelectedText() + text);
                    }
                });
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        if (!calDialog.isShowing()) {
            calDialog.show();
        }
    }


    private void search() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("汉族");
        strings.add("阿昌族");
        strings.add("白族");
        strings.add("保安族");
        strings.add("布朗族");
        strings.add("布依族");
        strings.add("朝鲜族");
        strings.add("达斡尔族");
        strings.add("傣族");
        strings.add("德昂族");
        strings.add("东乡族");
        strings.add("侗族");
        strings.add("侗族");
        strings.add("独龙族");
        strings.add("俄罗斯族");
        strings.add("鄂伦春族");
        strings.add("鄂温克族");
        strings.add("高山族");
        strings.add("仡佬族");
        strings.add("哈尼族");
        strings.add("哈萨克族");
        strings.add("赫哲族");
        strings.add("回族");
        strings.add("基诺族");
        strings.add("京族");
        strings.add("景颇族");
        strings.add("柯尔克孜族");
        strings.add("拉祜族");
        strings.add("黎族");
        strings.add("傈僳族");
        strings.add("珞巴族");
        strings.add("满族");
        strings.add("毛南族");
        strings.add("门巴族");
        strings.add("蒙古族");
        strings.add("苗族");
        strings.add("仫佬族");
        strings.add("纳西族");
        strings.add("怒族");
        strings.add("普米族");
        strings.add("羌族");
        strings.add("撒拉族");
        strings.add("畲族");
        strings.add("水族");
        strings.add("塔吉克族");
        strings.add("塔塔尔族");
        strings.add("土家族");
        strings.add("土族");
        strings.add("佤族");
        strings.add("维吾尔族");
        strings.add("乌孜别克族");
        strings.add("锡伯族");
        strings.add("瑶族");
        strings.add("彝族");
        strings.add("裕固族");
        strings.add("藏族");
        strings.add("壮族");
        DialogHelper calDialog = new DialogHelper(_mActivity);
        View layout = calDialog.setSearchLayout(R.layout.module_view_dialog_search);
        SearchView searchView = layout.findViewById(R.id.searchView);
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        searchView.setIconified(false);
        BaseRecyclerView recyclerView = layout.findViewById(R.id.RecyclerView);
        SearchAdapter searchAdapter = new SearchAdapter(_mActivity);
        recyclerView.setRefreshing(false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.addAll(strings);
        searchAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = searchAdapter.getAllData().get(position);
                int rawPosition = rawPosition(s, strings);
                Toast.makeText(_mActivity, rawPosition + ",  " + s, Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //onQueryTextSumit即是你提交搜索是调用的方法,好像query为null的时候不会回调
            @Override
            public boolean onQueryTextSubmit(String query) {
                Logger.d(query);
                if (query.isEmpty()) {
                    searchAdapter.clear();
                    searchAdapter.addAll(strings);
                } else {
                    ArrayList<String> arrayList = query(query, strings);
                    int size = arrayList.size();
                    searchAdapter.clear();
                    if (size != 0) {
                        searchAdapter.addAll(arrayList);
                    }
                }
                return false;
            }

            //输入的变化
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchAdapter.clear();
                    searchAdapter.addAll(strings);
                }
                return false;
            }
        });
        if (!calDialog.isShowing()) {
            calDialog.show();
        }
    }


    private ArrayList<String> query(@NonNull String query, @NonNull ArrayList<String> allData) {
        ArrayList<String> resault = new ArrayList<>();
        int size = allData.size();
        for (int i = 0; i < size; i++) {
            String item = allData.get(i);
            //第一种是完全不一样
            //第二种是搜索内容包含于item内容   query<=item
            //第三种是item内容包含于搜索内容   query>item
            //第四种是搜索内容和item内容有相同部分
            /*char querys[] = query.toCharArray();
            char items[] = item.toCharArray();
            int querys_length = querys.length;
            int items_length = items.length;
            for (int i1 = 0; i1 < querys_length; i1++) {
                char a = querys[i];
                for (int i2 = 0; i2 < items_length; i2++) {
                    char b = items[i2];
                    if (a == b) {
                        resault.add(item);
                        break;
                    }
                }
            }*/
            if (item.contains(query)) {
                resault.add(item);
            }

        }
        return resault;
    }

    private int rawPosition(@NonNull String resault, @NonNull ArrayList<String> rawAll) {
        int size = rawAll.size();
        for (int i = 0; i < size; i++) {
            String s = rawAll.get(i);
            if (resault.equals(s)) {
                return i;
            }
        }
        return 0;
    }


    private void radioGroup() {
        Fragment[] items = {ShapeFragment.newInstance(), KLineFragment.newInstance()};
        vp_rg.setAdapter(new SimpleFragmentPagerAdapter(_mActivity.getSupportFragmentManager(), items));
        vp_rg.setCurrentItem(0, false);
        vp_rg.setOffscreenPageLimit(items.length);
        rg.check(R.id.rb1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb1) {
                    vp_rg.setCurrentItem(0);
                } else if (checkedId == R.id.rb2) {
                    vp_rg.setCurrentItem(1);
                }
            }
        });
        vp_rg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rg.check(R.id.rb1);
                        break;
                    case 1:
                        rg.check(R.id.rb2);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
