package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.base.Constants
import afkt.app.module.item.FileApkItem
import afkt.app.ui.activity.ApkDetailsActivity
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

/**
 * detail: App 列表 Adapter
 * @author Ttt
 */
class ApkListAdapter(data: MutableList<FileApkItem>?) :
    BaseQuickAdapter<FileApkItem, BaseViewHolder>(R.layout.adapter_item_app, data) {

    init {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                (data?.get(position) as FileApkItem)?.let {
                    if (FileUtils.isFileExists(it.uri)) {
                        var intent = Intent(context, ApkDetailsActivity::class.java)
                        intent.putExtra(Constants.Key.KEY_APK_URI, it.uri)
                        AppUtils.startActivity(intent)
                    } else {
                        ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
                    }
                }
            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: FileApkItem) {
        var appInfoBean = item.appInfoBean
        holder.setText(R.id.vid_aia_name_tv, appInfoBean.appName)
            .setText(R.id.vid_aia_pack_tv, appInfoBean.appPackName)
            .setImageDrawable(R.id.vid_aia_igview, appInfoBean.appIcon)

        var vid_aia_name_tv: TextView = holder.getView(R.id.vid_aia_name_tv)
        var vid_aia_pack_tv: TextView = holder.getView(R.id.vid_aia_pack_tv)
        if (FileUtils.isFileExists(item.uri)) {
            ViewHelper.get().setAntiAliasFlag(vid_aia_name_tv)
                .setAntiAliasFlag(vid_aia_pack_tv)
        } else {
            ViewHelper.get().setStrikeThruText(vid_aia_name_tv)
                .setStrikeThruText(vid_aia_pack_tv)
        }
    }

    public override fun getDefItemCount(): Int {
        return super.getDefItemCount()
    }
}