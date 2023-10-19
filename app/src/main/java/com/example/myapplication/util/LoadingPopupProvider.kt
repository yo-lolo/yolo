package com.example.myapplication.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ActivityUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.LoadingPopupView
import java.lang.ref.WeakReference

/**
 * @author  张磊  on  2022/03/04 at 19:05
 * Email: 913305160@qq.com
 */
object LoadingPopupProvider {

    private var loadingPopupWeakReference: WeakReference<LoadingPopupView>? = null

    /**
     * 提供与当前显示的 Activity， 即栈顶  Activity 所关联的 LoadingPopupView
     */
    fun provide(): LoadingPopupView? {
        val topActivity = ActivityUtils.getTopActivity() as? FragmentActivity
        topActivity ?: return null

        if (loadingPopupWeakReference == null) {
            val popupView = LoadingPopupView(topActivity, 0).createPop {
                // 该 loading 弹窗需要复用, 为 true 时会产生异常
                this.isDestroyOnDismiss(false)
                this.dismissOnBackPressed(false)
                this.dismissOnTouchOutside(false)
                this.hasShadowBg(false)
            }

            loadingPopupWeakReference = WeakReference<LoadingPopupView>(popupView)
        }

        val activityLifecycle = topActivity.lifecycle
        activityLifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                // topActivity 销毁时清空引用，同时移除监听
                loadingPopupWeakReference?.clear()
                loadingPopupWeakReference = null
                activityLifecycle.removeObserver(this)
            }
        })

        return loadingPopupWeakReference?.get()
    }
}

fun getLoadingPopup() = LoadingPopupProvider.provide()

fun <T : BasePopupView> T.createPop(popOptions: XPopup.Builder.() -> Unit = {}): T {
    return XPopup.Builder(this.context)
        .isViewMode(true)
        .isDestroyOnDismiss(true)  //对于只使用一次的弹窗，推荐设置这个
        .apply(popOptions)
        .asCustom(this) as T
}