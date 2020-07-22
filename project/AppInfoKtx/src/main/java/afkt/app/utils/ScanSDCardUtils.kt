package afkt.app.utils

import afkt.app.module.event.ScanSDCardEvent
import afkt.app.module.item.FileApkItem
import dev.utils.app.PathUtils
import dev.utils.app.info.AppInfoUtils
import dev.utils.app.logger.DevLogger
import dev.utils.common.DevCommonUtils
import dev.utils.common.FileUtils
import dev.utils.common.assist.search.FileBreadthFirstSearchUtils
import dev.utils.common.thread.DevThreadManager
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * detail: 扫描 SDCard 工具类
 * @author Ttt
 */
class ScanSDCardUtils private constructor() {

    companion object {
        val instance: ScanSDCardUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ScanSDCardUtils()
        }
    }

    // 文件广度优先搜索算法 ( 多线程 + 队列, 搜索某个目录下的全部文件 )
    private var utils: FileBreadthFirstSearchUtils

    // 搜索后缀
    private var querySuffixArray: Array<String> = arrayOf("")

    // 搜索后的数据 ( 节省资源, 不遍历 FileBreadthFirstSearchUtils.FileItem )
    private var files: ArrayList<File> = ArrayList()

    // 转换后的数据
    private var data: ArrayList<FileApkItem>? = null

    init {
        utils = FileBreadthFirstSearchUtils(object : FileBreadthFirstSearchUtils.SearchHandler {
            override fun isHandlerFile(file: File?): Boolean {
                return true
            }

            override fun isAddToList(file: File): Boolean {
                if (querySuffixArray != null && file.exists()) {
                    if (DevCommonUtils.isEndsWith(true, file.name, *querySuffixArray)) {
                        files.add(file)
                        return true
                    }
                }
                return false
            }

            override fun OnEndListener(
                rootFileItem: FileBreadthFirstSearchUtils.FileItem?,
                startTime: Long, endTime: Long
            ) {
                var diff = endTime - startTime
                DevLogger.d("搜索耗时: " + diff + " ms")
                var lists = convertList()
                Collections.sort(lists, ApkListsComparator())
                data = lists
                EventBusUtils.post(ScanSDCardEvent(lists))
            }
        })
        utils.setQueueSameTimeNumber(DevThreadManager.getInstance(2).calcThreads)
            .setDelayTime(100L)
    }

    /**
     * 开始搜索
     */
    fun query() {
        query(false)
    }

    /**
     * 开始搜索
     * @param refresh 是否刷新
     */
    fun query(refresh: Boolean) {
        if (data != null && !refresh) {
            EventBusUtils.post(ScanSDCardEvent(data!!))
            return
        }
        if (utils.isRunning) return
        querySuffixArray = QuerySuffixUtils.querySuffixArray
        files = ArrayList()
        utils.query(PathUtils.getSDCard().sdCardPath)
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 转换处理
     */
    private fun convertList(): ArrayList<FileApkItem> {
        var lists = ArrayList<FileApkItem>()
        for (file in files) {
            val appInfoBean = AppInfoUtils.getAppInfoBeanToPath(file.path)
            appInfoBean?.let {
                lists.add(
                    FileApkItem(
                        file, file.name, file.path,
                        FileUtils.getFileMD5ToHexString(file),
                        appInfoBean
                    )
                )
            }
        }
        return lists
    }

    class ApkListsComparator : Comparator<FileApkItem> {
        override fun compare(a: FileApkItem, b: FileApkItem): Int {
            return if (a != null && b != null) {
                if (a.lastModified === b.lastModified) {
                    0 // 安装时间相等
                } else { // 近期安装的在最前面
                    if (a.lastModified > b.lastModified) -1 else 1
                }
            } else 0
        }
    }
}