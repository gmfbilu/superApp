package org.gmfbilu.neteasycloudcourse.预热课.rxjava02;


/**
 * 观察者，下游，事件接收方，主动订阅者
 */
public interface Observer {

    //如果我们的被观察者发出了改变事件，我们就能够收到
    void change(Object object);
}
