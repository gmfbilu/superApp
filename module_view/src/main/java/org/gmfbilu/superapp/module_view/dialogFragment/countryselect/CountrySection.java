package org.gmfbilu.superapp.module_view.dialogFragment.countryselect;

import com.chad.library.adapter.base.entity.SectionEntity;

public class CountrySection extends SectionEntity<Country> {


    public CountrySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public CountrySection(Country t) {
        super(t);
    }
}
