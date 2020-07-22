package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.Constants
import afkt.app.module.event.SortEvent
import afkt.app.ui.dialog.AppSortDialog
import afkt.app.ui.dialog.QuerySuffixDialog
import afkt.app.utils.AppListUtils
import afkt.app.utils.ProjectUtils
import afkt.app.utils.QuerySuffixUtils
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import dev.utils.app.ResourceUtils
import dev.utils.app.share.SharedUtils
import dev.utils.app.toast.ToastTintUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SettingFragment : BaseFragment(), View.OnClickListener {

    // = View =

    @JvmField
    @BindView(R.id.vid_fs_appsort_tv)
    var vid_fs_appsort_tv: TextView? = null

    // = Object =

    var appSortArys: Array<String>? = null // 排序数据

    override fun layoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun layoutInit(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view!!)
        appSortArys = ResourceUtils.getStringArray(R.array.array_app_sort)
        selectAppSort()
    }

    @OnClick(R.id.vid_fs_appsort_linear, R.id.vid_fs_scanapk_linear, R.id.vid_fs_reset_linear)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.vid_fs_appsort_linear -> AppSortDialog(context).show()
            R.id.vid_fs_scanapk_linear -> QuerySuffixDialog(context).show()
            R.id.vid_fs_reset_linear -> {
                SharedUtils.put(Constants.Key.KEY_APP_SORT, 0)
                QuerySuffixUtils.reset() // 清空后缀
                AppListUtils.reset() // 清空应用列表
                selectAppSort() // 重置排序文案
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_reset_desetting_suc))
            }
        }
    }

    /**
     * 设置选择排序文案
     */
    private fun selectAppSort() {
        vid_fs_appsort_tv?.setText(appSortArys!![ProjectUtils.getAppSortType()])
    }

    // ============
    // = 事件相关 =
    // ============

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: SortEvent) {
        selectAppSort()
    }
}