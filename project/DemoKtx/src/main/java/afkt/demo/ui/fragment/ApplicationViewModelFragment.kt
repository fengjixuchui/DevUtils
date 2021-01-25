package afkt.demo.ui.fragment

import afkt.demo.R
import afkt.demo.base.BaseApplication
import afkt.demo.databinding.FragmentParentBinding
import afkt.demo.model.ApplicationViewModel
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dev.base.expand.viewbinding.DevBaseViewBindingFragment
import dev.base.utils.assist.DevBaseViewModelAssist
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.common.RandomUtils

/**
 * detail: 测试 Application ViewModel Fragment
 * @author Ttt
 */
class ApplicationViewModelFragment : DevBaseViewBindingFragment<FragmentParentBinding>() {

    override fun baseContentId(): Int {
        return R.layout.fragment_parent
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            var position = it.getInt(DevFinal.POSITION)
            var max = it.getInt(DevFinal.MAX)

            var positionStr = (position + 1).toString()

            // 设置索引文案
            binding.vidFpPositionTv.text = positionStr

            var viewModel = DevBaseViewModelAssist().getAppViewModel(
                BaseApplication.getApplication(), ApplicationViewModel::class.java
            )!!
            // 进行 ViewModel 绑定
            ViewModelTempUtils.observe(TAG + positionStr, this, viewModel)
            // 临时改变值
            Handler().postDelayed(Runnable {
                viewModel.number.value = RandomUtils.nextInt()
            }, (position + 1) * 1000L + 2000L)
            // 判断是否达到最大值
            if (position >= max) return

            // 设置 Fragment
            commit(childFragmentManager, binding.vidFpFrame.id, position + 1, max)
        }

        LogPrintUtils.dTag(
            LOG_TAG,
            "ApplicationViewModelFragment => parentFragment: %s",
            parentFragment
        )
    }

    companion object {
        fun get(
            position: Int,
            max: Int
        ): DevBaseViewBindingFragment<FragmentParentBinding> {
            val fragment = ApplicationViewModelFragment()
            val bundle = Bundle()
            bundle.putInt(DevFinal.POSITION, position)
            bundle.putInt(DevFinal.MAX, max)
            fragment.arguments = bundle
            return fragment
        }

        fun commit(
            manager: FragmentManager,
            id: Int,
            position: Int,
            max: Int
        ) {
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(id, get(position, max))
            transaction.commit()
        }

        const val LOG_TAG = "ApplicationViewModelFragment_TAG"
    }
}