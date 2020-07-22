package afkt.app.module.event

import afkt.app.module.TypeEnum
import afkt.app.module.item.DeviceInfoItem

/**
 * detail: 信息 ( 设备、屏幕 ) 通知事件
 * @author Ttt
 */
class InfoEvent(type: TypeEnum, lists: ArrayList<DeviceInfoItem>) {

    val type: TypeEnum = type
    val lists: ArrayList<DeviceInfoItem> = lists
}