package com.obaniu.inappfloatview.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.obaniu.inappfloatview.InAppFloatView
import com.obaniu.inappfloatview.LayoutParams

/**
 * Created by obaniu on 2018/12/19.
 */
open class MainActivity : Activity(), View.OnClickListener {
    private val listener = View.OnClickListener { v ->
        when (v.id) {
            R.id.hide -> InAppFloatView.hide(Common.FLOAT_VIEW_FEED_BACK)
            R.id.show -> {
                InAppFloatView.getView(Common.FLOAT_VIEW_FEED_BACK)?.let {
                    InAppFloatView.show(Common.FLOAT_VIEW_FEED_BACK)
                } ?: run {
                    val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Common.randPosition)
                    InAppFloatView.show(R.layout.float_view, Common.FLOAT_VIEW_FEED_BACK, params, true)
                }
            }
            R.id.remove -> InAppFloatView.remove(Common.FLOAT_VIEW_FEED_BACK)
            R.id.update -> {
                val p = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Common.randPosition)
                Common.setViewBgWithGravity(tag=Common.FLOAT_VIEW_FEED_BACK,gravity = p.gravity)
                InAppFloatView.update(Common.FLOAT_VIEW_FEED_BACK, p)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = javaClass.simpleName
        setContentView(R.layout.main)

        findViewById<View>(R.id.hide).setOnClickListener(listener)
        findViewById<View>(R.id.show).setOnClickListener(listener)
        findViewById<View>(R.id.remove).setOnClickListener(listener)
        findViewById<View>(R.id.update).setOnClickListener(listener)
        findViewById<View>(R.id.next_activity).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}
