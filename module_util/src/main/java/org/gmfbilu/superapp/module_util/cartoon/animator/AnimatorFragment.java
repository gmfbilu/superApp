package org.gmfbilu.superapp.module_util.cartoon.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

/**
 * https://github.com/OCNYang/Android-Animation-Set/tree/master/property-animation
 * 属性动画,属性动画实现原理就是修改控件的属性值实现的动画
 * <p>
 * <p>
 * 在使用属性动画之前先来看几个常用的 View 属性成员：
 * translationX，translationY：控制View的位置，值是相对于View容器左上角坐标的偏移。
 * rotationX，rotationY：控制相对于轴心旋转。
 * x，y：控制View在容器中的位置，即左上角坐标加上translationX和translationY的值。
 * alpha：控制View对象的alpha透明度值。
 * <p>
 * 属性动画的实现有7个类,平时使用属性动画的重点就在于 AnimatorSet、ObjectAnimator、TimeAnimator、ValueAnimator
 * ValueAnimator:<animator> 放置在 res/animator/ 目录下,在一个特定的时间里执行一个动画
 * TimeAnimator:不支持xml,时序监听回调工具
 * ObjectAnimator:<objectAnimator> 放置在 res/animator/ 目录下,一个对象的一个属性动画
 * AnimatorSet:<set> 放置在 res/animator/ 目录下,动画集合
 * <p>
 * Android 属性动画提供了以下属性：
 * Duration：动画的持续时间
 * TimeInterpolation：定义动画变化速率的接口，所有插值器都必须实现此接口，如线性、非线性插值器
 * TypeEvaluator：用于定义属性值计算方式的接口，有 int、float、color 类型，根据属性的起始、结束值和插值一起计算出当前时间的属性值
 * Animation sets：动画集合，即可以同时对一个对象应用多个动画，这些动画可以同时播放也可以对不同动画设置不同的延迟
 * Frame refreash delay：多少时间刷新一次，即每隔多少时间计算一次属性值，默认为 10ms，最终刷新时间还受系统进程调度与硬件的影响
 * Repeat Country and behavoir：重复次数与方式，如播放3次、5次、无限循环，可以让此动画一直重复，或播放完时向反向播放
 */
public class AnimatorFragment extends BaseFragment {

    private AppCompatButton bt_alpha;
    private AppCompatButton bt_rotation;
    private AppCompatButton bt_translationX;
    private AppCompatButton bt_scaleY;
    private AppCompatButton bt_animatorSet;
    private AppCompatButton bt_viewPropertyAnimator;

    public static AnimatorFragment newInstance() {
        Bundle args = new Bundle();
        AnimatorFragment fragment = new AnimatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        bt_alpha = view.findViewById(R.id.bt_alpha);
        bt_alpha.setOnClickListener(this);
        bt_rotation = view.findViewById(R.id.bt_rotation);
        bt_rotation.setOnClickListener(this);
        bt_translationX = view.findViewById(R.id.bt_translationX);
        bt_translationX.setOnClickListener(this);
        bt_scaleY = view.findViewById(R.id.bt_scaleY);
        bt_scaleY.setOnClickListener(this);
        bt_animatorSet = view.findViewById(R.id.bt_animatorSet);
        bt_animatorSet.setOnClickListener(this);
        bt_viewPropertyAnimator = view.findViewById(R.id.bt_viewPropertyAnimator);
        bt_viewPropertyAnimator.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_cartoon_animator;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_alpha) {
            hello4();
        } else if (id == R.id.bt_rotation) {
            hello5();
        } else if (id == R.id.bt_translationX) {
            hello6();
        } else if (id == R.id.bt_scaleY) {
            hello7();
        } else if (id == R.id.bt_animatorSet) {
            hello8();
        } else if (id == R.id.bt_viewPropertyAnimator) {
            hello12();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        hello1();
        hello2();
        hello3();
        //hello4();
        //hello5();
        //hello6();
        //hello7();
        //hello8();
        hello9();
        //hello10();
        hello11();
        // hello12();
    }

