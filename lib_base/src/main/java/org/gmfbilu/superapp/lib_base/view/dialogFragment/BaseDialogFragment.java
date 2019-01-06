package org.gmfbilu.superapp.lib_base.view.dialogFragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.gmfbilu.superapp.lib_base.R;
import org.gmfbilu.superapp.lib_base.utils.Utils;

public abstract class BaseDialogFragment extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String THEME = "theme";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private boolean showBottom;//是否底部显示
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    protected int theme = R.style.lib_base_BaseDialogFragmentStyle; // dialog主题
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    public abstract int intLayoutId();

    public abstract void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog);

    public int initTheme() {
        return theme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, initTheme());

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            showBottom = savedInstanceState.getBoolean(BOTTOM);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            theme = savedInstanceState.getInt(THEME);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutId = intLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        convertView(DialogFragmentViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putBoolean(BOTTOM, showBottom);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(THEME, theme);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            //是否在底部显示
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM;
                if (animStyle == 0) {
                    animStyle = R.style.lib_base_BaseDialogFragment_DefaultAnimation;
                }
            }

            //设置dialog宽度
            if (width == 0) {
                lp.width = Utils.getScreenWidth(getContext()) - 2 * Utils.dp2px(getContext(), margin);
            } else if (width == -1) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = Utils.dp2px(getContext(), width);
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = Utils.dp2px(getContext(), height);
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    public BaseDialogFragment setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialogFragment setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public BaseDialogFragment setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseDialogFragment show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }

}
