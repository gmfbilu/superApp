package org.gmfbilu.superapp.module_googlelibrary.switch_checkbox_listpreference;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_googlelibrary.R;

import java.text.DateFormat;
import java.util.Calendar;

public class Switch_CheckBox_ListPreferenceFragment extends BaseFragment {

    private Toolbar toolbar;


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
        view.findViewById(R.id.bt_TimePicker).setOnClickListener(this);
        view.findViewById(R.id.bt_BottomSheetDialog).setOnClickListener(this);
        view.findViewById(R.id.bt_FullscreenDialog).setOnClickListener(this);
        view.findViewById(R.id.CardView).setOnClickListener(this);

    }

    @Override
    public int setLayout() {
        return R.layout.module_googlelibrary_fragment_switch_checkbox_listpreference;
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
        }else if (id==R.id.bt_DatePicker){
            Calendar calendar =Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                    Toast.makeText(_mActivity, date , Toast.LENGTH_SHORT).show();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if (id==R.id.bt_TimePicker){
            Calendar calendar =Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, i1);
                    String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                    Toast.makeText(_mActivity, time , Toast.LENGTH_SHORT).show();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }else if (id==R.id.bt_BottomSheetDialog){
            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(_mActivity);
            View dialogView = _mActivity.getLayoutInflater().inflate(R.layout.module_googlelibrary_dialog_bottom_sheet, null);
            mBottomSheetDialog.setContentView(dialogView);
            mBottomSheetDialog.show();
        }else if (id==R.id.bt_FullscreenDialog){
            final Dialog fullscreenDialog = new Dialog(_mActivity, R.style.DialogFullscreen);
            fullscreenDialog.setContentView(R.layout.module_googlelibrary_dialog_fullscreen);
            fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close).setOnClickListener(v1 -> fullscreenDialog.dismiss());
            fullscreenDialog.show();
        }
    }
}
