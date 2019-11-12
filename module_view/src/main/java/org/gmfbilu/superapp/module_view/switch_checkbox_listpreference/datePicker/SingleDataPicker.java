package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.datePicker;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.dialog.DialogHelper;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SingleDataPicker {

    private Activity _mActivity;
    private DialogHelper mDialogHelper;
    private onDataSelectedListener mOnDataSelectedListener;

    public interface onDataSelectedListener {
        void onDataSelected(String time);
    }

    public void addOnDataSelectedListener(SingleDataPicker.onDataSelectedListener onDataSelectedListener) {
        this.mOnDataSelectedListener = onDataSelectedListener;
    }

    public SingleDataPicker(Activity activity) {
        _mActivity = activity;
        initPicker();
    }

    private void initPicker() {
        mDialogHelper = new DialogHelper(_mActivity);
        View calLayout = mDialogHelper.setCalendarLayout(R.layout.module_view_dialog_single_date);
        TextView tv_time = calLayout.findViewById(R.id.tv_time);
        ScrollChoice scrollChoiceYear = calLayout.findViewById(R.id.ScrollChoiceYear);
        ScrollChoice scrollChoiceMonth = calLayout.findViewById(R.id.ScrollChoiceMonth);
        ScrollChoice scrollChoiceDay = calLayout.findViewById(R.id.ScrollChoiceDay);
        scrollChoiceYear.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                tv_time.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
            }
        });
        scrollChoiceMonth.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                int month = Integer.valueOf(name);
                int day = Integer.parseInt(scrollChoiceDay.getCurrentSelection());
                int year = Integer.parseInt(scrollChoiceYear.getCurrentSelection());
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    if (day > 30) {
                        scrollChoiceDay.addItems(getTotal30Day(), 29);
                    } else {
                        scrollChoiceDay.addItems(getTotal30Day(), day - 1);
                    }
                } else if (month == 2) {
                    if (!leapyear(year)) {
                        if (day > 28) {
                            scrollChoiceDay.addItems(getTotal28Day(), 27);
                        } else {
                            scrollChoiceDay.addItems(getTotal28Day(), day - 1);
                        }
                    } else {
                        if (day > 29) {
                            scrollChoiceDay.addItems(getTotal29Day(), 28);
                        } else {
                            scrollChoiceDay.addItems(getTotal29Day(), day - 1);
                        }
                    }
                } else {
                    scrollChoiceDay.addItems(getTotal31Day(), day - 1);
                }
                tv_time.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
            }
        });
        scrollChoiceDay.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                tv_time.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
            }
        });
        calLayout.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogHelper.dismiss();
            }
        });
        calLayout.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogHelper.dismiss();
                mOnDataSelectedListener.onDataSelected(tv_time.getText().toString());
            }
        });
        scrollChoiceYear.addItems(getTotalYear(), getYearDefaultIndex());
        scrollChoiceMonth.addItems(getTotalMonth(), getMonthDefaultIndex());

        int defaultMonth = Integer.parseInt(scrollChoiceMonth.getCurrentSelection());
        int defaultYear = Integer.parseInt(scrollChoiceYear.getCurrentSelection());
        if (defaultMonth == 4 || defaultMonth == 6 || defaultMonth == 9 || defaultMonth == 11) {
            scrollChoiceDay.addItems(getTotal30Day(), getDayDefaultIndex());
        } else if (defaultMonth == 2) {
            if (!leapyear(defaultYear)) {
                scrollChoiceDay.addItems(getTotal28Day(), getDayDefaultIndex());
            } else {
                scrollChoiceDay.addItems(getTotal29Day(), getDayDefaultIndex());
            }
        } else {
            scrollChoiceDay.addItems(getTotal31Day(), getDayDefaultIndex());
        }
        //配置默认显示时间
        //配置默认显示时间
        tv_time.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
    }

    public void show() {
        if (!mDialogHelper.isShowing()) {
            mDialogHelper.show();
        }
    }


    private String getSysYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    private String getSysMonth() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.MONTH) + 1);
    }

    private String getSysDay() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

    private ArrayList<String> getTotalYear() {
        ArrayList<String> dataYear = new ArrayList<>();
        int minY = 2010;
        int maxY = Integer.valueOf(getSysYear());
        int length = maxY - minY;
        for (int i = 0; i < length + 1; i++) {
            dataYear.add((minY + i) + "");
        }
        return dataYear;
    }

    private int getYearDefaultIndex() {
        ArrayList<String> totalYear = getTotalYear();
        return Integer.valueOf(totalYear.get(totalYear.size() - 1));
    }

    private ArrayList<String> getTotalMonth() {
        ArrayList<String> dataMonth = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                dataMonth.add("0" + i);
            } else {
                dataMonth.add(i + "");
            }
        }
        return dataMonth;
    }

    private int getMonthDefaultIndex() {
        int m = 5;
        ArrayList<String> totalMonth = getTotalMonth();
        int size = totalMonth.size();
        for (int i = 0; i < size; i++) {
            if ((Integer.valueOf(totalMonth.get(i)) + "").equals(getSysMonth())) {
                m = i;
                break;
            }
        }
        return m;
    }


    private ArrayList<String> getTotal31Day() {
        ArrayList<String> dataMonth = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            if (i < 10) {
                dataMonth.add("0" + i);
            } else {
                dataMonth.add(i + "");
            }
        }
        return dataMonth;
    }

    private ArrayList<String> getTotal30Day() {
        ArrayList<String> dataMonth = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            if (i < 10) {
                dataMonth.add("0" + i);
            } else {
                dataMonth.add(i + "");
            }
        }
        return dataMonth;
    }

    private ArrayList<String> getTotal29Day() {
        ArrayList<String> dataMonth = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            if (i < 10) {
                dataMonth.add("0" + i);
            } else {
                dataMonth.add(i + "");
            }
        }
        return dataMonth;
    }

    private ArrayList<String> getTotal28Day() {
        ArrayList<String> dataMonth = new ArrayList<>();
        for (int i = 1; i < 29; i++) {
            if (i < 10) {
                dataMonth.add("0" + i);
            } else {
                dataMonth.add(i + "");
            }
        }
        return dataMonth;
    }

    private int getDayDefaultIndex() {
        int m = 15;
        ArrayList<String> totalDay = getTotal31Day();
        int size = totalDay.size();
        for (int i = 0; i < size; i++) {
            if ((Integer.valueOf(totalDay.get(i)) + "").equals(getSysDay())) {
                m = i;
                break;
            }
        }
        return m;
    }

    private boolean leapyear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}
