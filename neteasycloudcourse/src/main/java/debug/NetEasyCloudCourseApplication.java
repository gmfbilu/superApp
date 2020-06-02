package debug;

import android.content.Context;

import org.gmfbilu.neteasycloudcourse.MyEventBusIndex;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.greenrobot.eventbus.EventBus;

public class NetEasyCloudCourseApplication extends BaseApplication {

    public static Context sApplicationContext;


    public static Context getInstance() {
        return sApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
        initEventBus();
    }

    /**
     * 正确的使用eventbus，在3.0之后不进行此操作的话就会使用反射，进行此操作就会使用apt。3.0之前都是使用反射
     */
    private void initEventBus(){
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
