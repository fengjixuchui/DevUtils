package afkt.app.ui.dialog

import afkt.app.R
import afkt.app.databinding.DialogQuerySuffixBinding
import afkt.app.utils.QuerySuffixUtils
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 搜索后缀设置 Dialog
 * @author Ttt
 */
class QuerySuffixDialog(context: Context?) :
    Dialog(context!!, R.style.Theme_Light_FullScreenDialogOperate) {

    private var binding: DialogQuerySuffixBinding

    init {
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        binding = DialogQuerySuffixBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        val params = window!!.attributes
        val screen = ScreenUtils.getScreenWidthHeight()
        params.dimAmount = 0.5f
        params.width = screen[0]
        params.height = screen[1]
        params.gravity = Gravity.CENTER
        window!!.attributes = params

        // 禁止返回键关闭
        setCancelable(false)
        // 禁止点击其他地方自动关闭
        setCanceledOnTouchOutside(false)

        // 初始化适配器并绑定
        val adapter = QuerySuffixAdapter()
        binding.vidDqsRecycleview.adapter = adapter
        adapter.refreshData()

        binding.vidDqsCloseTv.setOnClickListener {
            // 提示设置生效
            ToastTintUtils.success(ResourceUtils.getString(R.string.str_setting_scan_suffix_suc))
            cancel()
        }
    }

    // ===========
    // = Adapter =
    // ===========

    class QuerySuffixAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_item_query_suffix, ArrayList()) {

        init {
            addChildClickViewIds(R.id.vid_aiqs_framelayout)
            setOnItemChildClickListener { _, _, position ->
                val item: String = data[position]
                if (item.isEmpty()) {
                    QuerySuffixEditDialog(context) {
                        refreshData()
                    }.show()
                } else {
                    maps.remove(item)
                    QuerySuffixUtils.refresh(maps)
                    refreshData()
                }
            }
        }

        override fun convert(
            holder: BaseViewHolder,
            item: String
        ) {
            holder.setText(R.id.vid_aiqs_suffix_tv, item)
                .setImageResource(
                    R.id.vid_aiqs_igview,
                    if (item.isEmpty()) R.drawable.icon_add else R.drawable.icon_close
                )
        }

        // =

        private var maps = LinkedHashMap<String, String>()

        /**
         * 刷新数据
         */
        fun refreshData() {
            setNewInstance(readData())
        }

        /**
         * 读取数据
         */
        private fun readData(): ArrayList<String> {
            maps = LinkedHashMap(QuerySuffixUtils.querySuffixMap)
            val lists = ArrayList<String>(maps.keys)
            lists.add("")
            return lists
        }
    }
}