package org.gmfbilu.neteasycloudcourse.预热课.rxjava02;

/**
 * 被观察者，上游，事件发送方
 */
public interface Observable {

    //注册观察者
    void registerObserver(Observer observer);

    //移除观察者
    void removeObserver(Observer observer);

    //产生事件
    void changeObservers();
}
