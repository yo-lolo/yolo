package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import com.example.myapplication.getTag


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
    var expandWidth = 720   // View待展开的布局宽度 需要手动设置 3*dp
    private var expandState = false   // View的展开状态
    private val displayWidth =
        context.applicationContext.resources.displayMetrics.widthPixels  // 屏幕宽度
    private var state = true


    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e(TAG, "onTouchEvent $event")
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
        return state
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(TAG, "onInterceptTouchEvent Result : ${onInterceptTouchEvent(ev)}")
        Log.e(TAG, "dispatchTouchEvent : $ev")
        mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker!!.addMovement(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e(TAG, "onInterceptTouchEvent $ev")
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.rawX
                lastY = ev.rawY
                // 处于展开状态且点击的位置不在扩展布局中 拦截点击事件
                intercept = expandState && ev.x < (displayWidth - expandWidth)
            }
            MotionEvent.ACTION_MOVE -> {
                // 当滑动的距离超过10 拦截点击事件
                intercept = lastX - ev.x > 10
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

    /**
     * 将布局修正到正确的位置
     */
    private fun chargeToRightPlace(ev: MotionEvent) {
        val eventX = ev.x - lastX

        Log.e(TAG, "该事件滑动的水平距离 $eventX")
        if (eventX < -(expandWidth / 4)) {
            smoothScrollTo(expandWidth, 0)
            expandState = true
            invalidate()
        } else {
            expandState = false
            smoothScrollTo(0, 0)
            invalidate()
        }

        // 回收内存
        mVelocityTracker?.apply {
            clear()
            recycle()
        }
        //清除状态
        lastX = 0f
        invalidate()
    }

    /**
     * 跟随手指移动
     */
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
        invalidate()

    }

    /**
     * 缓慢滚动到指定位置
     */
    private fun smoothScrollTo(destX: Int, destY: Int) {
        val delta = destX - scrollX
        // 在多少ms内滑向destX
        mScroller.startScroll(scrollX, 0, delta, 0, 600)
        invalidate()
        translationY = 0f
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
        invalidate()
        super.onLayout(changed, l, t, r, b)
    }

    /**
     * 最多只允许有两个子布局
     */
    private fun childViewWidth(): Int {
        Log.e(TAG, "childCount ${this.childCount}")
        return if (this.childCount > 1) {
            val expandChild = this.getChildAt(1) as LinearLayout
            if (expandChild.measuredWidth != 0){
                expandWidth = expandChild.measuredWidth
            }
            Log.e(TAG, "expandWidth $expandWidth")
            expandWidth
        } else
            0
    }

    companion object {
        const val TAG = "ScrollerLinearLayout_YOLO"
    }
}