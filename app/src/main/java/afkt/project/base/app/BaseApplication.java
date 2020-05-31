package afkt.project.base.app;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;

import androidx.multidex.MultiDexApplication;

import afkt.project.R;
import afkt.project.base.config.AppConfig;
import afkt.project.base.config.PathConfig;
import afkt.project.util.ProjectUtils;
import dev.DevUtils;
import dev.assist.WebViewAssist;
import dev.other.GlideUtils;
import dev.utils.app.ActivityUtils;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.CrashUtils;
import dev.utils.app.PathUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.logger.LogConfig;
import dev.utils.app.logger.LogLevel;
import dev.utils.common.DateUtils;
import dev.utils.common.FileRecordUtils;
import dev.utils.common.assist.TimeCounter;
import dev.widget.StateLayout;

/**
 * detail: Base Application
 * @author Ttt
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化计时器
        TimeCounter timeCounter = new TimeCounter();

        // ============
        // = DevUtils =
        // ============

//        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
//        DevUtils.init(this.getApplicationContext());
        // = 初始化日志配置 =
        // 设置默认 Logger 配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = AppConfig.LOG_TAG;
        logConfig.sortLog = true; // 美化日志, 边框包围
        logConfig.methodCount = 0;
        DevLogger.init(logConfig);
        // 打开 lib 内部日志 - 线上环境, 不调用方法就行
        DevUtils.openLog();
        DevUtils.openDebug();

        // ==============
        // = 初始化操作 =
        // ==============

        // 初始化
        init();

        // 属于 Debug 才打印信息
        if (isDebug()) {
            printProInfo(timeCounter);
        }
    }

    /**
     * 是否 Debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static final boolean isDebug() {
        return DevUtils.isDebug();
    }

    /**
     * 打印项目信息
     * @param timeCounter {@link TimeCounter}
     */
    private void printProInfo(TimeCounter timeCounter) {
        StringBuilder bulder = new StringBuilder();
        bulder.append("项目名: " + ResourceUtils.getString(R.string.str_app_name) + " (" + ResourceUtils.getString(R.string.str_app_name_en) + ")");
        bulder.append("\nSDK: " + Build.VERSION.SDK_INT + "(" + AppCommonUtils.convertSDKVersion(Build.VERSION.SDK_INT) + ")");
        bulder.append("\nPackageName: " + AppUtils.getPackageName());
        bulder.append("\nVersionCode: " + AppUtils.getAppVersionCode());
        bulder.append("\nVersionName: " + AppUtils.getAppVersionName());
        bulder.append("\nDevUtils 版本: " + DevUtils.getDevAppUtilsVersion());
        bulder.append("\nDevJava 版本: " + DevUtils.getDevJavaUtilsVersion());
        bulder.append("\n时间: " + DateUtils.getDateNow());
        bulder.append("\n初始化耗时(毫秒): " + timeCounter.duration());
        DevLogger.i(bulder.toString());
    }

    // ==============
    // = 初始化方法 =
    // ==============

    /**
     * 统一初始化方法
     */
    private void init() {
        // 初始化项目文件夹
        ProjectUtils.createFolder();
        // 插入设备信息
        FileRecordUtils.setInsertInfo(AppCommonUtils.getAppDeviceInfo());
        // 初始化 Glide
        GlideUtils.init(this);
        // 初始化状态布局配置
        initStateLayout();
        // 初始化异常捕获处理
        initCrash();
        // 初始化 WebView 辅助类全局配置
        initWebViewBuilder();
    }

    /**
     * 初始化状态布局配置
     */
    private void initStateLayout() {
        StateLayout.GlobalBuilder globalBuilder = new StateLayout.GlobalBuilder(new StateLayout.OnStateChanged() {
            @Override
            public void OnChanged(StateLayout stateLayout, int state, String type, int size) {
                View view = stateLayout.getView(state);
                if (view != null) {
                    if (state == 4) { // NO_DATA
                        View vid_slnd_tips_tv = ViewUtils.findViewById(view, R.id.vid_slnd_tips_tv);
                        TextViewUtils.setText(vid_slnd_tips_tv, "暂无数据");
                    } else if (state == 2) { // FAIL
                        View vid_slf_tips_tv = ViewUtils.findViewById(view, R.id.vid_slf_tips_tv);
                        TextViewUtils.setText(vid_slf_tips_tv, "请求失败, 请稍后重试!");
                    }
                }
            }
        }).insert(StateLayout.State.NO_DATA, R.layout.state_layout_no_data)
                .insert(StateLayout.State.FAIL, R.layout.state_layout_fail)
                .insert(StateLayout.State.ING, R.layout.state_layout_ing);
        // 设置全局配置
        StateLayout.setBuilder(globalBuilder);
    }

    /**
     * 初始化异常捕获处理
     */
    private void initCrash() {
        // 捕获异常处理 => 在 BaseApplication 中调用
        CrashUtils.getInstance().init(getApplicationContext(), new CrashUtils.CrashCatchListener() {
            @Override
            public void handleException(Throwable ex) {
                // 保存日志信息
                FileRecordUtils.saveErrorLog(ex, PathConfig.SDP_ERROR_PATH, "crash_" + DateUtils.getDateNow() + ".txt");
            }

            @Override
            public void uncaughtException(Context context, Thread thread, Throwable ex) {
//                // 退出 JVM (Java 虚拟机 ) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
//                System.exit(-1);
//                // 从操作系统中结束掉当前程序的进程
//                android.os.Process.killProcess(android.os.Process.myPid());
                // 关闭 APP
                ActivityUtils.getManager().exitApplication();
                // 可开启定时任务, 延迟几秒启动 APP
            }
        });
    }

    /**
     * 初始化 WebView 辅助类全局配置
     */
    private void initWebViewBuilder() {
        WebViewAssist.Builder builder = new WebViewAssist.Builder();
        builder.setBuiltInZoomControls(true) // 显示内置缩放工具
                .setDisplayZoomControls(true) // 显示缩放工具
                .setAppCachePath(PathUtils.getInternal().getAppCachePath("cache")) // Application Caches 地址
                .setDatabasePath(PathUtils.getInternal().getAppCachePath("db")) // 数据库缓存路径
                .setRenderPriority(WebSettings.RenderPriority.HIGH) // 渲染优先级高
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 基础布局算法
                .setOnApplyListener(new WebViewAssist.Builder.OnApplyListener() {
                    @Override
                    public void onApply(WebViewAssist webViewAssist, WebViewAssist.Builder builder) {
                        DevLogger.d("WebViewAssist Builder onApply");
                        if (webViewAssist != null) {
//                            // 自己控制配置
//                            WebSettings webSettings = webViewAssist.getSettings();
//                            if (webSettings != null) {
//                                webSettings.setAllowFileAccess(true);
//                            }
                        }
                    }
                });
        WebViewAssist.setGlobalBuilder(builder);
    }
}
