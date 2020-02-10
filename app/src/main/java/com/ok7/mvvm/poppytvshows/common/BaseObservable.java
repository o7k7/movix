package com.ok7.mvvm.poppytvshows.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseObservable<LISTENER_CLASS> {

    private Set<LISTENER_CLASS> listeners = Collections
            .newSetFromMap(new ConcurrentHashMap<>(1));

    public void registerListener(LISTENER_CLASS listener) {
        listeners.add(listener);
    }

    public void unregisterListener(LISTENER_CLASS listener) {
        listeners.remove(listener);
    }

    protected Set<LISTENER_CLASS> getListeners() {
        return Collections.unmodifiableSet(listeners);
    }

}
