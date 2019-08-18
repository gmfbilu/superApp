package org.gmfbilu.superapp.module_util.jetpack.livedata_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimpleViewModel extends ViewModel {

    private final MutableLiveData<String> mMutableLiveData = new MutableLiveData<>();

    public void changeData(String data) {
        //setValue要在主线程中调用，postValue在子线程中调用
        mMutableLiveData.setValue(data);
    }

    public MutableLiveData<String> getData() {
        return mMutableLiveData;
    }
}
