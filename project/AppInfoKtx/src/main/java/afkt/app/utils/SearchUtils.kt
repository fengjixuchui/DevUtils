package afkt.app.utils

import afkt.app.module.event.StartSearchEvent
import android.os.Handler
import android.os.Message

/**
 * detail: 搜索工具类
 * @author Ttt
 */
object SearchUtils {

    private val SEARCH_WHAT = Integer.MAX_VALUE

    // 搜索线程
    private var sSearchRunn: Runnable? = null

    /**
     * 移除搜索任务
     */
    fun removeSearchTask() {
        sHandler.removeCallbacks(getSearchRunnable())
    }

    /**
     * 开始搜索任务
     */
    fun startSearchTask() {
        sHandler.removeCallbacks(getSearchRunnable())
        sHandler.postDelayed(getSearchRunnable(), 350)
    }

    // =

    private fun getSearchRunnable(): Runnable? {
        if (sSearchRunn == null) {
            sSearchRunn = Runnable {
                sHandler.sendEmptyMessage(SEARCH_WHAT)
            }
        }
        return sSearchRunn
    }

    private val sHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SEARCH_WHAT -> EventBusUtils.post(StartSearchEvent())
            }
        }
    }
}