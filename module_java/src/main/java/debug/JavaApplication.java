package debug;

import android.content.Context;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;

public class JavaApplication extends BaseApplication {

    public static Context sApplicationContext;


    public static Context getInstance() {
        return sApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
    }
}
