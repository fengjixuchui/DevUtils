package afkt.app.module.item

import androidx.annotation.StringRes

/**
 * detail: 设备信息 Item
 * @author Ttt
 */
class DeviceInfoItem(@StringRes resId: Int, value: String) {

    // 提示文案
    @StringRes
    val resId: Int = resId

    // 配置值
    val value: String = value
}