package afkt.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import dev.base.expand.viewbinding.DevBaseViewBindingFragment

abstract class BaseFragment<VB : ViewBinding> : DevBaseViewBindingFragment<VB>() {

    val viewModel by activityViewModels<AppViewModel>()

    val dataStore = AppDataStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataStore.initDataStore(arguments = arguments)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    override fun baseContentView(): View? {
        return null
    }
}