package org.gmfbilu.superapp.module_util;

import org.gmfbilu.superapp.lib_base.base.mvp.BaseModel;

public class MainModel extends BaseModel<MainPresenterImpl> {


    public MainModel(MainPresenterImpl iPresenter) {
        super(iPresenter);
    }

/*    public void signInM(SignInReq signInReq) {
        mPresenter.showLoadingP();
        Subscriber<SignUpRes> succeedSubscribe = new Subscriber<SignUpRes>() {

            @Override
            public void onNext(SignUpRes httpResult) {
                mPresenter.signInP(httpResult);
                mPresenter.hideLoadingP();
            }

            @Override
            public void onError(Throwable e) {
                mPresenter.hideLoadingP();
                if (e instanceof ApiException) {
                    //服务器内部错误
                    ApiException apiException = (ApiException) e;
                    String responseCode = apiException.ResponseCodeError;
                    String responseMessageError = apiException.ResponseMessageError;
                    if (!TextUtils.isEmpty(responseMessageError)) {
                        Toast.error(responseMessageError);
                    }
                    if ("111".equals(responseCode)) {//登陆密码错误
                    }
                }
            }

            @Override
            public void onCompleted() {
                mPresenter.hideLoadingP();
            }
        };
        HttpMethods.getInstance().login(succeedSubscribe, signInReq);
        ActivityManager.getActivity(SignInActivity.class).addSubscription(succeedSubscribe);
    }*/

}
