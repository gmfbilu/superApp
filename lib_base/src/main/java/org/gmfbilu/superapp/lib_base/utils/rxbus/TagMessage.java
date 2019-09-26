package org.gmfbilu.superapp.lib_base.utils.rxbus;


final class TagMessage {

    Object mEvent;
    String mTag;

    TagMessage(Object event, String tag) {
        mEvent = event;
        mTag = tag;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return RxBusUtils.equals(getEventType(), eventType)
                && RxBusUtils.equals(this.mTag, tag);
    }

    Class getEventType() {
        return RxBusUtils.getClassFromObject(mEvent);
    }

    @Override
    public String toString() {
        return "event: " + mEvent + ", tag: " + mTag;
    }
}
