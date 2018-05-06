package org.gmfbilu.superapp.lib_base.bean.response;

import java.util.ArrayList;

public class LoginRes {

    public int id;
    public Store store;
    public String status;
    public String email;
    public ArrayList<Roles> roles;
    public String login_name;
    public String name;
    public int gender;
    public String role_name;
    public String status_display;
    public String mobile;

    public class Store {
        public int id;
        public String name;

        @Override
        public String toString() {
            return "Store{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public class Roles {
        public int id;
        public String name;

        @Override
        public String toString() {
            return "Roles{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginRes{" +
                "id=" + id +
                ", store=" + store +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", login_name='" + login_name + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", role_name='" + role_name + '\'' +
                ", status_display='" + status_display + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
