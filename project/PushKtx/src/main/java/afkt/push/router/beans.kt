package afkt.push.jpush

import android.os.Parcelable
import cn.jpush.android.api.NotificationMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.utils.app.logger.DevLogger
import kotlinx.android.parcel.Parcelize

/**
 * detail: 推送消息包装类
 * @author Ttt
 */
@Parcelize
data class PushMessage(
    val msgId: String = "",
    val notificationId: Int = 0,
    val notificationTitle: String = "",
    val notificationContent: String = "",
    val notificationExtras: String = "",
    val pushExtras: PushExtras? = null
) : Parcelable

/**
 * detail: 附加字段 JSON 映射类
 * @author Ttt
 * {"routerUri": "/login/account"}
 */
@Parcelize
data class PushExtras(
    // 路由 Uri
    var routerUri: String = ""
    // 参数... ( 忽略 )
) : Parcelable

// ===========
// = 快捷方法 =
// ===========

/**
 * 进行推送数据包装
 * @param message [NotificationMessage]
 * @return [PushMessage]
 */
fun toPushMessage(message: NotificationMessage): PushMessage {
    return PushMessage(
        msgId = message.msgId,
        notificationId = message.notificationId,
        notificationTitle = message.notificationTitle,
        notificationContent = message.notificationContent,
        notificationExtras = message.notificationExtras,
        pushExtras = fromJson(
            message.notificationExtras, PushExtras::class.java
        )
    )
}

fun <T> fromJson(
    json: String?,
    classOfT: Class<T>?
): T? {
    try {
        return Gson().fromJson(json, classOfT)
    } catch (e: Exception) {
        DevLogger.e(e)
    }
    return null
}

fun toJson(obj: Any?): String? {
    try {
        return Gson().toJson(obj)
    } catch (e: Exception) {
        DevLogger.e(e)
    }
    return null
}

fun toJsonFormat(obj: Any?): String? {
    try {
        return GsonBuilder().setPrettyPrinting().create().toJson(obj)
    } catch (e: Exception) {
        DevLogger.e(e)
    }
    return null
}