package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.module.bean.DeviceInfoBean
import afkt.app.module.item.DeviceInfoItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.app.ClipboardUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 设备、屏幕信息 Adapter
 * @author Ttt
 */
class InfoAdapter(data: MutableList<DeviceInfoItem>?) :
    BaseQuickAdapter<DeviceInfoItem, BaseViewHolder>(R.layout.adapter_item_device_info, data) {

    init {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                (data?.get(position) as DeviceInfoItem)?.let {
                    val txt: String = DeviceInfoBean.copyString(it)
                    // 复制到剪切板
                    ClipboardUtils.copyText(txt)
                    // 进行提示
                    ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + ", " + txt)
                }
            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: DeviceInfoItem) {
        holder.setText(R.id.vid_aidi_value_tv, item.value)
            .setText(R.id.vid_aidi_key_tv, ResourceUtils.getString(item.resId))
    }
}