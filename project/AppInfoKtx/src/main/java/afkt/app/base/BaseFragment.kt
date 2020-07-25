package afkt.app.base

import afkt.app.utils.EventBusUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.utils.LogPrintUtils

abstract class BaseFragment : Fragment() {

    protected var mRootView: View? = null

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtils.unregister(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView != null) {
            val parent = mRootView?.parent as ViewGroup
            parent?.removeView(mRootView)
        }
        try {
            mRootView = inflater.inflate(layoutId(), container, false)
        } catch (e: Exception) {
            LogPrintUtils.e(e)
        }
        readArguments()
        if (isRegister()) EventBusUtils.register(this)
        return mRootView
    }

    // 获取 Layout
    abstract fun layoutId(): Int

    // 是否注册 EventBus
    open fun isRegister(): Boolean {
        return true
    }

    // 读取传参数据
    open fun readArguments() {
    }
}