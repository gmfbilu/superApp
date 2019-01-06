package org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter;

import android.view.View;


public interface EventDelegate {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, RecyclerArrayAdapter.OnMoreListener listener);
    void setNoMore(View view, RecyclerArrayAdapter.OnNoMoreListener listener);
    void setErrorMore(View view, RecyclerArrayAdapter.OnErrorListener listener);
    void setMore(int res, RecyclerArrayAdapter.OnMoreListener listener);
    void setNoMore(int res, RecyclerArrayAdapter.OnNoMoreListener listener);
    void setErrorMore(int res, RecyclerArrayAdapter.OnErrorListener listener);
}