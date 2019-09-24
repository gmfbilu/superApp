package org.gmfbilu.superapp.lib_base.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public interface FragmentMvpDelegate<V extends BaseView, P extends BasePresenter<V>> {

    /**
     * Must be called from { Fragment#onCreate(Bundle)}
     *
     * @param saved The bundle
     */
    void onCreate(Bundle saved);

    /**
     * Must be called from { Fragment#onDestroy()}
     */
    void onDestroy();

    /**
     * Must be called from { Fragment#onViewCreated(View, Bundle)}
     *
     * @param view The inflated view
     * @param savedInstanceState the bundle with the viewstate
     */
    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * Must be called from { Fragment#onDestroyView()}
     */
    void onDestroyView();

    /**
     * Must be called from { Fragment#onPause()}
     */
    void onPause();

    /**
     * Must be called from { Fragment#onResume()}
     */
    void onResume();

    /**
     * Must be called from { Fragment#onStart()}
     */
    void onStart();

    /**
     * Must be called from { Fragment#onStop()}
     */
    void onStop();

    /**
     * Must be called from { Fragment#onActivityCreated(Bundle)}
     *
     * @param savedInstanceState The saved bundle
     */
    void onActivityCreated(Bundle savedInstanceState);

    /**
     * Must be called from {@link Fragment#onAttach(Activity)}
     *
     * @param activity The activity the fragment is attached to
     */
    void onAttach(Activity activity);

    /**
     * Must be called from {@link Fragment#onDetach()}
     */
    void onDetach();

    /**
     * Must be called from {@link Fragment#onSaveInstanceState(Bundle)}
     */
    void onSaveInstanceState(Bundle outState);
}
