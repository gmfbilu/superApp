package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick;

import android.view.View;


public interface OnItemClickListener<T> {

    void onItemClick(View view, T data, int position);

}
