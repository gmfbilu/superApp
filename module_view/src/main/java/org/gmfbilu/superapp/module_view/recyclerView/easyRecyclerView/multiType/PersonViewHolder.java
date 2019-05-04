package org.gmfbilu.superapp.module_view.recyclerView.easyRecyclerView.multiType;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class PersonViewHolder extends BaseViewHolder<Person> {

    private TextView tv_name;
    private TextView tv_age;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_view_recyclerview_item_multi_type_person);
        tv_name = $(R.id.tv_name);
        tv_age = $(R.id.tv_age);
    }

    @Override
    public void setData(Person person) {
        tv_name.setText(person.name);
        tv_age.setText(person.age + "");
    }
}
