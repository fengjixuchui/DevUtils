package afkt.accessibility.base.model

/**
 * detail: Activity 改变通知事件
 */
class ActivityChangedEvent(
    packageName: String,
    className: String
) {

    val packageName: String = packageName
    val className: String = className
}