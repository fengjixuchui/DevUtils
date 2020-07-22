package afkt.app.base

/**
 * detail: 常量
 * @author Ttt
 */
class Constants {

    object Key {

        // 包名
        const val KEY_PACKNAME = "packName"

        // APK 地址
        const val KEY_APK_URI = "apkUri"

        // 应用排序
        const val KEY_APP_SORT = "appSort"

        // 搜索后缀
        const val KEY_QUERY_SUFFIX = "querySuffix"

        // 传值
        const val KEY_VALUE = "value"
    }

    object Notify {

        private const val BASE = 1000

        // 查询 APP 列表结束通知
        const val H_QUERY_APPLIST_END_NOTIFY = BASE + 1
    }

    object RequestCode {

        private const val BASE = 3000

        // 跳转 App 详情页面 回传
        const val FOR_R_APP_DETAILS = BASE + 1

        // 卸载 App 回传
        const val FOR_R_APP_UNINSTALL = BASE + 2
    }
}