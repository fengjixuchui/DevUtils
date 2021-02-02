package afkt.push.base

import afkt.push.BuildConfig
import afkt.push.jpush.PushMessage
import afkt.push.jpush.fromJson
import afkt.push.router.IPushCallback
import afkt.push.router.PushRouterChecker
import afkt.push.ui.activity.MessageActivity
import afkt.push.ui.activity.SplashActivity
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import androidx.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel

class BaseApplication : MultiDexApplication() {

    private val TAG = "PushKtx_TAG"

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // 初始化 Logger 配置
            DevLogger.init(
                LogConfig()
                    .logLevel(LogLevel.DEBUG)
                    .tag(TAG)
                    .sortLog(true)
                    .methodCount(0)
            )
            // 打开 lib 内部日志 - 线上环境, 不调用方法
            DevUtils.openLog()
            DevUtils.openDebug()
        }
        // 推送初始化
        JPushInterface.setDebugMode(BuildConfig.DEBUG)
        JPushInterface.init(this)

        // ===============
        // = 推送路由处理 =
        // ===============

        // 设置启动页、推送点击回调处理
        PushRouterChecker.setCallback(SplashActivity::class.java, object : IPushCallback {
            override fun isTriggerCallback(activityClass: String?): Boolean {
                if (TextUtils.isEmpty(activityClass)) return false

                activityClass?.let { className ->
                    if (className == SplashActivity::class.java.simpleName) {
                        return false // 属于欢迎页面则不进行处理
                    }
                }
                return true
            }

            override fun onCallback(
                activity: Activity?,
                pushData: String?
            ) {
                fromJson(pushData, PushMessage::class.java)?.let {
                    DevLogger.dTag(TAG, "[onCallback] ${it.pushExtras?.routerUri}")

                    // 自行判断 it.pushExtras?.routerUri 进行路由分发

                    var intent = Intent(activity, MessageActivity::class.java)
                    intent.putExtra(DevFinal.DATA, it)
                    AppUtils.startActivity(intent)
                }
            }
        })
    }
}