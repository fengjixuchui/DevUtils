package afkt.app.ui.dialog

import afkt.app.R
import afkt.app.base.Constants
import afkt.app.module.event.SortEvent
import afkt.app.utils.EventBusUtils
import afkt.app.utils.ProjectUtils
import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.RadioButton
import android.widget.RadioGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.utils.app.share.SharedUtils

/**
 * detail: App 排序 Dialog
 * @author Ttt
 */
class AppSortDialog(context: Context?) :
    Dialog(context!!, R.style.Theme_Light_FullScreenDialogOperate), View.OnClickListener {

    @JvmField
    @BindView(R.id.vid_das_radiogroup)
    var vid_das_radiogroup: RadioGroup? = null

    @OnClick(R.id.vid_das_cancel_tv)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.vid_das_cancel_tv -> cancel()
        }
    }

    init {
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.setContentView(R.layout.dialog_app_sort)
        ButterKnife.bind(this)

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
                    SharedUtils.put(Constants.Key.KEY_APP_SORT, i)
                    // 发送应用排序变更通知事件
                    EventBusUtils.post(SortEvent())
                }
                cancel()
            }
            vid_das_radiogroup!!.addView(
                itemView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
        vid_das_radiogroup!!.check(ProjectUtils.getAppSortType())
    }
}