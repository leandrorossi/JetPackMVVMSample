package com.example.jetpack_mvvm_sample.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpack_mvvm_sample.R

open class BaseActivity : AppCompatActivity() {

    private var pressStartTime: Long = 0
    private var pressedX = 0f
    private var pressedY = 0f
    private var stayedWithinClickDistance = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                pressStartTime = System.currentTimeMillis()
                pressedX = e.x
                pressedY = e.y
                stayedWithinClickDistance = true
            }

            MotionEvent.ACTION_MOVE -> {
                if (stayedWithinClickDistance && distance(
                        pressedX,
                        pressedY,
                        e.x,
                        e.y
                    ) > MAX_CLICK_DISTANCE
                ) {
                    stayedWithinClickDistance = false
                }
            }

            MotionEvent.ACTION_UP -> {
                val pressDuration = System.currentTimeMillis() - pressStartTime
                if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                    val v2 = currentFocus
                    if (v2 is EditText) {
                        val outRect = Rect()
                        v2.getGlobalVisibleRect(outRect)
                        if (!outRect.contains(e.rawX.toInt(), e.rawY.toInt())) {
                            v2.clearFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v2.getWindowToken(), 0)
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(e)
    }

    private fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        val distanceInPx = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        return pxToDp(distanceInPx)
    }

    private fun pxToDp(px: Float): Float {
        return px / resources.displayMetrics.density
    }

    companion object {
        private const val MAX_CLICK_DURATION = 1000

        /**
         * Max allowed distance to move during a "click", in DP.
         */
        private const val MAX_CLICK_DISTANCE = 15
    }

}