    private void hello1() {
        //ValueAnimator只不过是对值进行了一个平滑的动画过渡，实际使用到这种功能的场景和View动画类似
        //将一个值从0平滑过渡到1，时长300毫秒。300毫秒是默认值
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(300);
        //在动画执行的过程中会不断地进行回调
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                //Logger.d(currentValue);
            }
        });
        //设置动画延迟播放的时间
        anim.setStartDelay(1000);
        //设置动画循环播放的次数
        anim.setRepeatCount(1);
        //设置动画循环播放的模式,循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.start();
    }

    private void hello2() {
        //ofFloat()方法当中是可以传入任意多个参数的，因此我们还可以构建出更加复杂的动画逻辑，比如说将一个值在5秒内从0过渡到5，再过渡到3，再过渡到10
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 5f, 3f, 10f);
        anim.setDuration(5000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                //Logger.d(currentValue);
            }
        });
        anim.start();
    }

    private void hello3() {
        //ValueAnimator当中最常用的应该就是ofFloat()和ofInt()这两个方法，还有ofObject()方法
        //将一个整数值从0平滑地过渡到100
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                Logger.d(currentValue);
            }
        });
        anim.start();
    }

    private void hello4() {
        //ObjectAnimator比ValueAnimator强大得多，它是可以直接对任意对象的任意属性进行动画操作的，比如说View的alpha属性
        //虽说ObjectAnimator会更加常用一些，但是它其实是继承自ValueAnimator的，底层的动画实现机制也是基于ValueAnimator来完成的，因此ValueAnimator仍然是整个属性动画当中最核心的一个类
        /**
         * 将一个AppCompatButton在5秒中内从常规变换成全透明，再从全透明变换成常规
         * 第一个参数要求传入一个object对象，我们想要对哪个对象进行动画操作就传入什么
         * 第二个参数是想要对该对象的哪个属性进行动画操作，由于我们想要改变AppCompatButton的不透明度，因此这里传入"alpha"
         * 后面的参数就是不固定长度了，想要完成什么样的动画就传入什么值
         *
         * (可以传入任意的值到ofFloat()方法的第二个参数当中,因为ObjectAnimator在设计的时候就没有针对于View来进行设计，而是针对于任意对象的，它所负责的工作就是不断地向某个对象中的某个属性进行赋值，然后对象根据属性值的改变再来决定如何展现出来)
         * (这段代码的意思就是ObjectAnimator会帮我们不断地改变AppCompatButton对象中alpha属性的值，从1f变化到0f。然后AppCompatButton对象需要根据alpha属性值的改变来不断刷新界面的显示，从而让用户可以看出淡入淡出的动画效果)
         * (那么AppCompatButton对象中是不是有alpha属性这个值呢？没有，不仅AppCompatButton没有这个属性，连它所有的父类也是没有这个属性的)
         * (这就奇怪了AppCompatButton当中并没有alpha这个属性，ObjectAnimator是如何进行操作的呢？其实ObjectAnimator内部的工作机制并不是直接对我们传入的属性名进行操作的，
         * 而是会去寻找这个属性名对应的get和set方法，因此alpha属性所对应的get和set方法应该就是:
         * public void setAlpha(float value);
         * public float getAlpha();
         * 那么AppCompatButton对象中是否有这两个方法呢？确实有，并且这两个方法是由View对象提供的，也就是说不仅AppCompatButton可以使用这个属性来进行淡入淡出动画操作，任何继承自View的对象都可以的
         * View当中一定也存在着setRotation()、getRotation()、setTranslationX()、getTranslationX()、setScaleY()、getScaleY()这些方法
         */
        ObjectAnimator animator = ObjectAnimator.ofFloat(bt_alpha, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    private void hello5() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(bt_rotation, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
    }

    private void hello6() {
        //将AppCompatButton先向右移出屏幕，然后再移动回来。
        //第一个translationX当前AppCompatButton的translationX的位置也就是初始位置，第二个参数是移动值，第三个参数是最终位置
        //不等参数最少要有一个初始值和结束值一共两个参数，中间的值是过渡状态
        float translationX = bt_translationX.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(bt_translationX, "translationX", translationX, 500f, translationX);
        animator.setDuration(5000);
        animator.start();
    }

    private void hello7() {
        //将AppCompatButton在垂直方向上放大3倍再还原
        ObjectAnimator animator = ObjectAnimator.ofFloat(bt_scaleY, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    private void hello8() {
        /**
         * after(Animator anim)   将现有动画插入到传入的动画之后执行
         * after(long delay)   将现有动画延迟指定毫秒后执行
         * before(Animator anim)   将现有动画插入到传入的动画之前执行
         * with(Animator anim)   将现有动画和传入的动画同时执行
         */
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(bt_animatorSet, "translationX", 500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(bt_animatorSet, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(bt_animatorSet, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate)
                .with(fadeInOut) //同时执行
                .after(moveIn);//最先执行
        animSet.setDuration(5000);
        animSet.start();
    }

    private void hello9() {
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.start();
        //动画过程中数值不断变化的的监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            //动画开始的时候调用
            @Override
            public void onAnimationStart(Animator animation) {
            }

            //动画重复执行的时候调用
            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            //动画结束的时候调用
            @Override
            public void onAnimationEnd(Animator animation) {
            }

            //动画被取消的时候调用
            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

        //只需要某个监听方法的时候
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

    }

    private void hello10() {
        //TypeEvaluator就是告诉动画系统如何从初始值过度到结束值
        //ValueAnimator.ofFloat()方法就是实现了初始值与结束值之间的平滑过度，其实就是系统内置了一个FloatEvaluator，它通过计算告知动画系统如何从初始值过度到结束值
        //FloatEvaluator实现了TypeEvaluator接口，然后重写evaluate()方法。evaluate()方法当中传入了三个参数，第一个参数fraction用于表示动画的完成度的，我们应该根据它来计算当前动画的值应该是多少，第二第三个参数分别表示动画的初始值和结束值。用结束值减去初始值，算出它们之间的差值，然后乘以fraction这个系数，再加上初始值，那么就得到当前动画的值了
        //先是new出了两个Point对象，并在构造函数中分别设置了它们的坐标点。然后调用ValueAnimator的ofObject()方法来构建ValueAnimator的实例，这里需要注意的是，ofObject()方法要求多传入一个TypeEvaluator参数
        Point point1 = new Point(0, 0);
        Point point2 = new Point(300, 300);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), point1, point2);
        anim.setDuration(5000);
        anim.start();

    }

    private void hello11() {
        //Interpolator是补间器的意思，它的主要作用是可以控制动画的变化速率,TimeInterpolator是一个接口，只有一个getInterpolation()方法
        //getInterpolation()方法中接收一个input参数，这个参数的值会随着动画的运行而不断变化，不过它的变化是非常有规律的，就是根据设定的动画时长匀速增加，变化范围是0到1
        //input的值决定了fraction的值。input的值是由系统经过计算后传入到getInterpolation()方法中的，然后我们可以自己实现getInterpolation()方法中的算法，根据input的值来计算出一个返回值，而这个返回值就是fraction了
        //最简单的情况就是input值和fraction值是相同的，这种情况由于input值是匀速增加的，因而fraction的值也是匀速增加的，所以动画的运动情况也是匀速的。系统中内置的LinearInterpolator就是一种匀速运动的Interpolator
    }

    private void hello12() {
        //属性动画的机制已经不是再针对于View而进行设计的了，而是一种不断地对值进行操作的机制，它可以将值赋值到指定对象的指定属性上。但是，在绝大多数情况下开发者主要都还是对View进行动画操作的。Android开发团队也是意识到了这一点，没有为View的动画操作提供一种更加便捷的用法确实是有点太不人性化了，于是在Android 3.1系统当中补充了ViewPropertyAnimator这个机制
        bt_viewPropertyAnimator.animate().x(500).y(500).setDuration(5000).setInterpolator(new BounceInterpolator());
    }
}
