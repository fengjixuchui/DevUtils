package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.base.Constants
import afkt.app.ui.activity.AppDetailsActivity
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
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
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                (data?.get(position) as AppInfoBean)?.let {
                    if (AppUtils.isInstalledApp(it.appPackName)) {
                        var intent = Intent(context, AppDetailsActivity::class.java)
                        intent.putExtra(Constants.Key.KEY_PACKNAME, it.appPackName)
                        AppUtils.startActivity(intent)
                    } else {
                        ToastTintUtils.warning(ResourceUtils.getString(R.string.str_app_not_exist))
                    }
                }
            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: AppInfoBean) {
        holder.setText(R.id.vid_aia_name_tv, item.appName)
            .setText(R.id.vid_aia_pack_tv, item.appPackName)
            .setImageDrawable(R.id.vid_aia_igview, item.appIcon)
    }
}