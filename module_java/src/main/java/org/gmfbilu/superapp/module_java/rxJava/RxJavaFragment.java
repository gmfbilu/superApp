package org.gmfbilu.superapp.module_java.rxJava;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_java.R;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 从事件流这个角度来说明RxJava的基本工作原理
 * 先假设有两根水管：上面一根水管为事件产生的水管，叫它上游，下面一根水管为事件接收的水管叫它下游
 * 两根水管通过一定的方式连接起来，使得上游每产生一个事件，下游就能收到该事件
 * 这里的上游和下游就分别对应着RxJava中的Observable和Observer，它们之间的连接就对应着subscribe()
 */

public class RxJavaFragment extends BaseFragment {

    private Toolbar mToolbar;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static RxJavaFragment newInstance() {
        Bundle args = new Bundle();
        RxJavaFragment fragment = new RxJavaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_java_toolbar);
    }

    @Override
    public int setLayout() {
        return R.layout.module_java_fragment_rxjava;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("RxJava");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        helloRxJava();
        hello2RxJava();
        hello3RxJava();
        rxJavaOperators1();
        rxJavaOperators2();
        rxJavaOperators3();
        rxJavaOperators4();
        rxJavaOperators5();
        rxJavaOperators6();
        rxJavaOperators7();
        rxJavaOperators8();
        rxJavaOperators9();
        rxJavaOperators10();
        rxJavaOperators11();
        rxJavaOperators12();
        rxJavaOperators13();
        rxJavaOperators14();
        rxJavaOperators15();
        rxJavaOperators16();
        rxJavaOperators17();
        rxJavaOperators18();
        rxJavaOperators19();
        rxJavaOperators20();
        rxJavaOperators21();
        rxJavaOperators22();
    }

    private void helloRxJava() {
        //create 操作符应该是最常见的操作符了，主要用于产生一个 Obserable 被观察者对象,为了方便认知，统一把被观察者 Observable 称为发射器（上游事件），观察者 Observer 称为接收器（下游事件）
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {

            /**
             *  ObservableEmitter用来发射事件的,它可以发出三种类型的事件
             *  并不意味着你可以随意乱七八糟发射事件，需要满足一定的规则：
             *  上游可以发送无限个onNext, 下游也可以接收无限个onNext.
             *  当上游发送了一个onComplete后, 上游onComplete之后的事件将会继续发送, 而下游收到onComplete事件之后将不再继续接收事件.
             *  当上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件
             *  上游可以不发送onComplete或onError
             *  最为关键的是onComplete和onError必须唯一并且互斥
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onComplete();
                //emitter.onError();
            }
        });
        //创建下游
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }

            /**
             * 可以把Disposable理解成两根管道之间的一个机关, 当调用它的dispose()方法时, 它就会将两根管道切断, 从而导致下游收不到事件
             * 注意: 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件
             */
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }
        };
        //上游连接下游
        integerObservable.safeSubscribe(observer);
    }

    private void hello2RxJava() {
        //链式调用
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

            }
        }).safeSubscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void hello3RxJava() {
        /**
         * 正常情况下, 上游和下游是工作在同一个线程中的, 也就是说上游在哪个线程发事件, 下游就在哪个线程接收事件
         * 在RxJava中, 当我们在主线程中去创建一个上游Observable来发送事件, 则这个上游默认就在主线程发送事件。当我们在主线程去创建一个下游Observer来接收事件, 则这个下游默认就在主线程中接收事件
         * 我们更多想要的是这么一种情况, 在子线程中做耗时的操作, 然后回到主线程中来操作UI
         * 我们需要先改变上游发送事件的线程, 让它去子线程中发送事件, 然后再改变下游的线程, 让它去主线程接收事件. 通过RxJava内置的线程调度器可以很轻松的做到这一点
         *
         * 在RxJava中, 已经内置了很多线程选项供我们选择, 例如有
         * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
         * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
         * Schedulers.newThread() 代表一个常规的新线程
         * AndroidSchedulers.mainThread() 代表Android的主线程
         */
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

            }
        });
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        integerObservable
                /**
                 * 指定的是上游发送事件的线程
                 * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略
                 */
                .subscribeOn(Schedulers.newThread())
                /**
                 * 指定的是下游接收事件的线程
                 * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次
                 */
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(observer);

    }


    private void rxJavaOperators1() {
        //just操作符是一个接着一个的发射数据给下游
        Observable.just("best", "player", "is", "messi")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Log.e(Constant.LOG_NAME, d.isDisposed() + "");
                    }

                    @Override
                    public void onNext(String s) {
                        // Log.e(Constant.LOG_NAME, s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Log.e(Constant.LOG_NAME, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Log.e(Constant.LOG_NAME, "onComplete");
                    }
                });
    }

    private void rxJavaOperators2() {
        /**
         * map是RxJava中最简单的一个变换操作符了, 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化
         * 在上游我们发送的是数字类型, 而在下游我们接收的是String类型, 中间起转换作用的就是map操作符
         * map和flatMap，前者是数据类型或者数据结构的改变，而后者是将源Observable转换为新建Observable
         * map操作符只能把“Observable<RegisterResponse>”里面的“RegisterResponse”转化成“LoginResponse”，而“LoginResponse”只是一个model对象
         * flatmap能转化发射源，既“Observable<RegisterResponse>” -> "Observable<LoginResponse>" ,配合Retrofit就能在完成注册事件后继续完成登录事件
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return "" + integer;
            }
        }).safeSubscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators3() {
        /**
         * flatMap是一个非常强大的操作符
         * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里
         * 上游每发送一个事件, flatMap都将创建一个新的水管, 然后发送转换之后的新的事件, 下游接收到的就是这些新的水管发送的数据. 这里需要注意的是, flatMap并不保证事件的顺序
         * 如果需要保证顺序则需要使用concatMap
         * 用户注册后紧接着登录可以使用flatMap
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) {
                //我们在flatMap中将上游发来的每个事件转换为一个新的发送2个String事件的水管, 为了看到flatMap结果是无序的,所以加了10毫秒的延时
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).safeSubscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                // Logger.d(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators4() {
        /**
         * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件. 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据
         * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取, 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了
         */
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                //Logger.d(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators5() {
        //disposables可以取消订阅事件
        disposables.add(Observable.defer(new Callable<ObservableSource<? extends String>>() {

            @Override
            public ObservableSource<? extends String> call() {
                // Do some long running operation
                SystemClock.sleep(1000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void rxJavaOperators6() {
        //take操作符指定下游最多收到多少个事件
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //只允许前三个事件通过
                .take(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Log.e(Constant.LOG_NAME, d.isDisposed() + "");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        //Log.e(Constant.LOG_NAME, integer + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(Constant.LOG_NAME, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //.e(Constant.LOG_NAME, "onComplete");
                    }
                });
    }

    private void rxJavaOperators7() {
        //timer操作符是在一段时间后执行发射事件
        Observable
                //2秒后执行
                .timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxJavaOperators8() {
        //interval操作符是间隔一段时间发射一次事件
        disposables.add(Observable.interval(0, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        // Log.e(Constant.LOG_NAME, aLong + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Log.e(Constant.LOG_NAME, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Log.e(Constant.LOG_NAME, "onComplete");
                    }
                }));
    }

    private void rxJavaOperators9() {
        //Single类似于Observable，只发射一条单一的数据，或者一条异常通知，不能发射完成通知，其中数据与异常通知只能发射一个
        //Single,Completable、Maybe都是简化版的Observable
        //Observale和Flowable都是用来发射数据流的，但是，我们在实际应用中，很多时候需要发射的数据并不是数据流的形式，而只是一条单一的数据，或者一条完成通知，或者一条错误通知。在这种情况下，我们再使用Observable或者Flowable就显得有点大材小用
        Single.just(1).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                //Log.e(Constant.LOG_NAME, integer + "");
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> emitter) {
                //发射一条单一的数据
                emitter.onSuccess(100);
                //发射一条异常通知
                //emitter.onError(new Exception("Exception"));
            }
        }).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void rxJavaOperators10() {
        //Completable只发射一条完成通知，或者一条异常通知，不能发射数据，其中完成通知与异常通知只能发射一个
        Completable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                //发射一条完成通知
                emitter.onComplete();
                //发射一条异常通知
                //emitter.onError(new Exception(""));
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    private void rxJavaOperators11() {
        //可发射一条单一的数据，以及发射一条完成通知，或者一条异常通知，其中完成通知和异常通知只能发射一个，发射数据只能在发射完成通知或者异常通知之前，否则发射数据无效
        //Maybe发射单一数据和完成通知或者发射单一数据和异常通知
        Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(MaybeEmitter<Integer> emitter) {
                emitter.onSuccess(100);
                emitter.onComplete();
                // emitter.onError(new Exception(""));
            }
        }).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void rxJavaOperators12() {
        //filter是过滤发射的事件
        Observable
                .just(0, 1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer % 2 == 0;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void rxJavaOperators13() {
        //skip操作符接收一个long类型参数，代表下游跳过多少个数目的事件再开始接收
        Observable
                .just(1, 100, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //Log.e(Constant.LOG_NAME, integer + "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void rxJavaOperators14() {
        //scan操作符是用上一个事件的结果和本次事件结果进行函数结合
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //第一次是1+0，第二次是2+1，第三次是3+3，第四次是4+6，第五次是5+10
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void rxJavaOperators15() {
        //concat操作符是接收若干个Observables，发射数据是有序的,即先发射aObservable的数据在发射bObservable的数据，即使不在同一个线程中
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};
        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);
        Observable.concat(aObservable, bObservable).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators16() {
        //doOnNext操作符让下游在接收到数据前干点事情
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("A");
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) {
                //Logger.d(s + "doOnNext");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void rxJavaOperators17() {
        //distinct操作符是过滤的操作符
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("a");
                emitter.onNext("a");
            }
        }).distinct().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                //Logger.d(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators18() {
        //buffer将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onNext("c");
                emitter.onNext("d");
                emitter.onNext("e");
                emitter.onNext("f");
                emitter.onNext("g");
            }
        }).buffer(2, 3).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
                for (String s : strings) {
                    // Logger.d(s);
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

    private void rxJavaOperators19() {
        //debounce操作符将过滤掉发射过快的事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(150);
                emitter.onNext(2);
                Thread.sleep(99);
                emitter.onNext(3);
                Thread.sleep(200);
            }
        }).debounce(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                // Logger.d(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxJavaOperators20() {
        //last操作符取出最后一个值，参数是没有值的时候的默认值
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(10);
                emitter.onNext(100);
                emitter.onComplete();
            }
        }).last(0).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                //Logger.d(integer);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void rxJavaOperators21() {
        //merge操作符将多个Observable合并起来
        Observable
                .merge(Observable.just(1, 2), Observable.just("a", "b", "c"))
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Serializable serializable) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxJavaOperators22(){
        //reduce就是一次用一个方法处理一个值，可以有一个seed作为初始值
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("a");
                //emitter.onNext("b");
                //emitter.onNext("c");
                emitter.onComplete();
            }
        }).reduce(new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                Logger.d(s+"-------"+s2);
                return "messi";
            }
        }).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Logger.d(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void hello7RxJava() {
        /**
         * Zip可以将多个上游发送的事件组合起来发送给下游, 如果其中一个水管A发送事件特别快, 而另一个水管B 发送事件特别慢, 那就可能出现这种情况, 发得快的水管A 已经发送了1000个事件了, 而发的慢的水管B 才发一个出来, 组合了一个之后水管A 还剩999个事件, 这些事件需要继续等待水管B 发送事件出来组合, 那么这么多的事件是放在哪里的呢
         * Zip给我们的每一根水管都弄了一个水缸 , 用来保存这些事件
         * 水缸将每根水管发出的事件保存起来, 等两个水缸都有事件了之后就分别从水缸中取出一个事件来组合, 当其中一个水缸是空的时候就处于等待的状态
         * 缸有什么特点呢? 它是按顺序保存的, 先进来的事件先取出来, 这就是我们熟知的队列, 这个水缸在Zip内部的实现就是用的队列
         * 所谓的Backpressure其实就是为了控制流量, 水缸存储的能力毕竟有限, 因此我们还得从源头去解决问题, 既然你发那么快, 数据量那么大, 那我就想办法不让你发那么快
         *
         * 当上下游工作在同一个线程中时, 这时候是一个同步的订阅关系, 也就是说上游每发送一个事件必须等到下游接收处理完了以后才能接着发送下一个事件
         * 当上下游工作在不同的线程中时, 这时候是一个异步的订阅关系, 这个时候上游发送数据不需要等待下游接收, 为什么呢, 因为两个线程并不能直接进行通信, 因此上游发送的事件并不能直接到下游里去, 这个时候就需要一个水缸帮助它们俩, 上游把事件发送到水缸里去, 下游从水缸里取出事件来处理, 因此, 当上游发事件的速度太快, 下游取事件的速度太慢, 水缸就会迅速装满, 然后溢出来, 最后就OOM了
         * 同步和异步的区别仅仅在于是否有水缸
         * Observable如果来不及消费会死命的缓存直到OOM，所以大数据流用Flowable，小数据流用Observable
         *
         * Flowable在设计的时候采用了一种新的思路也就是响应式拉取的方式来更好的解决上下游流速不均衡的问题
         * 这种方式用通俗易懂的话来说就好比是叶问打鬼子, 我们把上游看成小日本, 把下游当作叶问, 当调用Subscription.request(1)时, 叶问就说我要打一个! 然后小日本就拿出一个鬼子给叶问, 让他打, 等叶问打死这个鬼子之后, 再次调用request(10), 叶问就又说我要打十个! 然后小日本又派出十个鬼子给叶问, 然后就在边上看热闹, 看叶问能不能打死十个鬼子, 等叶问打死十个鬼子后再继续要鬼子接着打
         * 我们把request当做是一种能力, 当成下游处理事件的能力, 下游能处理几个就告诉上游我要几个, 这样只要上游根据下游的处理能力来决定发送多少事件, 就不会造成一窝蜂的发出一堆事件来, 从而导致OOM
         * 只有当上游正确的实现了如何根据下游的处理能力来发送事件的时候, 才能达到这种效果, 如果上游根本不管下游的处理能力, 一股脑的瞎他妈发事件, 仍然会产生上下游流速不均衡的问题, 这就好比小日本管他叶问要打几个, 老子直接拿出1万个鬼子, 这尼玛有种打死给我看看?
         *
         */
        Flowable<Integer> integerFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }

            //BackpressureStrategy.ERROR这个参数是用来选择背压,也就是出现上下游流速不均衡的时候应该怎么处理的办法, 这里我们直接用BackpressureStrategy.ERROR这种方式, 这种方式会在出现上下游流速不均衡的时候直接抛出一个异常,这个异常就是著名的MissingBackpressureException,默认是一个大小为128的水缸
            // BackpressureStrategy.BUFFER默认的水缸容量很大
            //BackpressureStrategy.DROP就是直接把存不下的事件丢弃
            //BackpressureStrategy.LATEST就是只保留最新的事件
        }, BackpressureStrategy.ERROR);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                //同步订阅的时候，下游没有调用request, 上游就认为下游没有处理事件的能力，下游就接收不到事件同时也会抛出MissingBackpressureException异常
                //这是因为下游没有调用request, 上游就认为下游没有处理事件的能力, 而这又是一个同步的订阅, 既然下游处理不了, 那上游不可能一直等待吧, 如果是这样, 万一这两根水管工作在主线程里, 界面不就卡死了吗, 因此只能抛个异常来提醒我们
                //异步订阅的时候，在Flowable里默认有一个大小为128的水缸, 当上下游工作在不同的线程中时, 上游就会先把事件发送到这个水缸中, 因此, 下游虽然没有调用request, 但是上游在水缸中保存着这些事件, 只有当下游调用request时, 才从水缸里取出事件发给下游
                //在上游发送了第129个事件的时候, 就抛出了MissingBackpressureException异常
                s.request(2);
            }

            @Override
            public void onNext(Integer o) {
                Logger.d(o);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        integerFlowable.subscribe(subscriber);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        //取消订阅后既不走onComplete又不走onError
        disposables.clear();
    }
}
