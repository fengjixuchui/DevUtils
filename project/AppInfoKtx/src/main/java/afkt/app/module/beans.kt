package afkt.app.module

import afkt.app.utils.ProjectUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.info.KeyValueBean
import java.util.*

/**
 * detail: 设备参数导出实体类
 * @author Ttt
 */
class DeviceInfoBean(
    key: String?,
    value: String?
) : KeyValueBean(key, value) {

    companion object {

        operator fun get(deviceInfoItem: DeviceInfoItem): DeviceInfoBean {
            val key = ResourceUtils.getString(deviceInfoItem.resId)
            val value = deviceInfoItem.value
            return DeviceInfoBean(key, value)
        }

        fun copyString(deviceInfoItem: DeviceInfoItem): String {
            return get(deviceInfoItem).toString()
        }

        fun jsonString(deviceList: MutableList<DeviceInfoItem>): String? {
            val lists = ArrayList<DeviceInfoBean>()
            for (item in deviceList) lists.add(get(item))
            return ProjectUtils.toJson(lists)
        }
    }
}

/**
 * detail: 设备、屏幕信息
 * @author Ttt
 */
class DeviceInfo(
    val type: TypeEnum,
    val lists: ArrayList<DeviceInfoItem>
)