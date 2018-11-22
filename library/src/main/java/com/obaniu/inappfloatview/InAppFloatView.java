package com.obaniu.inappfloatview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by obaniu on 2018/12/19.
 */
public final class InAppFloatView {
    private final static InAppFloatViewImpl sInstance = new InAppFloatViewImpl();

    private InAppFloatView() {
    }

    public static void init(Context context, boolean isMainProcess) {
        init(context, isMainProcess, new SynchronizationImpl(context));
    }

    public static void init(Context context, boolean isMainProcess, ISynchronization iSynchronization) {
        sInstance.init(context, isMainProcess, iSynchronization);
    }

    public static void show(int layoutRId, String tag, LayoutParams params, boolean multiProcess) {
        sInstance.show(layoutRId, tag, params, multiProcess, true);
    }

    public static void show(String tag) {
        sInstance.setVisibility(tag, View.VISIBLE, true);
    }

    public static void hide(String tag) {
        sInstance.setVisibility(tag, View.GONE, true);
    }

    public static void update(String tag, LayoutParams newParams) {
        sInstance.update(tag, newParams, true);
    }

    public static void remove(String tag) {
        sInstance.remove(tag, true);
    }

    public static LayoutParams getLayoutParams(String tag) {
        return sInstance.getLayoutParams(tag);
    }

    public static View getView(String tag){
        return sInstance.getView(tag);
    }

    public static void setOnFloatViewAttached(OnFloatViewAttached onFloatViewAttached) {
        sInstance.onFloatViewAttached = onFloatViewAttached;
    }

    public static void setOnFloatViewCreated(OnFloatViewCreated onFloatViewCreated) {
        sInstance.onFloatViewCreated = onFloatViewCreated;
    }

    public static void setOnFloatViewDetached(OnFloatViewDetached onFloatViewDetached) {
        sInstance.onFloatViewDetached = onFloatViewDetached;
    }
    public static void setOnFloatViewChanged(OnFloatViewChanged onFloatViewChanged) {
        sInstance.onFloatViewChanged = onFloatViewChanged;
    }
    public static void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        sInstance.onActivityCreated(activity, savedInstanceState);
    }

    public static void onActivityStarted(Activity activity) {
        sInstance.onActivityStarted(activity);
    }

    public static void onActivityResumed(Activity activity) {
        sInstance.onActivityResumed(activity);
    }

    public static void onActivityPaused(Activity activity) {
        sInstance.onActivityPaused(activity);
    }

    public static void onActivityStopped(Activity activity) {
        sInstance.onActivityStopped(activity);
    }

    public static void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        sInstance.onActivitySaveInstanceState(activity, outState);
    }

    public static void onActivityDestroyed(Activity activity) {
        sInstance.onActivityDestroyed(activity);
    }
}
