package com.obaniu.inappfloatview.test

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.util.Log
import com.obaniu.inappfloatview.InAppFloatView
import com.obaniu.inappfloatview.LayoutParams

/**
 * Created by obaniu on 2018/12/19.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isMainProcess = getCurrentProcessName().equals(packageName);
        InAppFloatView.init(this, isMainProcess)
        InAppFloatView.setOnFloatViewCreated { view, tag ->
            Log.i("InAppFloatView", "onCreated:$tag")
        }
        InAppFloatView.setOnFloatViewAttached { _, view, tag ->
            Common.setViewBgWithGravity(tag,view)
            Log.i("InAppFloatView", "onAttached:$tag")
        }
        InAppFloatView.setOnFloatViewDetached { _, tag -> Log.i("InAppFloatView", "onDetached:$tag") }
        InAppFloatView.setOnFloatViewChanged{view,tag->
            Common.setViewBgWithGravity(tag,view)
            Log.i("InAppFloatView", "onChanged:$tag")
        }

        if(isMainProcess) {
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Common.randPosition)
            InAppFloatView.show(R.layout.float_view, Common.FLOAT_VIEW_FEED_BACK, params, true)
        }
    }

    private fun getCurrentProcessName(): String? {
        val pid = android.os.Process.myPid()
        val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) return appProcess.processName
        }
        return null
    }
}
