package org.gmfbilu.superapp.module_view.amap;

import android.Manifest;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;
import org.gmfbilu.superapp.lib_base.utils.SystemPictureInfo;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * INVALID_USER_KEY:开发过程中经常报这个错误
 * <p>
 * 3D地图才需要添加so库，2D地图无需这一步骤。
 * <p>
 * 地图 SDK 底层运用两种 OpenGL ES 组件构建地图，分别是 GLSurfaceView 和 TextureView
 * GLSurfaceView：包括 MapView、MapFragment、SupportMapFragment 三种容器
 * MapFragment 是 Android Fragment 类的一个子类，用于在 Android Fragment 中放置地图。 MapFragment 也是地图容器，与 MapView 一样提供对 AMap 对象（地图的控制类）的访问权。与 MapView 相比 SupportMapFragment 方便之处在于其可以更好的管理地图的生命周期，布局灵活
 * TextureView：包括TextureMapView、TextureMapFragment、TextureSupportMapFragment 三种容器
 * 您将MapView与其他的GLSurfaceView（比如相机）叠加展示，或者是在ScrollView中加载地图时，建议使用TextureMapView及SupportTextureMapFragment来展示地图，可以有效解决 GLSurfaceView 叠加时出现的穿透、滚动黑屏等问题
 * <p>
 * AMap 类是地图的控制器类，用来操作地图。它所承载的工作包括：地图图层切换（如卫星图、黑夜地图）、改变地图状态（地图旋转角度、俯仰角、中心点坐标和缩放级别）、添加点标记（Marker）、绘制几何图形(Polyline、Polygon、Circle)、各类事件监听(点击、手势等)等，AMap 是地图 SDK 最重要的核心类，诸多操作都依赖它完成
 */
public class AmapFragment extends BaseFragment implements DistrictSearch.OnDistrictSearchListener {

    private MapView mMapView;
    private AMap mAMap;
    private LatLng mLatLng;
    //地理位置搜索
    private GeocodeSearch geocoderSearch;
    //有地理位置照片的省份，重复
    private ArrayList<String> mAllProvinces = new ArrayList<>();

