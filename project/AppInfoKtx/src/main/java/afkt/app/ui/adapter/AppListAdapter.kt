package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.ui.activity.AppDetailsActivity
import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.info.AppInfoBean
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: App 列表 Adapter
 * @author Ttt
 */
class AppListAdapter(data: MutableList<AppInfoBean>?) :
    BaseQuickAdapter<AppInfoBean, BaseViewHolder>(R.layout.adapter_item_app, data) {

    init {
        setOnItemClickListener { _, _, position ->
            (data?.get(position) as AppInfoBean).run {
                if (AppUtils.isInstalledApp(appPackName)) {
                    val intent = Intent(context, AppDetailsActivity::class.java)
                    intent.putExtra(DevFinal.PACKNAME, appPackName)
                    AppUtils.startActivity(intent)
                } else {
                    ToastTintUtils.warning(ResourceUtils.getString(R.string.str_app_not_exist))
                }
            }
        }
    }

    override fun convert(
        holder: BaseViewHolder,
        item: AppInfoBean
    ) {
        holder.setText(R.id.vid_aia_name_tv, item.appName)
            .setText(R.id.vid_aia_pack_tv, item.appPackName)
            .setImageDrawable(R.id.vid_aia_igview, item.appIcon)
    }
}