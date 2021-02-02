package afkt.push.jpush

import afkt.push.router.PushRouterActivity
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import dev.utils.app.logger.DevLogger

/**
 * detail: 极光推送广播
 * @author Ttt
 */
class PushReceiver : JPushMessageReceiver() {

    // 日志 TAG
    private val TAG = PushReceiver::class.java.simpleName

    override fun onMessage(
        context: Context?,
        customMessage: CustomMessage?
    ) {
        DevLogger.dTag(TAG, "[onMessage] ${customMessage}")
//        // 自定义消息 ( 透传 )
//        customMessage?.let {
//            var message = it.message
//        }
    }

    override fun onNotifyMessageOpened(
        context: Context?,
        message: NotificationMessage?
    ) {
        DevLogger.dTag(TAG, "[onNotifyMessageOpened] ${message}")
        // 点击通知消息
        message?.let {
            var pushMessage = toPushMessage(message)
            // 跳转进行推送消息解析分发
            PushRouterActivity.start(context, pushMessage)
        }
    }

    override fun onNotifyMessageArrived(
        context: Context?,
        message: NotificationMessage?
    ) {
        DevLogger.dTag(TAG, "[onNotifyMessageArrived] ${message}")
        // 收到通知消息
    }

    override fun onNotifyMessageDismiss(
        context: Context?,
        message: NotificationMessage?
    ) {
        DevLogger.dTag(TAG, "[onNotifyMessageDismiss] ${message}")
    }

    override fun onMultiActionClicked(
        context: Context?,
        intent: Intent?
    ) {
        DevLogger.dTag(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮")
    }

    override fun onRegister(
        context: Context?,
        registrationId: String?
    ) {
        DevLogger.dTag(TAG, "[onRegister] ${registrationId}")
    }

    override fun onConnected(
        context: Context?,
        isConnected: Boolean
    ) {
        DevLogger.dTag(TAG, "[onConnected] ${isConnected}")
    }

    override fun onCommandResult(
        context: Context?,
        cmdMessage: CmdMessage?
    ) {
        DevLogger.dTag(TAG, "[onCommandResult] ${cmdMessage}")
    }

    override fun onNotificationSettingsCheck(
        context: Context?,
        isOn: Boolean,
        source: Int
    ) {
        DevLogger.dTag(TAG, "[onNotificationSettingsCheck] isOn : ${isOn}, source: ${source}")
    }
}