package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.doubleDatePicker;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.dialog.DialogHelper;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DoubleDatePicker {

    private Activity _mActivity;
    private boolean mSecondSelected;//是否第二个被选中
    private DialogHelper mDialogHelper;
    private boolean leftSmallThanRight;

    private onDataSelectedListener mOnDataSelectedListener;

    public DoubleDatePicker(Activity activity, boolean secondSelected) {
        _mActivity = activity;
        mSecondSelected = secondSelected;
        initPicker();
    }

    private void initPicker() {
        mDialogHelper = new DialogHelper(_mActivity);
        View calLayout = mDialogHelper.setCalendarLayout(R.layout.module_view_dialog_double_date);
        TextView tv_beginTime = calLayout.findViewById(R.id.tv_beginTime);
        TextView tv_endTime = calLayout.findViewById(R.id.tv_endTime);
        ScrollChoice scrollChoiceYear = calLayout.findViewById(R.id.ScrollChoiceYear);
        ScrollChoice scrollChoiceMonth = calLayout.findViewById(R.id.ScrollChoiceMonth);
        ScrollChoice scrollChoiceDay = calLayout.findViewById(R.id.ScrollChoiceDay);
        tv_beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondSelected = false;
                tv_beginTime.setTextColor(Color.parseColor("#FFA52C"));
                tv_endTime.setTextColor(Color.parseColor("#A5A5A5"));
                String s = tv_beginTime.getText().toString();
                String y = s.substring(0, 4);
                String m = s.substring(5, 7);
                String d = s.substring(8, 10);
                ArrayList<String> totalYear = getTotalYear();
                int size = totalYear.size();
                int iY = 0;
                for (int i = 0; i < size; i++) {
                    if (totalYear.get(i).equals(y)) {
                        iY = i;
                        break;
                    }
                }
                ArrayList<String> totalMonth = getTotalMonth();
                int size1 = totalMonth.size();
                int iM = 0;
                for (int i = 0; i < size1; i++) {
                    if (totalMonth.get(i).equals(m)) {
                        iM = i;
                        break;
                    }
                }
                ArrayList<String> totalDay = getTotal31Day();
                int size2 = totalDay.size();
                int iD = 0;
                for (int i = 0; i < size2; i++) {
                    if (totalDay.get(i).equals(d)) {
                        iD = i;
                        break;
                    }
                }
                scrollChoiceYear.setSelectedItemPosition(iY);
                scrollChoiceMonth.setSelectedItemPosition(iM);
                scrollChoiceDay.setSelectedItemPosition(iD);
            }
        });
        tv_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondSelected = true;
                tv_endTime.setTextColor(Color.parseColor("#FFA52C"));
                tv_beginTime.setTextColor(Color.parseColor("#A5A5A5"));
                String s = tv_endTime.getText().toString();
                String y = s.substring(0, 4);
                String m = s.substring(5, 7);
                String d = s.substring(8, 10);
                ArrayList<String> totalYear = getTotalYear();
                int size = totalYear.size();
                int iY = 0;
                for (int i = 0; i < size; i++) {
                    if (totalYear.get(i).equals(y)) {
                        iY = i;
                        break;
                    }
                }
                ArrayList<String> totalMonth = getTotalMonth();
                int size1 = totalMonth.size();
                int iM = 0;
                for (int i = 0; i < size1; i++) {
                    if (totalMonth.get(i).equals(m)) {
                        iM = i;
                        break;
                    }
                }
                ArrayList<String> totalDay = getTotal31Day();
                int size2 = totalDay.size();
                int iD = 0;
                for (int i = 0; i < size2; i++) {
                    if (totalDay.get(i).equals(d)) {
                        iD = i;
                        break;
                    }
                }
                scrollChoiceYear.setSelectedItemPosition(iY);
                scrollChoiceMonth.setSelectedItemPosition(iM);
                scrollChoiceDay.setSelectedItemPosition(iD);
            }
        });
        scrollChoiceYear.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                if (!mSecondSelected) {
                    tv_beginTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                } else {
                    tv_endTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                }

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

                if (!mSecondSelected) {
                    tv_beginTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                } else {
                    tv_endTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                }
            }
        });
        scrollChoiceDay.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                if (!mSecondSelected) {
                    tv_beginTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                } else {
                    tv_endTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
                }
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
                String startTime = "";
                String endTime = "";
                String s = tv_beginTime.getText().toString();
                String s1 = tv_endTime.getText().toString();
                int sY = Integer.valueOf(s.substring(0, 4));
                int s1Y = Integer.valueOf(s1.substring(0, 4));
                if (sY < s1Y) {
                    startTime = s;
                    endTime = s1;
                } else if (sY == s1Y) {
                    int sM = Integer.valueOf(s.substring(5, 7));
                    int s1M = Integer.valueOf(s1.substring(5, 7));
                    if (sM < s1M) {
                        startTime = s;
                        endTime = s1;
                    } else if (sM == s1M) {
                        int sD = Integer.valueOf(s.substring(8, 10));
                        int s1D = Integer.valueOf(s1.substring(8, 10));
                        if (sD < s1D) {
                            startTime = s;
                            endTime = s1;
                        } else if (sD == s1D) {
                            startTime = s;
                            endTime = s1;
                        } else {
                            startTime = s1;
                            endTime = s;
                        }
                    } else {
                        startTime = s1;
                        endTime = s;
                    }
                } else {
                    startTime = s1;
                    endTime = s;
                }
                //如果不做限制，左边的数据会大于右边的数据
                mOnDataSelectedListener.onDataSelected(startTime, endTime);
            }
        });
        mDialogHelper.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mSecondSelected = false;
            }
        });


        if (mSecondSelected) {
            tv_beginTime.setTextColor(Color.parseColor("#A5A5A5"));
            tv_endTime.setTextColor(Color.parseColor("#FFA52C"));
        } else {
            tv_beginTime.setTextColor(Color.parseColor("#FFA52C"));
            tv_endTime.setTextColor(Color.parseColor("#A5A5A5"));
        }


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
        tv_beginTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());
        tv_endTime.setText(scrollChoiceYear.getCurrentSelection() + "-" + scrollChoiceMonth.getCurrentSelection() + "-" + scrollChoiceDay.getCurrentSelection());



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


    public void addOnDataSelectedListener(onDataSelectedListener onDataSelectedListener) {
        this.mOnDataSelectedListener = onDataSelectedListener;
    }

    public interface onDataSelectedListener {

        void onDataSelected(String leftTime, String rightTime);
    }
}
