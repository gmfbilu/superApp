package org.gmfbilu.superapp.module_view.customViews.canvasView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

/**
 * translate(x,y);位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动
 * <p>
 * scale (float sx, float sy, float px, float py);前两个参数分别为x轴和y轴的缩放比例，后两个参数用来控制缩放中心位置的
 * 缩放比例(sx,sy)取值范围：
 * (-∞, -1)	先根据缩放中心放大n倍，再根据中心轴进行翻转
 * -1	根据缩放中心轴进行翻转
 * (-1, 0)	先根据缩放中心缩小到n，再根据中心轴进行翻转
 * 0	不会显示，若sx为0，则宽度为0，不会显示，sy同理
 * (0, 1)	根据缩放中心缩小到n
 * 1	没有变化
 * (1, +∞)	根据缩放中心放大n倍
 * <p>
 * rotate (float degrees, float px, float py):后两个参数是旋转中心点
 * <p>
 * 快照(save)和回滚(restore)：画布的操作是不可逆的，而且很多画布操作会影响后续的步骤
 * <p>
 * 将Bitmap绘制到画布上:
 * drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint):后两个参数(matrix, paint)是在绘制的时候对图片进行一些改变，如果只是需要将图片内容绘制出来只需要canvas.drawBitmap(bitmap,new Matrix(),new Paint())就可以了
 * drawBitmap (Bitmap bitmap, float left, float top, Paint paint)：绘制时指定了图片左上角的坐标(距离坐标原点的距离)
 * drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint):src指定了图片绘制部分的区域，dst指定了绘制在屏幕上的绘制区域
 */
public class CanvasView extends BaseView {

    private Bitmap canvas_bitmap;
    private int canvas_bitmapW, canvas_bitmapH;
    private Rect canvas_bitmap_dst = new Rect(), canvas_bitmap_src = new Rect();


    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         *通过BitmapFactory从不同位置获取Bitmap:
         */
        //资源文件(drawable/mipmap/raw):Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.raw.bitmap);
        //资源文件(assets):
      /*  Bitmap bitmap=null;
        try {
            InputStream is = mContext.getAssets().open("bitmap.png");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //内存卡文件:Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");
        //网络文件:

        canvas_bitmap = BitmapFactory.decodeResource(_mContext.getResources(), R.mipmap.canvas_bitmap);
        canvas_bitmapW = canvas_bitmap.getWidth();
        canvas_bitmapH = canvas_bitmap.getHeight();
        startAnimation();
    }

    private void startAnimation() {
        canvas_bitmap_dst.left = 0;
        canvas_bitmap_dst.top = 0;
        canvas_bitmap_dst.right = 0;
        canvas_bitmap_dst.bottom = 0;
        canvas_bitmap_src = canvas_bitmap_dst;
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                canvas_bitmap_dst.left = 0;
                canvas_bitmap_dst.top = 0;
                canvas_bitmap_dst.right = (int) (canvas_bitmapW * currentValue);
                canvas_bitmap_dst.bottom = canvas_bitmapH;
                canvas_bitmap_src = canvas_bitmap_dst;
                if (currentValue == 1) {
                    startAnimation();
                }
                invalidate();
            }
        });
        anim.setDuration(5000);
        anim.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = _mScreenWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = AppUtils.dp2px(200);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(canvas_bitmap, new Matrix(), null);
        //canvas.translate(canvas_bitmapW, 0);
        canvas.drawBitmap(canvas_bitmap, canvas_bitmap_src, canvas_bitmap_dst, null);
    }
}
