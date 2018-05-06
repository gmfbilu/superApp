package org.gmfbilu.superapp.module_util.surfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 在Activity或者Fragment中需要重写onPause和onResume，在onPause中停止SurfaceView的绘制，在onPause中启动绘制
 * https://www.jianshu.com/u/82a1196fe262
 */
public class MySurfaceView extends SurfaceView implements Runnable{

    private Thread mThread;
    private boolean isRunning;
    private SurfaceHolder mSurfaceHolder;

    public MySurfaceView(Context context) {
        this(context,null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
    }

    @Override
    public void run() {
        while (isRunning){
            // 判断Surface是否已经准备好
            if (!mSurfaceHolder.getSurface().isValid()){
                continue;
            }
            Canvas canvas = mSurfaceHolder.lockCanvas();
            //draw something
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        isRunning=false;
        while (true){
            try {
                //让调用该方法的线程执行run方法中的内容直到结束
                mThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        mThread=null;
    }

    /**
     * 启动一个新线程并执行start方法，此时MySurfaceView中的run方法会在Thread线程执行
     */
    public void resume(){
        isRunning=true;
        mThread = new Thread(this);
        mThread.start();
    }
}
