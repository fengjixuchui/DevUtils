package afkt.app.base

import afkt.app.module.DeviceInfo
import afkt.app.module.TypeEnum
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    // 应用排序
    val appSort = MutableLiveData<Boolean>()

    // Fragment 切换
    val fragmentChange = MutableLiveData<TypeEnum>()

    // 设备信息
    val deviceInfo = MutableLiveData<DeviceInfo>()

    // 屏幕信息
    val screenInfo = MutableLiveData<DeviceInfo>()

    // 导出设备、屏幕信息
    val exportInfo = MutableLiveData<TypeEnum>()

    fun infoObserve(
        owner: LifecycleOwner,
        observer: Observer<DeviceInfo>
    ) {
        deviceInfo.observe(owner, observer)
        screenInfo.observe(owner, observer)
    }
}