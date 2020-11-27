package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainFragmentMvvmBinding
import afkt.demo.model.FragmentViewModel
import afkt.demo.ui.fragment.FragmentMVVMFragment
import android.os.Bundle
import android.view.View
import dev.base.expand.mvvm.DevBaseMVVMActivity
import dev.utils.common.ColorUtils

/**
 * detail: Main Fragment MVVM Activity
 * @author Ttt
 */
class MainFragmentMVVMActivity :
    DevBaseMVVMActivity<ActivityMainFragmentMvvmBinding, FragmentViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_fragment_mvvm
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding.title = "Fragment MVVM Title"

        // 随机设置背景色
        binding.vidAmfmvvmInclude.color = ColorUtils.getRandomColor()

        // 嵌套处理
        FragmentMVVMFragment.commit(supportFragmentManager, R.id.vid_amfmvvm_frame, 0, 4)
    }

    override fun initViewModel() {
    }
}