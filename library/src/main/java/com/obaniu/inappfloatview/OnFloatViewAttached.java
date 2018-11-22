package com.obaniu.inappfloatview;

import android.app.Activity;
import android.view.View;
/**
 * Created by obaniu on 2018/12/19.
 */
public interface OnFloatViewAttached {
    void onAttached(Activity activity, View view, String tag);
}
