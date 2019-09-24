package org.gmfbilu.superapp.lib_base.base.mvp;

import android.os.Bundle;


public interface ActivityMvpDelegate<V extends BaseView, P extends BasePresenter<V>>  {

    void onCreate(Bundle bundle);

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    void onRestart();

    void onContentChanged();

    void onSaveInstanceState(Bundle outState);

    void onPostCreate(Bundle savedInstanceState);

}
