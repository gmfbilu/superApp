package org.gmfbilu.superapp.module_util;

public class MainPresenterImpl implements MainPresenter {


    private MainView mView;
    private MainModel mModel;

    public MainPresenterImpl(MainView view) {
        mView = view;
        mModel = new MainModel(this);
    }


    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void signInP(String httpResult) {

    }

/*    public void signIn(String phone, String pass,String tokenId) {
        if (TextUtils.isEmpty(pass)) {
            mView.showToast("密码不能为空");
            return;
        }
        SignInReq signInReq = new SignInReq();
        signInReq.Account = phone;
        signInReq.Password = Util.transformMD5(pass);
        signInReq.AppVersion = Util.getAppVersionName(DevoutcatApplication.getInstance());
        signInReq.EquipName = Build.BRAND + " " + Build.MODEL;
        signInReq.EquipVersion = Build.VERSION.RELEASE;
        signInReq.StoreName = ChannelReaderUtil.getChannel(DevoutcatApplication.getInstance());// Util.metaData(DevoutcatApplication.getInstance(),"UMENG_CHANNEL");
        signInReq.TokenId = tokenId;
        mModel.signInM(signInReq);
    }*/

    public void showLoadingP() {
        mView.showLoading();
    }

    public void hideLoadingP() {
        mView.hideLoading();
    }
}
