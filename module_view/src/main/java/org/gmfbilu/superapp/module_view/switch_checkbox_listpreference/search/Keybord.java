package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.search;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;

public class Keybord {

    public interface OnSoftKeybordChangeListener {
        void onSoftKeybordChange(int softKeybordHeight, boolean visible);
    }

    public static void open(EditText editText) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void close(EditText editText) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static boolean isSoftInputShow(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            return imm.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    public static void observeSoftkeybord(Activity activity, final OnSoftKeybordChangeListener listener) {
        View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom;
                int height = decorView.getHeight();
                int keybordHeight = height = displayHeight;
                if (previousKeyboardHeight != keybordHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    listener.onSoftKeybordChange(keybordHeight, !hide);
                }
                previousKeyboardHeight = height;
            }
        });
    }
}
