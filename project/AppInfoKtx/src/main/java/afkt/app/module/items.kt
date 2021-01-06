package afkt.app.module

import androidx.annotation.StringRes
import dev.utils.app.info.AppInfoBean
import java.io.File

/**
 * detail: 文件资源 Item
 * @author Ttt
 */
class FileApkItem(
    val file: File,
    // 文件名字 ( 前缀.后缀 )
    val name: String,
    // 文件地址
    val uri: String,
    // APP 信息
    val appInfoBean: AppInfoBean,
    // 文件最后操作时间
    val lastModified: Long = file.lastModified()
)

/**
 * detail: 设备信息 Item
 * @author Ttt
 */
class DeviceInfoItem(
    // 提示文案
    @StringRes val resId: Int,
    // 配置值
    val value: String
)