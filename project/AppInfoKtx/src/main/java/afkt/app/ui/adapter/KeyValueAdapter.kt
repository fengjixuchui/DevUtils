package afkt.app.ui.adapter

import afkt.app.R
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.app.ClipboardUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.info.KeyValueBean
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 键值对 Adapter
 * @author Ttt
 */
class KeyValueAdapter(data: MutableList<KeyValueBean>) :
    BaseQuickAdapter<KeyValueBean, BaseViewHolder>(R.layout.adapter_item_key_value, data) {

    init {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                data?.get(position)?.let {
                    if (listener != null && listener!!.onItemClick(it, position)) {
                        return
                    }
                    val txt: String = it.toString()
                    // 复制到剪切板
                    ClipboardUtils.copyText(txt)
                    // 进行提示
                    ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + ", " + txt)
                }
            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: KeyValueBean) {
        holder.setText(R.id.vid_aikv_key_tv, item.key)
            .setText(R.id.vid_aikv_value_tv, item.value)
    }

    // =

    private var listener: Listener? = null

    interface Listener {
        fun onItemClick(item: KeyValueBean, position: Int): Boolean
    }

    fun setListener(listener: Listener): KeyValueAdapter {
        this.listener = listener
        return this
    }
}