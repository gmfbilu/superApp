package org.gmfbilu.superapp.lib_base.base.mvp;


import androidx.annotation.NonNull;

public interface DelegateCallBack<V extends BaseView, P extends BasePresenter<V>>  {

    /**
     * Creates the presenter instance
     *
     * @return the created presenter instance
     */
    @NonNull P createPresenter();

    /**
     * Gets the presenter. If null is returned, then a internally a new presenter instance gets
     * created by calling {@link #createPresenter()}
     *
     * @return the presenter instance. can be null.
     */
    P getPresenter();

    /**
     * Sets the presenter instance
     *
     * @param presenter The presenter instance
     */
    void setPresenter(P presenter);

    /**
     * Gets the MvpView for the presenter
     *
     * @return The view associated with the presenter
     */
    V getMvpView();
}
