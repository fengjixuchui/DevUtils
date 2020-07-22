package afkt.app.module.item

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
    // 文件MD5
    val md5: String,
    // APP 信息
    val appInfoBean: AppInfoBean
) {

    // 文件最后操作时间
    val lastModified: Long = file.lastModified()
}