package afkt.app.utils

import afkt.app.base.Constants
import com.google.gson.Gson
import dev.utils.app.share.SharedUtils
import java.util.*

/**
 * detail: 搜索后缀 工具类
 * @author Ttt
 */
object QuerySuffixUtils {

    // 日志 TAG
    private val TAG = QuerySuffixUtils::class.java.simpleName

    /**
     * 默认配置
     */
    private val dfConfig: String?
        private get() {
            val maps = LinkedHashMap<String, String>()
            maps["apk"] = "apk"
            maps["apk.1"] = "apk.1"
            maps["apk.1.1"] = "apk.1.1"
            maps["apk.1.1.1"] = "apk.1.1.1"
            return ProjectUtils.toJson(maps)
        }

    /**
     * 重置处理
     */
    fun reset() {
        SharedUtils.remove(Constants.Key.KEY_QUERY_SUFFIX)
    }

    /**
     * 刷新配置
     */
    fun refresh(maps: LinkedHashMap<String, String>?) {
        SharedUtils.put(Constants.Key.KEY_QUERY_SUFFIX, Gson().toJson(maps))
    }

    /**
     * 获取搜索后缀
     */
    val querySuffixStr: String?
        get() = SharedUtils.getString(Constants.Key.KEY_QUERY_SUFFIX) ?: dfConfig

    /**
     * 获取搜索后缀
     */
    val querySuffixMap: LinkedHashMap<String, String>
        get() {
            val maps = LinkedHashMap<String, String>()
            maps.putAll(Gson().fromJson(querySuffixStr, maps.javaClass))
            return maps
        }

    /**
     * 获取搜索后缀
     */
    val querySuffixArray: Array<String>
        get() {
            return querySuffixMap.keys.toTypedArray()
        }
}