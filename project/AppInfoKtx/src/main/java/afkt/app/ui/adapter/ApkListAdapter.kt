package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.base.model.FileApkItem
import afkt.app.ui.activity.ApkDetailsActivity
import android.content.Intent
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.DevFinal
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
        setOnItemClickListener { _, _, position ->
            (data?.get(position) as FileApkItem).run {
                if (FileUtils.isFileExists(uri)) {
                    val intent = Intent(context, ApkDetailsActivity::class.java)
                    intent.putExtra(DevFinal.URI, uri)
                    AppUtils.startActivity(intent)
                } else {
                    ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
                }
            }
        }
    }

    override fun convert(
        holder: BaseViewHolder,
        item: FileApkItem
    ) {
        val appInfoBean = item.appInfoBean
        holder.setText(R.id.vid_aia_name_tv, appInfoBean.appName)
            .setText(R.id.vid_aia_pack_tv, appInfoBean.appPackName)
            .setImageDrawable(R.id.vid_aia_igview, appInfoBean.appIcon)

        val nameTv: TextView = holder.getView(R.id.vid_aia_name_tv)
        val packTv: TextView = holder.getView(R.id.vid_aia_pack_tv)
        if (FileUtils.isFileExists(item.uri)) {
            ViewHelper.get().setAntiAliasFlag(nameTv)
                .setAntiAliasFlag(packTv)
        } else {
            ViewHelper.get().setStrikeThruText(nameTv)
                .setStrikeThruText(packTv)
        }
    }

    public override fun getDefItemCount(): Int {
        return super.getDefItemCount()
    }
}