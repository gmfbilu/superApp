package org.gmfbilu.superapp.lib_base.bean.request;

public class LoginReq {

    public String login_name;
    public String password;
    public String nonce;

    @Override
    public String toString() {
        return "LoginReq{" +
                "login_name='" + login_name + '\'' +
                ", password='" + password + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}
