package org.gmfbilu.superapp.module_googlelibrary.dialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.baseDialogFragment.BaseDialog;
import org.gmfbilu.superapp.lib_base.base.baseDialogFragment.BaseDialogFragment;
import org.gmfbilu.superapp.lib_base.base.baseDialogFragment.DialogFragmentViewConvertListener;
import org.gmfbilu.superapp.lib_base.base.baseDialogFragment.DialogFragmentViewHolder;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_googlelibrary.R;

/**
 * Google官方建议使用DialogFragment代替Dialog
 */
public class DialogFragment extends BaseFragment {


    public static DialogFragment newInstance() {
        Bundle args = new Bundle();
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_dialog_share).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_setting).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_commit).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_redpocket).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_loading).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_pop).setOnClickListener(this);

    }

    @Override
    public int setLayout() {
        return R.layout.module_googlelibrary_fragment_dialogfragment;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_dialog_share) {
            BaseDialog.init()
                    .setLayoutId(R.layout.module_googlelibrary_dialogfragment_share) //设置dialog布局文件
                    //.setTheme() 设置dialog主题，默认主题继承自Theme.AppCompat.Light.Dialog
                    .setConvertListener(new DialogFragmentViewConvertListener() { //进行相关View操作的回调
                        @Override
                        public void convertView(DialogFragmentViewHolder holder, final BaseDialogFragment dialog) {
                            TextView tv = holder.getView(R.id.tv);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(_mActivity, "分享成功", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    })
                    .setDimAmount(0.3f)//调节灰色背景透明度[0-1]，默认0.5f
                    .setShowBottom(true) //是否在底部显示dialog，默认flase
                    //.setMargin()     dialog左右两边到屏幕边缘的距离（单位：dp），默认0dp
                    //.setWidth()     dialog宽度（单位：dp），默认为屏幕宽度，-1代表WRAP_CONTENT. setMargin()和setWidth()选择一个即可
                    //.setHeight()     dialog高度（单位：dp），默认为WRAP_CONTENT
                    //.setOutCancel(false)     点击dialog外是否可取消，默认true
                    //.setAnimStyle(R.style.EnterExitAnimation)     设置dialog进入、退出的动画style(底部显示的dialog有默认动画)
                    .show(_mActivity.getSupportFragmentManager());
        } else if (id == R.id.bt_dialog_setting) {
            BaseDialog.init()
                    .setLayoutId(R.layout.module_googlelibrary_dialogfragment_setting)
                    .setConvertListener(new DialogFragmentViewConvertListener() {
                        @Override
                        protected void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {

                        }
                    })
                    .setShowBottom(true)
                    .setHeight(310)
                    .show(_mActivity.getSupportFragmentManager());
        } else if (id == R.id.bt_dialog_commit) {
            BaseDialog.init()
                    .setLayoutId(R.layout.module_googlelibrary_dialogfragment_commit)
                    .setConvertListener(new DialogFragmentViewConvertListener() {
                        @Override
                        protected void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {
                            final EditText editText = holder.getView(R.id.edit_input);
                            editText.post(new Runnable() {
                                @Override
                                public void run() {
                                    InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(editText, 0);
                                }
                            });
                        }
                    })
                    .setShowBottom(true)
                    .show(_mActivity.getSupportFragmentManager());
        } else if (id == R.id.bt_dialog_redpocket) {
            BaseDialog.init()
                    .setLayoutId(R.layout.module_googlelibrary_dialogfragment_redpocket)
                    .setConvertListener(new DialogFragmentViewConvertListener() {
                        @Override
                        protected void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {
                            ImageView close = holder.getView(R.id.close);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    })
                    .setWidth(210)
                    .setOutCancel(false)
                    .setAnimStyle(R.style.RedPocketDialogFragmentEnterExitAnimation)
                    .show(_mActivity.getSupportFragmentManager());
        } else if (id == R.id.bt_dialog_loading) {
            BaseDialog.init()
                    .setLayoutId(R.layout.module_googlelibrary_dialogfragment_loading)
                    .setTheme(R.style.LoadingDialogFragment)
                    .setWidth(100)
                    .setHeight(100)
                    .setDimAmount(0)
                    .show(_mActivity.getSupportFragmentManager());
        } else if (id == R.id.bt_dialog_pop) {
            ConfirmDialogFragment.newInstance("1")
                    .setMargin(60)
                    .setOutCancel(false)
                    .show(_mActivity.getSupportFragmentManager());
        }
    }
}
