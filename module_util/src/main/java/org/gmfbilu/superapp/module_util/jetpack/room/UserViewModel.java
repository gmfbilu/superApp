package org.gmfbilu.superapp.module_util.jetpack.room;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    //我们单独使用LiveData的时候，都是使用MutableLiveData，当与Room一块使用的时候只能使用LiveData
    private LiveData<List<User>> mUsers;
    private UserRepository mRepository;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUsers = mRepository.getAllUser();
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void insertUser(User user) {
        mRepository.insert(user);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void update(User user) {
        mRepository.update(user);
    }
}
