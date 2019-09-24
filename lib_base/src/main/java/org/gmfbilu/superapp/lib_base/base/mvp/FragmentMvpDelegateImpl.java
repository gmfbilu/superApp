package org.gmfbilu.superapp.lib_base.base.mvp;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class FragmentMvpDelegateImpl<V extends BaseView, P extends BasePresenter<V>> implements FragmentMvpDelegate<V, P> {

    protected static final String KEY_MOSBY_VIEW_ID = "com.hannesdorfmann.mosby3.fragment.mvp.id";
    public static boolean DEBUG = false;
    private static final String DEBUG_TAG = "FragmentMvpVSDelegate";
    private DelegateCallBack<V, P> delegateCallback;
    protected Fragment fragment;
    protected final boolean keepPresenterInstanceDuringScreenOrientationChanges;
    protected final boolean keepPresenterOnBackstack;
    private boolean onViewCreatedCalled = false;
    protected String mosbyViewId;

    public FragmentMvpDelegateImpl(@NonNull Fragment fragment, @NonNull DelegateCallBack<V, P> delegateCallback, boolean keepPresenterDuringScreenOrientationChange, boolean keepPresenterOnBackstack) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        } else if (fragment == null) {
            throw new NullPointerException("Fragment is null!");
        } else if (!keepPresenterDuringScreenOrientationChange && keepPresenterOnBackstack) {
            throw new IllegalArgumentException("It is not possible to keep the presenter on backstack, but NOT keep presenter through screen orientation changes. Keep presenter on backstack also requires keep presenter through screen orientation changes to be enabled");
        } else {
            this.fragment = fragment;
            this.delegateCallback = delegateCallback;
            this.keepPresenterInstanceDuringScreenOrientationChanges = keepPresenterDuringScreenOrientationChange;
            this.keepPresenterOnBackstack = keepPresenterOnBackstack;
        }
    }

    private P createViewIdAndCreatePresenter() {
        P presenter = this.delegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from createPresenter() is null. Activity is " + this.getActivity());
        } else {
            if (this.keepPresenterInstanceDuringScreenOrientationChanges) {
                this.mosbyViewId = UUID.randomUUID().toString();
                PresenterManager.putPresenter(this.getActivity(), this.mosbyViewId, presenter);
            }

            return presenter;
        }
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        P presenter = this.getPresenter();
        presenter.attachView(this.getMvpView());
        if (DEBUG) {
            Log.d("FragmentMvpVSDelegate", "View" + this.getMvpView() + " attached to Presenter " + presenter);
        }

        this.onViewCreatedCalled = true;
    }

    @NonNull
    private Activity getActivity() {
        Activity activity = this.fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity returned by Fragment.getActivity() is null. Fragment is " + this.fragment);
        } else {
            return activity;
        }
    }

    private P getPresenter() {
        P presenter = this.delegateCallback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        } else {
            return presenter;
        }
    }

    private V getMvpView() {
        V view = this.delegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException("View returned from getMvpView() is null");
        } else {
            return view;
        }
    }

    static boolean retainPresenterInstance(Activity activity, Fragment fragment, boolean keepPresenterInstanceDuringScreenOrientationChanges, boolean keepPresenterOnBackstack) {
        if (activity.isChangingConfigurations()) {
            return keepPresenterInstanceDuringScreenOrientationChanges;
        } else if (activity.isFinishing()) {
            return false;
        } else if (keepPresenterOnBackstack && BackstackAccessor.isInBackStackAndroidX(fragment)) {
            return true;
        } else {
            return !fragment.isRemoving();
        }
    }

    public void onDestroyView() {
        this.onViewCreatedCalled = false;
        this.getPresenter().detachView();
        if (DEBUG) {
            Log.d("FragmentMvpVSDelegate", "detached MvpView from Presenter. MvpView " + this.delegateCallback.getMvpView() + "   Presenter: " + this.getPresenter());
        }

    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStart() {
        if (!this.onViewCreatedCalled) {
            throw new IllegalStateException("It seems that you are using " + this.delegateCallback.getClass().getCanonicalName() + " as headless (UI less) fragment (because onViewCreated() has not been called or maybe delegation misses that part). Having a Presenter without a View (UI) doesn't make sense. Simply use an usual fragment instead of an MvpFragment if you want to use a UI less Fragment");
        }
    }

    public void onStop() {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
    }

    public void onAttach(Activity activity) {
    }

    public void onDetach() {
    }

    public void onSaveInstanceState(Bundle outState) {
        if ((this.keepPresenterInstanceDuringScreenOrientationChanges || this.keepPresenterOnBackstack) && outState != null) {
            outState.putString("com.hannesdorfmann.mosby3.fragment.mvp.id", this.mosbyViewId);
            if (DEBUG) {
                Log.d("FragmentMvpVSDelegate", "Saving MosbyViewId into Bundle. ViewId: " + this.mosbyViewId);
            }
        }

    }

    public void onCreate(Bundle bundle) {
        P presenter = null;
        if (bundle != null && this.keepPresenterInstanceDuringScreenOrientationChanges) {
            this.mosbyViewId = bundle.getString("com.hannesdorfmann.mosby3.fragment.mvp.id");
            if (DEBUG) {
                Log.d("FragmentMvpVSDelegate", "MosbyView ID = " + this.mosbyViewId + " for MvpView: " + this.delegateCallback.getMvpView());
            }

            if (this.mosbyViewId != null && (presenter = PresenterManager.getPresenter(this.getActivity(), this.mosbyViewId)) != null) {
                if (DEBUG) {
                    Log.d("FragmentMvpVSDelegate", "Reused presenter " + presenter + " for view " + this.delegateCallback.getMvpView());
                }
            } else {
                presenter = this.createViewIdAndCreatePresenter();
                if (DEBUG) {
                    Log.d("FragmentMvpVSDelegate", "No presenter found although view Id was here: " + this.mosbyViewId + ". Most likely this was caused by a process death. New Presenter created" + presenter + " for view " + this.getMvpView());
                }
            }
        } else {
            presenter = this.createViewIdAndCreatePresenter();
            if (DEBUG) {
                Log.d("FragmentMvpVSDelegate", "New presenter " + presenter + " for view " + this.getMvpView());
            }
        }

        if (presenter == null) {
            throw new IllegalStateException("Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues");
        } else {
            this.delegateCallback.setPresenter(presenter);
        }
    }

    public void onDestroy() {
        Activity activity = this.getActivity();
        boolean retainPresenterInstance = retainPresenterInstance(activity, this.fragment, this.keepPresenterInstanceDuringScreenOrientationChanges, this.keepPresenterOnBackstack);
        P presenter = this.getPresenter();
        if (!retainPresenterInstance) {
            presenter.destroy();
            if (DEBUG) {
                Log.d("FragmentMvpVSDelegate", "Presenter destroyed. MvpView " + this.delegateCallback.getMvpView() + "   Presenter: " + presenter);
            }
        }

        if (!retainPresenterInstance && this.mosbyViewId != null) {
            PresenterManager.remove(activity, this.mosbyViewId);
        }

    }
}
