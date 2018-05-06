package org.gmfbilu.superapp.lib_base.bean.response;

public class GetProductsByTypeRes {

    public String Name;
    public String ID;
    public String Code;
    public int Type;

    @Override
    public String toString() {
        return "GetProductsByTypeRes{" +
                "Name='" + Name + '\'' +
                ", ID='" + ID + '\'' +
                ", Code='" + Code + '\'' +
                ", Type=" + Type +
                '}';
    }
}
