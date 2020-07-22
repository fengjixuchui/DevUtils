package afkt.app.ui.activity

import afkt.app.R
import afkt.app.base.Constants
import afkt.app.base.config.PathConfig
import afkt.app.ui.adapter.KeyValueAdapter
import afkt.app.utils.ProjectUtils
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import dev.utils.app.AppUtils
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.app.info.AppInfoItem
import dev.utils.app.info.AppInfoUtils
import dev.utils.app.info.KeyValueBean
import dev.utils.app.logger.DevLogger
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils
import dev.utils.common.thread.DevThreadManager

class AppDetailsActivity : AppCompatActivity(), View.OnClickListener {

    // = View =

    @JvmField
    @BindView(R.id.vid_aad_toolbar)
    var vid_aad_toolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.vid_aad_app_igview)
    var vid_aad_app_igview: ImageView? = null

    @JvmField
    @BindView(R.id.vid_aad_name_tv)
    var vid_aad_name_tv: TextView? = null

    @JvmField
    @BindView(R.id.vid_aad_vname_tv)
    var vid_aad_vname_tv: TextView? = null

    @JvmField
    @BindView(R.id.vid_aad_recy)
    var vid_aad_recy: RecyclerView? = null

    // = Object =

    var appInfoItem: AppInfoItem? = null // APP 信息 Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)
        ButterKnife.bind(this)

        init()
    }

    fun init() {
        try {
            appInfoItem =
                AppInfoUtils.getAppInfoItem(getIntent().getStringExtra(Constants.Key.KEY_PACKNAME))
        } catch (e: Exception) {
            DevLogger.e(e)
        }
        if (appInfoItem == null) {
            ToastTintUtils.warning(ResourceUtils.getString(R.string.str_get_appinfo_fail))
            finish()
            return
        }

        setSupportActionBar(vid_aad_toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            actionBar.setDisplayShowTitleEnabled(false)
            // 设置点击事件
            vid_aad_toolbar?.setNavigationOnClickListener {
                finish()
            }
        }

        // 获取 APP 信息
        val appInfoBean = appInfoItem!!.appInfoBean
        ViewHelper.get()
            .setImageDrawable(vid_aad_app_igview, appInfoBean.appIcon) // 设置 app 图标
            .setText(vid_aad_name_tv, appInfoBean.appName) // 设置 app 名
            .setText(vid_aad_vname_tv, appInfoBean.versionName) // 设置 app 版本

        var lists = appInfoItem!!.listKeyValues
        lists.add(
            0,
            KeyValueBean.get(
                R.string.str_app_market,
                ResourceUtils.getString(R.string.str_goto_app_market)
            )
        )
        lists.add(
            1,
            KeyValueBean.get(
                R.string.str_app_details_setting,
                ResourceUtils.getString(R.string.str_goto_app_details_setting)
            )
        )
        vid_aad_recy!!.adapter =
            KeyValueAdapter(lists).setListener(object : KeyValueAdapter.Listener {
                override fun onItemClick(item: KeyValueBean, position: Int): Boolean {
                    return when (position) {
                        0 -> {
                            if (!AppUtils.launchAppDetails(appInfoBean.appPackName, "")) {
                                ToastTintUtils.error(ResourceUtils.getString(R.string.str_operate_fail))
                            }
                            true
                        }
                        1 -> {
                            if (AppUtils.isInstalledApp(appInfoBean.appPackName)) {
                                AppUtils.launchAppDetailsSettings(appInfoBean.appPackName)
                            } else {
                                ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
                            }
                            true
                        }
                        else -> false
                    }
                }
            })
    }

    // ===========
    // = OnClick =
    // ===========

    @OnClick(R.id.vid_aad_open_app_tv, R.id.vid_aad_uninstall_tv)
    override fun onClick(v: View) {
        if (!AppUtils.isInstalledApp(appInfoItem!!.appInfoBean.appPackName)) {
            ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
            return
        }
        when (v.id) {
            R.id.vid_aad_open_app_tv -> AppUtils.launchApp(appInfoItem!!.appInfoBean.appPackName)
            R.id.vid_aad_uninstall_tv -> AppUtils.uninstallApp(appInfoItem!!.appInfoBean.appPackName)
        }
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bar_menu_app_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.bmai_export_app_msg) {
            if (!AppUtils.isInstalledApp(appInfoItem!!.appInfoBean.appPackName)) {
                ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
                return false
            }
        }
        when (item.itemId) {
            R.id.bmai_share -> {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.type = "*/*"
                    intent.putExtra(
                        Intent.EXTRA_STREAM,
                        Uri.fromFile(
                            FileUtils.getFile(appInfoItem!!.appInfoBean.sourceDir)
                        )
                    )
                    startActivity(intent)
                } catch (e: java.lang.Exception) {
                    ToastTintUtils.error(ResourceUtils.getString(R.string.str_share_fail))
                }
            }
            R.id.bmai_export_app -> {// 提示导出中
                DevThreadManager.getInstance(3).execute {
                    // 提示导出中
                    ToastTintUtils.normal(ResourceUtils.getString(R.string.str_export_ing))

                    val appInfoBean = appInfoItem!!.appInfoBean
                    // 应用名_包名_版本名.apk
                    var fileName =
                        appInfoBean.appName + "_" + appInfoBean.appPackName + "_" + appInfoBean.versionName + ".apk"
                    // 导出应用
                    var result = FileUtils.copyFile(
                        appInfoBean.sourceDir,
                        FileUtils.getFile(PathConfig.AEP_APK_PATH, fileName).absolutePath,
                        true
                    )
                    if (result) {
                        ToastTintUtils.success(
                            ResourceUtils.getString(R.string.str_export_suc)
                                    + " " + PathConfig.AEP_APK_PATH + fileName
                        )
                    } else {
                        ToastTintUtils.error(ResourceUtils.getString(R.string.str_export_fail))
                    }
                }
            }
            R.id.bmai_export_app_msg -> {
                HandlerUtils.postRunnable {
                    val appInfoBean = appInfoItem!!.appInfoBean
                    // 应用名_包名_版本名.txt
                    var fileName =
                        appInfoBean.appName + "_" + appInfoBean.appPackName + "_" + appInfoBean.versionName + ".txt"
                    // 导出数据
                    var result = FileUtils.saveFile(
                        FileUtils.getFile(PathConfig.AEP_APPMSG_PATH, fileName),
                        ProjectUtils.toJson(appInfoItem)?.toByteArray()
                    )
                    if (result) {
                        ToastTintUtils.success(
                            ResourceUtils.getString(R.string.str_export_suc)
                                    + " " + PathConfig.AEP_APPMSG_PATH + fileName
                        )
                    } else {
                        ToastTintUtils.error(ResourceUtils.getString(R.string.str_export_fail))
                    }
                }
            }
        }
        return true
    }
}