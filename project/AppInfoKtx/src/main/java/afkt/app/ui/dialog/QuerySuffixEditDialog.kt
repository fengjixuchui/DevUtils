package afkt.app.ui.dialog

import afkt.app.R
import afkt.app.utils.QuerySuffixUtils
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import dev.utils.app.HandlerUtils
import dev.utils.app.KeyBoardUtils
import dev.utils.app.ScreenUtils

/**
 * detail: 搜索后缀设置 Dialog
 * @author Ttt
 */
class QuerySuffixEditDialog(
    context: Context, onClickListener: View.OnClickListener
) : Dialog(context, R.style.Theme_Light_FullScreenDialogOperate), View.OnClickListener {

    @JvmField
    @BindView(R.id.vid_dqse_edit_text)
    var vid_dqse_edit_text: EditText? = null

    @OnClick(R.id.vid_dqse_add_tv, R.id.vid_dqse_cancel_tv)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.vid_dqse_add_tv -> {
                var input = vid_dqse_edit_text!!.text.toString()
                if (input.isBlank()) {
                    cancelDialog()
                    return
                }
                input = input.toLowerCase()
                val maps = QuerySuffixUtils.querySuffixMap
                if (maps.containsKey(input)) {
                    cancelDialog()
                    return
                }
                maps[input] = input
                QuerySuffixUtils.refresh(maps)
                notifyListener.onClick(v)
                cancelDialog()
            }
            R.id.vid_dqse_cancel_tv -> cancelDialog()
        }
    }

    // 点击通知事件
    val notifyListener: View.OnClickListener

    init {
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        this.setContentView(R.layout.dialog_query_suffix_edit)
        ButterKnife.bind(this)

        notifyListener = onClickListener

        val params = window!!.attributes
        val screen = ScreenUtils.getScreenWidthHeight()
        params.dimAmount = 0.5f
        params.width = screen[0]
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        window!!.attributes = params

        // 禁止返回键关闭
        setCancelable(false)
        // 禁止点击其他地方自动关闭
        setCanceledOnTouchOutside(false)
        // 关闭处理
        setOnDismissListener {
            HandlerUtils.postRunnable({ // 关闭输入法
                KeyBoardUtils.closeKeyboard(vid_dqse_edit_text)
            }, 100)
        }
    }

    fun cancelDialog() {
        KeyBoardUtils.closeKeyboard(vid_dqse_edit_text)
        if (this.isShowing) cancel()
    }
}