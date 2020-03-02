package org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.base.IAdapter;


/**
 * k线图的数据适配器
 */

public abstract class BaseKChartAdapter implements IAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void notifyDataSetChanged() {
        if (getCount() > 0) {
            mDataSetObservable.notifyChanged();
        } else {
            mDataSetObservable.notifyInvalidated();
        }
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }
}
