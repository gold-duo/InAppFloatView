package com.obaniu.inappfloatview.test

import android.content.Intent
import android.view.View

/**
 * Created by obaniu on 2018/12/19.
 */
class SecondActivity : MainActivity() {

    override fun onClick(v: View) {
        startActivity( Intent(this, ThreeActivity::class.java))
    }
}