    public static AmapFragment newInstance() {
        Bundle args = new Bundle();
        AmapFragment fragment = new AmapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_amap;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_district).setOnClickListener(this);
        view.findViewById(R.id.bt_district_boundary).setOnClickListener(this);
        mMapView = view.findViewById(R.id.map);
        geocoderSearch = new GeocodeSearch(_mActivity);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            /**
             * 逆地理编码回调，根据经纬度得出位置名
             */
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS && result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) {
                    RegeocodeAddress regeocodeAddress = result.getRegeocodeAddress();
                    //城市名称
                    String city = regeocodeAddress.getCity();
                    //城市编码
                    String cityCode = regeocodeAddress.getCityCode();
                    //国家名称
                    String country = regeocodeAddress.getCountry();
                    //省名称、直辖市的名称
                    String province = regeocodeAddress.getProvince();
                    if (!StringUtils.isEmpty(country) && country.equals("中国")) {
                        drawProvinceOrCity(province);
                    }
                }
            }

            /**
             * 地理编码查询回调，根据位置名得出经纬度
             */
            @Override
            public void onGeocodeSearched(GeocodeResult result, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS && result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0) {
                    GeocodeAddress address = result.getGeocodeAddressList().get(0);
                    //城市名称
                    String city = address.getCity();
                    //经纬度坐标
                    LatLonPoint latLonPoint = address.getLatLonPoint();
                    //省名称、直辖市的名称
                    String province = address.getProvince();
                    Logger.d("province=" + province + ", city=" + city + ", latLonPoint=" + latLonPoint);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_district) {
            start(DistrictFragment.newInstance());
        } else if (id == R.id.bt_district_boundary) {
            getAllSystemPicInfo();
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initAMap(savedInstanceState);
        getCurrentLocation();
    }

    /**
     * 初始化高德地图
     *
     * @param savedInstanceState
     */
    private void initAMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState); //此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        basic();
        locationBluePoint();
        widgetSetting();
        methodInterface();
        marker();
        draw();
        track();
        zoom();

    }


    private void basic() {
        //地图加载完成
        mAMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                //隐藏掉文字标注
                mAMap.showMapText(false);
            }
        });
        //设置定位资源。如果不设置此定位资源则定位按钮不可点击
        mAMap.setLocationSource(new LocationSource() {
            //处理定位更新的接口
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {

            }

            //激活位置接口。定位程序将通过将此接口将主线程回调定位信息，直到用户关闭此通知
            @Override
            public void deactivate() {

            }
        });
    }


    /**
     * 显示定位蓝点:定位蓝点指的是进入地图后显示当前位置点的功能
     */
    private void locationBluePoint() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //mAMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        locationBluePointLocationStyle(myLocationStyle);
        locationBluePointEnable(myLocationStyle, true);
        locationBluePointStyle(myLocationStyle, null);
    }

    /**
     * 定位蓝点展现模式
     *
     * @param myLocationStyle
     */
    private void locationBluePointLocationStyle(MyLocationStyle myLocationStyle) {
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
    /*    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。*/
    }

    /**
     * 控制是否显示定位蓝点
     *
     * @param myLocationStyle
     * @param enable
     */
    private void locationBluePointEnable(MyLocationStyle myLocationStyle, boolean enable) {
        myLocationStyle.showMyLocation(enable);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
    }

    /**
     * 自定义定位蓝点图标
     *
     * @param myLocationStyle
     * @param myLocationIcon
     */
    private void locationBluePointStyle(MyLocationStyle myLocationStyle, BitmapDescriptor myLocationIcon) {
        myLocationStyle.myLocationIcon(myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
    }

    /**
     * 控件是指浮在地图图面上的一系列用于操作地图的组件，例如缩放按钮、指南针、定位按钮、比例尺等
     * UiSettings 类用于操控这些控件，以定制自己想要的视图效果
     */
    private void widgetSetting() {
        UiSettings uiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        zoomButton(uiSettings);
        compass(uiSettings);
        locationButton(uiSettings);
        scaleControl(uiSettings);
        logo(uiSettings);
        gestures(uiSettings);
    }

    /**
     * 缩放按钮
     *
     * @param uiSettings
     */
    private void zoomButton(UiSettings uiSettings) {
        //控制其隐藏
        uiSettings.setZoomControlsEnabled(false);
        //UiSettings.setZoomPosition(int position)  设置缩放按钮的位置
        //UiSettings.getZoomPosition() 获取缩放按钮的位置
    }

    /**
     * 指南针
     * 指南针用于向 App 端用户展示地图方向，默认不显示
     */
    private void compass(UiSettings uiSettings) {
        uiSettings.setCompassEnabled(false);
    }

    /**
     * 定位按钮
     * App 端用户可以通过点击定位按钮在地图上标注一个蓝色定位点，代表其当前位置。不同于以上控件，定位按钮内部的逻辑实现依赖 Android 定位 SDK
     * SDK 没有提供自定义定位按钮的功能，如果您想要实现该功能，可以浏览参考论坛的帖子的内容
     *
     * @param uiSettings
     */
    private void locationButton(UiSettings uiSettings) {
        // mAMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        uiSettings.setMyLocationButtonEnabled(false); //显示默认的定位按钮
        mAMap.setMyLocationEnabled(false);// 可触发定位并显示当前位置
    }

    /**
     * 比例尺控件
     * 比例尺控件（最大比例是1：10m,最小比例是1：1000Km），位于地图右下角，可控制其显示与隐藏
     *
     * @param uiSettings
     */
    private void scaleControl(UiSettings uiSettings) {
        uiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
    }

    /**
     * 地图Logo
     * 高德地图的 logo 默认在左下角显示，不可以移除，但支持调整到固定位置
     *
     * @param uiSettings
     */
    private void logo(UiSettings uiSettings) {
        //uiSettings.setLogoPosition( int position);//设置logo位置
       /* AMapOptions.LOGO_POSITION_BOTTOM_LEFT LOGO边缘MARGIN（左边）
        AMapOptions.LOGO_MARGIN_BOTTOM LOGO边缘MARGIN（底部
        AMapOptions.LOGO_MARGIN_RIGHT LOGO边缘MARGIN（右边）
        AMapOptions.LOGO_POSITION_BOTTOM_CENTER Logo位置（地图底部居中）
        AMapOptions.LOGO_POSITION_BOTTOM_LEFT Logo位置（地图左下角）
        AMapOptions.LOGO_POSITION_BOTTOM_RIGHTLogo位置（地图右下角）*/
    }

    /**
     * 地图 SDK 提供了多种手势供 App 端用户与地图之间进行交互，如缩放、旋转、滑动、倾斜。这些手势默认开启，如果想要关闭某些手势，可以通过 UiSetting 类提供的接口来控制手势的开关
     */
    private void gestures(UiSettings uiSettings) {
        // TODO: 2019/4/25
        //关闭所有的手势操作
        //uiSettings.setAllGesturesEnabled(false);
    }

    /**
     * 方法交互的概念是从程序角度出发提出的。地图 SDK 提供了很多与地图交互的接口方法，例如：改变地图显示的区域（即改变地图中心点）、改变地图的缩放级别、限制地图的显示范围等
     * 地图视角交互的核心方法均依赖 AMap 类提供的这两个方法，区别在于所构造的 CameraUpdate 类对象参数不一样，得到的效果也不同。以下对例举的三种常用交互方法进行详细介绍，其余的交互方法请参考官方参考手册
     */
    private void methodInterface() {
        /**
         * animateCamera(CameraUpdate cameraupdate,long duration,AMap.CancelableCallback cancelablecallback) 带有地图视角移动动画的方法
         * moveCamera(CameraUpdate cameraupdate) 不带地图视角移动动画的方法
         */

        /**
         * 改变地图的中心点
         * 参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
         * CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(39.977290,116.337000),18,30,0));
         */

        /**
         * 改变地图的缩放级别。地图的缩放级别一共分为 17 级，从 3 到 19。数字越大，展示的图面信息越精细
         * CameraUpdate mCameraUpdate ＝ CameraUpdateFactory.zoomTo(17);
         *
         * 缩放地图到指定的缩放级别
         * AMap.moveCamera(CameraUpdateFactory.zoomTo(17))
         *
         * 缩放地图到当前缩放级别的上一级
         * AMap.moveCamera(CameraUpdateFactory.zoomIn())
         */

        /**
         * 限制地图的显示范围。手机屏幕仅显示设定的地图范围，例如：希望设置仅显示北京市区地图，可使用此功能。
         * 注意：如果限制了地图显示范围，地图旋转手势将会失效
         *
         * LatLng southwestLatLng = new LatLng(33.789925, 104.838326);
         * LatLng northeastLatLng = new LatLng(38.740688, 114.647472);
         * LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
         * aMap.setMapStatusLimits(latLngBounds)
         */

        /**
         * 改变地图默认显示区域。地图默认显示北京地区，通过采用重载的 MapView 构造方法更改默认地图显示区域：
         * MapView mapView = new MapView(this, mapOptions);
         *
         * 定义北京市经纬度坐标（此处以北京坐标为例）
         * LatLng centerBJPoint= new LatLng(39.904989,116.405285);
         *  定义了一个配置 AMap 对象的参数类
         * AMapOptions mapOptions = new AMapOptions();
         * 设置了一个可视范围的初始化位置
         *  CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
         *  CameraPosition 第二个参数： 目标可视区域的缩放级别
         *  CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
         *  CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
         * mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
         * 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
         * MapView mapView = new MapView(this, mapOptions);
         * 调用 onCreate方法 对 MapView LayoutParams 设置
         * mapView.onCreate(savedInstanceState);
         */

        // TODO: 2019/4/25  改变地图默认显示区域
        //改变地图默认显示区域，地理中国的中心，西安临潼LatLng(34.263161, 108.948024);。改变地图的缩放级别为5
        // CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(26.263161, 108.948024), 5, 0, 0));
        //mAMap.moveCamera(mCameraUpdate);
    }


    /**
     * 绘制点标记
     * 点标记用来在地图上标记任何位置，例如用户位置、车辆位置、店铺位置等一切带有位置属性的事物
     * 地图 SDK 提供的点标记功能包含两大部分，一部分是点（俗称 Marker）、另一部分是浮于点上方的信息窗体（俗称 InfoWindow）
     * 同时，SDK 对 Marker 和 InfoWindow 封装了大量的触发事件，例如点击事件、长按事件、拖拽事件
     * Marker 和 InfoWindow 有默认风格，同时也支持自定义。由于内容丰富，以下只能展示一些基础功能的使用，详细内容可分别参考官方参考手册。
     * Marker
     * MarkerOptions
     * AMap.InfoWindowAdapter
     */
    private void marker() {
        /**
         *  绘制绘制默认 Marker
         *  LatLng latLng = new LatLng(39.906901,116.397972);
         *  final Marker marker = mAMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
         *  Marker 常用属性
         *  position:在地图上标记位置的经纬度值。必填参数
         * title:点标记的标题
         * snippet:点标记的内容
         * draggable: 点标记是否可拖拽
         * visible:点标记是否可见
         * anchor:点标记的锚点
         * alpha:点的透明度
         */

        /**
         * 绘制自定义 Marker
         * 可根据实际的业务需求，在地图指定的位置上添加自定义的 Marker。MarkerOptions 是设置 Marker 参数变量的类，自定义 Marker 时会经常用到
         *      MarkerOptions markerOption = new MarkerOptions();
         *         markerOption.position(Constants.XIAN);
         *         markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
         *         markerOption.draggable(true);//设置Marker可拖动
         *         markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location_marker)));
         *         // 将Marker设置为贴地显示，可以双指下拉地图查看效果
         *         markerOption.setFlat(true);//设置marker平贴地图效果
         */

        /**
         * 绘制动画效果 Marker
         *  Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
         *         long duration = 1000L;
         *         animation.setDuration(duration);
         *         animation.setInterpolator(new LinearInterpolator());
         *         marker.setAnimation(animation);
         *         marker.startAnimation();
         */

        /**
         * Marker 点击事件
         * 点击 Marker 时会回调AMap.OnMarkerClickListener，监听器的实现示例如下
         *  AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
         *             // marker 对象被点击时回调的接口
         *             // 返回 true 则表示接口已响应事件，否则返回false
         *             @Override
         *             public boolean onMarkerClick(Marker marker) {
         *                 return false;
         *             }
         *         };
         *         mAMap.setOnMarkerClickListener(markerClickListener);
         */

        /**
         * Marker 拖拽事件
         * 拖拽 Marker 时会回调AMap.OnMarkerDragListener，监听器的实现示例如下
         * AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {
         *
         *             // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
         *             // 这个位置可能与拖动的之前的marker位置不一样。
         *             // marker 被拖动的marker对象。
         *             @Override
         *             public void onMarkerDragStart(Marker arg0) {
         *                 // TODO Auto-generated method stub
         *
         *             }
         *
         *             // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
         *             // 这个位置可能与拖动的之前的marker位置不一样。
         *             // marker 被拖动的marker对象。
         *             @Override
         *             public void onMarkerDragEnd(Marker arg0) {
         *                 // TODO Auto-generated method stub
         *
         *             }
         *
         *             // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
         *             // 这个位置可能与拖动的之前的marker位置不一样。
         *             // marker 被拖动的marker对象。
         *             @Override
         *             public void onMarkerDrag(Marker arg0) {
         *                 // TODO Auto-generated method stub
         *
         *             }
         *         };
         *         aMap.setOnMarkerDragListener(markerDragListener);
         */

        /**
         * InfoWindow 是点标记的一部分，默认的 Infowindow 只显示 Marker 对象的两个属性，一个是 title 和另一个 snippet，如果希望对InfoWindow 的样式或者内容有自定义需求，可以参考如下内容
         * 绘制默认 Infowindow
         * SDK 为用户提供了默认的 InfoWindow 样式，调用 Marker 类的 showInfoWindow() 和 hideInfoWindow() 方法可以控制显示和隐藏。当改变 Marker 的 title 和 snippet 属性时，再次调用 showInfoWindow()，可以更新 InfoWindow 显示内容
         */

        /**
         * 绘制自定义 InfoWindow
         * 实现 InfoWindowAdapter
         * InfoWindowAdapter是一个接口，其中有两个方法需要实现
         *  View getInfoWindow(Marker marker);
         *  当实现此方法并返回有效值时（返回值不为空，则视为有效）,SDK 将不会使用默认的样式，而采用此方法返回的样式（即 View）。默认会将Marker 的 title 和 snippet 显示到 InfoWindow 中
         *  如果此时修改了 Marker 的 title 或者 snippet 内容，再次调用类 Marker 的 showInfoWindow() 方法，InfoWindow 内容不会更新。自定义 InfoWindow 之后所有的内容更新都需要用户自己完成。当调用 Marker 类的 showInfoWindow() 方法时，SDK 会调用 getInfoWindow（Marker marker） 方法和 getInfoContents(Marker marker) 方法（之后会提到），在这些方法中更新 InfoWindow 的内容即可
         *  注意：如果此方法返回的 View 没有设置 InfoWindow 背景图，SDK 会默认添加一个背景图
         *
         *  View getInfoContents(Marker marker);
         *  此方法和 getInfoWindow（Marker marker） 方法的实质是一样的，唯一的区别是：此方法不能修改整个 InfoWindow 的背景和边框，无论自定义的样式是什么样，SDK 都会在最外层添加一个默认的边框
         *
         *  实现 InfoWindow 样式和内容:必须要先执行如下方法
         *  setInfoWindowAdapter(InfoWindowAdapter);//AMap类中
         *  然后实现如下接口方法，自定义Infowindow的内容和样式
         *    监听自定义infowindow窗口的infocontents事件回调
         *             @Override
         *
         *             public View getInfoWindow(Marker marker) {
         *                 return null;
         *             }
         *
         *             监听自定义infowindow窗口的infowindow事件回调
         *             @Override
         *
         *             public View getInfoContents(Marker marker) {
         *                 f(infoWindow == null) {
         *                     infoWindow = LayoutInflater.from(this).inflate(
         *                             R.layout.custom_info_window, null);
         *                 }
         *                 render(marker, infoWindow);
         *                 return infoWindow;
         *                 加载custom_info_window.xml布局文件作为InfoWindow的样式
         *                 该布局可在官方Demo布局文件夹下找到
         *             }
         *
         *         });
         *         自定义infowinfow窗口
         *         public void render(Marker marker, View view) {
         *             如果想修改自定义Infow中内容，请通过view找到它并修改
         *         }
         *
         */

        /**
         * InfoWindow 点击事件
         * 点击 InfoWindow 时会回调 AMap.OnInfoWindowClickListener
         * OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
         *
         *     @Override
         *     public void onInfoWindowClick(Marker arg0) {
         *
         *         arg0.setTitle("infowindow clicked");
         *     }
         * };
         * aMap.setOnInfoWindowClickListener(listener);
         */

    }

    private void draw() {
        drawLine();
        drawFace();
        drawHot();
    }

    /**
     * 地图上绘制的线是由 Polyline 类定义实现的，线由一组经纬度（LatLng对象）点连接而成
     * 与点标记一样，Polyline 的属性操作集中在 PolylineOptions 类中
     */
    private void drawLine() {
        /**
         * 定义该折线的颜色为黑色，宽度为 10 像素
         *  List<LatLng> latLngs = new ArrayList<LatLng>();
         *         latLngs.add(new LatLng(39.999391,116.135972));
         *         latLngs.add(new LatLng(39.898323,116.057694));
         *         latLngs.add(new LatLng(39.900430,116.265061));
         *         latLngs.add(new LatLng(39.955192,116.140092));
         *         polyline =AMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
         */

        /**
         * 这些方法均在 PolylienOptions 类中体现
         * setCustomTexture(BitmapDescriptor customTexture)设置线段的纹理，建议纹理资源长宽均为2的n次方
         * setCustomTextureIndex(java.util.List<java.lang.Integer> custemTextureIndexs)设置分段纹理index数组
         * setCustomTextureList(java.util.List customTextureList)设置分段纹理list
         * setDottedLine(boolean isDottedLine)设置是否画虚线，默认为false，画实线。
         * setUseTexture(boolean useTexture)是否使用纹理贴图
         * useGradient(boolean useGradient)设置是否使用渐变色
         * visible(boolean isVisible)设置线段的可见性
         * width(float width)设置线段的宽度，单位像素
         * zIndex(float zIndex)设置线段Z轴的值
         */

    }

    /**
     * 地图上的面分为圆形和多边形两种
     */
    private void drawFace() {
        /**
         * 绘制圆
         * 圆形由 Circle 类定义实现，构造一个圆形需要确定它的圆心和半径，具体的示例代码如下：
         * 义该圆形的填充灰色，边线颜色为灰色，宽度15 像素
         * LatLng latLng = new LatLng(39.984059,116.307771);
         * circle = AMap.addCircle(new CircleOptions().
         *         center(latLng).
         *         radius(1000).
         *         fillColor(Color.argb(progress, 1, 1, 1)).
         *         strokeColor(Color.argb(progress, 1, 1, 1)).
         *         strokeWidth(15));
         */

        /**
         * 绘制多边形
         * 多边形是由 Polygon 类定义的一组在地图上的封闭线段组成的图形，它由一组 LatLng 点按照传入顺序连接而成的封闭图形。与绘制线类似，面的属性操作集中在 PolygonOptions 中
         *
         * 绘制椭圆
         * aMap.addPolygon(new PolygonOptions()
         *                 .addAll(createRectangle( new LatLng(31.238068, 121.501654), 1, 1))
         *                 .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
         *         PolygonOptions options = new PolygonOptions();
         *         int numPoints = 400;
         *         float semiHorizontalAxis = 5f;
         *         float semiVerticalAxis = 2.5f;
         *         double phase = 2 * Math.PI / numPoints;
         *         for (int i = 0; i <= numPoints; i++) {
         *             options.add(new LatLng(Constants.BEIJING.latitude
         *                     + semiVerticalAxis * Math.sin(i * phase),
         *                     Constants.BEIJING.longitude + semiHorizontalAxis
         *                             * Math.cos(i * phase)));
         *         }
         *         // 绘制一个椭圆
         *         polygon = aMap.addPolygon(options.strokeWidth(25)
         *                 .strokeColor(Color.argb(50, 1, 1, 1))
         *                 .fillColor(Color.argb(50, 1, 1, 1)));
         *     }
         *
         *  以下是生成长方形四个顶点坐标列表的方法，可以简便的生成出长方形的四个顶点
         *  private List<LatLng> createRectangle(LatLng center, double halfWidth,
         *       double halfHeight) {
         *     List<LatLng> latLngs = new ArrayList<LatLng>();
         *     latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
         *     latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
         *     latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
         *     latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));
         *     return latLngs;
         *   }
         *
         *   绘制一个长方形
         *   aMap.addPolygon(new PolygonOptions()
         * .addAll(createRectangle(Constants.SHANGHAI, 1, 1))
         * .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
         *
         *  绘制5边形
         *  // 定义多边形的5个点点坐标
         * LatLng latLng1 = new LatLng(42.742467, 79.842785);
         * LatLng latLng2 = new LatLng(43.893433, 98.124035);
         * LatLng latLng3 = new LatLng(33.058738, 101.463879);
         * LatLng latLng4 = new LatLng(25.873426, 95.838879);
         * LatLng latLng5 = new LatLng(30.8214661, 78.788097);
         * // 声明 多边形参数对象
         * PolygonOptions polygonOptions = new PolygonOptions();
         * // 添加 多边形的每个顶点（顺序添加）
         * polygonOptions.add(latLng1, latLng2, latLng3, latLng4, latLng5);
         * polygonOptions.strokeWidth(15) // 多边形的边框
         * .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
         * .fillColor(Color.argb(1, 1, 1, 1));   // 多边形的填充色
         */

    }


    /**
     * 热力图功能提供将业务数据展示在地图上，可以给使用者直观描述一个区域的人员，车辆等事物的热度情况
     */
    private void drawHot() {
        /**
         * 第一步，组织热力图数据
         *
         生成热力点坐标列表
         LatLng[] latlngs = new LatLng[500];
         double x = 39.904979;
         double y = 116.40964;

         for (int i = 0; i < 500; i++) {
         double x_ = 0;
         double y_ = 0;
         x_ = Math.random() * 0.5 - 0.25;
         y_ = Math.random() * 0.5 - 0.25;
         latlngs[i] = new LatLng(x + x_, y + y_);
         }
         */

        /**
         * 第二步，构建热力图 HeatmapTileProvider
         * HeatmapTileProvider 是生成热力图的核心类，一些基础用法可参考如下代码
         * 构建热力图 HeatmapTileProvider
         * HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
         * builder.data(Arrays.asList(latlngs)) // 设置热力图绘制的数据
         * .gradient(ALT_HEATMAP_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
         *  Gradient 的设置可见参考手册
         * 构造热力图对象
         * HeatmapTileProvider heatmapTileProvider = builder.build();
         */

        /**
         * 第三步，绘制热力图图层
         * 通过 TileOverlay 绘制热力图，方法如下
         * 初始化 TileOverlayOptions
         * TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
         * tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
         * 向地图上添加 TileOverlayOptions 类对象
         * mAMap.addTileOverlay(tileOverlayOptions);
         */
    }

    /**
     * 轨迹记录、纠偏类需求强烈建议您使用高德开放平台提供的猎鹰SDK或者猎鹰服务API，后续版本的地图SDK会逐步停止轨迹纠偏接口的维护
     * 轨迹纠偏可帮助您将您记录的行车轨迹点进行抽稀、纠偏操作，将轨迹匹配到道路上，提供平滑的绘制效果，并计算行驶里程（地图SDK V3.4.0以上支持）；也可以通过结合高德定位帮助您记录真实行车轨迹（地图SDK V5.1.0版本以上支持）。
     * 值得注意的是，目前该功能只支持将驾车轨迹纠正到路上
     */
    private void track() {

    }

    /**
     * 调用函数animateCamera或moveCamera来改变可视区域
     */
    private void zoom() {
        //地图放大
        // CameraUpdate cameraUpdate = CameraUpdateFactory.zoomIn();
        //地图缩小
        // CameraUpdate cameraUpdate = CameraUpdateFactory.zoomOut();
        // mAMap.moveCamera(cameraUpdate);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        drawProvinceOrCity(districtResult);
    }

    private void drawProvinceOrCity(DistrictResult districtResult) {
        Maybe.create(new MaybeOnSubscribe<DistrictResult>() {
            @Override
            public void subscribe(MaybeEmitter<DistrictResult> emitter) {
                if (districtResult == null || districtResult.getDistrict() == null) {
                    return;
                }
                if (districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
                    final DistrictItem item = districtResult.getDistrict().get(0);
                    if (item == null) {
                        return;
                    }
                }
                emitter.onSuccess(districtResult);
                emitter.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(_mActivity)))
                .subscribe(new MaybeObserver<DistrictResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DistrictResult districtResult1) {
                        //返回查询行政区的结果
                        ArrayList<DistrictItem> district = districtResult.getDistrict();
                        //行政区信息类
                        if (district.size() == 0) {
                            return;
                        }
                        DistrictItem item = district.get(0);
                        //以字符串数组形式返回行政区划边界值.经度和纬度之间用","分隔，坐标点之间用";"分隔,各个岛屿之间的经纬度使用"|"分隔
                        String[] polyStr = item.districtBoundary();
                        if (polyStr == null || polyStr.length == 0) {
                            return;
                        }
                        //一个行政区域所有边界点的集合
                        //数据量太大，所以可能分组

                        for (int i = 0; i < polyStr.length; i++) {
                            String str = polyStr[i];
                            if (!str.contains("|")) {
                                String[] lat = str.split(";");
                                PolygonOptions polygonOptions = new PolygonOptions();
                                polygonOptions.fillColor(Color.BLUE);
                                polygonOptions.strokeColor(Color.BLUE);
                                boolean isFirst = true;
                                LatLng firstLatLng = null;
                                for (int i1 = 0; i1 < lat.length; i1++) {
                                    String latstr = lat[i1];
                                    //一个经纬点
                                    String[] lats = latstr.split(",");
                                    if (isFirst) {
                                        isFirst = false;
                                        firstLatLng = new LatLng(Double.parseDouble(lats[1]), Double.parseDouble(lats[0]));
                                    }
                                    polygonOptions.add(new LatLng(Double.parseDouble(lats[1]), Double.parseDouble(lats[0])));
                                }
                                if (firstLatLng != null) {
                                    polygonOptions.add(firstLatLng);
                                }
                                //添加多边形 PolygonOptions
                                mAMap.addPolygon(polygonOptions);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 绘制地区多边形
     *
     * @param cityName
     */
    private void drawProvinceOrCity(String cityName) {
        boolean has = false;
        int size = mAllProvinces.size();
        for (int i = 0; i < size; i++) {
            String s = mAllProvinces.get(i);
            if (s.equals(cityName)) {
                has = true;
            }
        }
        mAllProvinces.add(cityName);
        if (!has) {
            DistrictSearch search = new DistrictSearch(_mActivity);
            DistrictSearchQuery query = new DistrictSearchQuery();
            search.setOnDistrictSearchListener(this);
            query.setShowBoundary(true);
            query.setShowChild(false);
            query.setKeywords(cityName);
            search.setQuery(query);
            search.searchDistrictAsyn();
        }
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        Logger.d(latLonPoint.getLatitude() + ", " + latLonPoint.getLongitude());
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20000, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name) {
        GeocodeQuery query = new GeocodeQuery(name, "010");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }


    /**
     * 屏幕像素坐标转经纬度
     */
    private void toGeoLocation(int x, int y) {
        Point point = new Point(x, y);
        LatLng latLng = mAMap.getProjection().fromScreenLocation(point);
    }

    /**
     * 经纬度坐标转屏幕像素坐标
     */
    private Point toScreenLocation(LatLng latLng) {
        return mAMap.getProjection().toScreenLocation(latLng);
    }

    /**
     * 获取系统中所有照片的信息，照片名，拍摄时间，拍摄经纬度
     * 很多信息都可能是null
     */
    private void getAllSystemPicInfo() {
        Maybe.create(new MaybeOnSubscribe<ArrayList<SystemPictureInfo>>() {
            @Override
            public void subscribe(MaybeEmitter<ArrayList<SystemPictureInfo>> emitter) {
                Cursor cursor = BaseApplication.mApplicationContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
                if (cursor == null) {
                    return;
                }
                ArrayList<SystemPictureInfo> systemPictureInfos = new ArrayList<>();
                while (cursor.moveToNext()) {
                    SystemPictureInfo systemPictureInfo = new SystemPictureInfo();
                    systemPictureInfos.add(systemPictureInfo);
                    //获取图片的名称 mmexport1556030534481.webp
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    // storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1556030534481.webp
                    byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String path = new String(data, 0, data.length - 1);
                    //获取图片的详细信息，很可能为null
                    String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                    //The date & time that the image was taken in units of milliseconds since jan 1, 1970.
                    String date_taken = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                    //String shootTime = AppUtils.transMilesToDate(Long.parseLong(date_taken));
                    //经纬度
                    String latitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
                    String longitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
                    systemPictureInfo.picName = name;
                    systemPictureInfo.picPath = path;
                    systemPictureInfo.picShoot = date_taken;
                    systemPictureInfo.picLatitude = latitude;
                    systemPictureInfo.picLongitude = longitude;
                }
                cursor.close();
                emitter.onSuccess(systemPictureInfos);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new MaybeObserver<ArrayList<SystemPictureInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<SystemPictureInfo> systemPictureInfos) {
                        if (systemPictureInfos == null || systemPictureInfos.size() == 0) {
                            return;
                        }
                        //根据经纬度查询
                        int size = systemPictureInfos.size();
                        for (int i = 0; i < size; i++) {
                            SystemPictureInfo systemPictureInfo = systemPictureInfos.get(i);
                            Logger.d(systemPictureInfo);
                            if (StringUtils.isEmpty(systemPictureInfo.picLatitude) || StringUtils.isEmpty(systemPictureInfo.picLongitude)) {
                                continue;
                            }
                            float l;
                            float n;
                            try {
                                l = Float.valueOf(systemPictureInfo.picLatitude);
                                n = Float.valueOf(systemPictureInfo.picLatitude);
                            } catch (Exception e) {
                                continue;
                            }
                            getAddress(new LatLonPoint(l, n));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 获取当前地理位置信息
     */
    private void getCurrentLocation() {
        RxPermissions rxPermissions = new RxPermissions(_mActivity);
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                                @Override
                                public void gotLocation(Location location) {
                                    Logger.d(location.getLatitude() + ", " + location.getLongitude());
                                }
                            };
                            MyLocation myLocation = new MyLocation();
                            myLocation.getLocation(_mActivity, locationResult);

                        } else {
                            Toast.makeText(_mActivity, "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加Marker
     *
     * @param latLng
     */
    private void addMarker(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        if (mAMap == null) {
            return;
        }
        MarkerOptions markerOption = new MarkerOptions().position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_favorite_border_black_24dp)));
        markerOption.anchor(0.5f, 0.5f);
        Marker marker = mAMap.addMarker(markerOption);
        marker.setClickable(false);
    }


    /**
     * 添加Markers
     *
     * @param latLngs
     */
    private void addMarkers(ArrayList<LatLng> latLngs) {
        if (latLngs == null) {
            return;
        }
        int size = latLngs.size();
        if (size == 0) {
            return;
        }
        if (mAMap == null) {
            return;
        }
        //去重，因为可能得到的经纬度完全一样，这样去重保证了顺序
        Set<LatLng> middleLinkedHashSet = new LinkedHashSet<>(latLngs);
        List<LatLng> afterHashSetList = new ArrayList<>(middleLinkedHashSet);
        for (LatLng localtion : afterHashSetList) {
            addMarker(localtion);
        }
        middleLinkedHashSet=null;
        afterHashSetList=null;
        latLngs=null;
    }


    /**
     * 删除Markers
     *
     * @param latLngs
     */
    private void removeMarkers(ArrayList<LatLng> latLngs) {
        if (latLngs == null) {
            return;
        }
        int size = latLngs.size();
        if (size == 0) {
            return;
        }
        //获取当前可见地图区域的所有marker，注意是可见区域。不看见的区域也可能有marker
        List<Marker> mapScreenMarkers = mAMap.getMapScreenMarkers();
        int size1 = mapScreenMarkers.size();
        if (size1 == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            LatLng latLng = latLngs.get(i);
            for (int i1 = 0; i1 < size1; i1++) {
                Marker marker = mapScreenMarkers.get(i1);
                LatLng position = marker.getPosition();
                if ((latLng.latitude == position.latitude) && (latLng.longitude == position.longitude)) {
                    //移除当前Marker
                    marker.remove();
                }
            }
        }
        //刷新地图
        mMapView.invalidate();
    }

    /**
     * 根据一系列坐标点缩放地图
     *
     * @param pointList
     */
    private void zoomToSpan(List<LatLng> pointList) {
        if (pointList != null && pointList.size() > 0) {
            if (mAMap == null)
                return;
            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < pointList.size(); i++) {
                LatLng p = pointList.get(i);
                b.include(p);
            }
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(b.build(), 80);
            mAMap.moveCamera(cameraUpdate);
        }
    }

    /**
     * 根据中心点和一系列坐标点缩放地图
     *
     * @param centerpoint
     * @param pointList
     */
    public void zoomToSpanWithCenter(LatLng centerpoint, List<LatLng> pointList) {
        if (pointList != null && pointList.size() > 0) {
            if (mAMap == null)
                return;
            LatLngBounds.Builder b = LatLngBounds.builder();
            if (centerpoint != null) {
                for (int i = 0; i < pointList.size(); i++) {
                    LatLng p = pointList.get(i);
                    LatLng p1 = new LatLng((centerpoint.latitude * 2) - p.latitude, (centerpoint.longitude * 2) - p.longitude);
                    b.include(p);
                    b.include(p1);
                }
            }
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 50));
        }
    }

}