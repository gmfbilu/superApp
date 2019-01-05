package org.gmfbilu.superapp.module_util.recyclerView.multiType;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_util.R;

public class PersonViewHolder extends BaseViewHolder<Person> {

    private TextView tv_name;
    private TextView tv_age;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_util_recyclerview_item_multi_type_person);
        tv_name = $(R.id.tv_name);
        tv_age = $(R.id.tv_age);
    }

    @Override
    public void setData(Person person) {
        tv_name.setText(person.name);
        tv_age.setText(person.age + "");
    }
}
