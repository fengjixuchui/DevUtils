package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import androidx.viewbinding.ViewBinding

/**
 * detail: Material BottomSheetDialog
 * @author Ttt
 */
class BottomSheetDialogActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_bottom_sheet_dialog
}