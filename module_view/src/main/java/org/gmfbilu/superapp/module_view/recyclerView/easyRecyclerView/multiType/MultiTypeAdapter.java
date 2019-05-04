package org.gmfbilu.superapp.module_view.recyclerView.easyRecyclerView.multiType;

import android.content.Context;
import android.view.ViewGroup;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;

// TODO: 2019/1/2  应该采取其他方式写type
public class MultiTypeAdapter extends RecyclerArrayAdapter<Object> {

    public static final int TYPE_INVALID = 0;
    public static final int TYPE_AD = 1;
    public static final int TYPE_PERSON = 2;

    public MultiTypeAdapter(Context context) {
        super(context);
    }


    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof Ad) {
            return TYPE_AD;
        } else if (getItem(position) instanceof Person) {
            return TYPE_PERSON;
        }
        return TYPE_INVALID;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PERSON:
                return new PersonViewHolder(parent);
            case TYPE_AD:
                return new AdViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}
