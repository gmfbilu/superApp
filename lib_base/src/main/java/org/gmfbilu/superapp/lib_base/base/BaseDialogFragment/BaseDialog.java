package org.gmfbilu.superapp.lib_base.base.BaseDialogFragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

public class BaseDialog extends BaseDialogFragment {
    private DialogFragmentViewConvertListener convertListener;

    public static BaseDialog init() {
        return new BaseDialog();
    }

    @Override
    public int initTheme() {
        return theme;
    }

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder, dialog);
        }
    }

    public BaseDialog setTheme(@StyleRes int theme) {
        this.theme = theme;
        return this;
    }

    public BaseDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public BaseDialog setConvertListener(DialogFragmentViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            convertListener = savedInstanceState.getParcelable("listener");
        }
    }

    /**
     * 保存接口
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listener", convertListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        convertListener = null;
    }
}
