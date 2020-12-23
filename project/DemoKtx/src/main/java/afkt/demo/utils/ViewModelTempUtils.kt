package afkt.demo.utils

import afkt.demo.model.ActivityViewModel
import afkt.demo.model.ApplicationViewModel
import afkt.demo.model.FragmentViewModel
import androidx.lifecycle.LifecycleOwner
import dev.utils.app.logger.DevLogger

/**
 * detail: ViewModel 临时工具类
 * @author Ttt
 */
object ViewModelTempUtils {

    // 日志 TAG
    private val TAG = ViewModelTempUtils::class.java.simpleName

    /**
     * 统一监听方法
     * @param tag TAG
     * @param owner [LifecycleOwner]
     * @param viewModel [ApplicationViewModel]
     */
    fun observe(
        tag: String,
        owner: LifecycleOwner,
        viewModel: ApplicationViewModel?
    ) {
        viewModel?.let {
            // 进行监听
            viewModel.number.observe(owner, {
                DevLogger.dTag(TAG, "%s observe number: %s", tag, viewModel.number.value)
            })
        }
    }

    /**
     * 统一监听方法
     * @param tag TAG
     * @param owner [LifecycleOwner]
     * @param viewModel [ApplicationViewModel]
     */
    fun observe(
        tag: String,
        owner: LifecycleOwner,
        viewModel: ActivityViewModel?
    ) {
        viewModel?.let {
            // 进行监听
            viewModel.number.observe(owner, {
                DevLogger.dTag(TAG, "%s observe number: %s", tag, viewModel.number.value)
            })
        }
    }

    /**
     * 统一监听方法
     * @param tag TAG
     * @param owner [LifecycleOwner]
     * @param viewModel [ApplicationViewModel]
     */
    fun observe(
        tag: String,
        owner: LifecycleOwner,
        viewModel: FragmentViewModel?
    ) {
        viewModel?.let {
            // 进行监听
            viewModel.number.observe(owner, {
                DevLogger.dTag(TAG, "%s observe number: %s", tag, viewModel.number.value)
            })
        }
    }
}