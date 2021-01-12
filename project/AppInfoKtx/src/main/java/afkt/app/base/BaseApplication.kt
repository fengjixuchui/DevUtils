package afkt.app.base

import afkt.app.BuildConfig
import afkt.app.R
import afkt.app.base.model.AppConfig
import afkt.app.base.model.PathConfig
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.utils.app.ActivityUtils
import dev.utils.app.CrashUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import dev.utils.common.DateUtils
import dev.utils.common.FileRecordUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

class BaseApplication : MultiDexApplication(),
    ViewModelStoreOwner {

    // ViewModelStore
    private lateinit var mAppViewModelStore: ViewModelStore

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()

        mAppViewModelStore = ViewModelStore()

        if (BuildConfig.DEBUG) {
            // 初始化 Logger 配置
            DevLogger.init(
                LogConfig()
                    .logLevel(LogLevel.DEBUG)
                    .tag(AppConfig.LOG_TAG)
                    .sortLog(true)
                    .methodCount(0)
            )
            // 打开 lib 内部日志 - 线上环境, 不调用方法
            DevUtils.openLog()
            DevUtils.openDebug()
        }

        // 初始化文件夹
        PathConfig.createFolder()

        // 捕获异常处理
        CrashUtils.getInstance().init(this, object : CrashUtils.CrashCatchListener {
            override fun handleException(ex: Throwable?) {
                // 保存日志信息
                FileRecordUtils.saveErrorLog(
                    ex, PathConfig.AEP_ERROR_PATH,
                    "crash_" + DateUtils.getDateNow() + ".txt"
                )
            }

            override fun uncaughtException(
                context: Context?,
                thread: Thread?,
                ex: Throwable?
            ) {
                // 关闭 APP
                ActivityUtils.getManager().exitApplication()
            }
        })

        // 全局状态布局配置
        val global = StateLayout.Global(object : StateLayout.Listener {
            override fun onRemove(
                layout: StateLayout,
                type: Int,
                removeView: Boolean
            ) {
                if (removeView) layout.gone()
            }

            override fun onNotFound(
                layout: StateLayout,
                type: Int
            ) {
                layout.gone()
            }

            override fun onChange(
                layout: StateLayout,
                type: Int,
                oldType: Int,
                view: View
            ) {
                when (type) {
                    ViewAssist.TYPE_ING -> {
                    }
                    ViewAssist.TYPE_EMPTY_DATA -> {
                    }
                }
            }
        })
            .register(ViewAssist.TYPE_ING, R.layout.state_layout_ing)
            .register(ViewAssist.TYPE_EMPTY_DATA, R.layout.state_layout_no_data)
        // 设置全局配置
        StateLayout.setGlobal(global)
    }
}