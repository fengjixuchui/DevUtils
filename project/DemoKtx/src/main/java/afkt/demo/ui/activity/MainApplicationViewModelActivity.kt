package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.base.BaseApplication
import afkt.demo.model.ApplicationViewModel
import afkt.demo.ui.fragment.ApplicationViewModelFragment
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import dev.base.expand.viewmodel.DevBaseViewModelActivity
import dev.utils.app.helper.ViewHelper
import dev.utils.common.ColorUtils

/**
 * detail: Main Application ViewModel Activity
 * @author Ttt
 */
class MainApplicationViewModelActivity : DevBaseViewModelActivity<ApplicationViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_application_view_model
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        ViewHelper.get().setText(
            findViewById(R.id.vid_amavm_title_tv),
            "Application ViewModel Title"
        ).setBackgroundColor(
            findViewById(R.id.vid_ivd_view), ColorUtils.getRandomColor()
        )

        // 嵌套处理
        ApplicationViewModelFragment.commit(supportFragmentManager, R.id.vid_amavm_frame, 0, 4)
    }

    override fun initViewModel() {
        viewModel =
            getAppViewModel(BaseApplication.getApplication(), ApplicationViewModel::class.java)!!
        // 复用方法进行监听
        ViewModelTempUtils.observe(TAG, this, viewModel)
        // 临时改变值
        Handler().postDelayed(Runnable {
            viewModel.number.value = Int.MAX_VALUE
        }, 2000)
    }
}