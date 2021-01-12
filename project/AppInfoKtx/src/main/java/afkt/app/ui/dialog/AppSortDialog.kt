package afkt.app.ui.dialog

import afkt.app.R
import afkt.app.base.AppViewModel
import afkt.app.databinding.DialogAppSortBinding
import afkt.app.utils.ProjectUtils
import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.RadioButton
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.utils.app.share.SharedUtils

/**
 * detail: App 排序 Dialog
 * @author Ttt
 */
class AppSortDialog(
    viewModel: AppViewModel,
    context: Context?
) :
    Dialog(context!!, R.style.Theme_Light_FullScreenDialogOperate) {

    private var binding: DialogAppSortBinding

    init {
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DialogAppSortBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        val params = window!!.attributes
        val screen = ScreenUtils.getScreenWidthHeight()
        params.dimAmount = 0.5f
        params.width = screen[0]
        params.height = screen[1]
        params.gravity = Gravity.CENTER
        window!!.attributes = params

        val inflater = LayoutInflater.from(context)
        val appSortArys: Array<String> = ResourceUtils.getStringArray(R.array.array_app_sort)
        for (i in appSortArys.indices) {
            val itemView: View = inflater.inflate(R.layout.view_radio_btn, null, false)
            val radioButton = itemView.findViewById<RadioButton>(R.id.vid_vrb_radio)
            radioButton.id = i
            radioButton.text = appSortArys[i]
            radioButton.setOnClickListener { // 获取选中索引
                val sortPos: Int = ProjectUtils.getAppSortType()
                if (i != sortPos) {
                    SharedUtils.put(DevFinal.SORT, i)
                    // 发送应用排序变更通知事件
                    viewModel.postAppSort()
                }
                cancel()
            }
            binding.vidDasRadiogroup.addView(
                itemView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
        binding.vidDasRadiogroup.check(ProjectUtils.getAppSortType())
        binding.vidDasCancelTv.setOnClickListener { cancel() }
    }
}