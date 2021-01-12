package afkt.app.ui.activity

import afkt.app.R
import afkt.app.base.BaseActivity
import afkt.app.databinding.ActivityApkDetailsBinding
import afkt.app.ui.adapter.KeyValueAdapter
import afkt.app.utils.ExportUtils
import android.Manifest
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.IntentUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.app.info.ApkInfoItem
import dev.utils.app.info.AppInfoUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.permission.PermissionUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

class ApkDetailsActivity : BaseActivity<ActivityApkDetailsBinding>() {

    // APK 信息 Item
    private var apkInfoItem: ApkInfoItem? = null

    override fun baseContentId(): Int = R.layout.activity_apk_details

    override fun initValue() {
        super.initValue()
        try {
            apkInfoItem =
                AppInfoUtils.getApkInfoItem(intent.getStringExtra(DevFinal.URI))
        } catch (e: Exception) {
            DevLogger.e(e)
        }
        if (apkInfoItem == null) {
            ToastTintUtils.warning(ResourceUtils.getString(R.string.str_get_apkinfo_fail))
            finish()
            return
        }
        setSupportActionBar(binding.vidAadToolbar)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
            // 设置点击事件
            binding.vidAadToolbar.setNavigationOnClickListener { finish() }
        }
        // 获取 APP 信息
        val appInfoBean = apkInfoItem!!.appInfoBean
        ViewHelper.get()
            .setImageDrawable(binding.vidAadAppIgview, appInfoBean.appIcon) // 设置 app 图标
            .setText(binding.vidAadNameTv, appInfoBean.appName) // 设置 app 名
            .setText(binding.vidAadVnameTv, appInfoBean.versionName) // 设置 app 版本

        binding.vidAadRecy.adapter = KeyValueAdapter(apkInfoItem!!.listKeyValues)
    }

    override fun initListener() {
        super.initListener()
        // 安装应用
        binding.vidAadInstallTv.setOnClickListener {
            val sourceDir = apkInfoItem!!.appInfoBean.sourceDir
            if (FileUtils.isFileExists(sourceDir)) {
                // Android 8.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (packageManager.canRequestPackageInstalls()) {
                        AppUtils.installApp(sourceDir) // 安装 APK
                    } else {
                        PermissionUtils.permission(
                            Manifest.permission.REQUEST_INSTALL_PACKAGES
                        ).callback(object :
                            PermissionUtils.PermissionCallback {
                            override fun onGranted() {
                                AppUtils.installApp(sourceDir) // 安装 APK
                            }

                            override fun onDenied(
                                grantedList: List<String>,
                                deniedList: List<String>,
                                notFoundList: List<String>
                            ) {
                                val builder = StringBuilder()
                                    .append("申请通过的权限")
                                    .append(grantedList.toTypedArray().contentToString())
                                    .append(DevFinal.NEW_LINE_STR)
                                    .append("拒绝的权限").append(deniedList.toString())
                                    .append(DevFinal.NEW_LINE_STR)
                                    .append("未找到的权限").append(notFoundList.toString())
                                if (deniedList.isNotEmpty()) {
                                    DevLogger.d(builder.toString())
                                    ToastTintUtils.info(ResourceUtils.getString(R.string.str_install_request_tips))
                                    // 跳转设置页面, 开启安装未知应用权限
                                    startActivity(IntentUtils.getLaunchAppInstallPermissionSettingsIntent())
                                } else {
                                    onGranted()
                                }
                            }
                        }).request(this)
                    }
                } else { // 安装 APK
                    AppUtils.installApp(sourceDir)
                }
            } else {
                ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
            }
        }
        // 删除安装包
        binding.vidAadDeleteTv.setOnClickListener {
            val sourceDir = apkInfoItem!!.appInfoBean.sourceDir
            if (FileUtils.isFileExists(sourceDir)) {
                FileUtils.deleteFile(sourceDir)
                globalViewModel?.postFileDelete()
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_delete_suc))
            } else {
                ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
            }
        }
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menu_apk_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bmpi_share -> {
                apkInfoItem?.let { ExportUtils.shareApp(it) }
            }
            R.id.bmpi_export_apk -> {
                apkInfoItem?.let { ExportUtils.exportApp(it) }
            }
            R.id.bmpi_export_apk_msg -> {
                apkInfoItem?.let { ExportUtils.exportInfo(it) }
            }
        }
        return true
    }
}