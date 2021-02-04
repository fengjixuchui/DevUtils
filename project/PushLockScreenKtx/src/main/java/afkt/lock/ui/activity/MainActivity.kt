package afkt.lock.ui.activity

import afkt.lock.R
import afkt.lock.base.BaseActivity
import afkt.lock.databinding.ActivityMainBinding
import afkt.lock.receiver.DeviceReceiver
import android.content.DialogInterface
import dev.utils.app.DevicePolicyUtils
import dev.utils.app.DialogUtils
import dev.utils.app.toast.ToastTintUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initListener() {
        super.initListener()

        DevicePolicyUtils.getInstance()
            .setComponentName(DeviceReceiver::class.java)

        // 激活组件 ( 申请权限 )
        binding.vidAmActiveBtn.setOnClickListener {
            if (DevicePolicyUtils.getInstance().isAdminActive()) {
                showToast(true, "已激活组件")
                return@setOnClickListener
            }
            showToast(
                DevicePolicyUtils.getInstance().activeAdmin("需开启权限允许对设备进行操作!")
            )
        }

        // 移除组件
        binding.vidAmRemoveActiveBtn.setOnClickListener {
            DialogUtils.createAlertDialog(this, "移除组件",
                "是否移除组件 ( 移除设备管理权限 )", "取消", "确认",
                object : DialogUtils.DialogListener() {
                    override fun onRightButton(dialog: DialogInterface?) {
                        showToast(
                            DevicePolicyUtils.getInstance().removeActiveAdmin()
                        )
                    }
                }).show()
        }

        // 锁屏
        binding.vidAmLockBtn.setOnClickListener {
            showToast(
                DevicePolicyUtils.getInstance().lockNow()
            )
        }

        // 延迟锁屏
        binding.vidAmDelayLockBtn.setOnClickListener {
            showToast(
                DevicePolicyUtils.getInstance().lockByTime(30000L)
            )
        }
    }

    private fun showToast(result: Boolean) {
        showToast(result, if (result) "操作成功" else "操作失败")
    }

    private fun showToast(
        result: Boolean,
        text: String
    ) {
        if (result) {
            ToastTintUtils.success(text)
        } else {
            ToastTintUtils.error(text)
        }
    }
}