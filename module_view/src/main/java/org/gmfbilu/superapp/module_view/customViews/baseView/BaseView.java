package org.gmfbilu.superapp.module_view.customViews.baseView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;

/**
 * 自定义View需要处理padding，无需处理margin。自定义ViewGroup需要同时处理padding和margin
 * <p>
 * sinA=对边/斜边，cosA=邻边/斜边，tanA=对边/邻边。Math.sin(x)中的X是弧度，弧度 = 角度 * Math.PI / 180
 * Math.toDegrees() - 弧度转角度
 * Math.toRadians - 角度转弧度
 */
public abstract class BaseView extends View {

    /**
     * 上下文
     */
    protected Context _mContext;
    /**
     * View宽高
     */
    protected int _mViewWidth, _mViewHeight;
    /**
     * View内容宽高,就是View宽高减去padding后的值
     */
    protected int _mViewContentWidth, _mViewContentHeight;
    /**
     * view内容左上角坐标,View坐标系
     */
    protected float _mViewContentStartX, _mViewContentStartY;
    /**
     * padding
     */
    protected int _mPaddingLeft, _mPaddingRight, _mPaddingTop, _mPaddingBottom;

    /**
     * 屏幕宽高度
     */
    protected int _mScreenWidth, _mScreenHeight;

    //一般在直接New一个View的时候调用
    public BaseView(Context context) {
        this(context, null);
    }

    //一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来
    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _mContext = context;
        _mScreenWidth = AppUtils.getScreenWidth();
        _mScreenHeight = AppUtils.getScreenHeight();
        /**
         * padding就是XML中设置的padding,padding在初始化就可以得到
         */
        _mPaddingLeft = getPaddingLeft();
        _mPaddingRight = getPaddingRight();
        _mPaddingTop = getPaddingTop();
        _mPaddingBottom = getPaddingBottom();
    }

    /**
     * 确定大小
     * view宽高就是h和w
     * 当控件设置wrap_content的时候view宽高就是默认宽高mViewDefaultWidth和mViewDefaultHeight，和padding，margin无关
     * 当控件设置具体大小的时候view宽高就是具体大小的宽高，和padding，margin无关
     * getHeight()和getWidth()就是view宽高也就是h和w
     *
     * @param w    当前view的宽高
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        _mViewWidth = w;
        _mViewHeight = h;
        _mViewContentWidth = _mViewWidth - _mPaddingLeft - _mPaddingRight;
        _mViewContentHeight = _mViewHeight - _mPaddingTop - _mPaddingBottom;

        //view顶点坐标(viewLeft,viewTop),(viewRight,viewBottom),是个矩形区域。坐标点和margin有关
        //viewBottom-viewTop=h
        //viewRight-viewLeft=w
        int viewLeft = getLeft();
        int viewTop = getTop();
        int viewRight = getRight();
        int viewBottom = getBottom();
        _mViewContentStartX = _mPaddingLeft;
        _mViewContentStartY = _mPaddingTop;
        Logger.d("_mViewWidth=" + _mViewWidth + " ,_mViewHeight=" + _mViewHeight +
                " ,\n_mViewContentWidth=" + _mViewContentWidth + " ,_mViewContentHeight=" + _mViewContentHeight +
                " ,\n_mPaddingLeft=" + _mPaddingLeft + " ,_mPaddingRight=" + _mPaddingRight + " ,_mPaddingTop=" + _mPaddingTop + " ,_mPaddingBottom=" + _mPaddingBottom +
                " ,\n_mScreenWidth=" + _mScreenWidth + " ,_mScreenHeight=" + _mScreenHeight +
                " ,\nviewLeft=" + viewLeft + " ,viewTop=" + viewTop +
                " ,\n_mViewContentStartX=" + _mViewContentStartX + " ,_mViewContentStartY=" + _mViewContentStartY);
    }

    /**
     * 设置默认view大小 ,模板代码
     * onMeasure会被多次调用，且调用完毕后再才调用onSizeChanged方法。
     * 所以在onMeasure无法获取View的具体大小，以及View内容的大小,但是可以获取View的padding值
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent就是屏幕的宽度，如果是具体的值就直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            //默认宽度是可以超过屏幕宽度的，这里默认超过屏幕宽度
            width = _mScreenWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //这里默认高度是100dp
            height = AppUtils.dp2px(100);
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }*/

}
