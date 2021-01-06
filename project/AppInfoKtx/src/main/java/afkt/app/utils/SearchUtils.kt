package afkt.app.utils

import afkt.app.module.StartSearchEvent
import dev.utils.app.assist.DelayAssist

/**
 * detail: 搜索工具类
 * @author Ttt
 */
object SearchUtils {

    // 延迟触发辅助类
    private val assist = DelayAssist(350) {
        EventBusUtils.post(StartSearchEvent())
    }

    /**
     * 移除搜索任务
     */
    fun removeSearchTask() {
        assist.remove()
    }

    /**
     * 开始搜索任务
     */
    fun startSearchTask() {
        assist.post()
    }
}