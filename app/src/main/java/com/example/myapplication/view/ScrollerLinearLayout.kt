package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller


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

    private val mScroller = Scroller(context)  // 用于实现View的弹性滑动
    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mVelocityTracker: VelocityTracker? = null   // 速度追踪
    private var intercept = false   // 拦截状态 初始值为不拦截
    private var lastX: Float = 0f
    private var lastY: Float = 0f  // 用来记录手指按下的初始坐标
    private var expandWidth = 0   // View的展开的宽度
    private var expandState = false   // View的展开状态
    private val displayWidth =
        context.applicationContext.resources.displayMetrics.widthPixels  // 屏幕宽度

    private var state = true

    /**
     * question 1: 在第一次初始化时 在事件分发中获取不到expandWidth 第二次初始化正常
     * question 2: 滑动后点击事件对应不上
     */

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("yolo_charge", "onTouchEvent $event")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!expandState) {
                    state = false
                }
            }
            else -> {
                state = true
            }
        }
        invalidate()
        return state
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("yolo_hhh", "onInterceptTouchEvent Result : ${onInterceptTouchEvent(ev)}")
        Log.e("yolo_hhh", "dispatchTouchEvent : $ev")
        mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker!!.addMovement(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("yolo_charge", "onInterceptTouchEvent $ev")
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
                // 处于展开状态且点击的位置不在扩展布局中 拦截点击事件
                intercept = expandState && ev.x < (displayWidth - expandWidth)
            }
            MotionEvent.ACTION_MOVE -> {
                // 当滑动的距离超过6 拦截点击事件
                intercept = lastX - ev.x > 6
                moveWithFinger(ev)
            }
            MotionEvent.ACTION_UP -> {
                // 判断滑动距离是否超过布局的1/2
                chargeToRightPlace(ev)
                intercept = false
            }
            MotionEvent.ACTION_CANCEL -> {
                chargeToRightPlace(ev)
                intercept = false
            }
            else -> intercept = false
        }
        return intercept
    }


    private fun chargeToRightPlace(ev: MotionEvent) {
        val eventX = ev.x - lastX

        Log.e("yolo_charge", "该事件滑动的水平距离 $eventX")
        if (eventX < -(expandWidth / 4)) {
            smoothScrollTo(expandWidth, 0)
            expandState = true
        } else {
            expandState = false
            smoothScrollTo(0, 0)
        }

        // 回收内存
        mVelocityTracker?.apply {
            clear()
            recycle()
        }
    }

    private fun moveWithFinger(event: MotionEvent) {
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

    /**
     * 缓慢滚动到指定位置
     */
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        expandWidth = childViewWidth()
        super.onLayout(changed, l, t, r, b)
        onInterceptTouchEvent(null)
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