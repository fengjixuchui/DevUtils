package afkt.app.base.model

import dev.utils.app.PathUtils
import dev.utils.common.FileUtils
import java.io.File

/**
 * detail: App 配置
 * @author Ttt
 */
object AppConfig {

    // ===========
    // = 项目信息 =
    // ===========

    // 项目名
    const val BASE_NAME = "AppInfoKtx"

    // 缩写标识 - 小写
    const val BASE_NAME_SHORT = "aiktx"

    // 缩写标识 - 大写
    const val BASE_NAME_SHORT_CAP = "AIKTX"

    // ===========
    // = 其他配置 =
    // ===========

    // 项目日志 TAG
    const val LOG_TAG = BASE_NAME + "_Log"
}

/**
 * detail: 文件路径配置
 * @author Ttt
 */
object PathConfig {

    // ===============
    // = 应用外部存储 =
    // ===============

    // 应用外部存储
    private val BASE_APP_PATH = PathUtils.getAppExternal().appDataPath

    // 统一文件夹
    val AEP_PATH = BASE_APP_PATH + File.separator + AppConfig.BASE_NAME + File.separator

    // APP 信息导出地址
    val AEP_APPMSG_PATH = AEP_PATH + "AppMsg" + File.separator

    // APK 信息导出地址
    val AEP_APKMSG_PATH = AEP_PATH + "ApkMsg" + File.separator

    // APK 文件导出地址
    val AEP_APK_PATH = AEP_PATH + "APK" + File.separator

    // 错误日志路径
    val AEP_ERROR_PATH = AEP_PATH + "Error" + File.separator

    /**
     * 初始化创建文件夹
     */
    fun createFolder() {
        FileUtils.createFolderByPaths(
            AEP_APPMSG_PATH,
            AEP_APKMSG_PATH,
            AEP_APK_PATH,
            AEP_ERROR_PATH
        )
    }
}