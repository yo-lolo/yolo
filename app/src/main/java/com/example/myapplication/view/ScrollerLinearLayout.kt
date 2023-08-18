package com.example.myapplication.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import com.example.myapplication.R

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : ScrollerLinearLayout
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/17 17:05
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/17 17:05
 * @UpdateRemark : 更新说明
 */
class ScrollerLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private val mScroller = Scroller(context)
    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mVelocityTracker: VelocityTracker? = null

    // 记录初始手指按下的坐标
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    private var expandWidth = 0

    // 屏幕宽度
    private val displayWidth = context.applicationContext.resources.displayMetrics.widthPixels

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("yolo_charge", "onTouchEvent $event")
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("yolo_charge", "onInterceptTouchEvent $ev")
        mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker!!.addMovement(ev)
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                test(ev)
            }
            MotionEvent.ACTION_UP -> {
                // 判断滑动距离是否超过布局的1/2
                charge(ev)
            }
            MotionEvent.ACTION_CANCEL -> {
                charge(ev)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    private fun charge(ev: MotionEvent) {
        val eventX = ev.x - lastX

        Log.e("yolo_charge", "该事件滑动的水平距离 $eventX")
        if (eventX < -(expandWidth / 4)) {
            smoothScrollTo(expandWidth, 0)
        } else {
            smoothScrollTo(0, 0)
        }


//        // 回收内存
//        mVelocityTracker?.apply {
//            clear()
//            recycle()
//        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("yolo", "dispatchTouchEvent $ev")
        return super.dispatchTouchEvent(ev)
    }

    private fun test(event: MotionEvent) {
        //获得手指在水平方向上的坐标变化
        // 需要滑动的像素
        val mX = lastX - event.x
        if (mX > 0 && mX < expandWidth) {
            scrollTo(mX.toInt(), 0)
        }
        // 获取当前水平方向的滑动速度
        mVelocityTracker!!.computeCurrentVelocity(500)
        val xVelocity = mVelocityTracker!!.xVelocity.toInt()

    }

    // 缓慢滚动到指定位置
    private fun smoothScrollTo(destX: Int, destY: Int) {
        val delta = destX - scrollX
        // 在多少ms内滑向destX
        mScroller.startScroll(scrollX, 0, delta, 0, 600)
        invalidate()
    }

    // 流畅地滑动
    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY);
            postInvalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        expandWidth = childViewWidth()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 最多只允许有两个子布局
     */
    private fun childViewWidth(): Int {
        Log.e(TAG, "childCount ${this.childCount}")
        return if (this.childCount > 1) {
            val expandChild = this.getChildAt(1) as LinearLayout
            expandWidth = expandChild.measuredWidth
            Log.e(TAG, "expandWidth $expandWidth")
            expandChild.width
        } else
            0
    }

    companion object {
        const val TAG = "ScrollerLinearLayout_YOLO"
    }
}