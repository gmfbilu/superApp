package org.gmfbilu.neteasycloudcourse.预热课.rxjava02;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl implements Observable{

    //存放观察者集合
    private List<Observer> mObservers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void changeObservers() {
        //通知所有注册好的观察者
        for (Observer observer : mObservers) {
            observer.change("我发生改变了");
        }
    }
}
