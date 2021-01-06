package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.setDataStore
import afkt.app.databinding.FragmentInfoBinding
import afkt.app.module.DeviceInfoBean
import afkt.app.module.PathConfig
import afkt.app.module.TypeEnum
import afkt.app.ui.adapter.InfoAdapter
import afkt.app.utils.ProjectUtils
import android.os.Bundle
import android.view.View
import dev.utils.app.ResourceUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    companion object {
        fun get(type: TypeEnum): BaseFragment<FragmentInfoBinding> {
            val fragment = InfoFragment()
            fragment.setDataStore(type)
            return fragment
        }
    }

    override fun baseContentId() = R.layout.fragment_info

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setEnableRefresh(false)
            .setEnableLoadMore(false)

        viewModel.infoObserve(viewLifecycleOwner, {
            if (it.type == dataStore.typeEnum) {
                binding.root.setAdapter(InfoAdapter(it.lists))
            }
        })

        viewModel.exportInfo.observe(viewLifecycleOwner, {
            if (it == dataStore.typeEnum) {
                if (binding.root.getAdapter<InfoAdapter>() != null) {
                    var adapter: InfoAdapter? = binding.root.getAdapter()
                    if (adapter?.data != null) {
                        val content: String? = DeviceInfoBean.jsonString(adapter?.data)
                        var fileName =
                            if (TypeEnum.DEVICE_INFO == it) "device_info.txt" else "screen_info.txt"
                        // 导出数据
                        var result = FileUtils.saveFile(
                            FileUtils.getFile(PathConfig.AEP_PATH, fileName),
                            content?.toByteArray()
                        )
                        if (result) {
                            ToastTintUtils.success(ResourceUtils.getString(R.string.str_export_suc))
                            return@observe
                        }
                    }
                    ToastTintUtils.error(ResourceUtils.getString(R.string.str_export_fail))
                }
            }
        })

        when (dataStore.typeEnum) {
            TypeEnum.DEVICE_INFO -> ProjectUtils.getDeviceInfo(viewModel)
            TypeEnum.SCREEN_INFO -> ProjectUtils.getScreenInfo(viewModel)
        }
    }
}