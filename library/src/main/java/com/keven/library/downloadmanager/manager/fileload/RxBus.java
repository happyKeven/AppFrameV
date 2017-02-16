package com.keven.library.downloadmanager.manager.fileload;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private static volatile RxBus mInstance;
    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
    * 单例RxBus
    * @author keven
    * created at 2016/12/5 15:40
    */
    public static RxBus getDefault() {
        RxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
    * 发送一个新事件
    * @author keven
    * created at 2016/12/5 15:40
    */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
    * 返回特定类型的被观察者
    * @author keven
    * created at 2016/12/5 15:40
    */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
