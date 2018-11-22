package com.obaniu.inappfloatview.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle

import android.util.TypedValue
import android.view.View
import android.widget.TextView

/**
 * Created by obaniu on 2018/12/19.
 */
class ThreeActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="run on h5 process"
    }

    override fun onClick(v: View) {
        finish()
    }
}
