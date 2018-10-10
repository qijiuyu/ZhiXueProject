package com.example.administrator.zhixueproject.bean.eventBus;

/**
 *
 * @author petergee
 * @date 2018/10/9
 */
public class BaseEvent<T> {

    T data;

    int eventType;

    public BaseEvent(T data, int eventType) {
        this.data = data;
        this.eventType = eventType;
    }

    public BaseEvent(int eventType) {
        this.eventType = eventType;
    }

    public BaseEvent() {
    }

    public T getData() {
        return data;
    }

    public BaseEvent setData(T data) {
        this.data = data;
        return this;
    }

    public int getEventType() {
        return eventType;
    }

    public BaseEvent setEventType(int eventType) {
        this.eventType = eventType;
        return this;
    }
}
