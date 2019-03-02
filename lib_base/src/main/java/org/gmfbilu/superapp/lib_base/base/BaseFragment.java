package org.gmfbilu.superapp.lib_base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 2018/3/19.
 */

public abstract class BaseFragment extends SupportFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d(getClass().getName() + "---> onCreateView");
        int layout = setLayout();
        View view = null;
        if (layout != 0)
            view = inflater.inflate(layout, container, false);
        findViewById_setOnClickListener(view);
        return view;
    }

    /**
     * 初始化方法
     *
     * @param view
     */
    public abstract void findViewById_setOnClickListener(View view);

    public abstract int setLayout();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(getClass().getName() + "---> onAttach Context");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.d(getClass().getName() + "---> onAttach Activity");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getClass().getName() + "---> onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(getClass().getName() + "---> onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(getClass().getName() + "---> onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(getClass().getName() + "---> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getClass().getName() + "---> onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(getClass().getName() + "---> onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(getClass().getName() + "---> onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(getClass().getName() + "---> onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(getClass().getName() + "---> onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(getClass().getName() + "---> onDetach");
    }
}
