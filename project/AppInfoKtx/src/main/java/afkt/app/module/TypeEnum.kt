package afkt.app.module

import afkt.app.R
import androidx.annotation.StringRes

/**
 * detail: Navigation Type
 * @author Ttt
 */
enum class TypeEnum(tag: Int, @StringRes titleId: Int) {

    NONE(-1, R.string.str_empty),

    // 用户应用信息
    APP_USER(0, R.string.str_user_apps),

    // 系统应用信息
    APP_SYSTEM(1, R.string.str_system_apps),

    // 设备信息
    DEVICE_INFO(2, R.string.str_phone_info),

    // 屏幕信息
    SCREEN_INFO(3, R.string.str_screen_info),

    // 扫描 APK
    QUERY_APK(4, R.string.str_query_apk),

    // 设置
    SETTING(5, R.string.str_setting);

    val tag: Int = tag

    @StringRes
    val titleId: Int = titleId

    companion object {

        /**
         * 获取对应 Tag Type
         * @param tag Tag
         */
        fun get(tag: Int): TypeEnum {
            when (tag) {
                0 -> return TypeEnum.APP_USER
                1 -> return TypeEnum.APP_SYSTEM
                2 -> return TypeEnum.DEVICE_INFO
                3 -> return TypeEnum.SCREEN_INFO
                4 -> return TypeEnum.QUERY_APK
                5 -> return TypeEnum.SETTING
                else -> return TypeEnum.NONE
            }
        }
    }
}