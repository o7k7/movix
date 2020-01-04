package com.ok7.modanisa.poppytvshows.service;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class ConnectionController {

    private PublishSubject<String> subject;

    @NonNull
    private PublishSubject<String> getSubject() {
        if (subject == null) {
            subject = PublishSubject.create();
            subject.subscribeOn(AndroidSchedulers.mainThread());
        }
        return subject;
    }

    private Map<Object, CompositeDisposable> compositeDisposableMap = new HashMap<>();

    @NonNull
    private CompositeDisposable getCompositeSubscription(@NonNull Object object) {
        CompositeDisposable compositeSubscription = compositeDisposableMap.get(object);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeDisposable();
            compositeDisposableMap.put(object, compositeSubscription);
        }
        return compositeSubscription;
    }

    public void register(@NonNull Object lifecycle, @NonNull Consumer<String> action) {
        final Disposable disposable = getSubject().subscribe(action);
        getCompositeSubscription(lifecycle).add(disposable);
    }

    public void unregister(@NonNull Object lifecycle) {
        final CompositeDisposable compositeSubscription = compositeDisposableMap.remove(lifecycle);
        if (compositeSubscription != null) {
            compositeSubscription.dispose();
        }
    }

    public void publish() {
        new Handler(Looper.getMainLooper())
                .post(() -> getSubject().onNext("No Internet Connection"));
    }
}