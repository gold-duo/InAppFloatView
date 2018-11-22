package com.obaniu.inappfloatview.test

import android.view.Gravity
import android.view.View
import com.obaniu.inappfloatview.InAppFloatView
import java.util.*

object Common {
    val FLOAT_VIEW_FEED_BACK = "float_view_feed_back"

    private val RANDOM = Random()
    private val POSITION = intArrayOf(
        Gravity.LEFT or Gravity.CENTER_VERTICAL,
        Gravity.LEFT or Gravity.TOP,
        Gravity.LEFT or Gravity.BOTTOM,
        Gravity.RIGHT or Gravity.CENTER_VERTICAL,
        Gravity.RIGHT or Gravity.TOP,
        Gravity.RIGHT or Gravity.BOTTOM
    )

    val randPosition
        get() = POSITION[RANDOM.nextInt(POSITION.size)]


    fun setViewBgWithGravity(tag: String, view: View? = null, gravity: Int? = null) {
        val g = gravity ?: InAppFloatView.getLayoutParams(tag).gravity
        val bg = if ((g and Gravity.LEFT) == Gravity.LEFT) R.drawable.shape_feed_back_left else R.drawable.shape_feed_back_right
        val v = view ?: InAppFloatView.getView(tag)
        v.setBackgroundResource(bg)
    }
}
