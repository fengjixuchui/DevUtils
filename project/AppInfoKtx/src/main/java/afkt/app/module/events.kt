package afkt.app.module

import dev.utils.app.info.AppInfoBean

/**
 * detail: 文件删除事件
 * @author Ttt
 */
class FileDeleteEvent

/**
 * detail: 开始搜索事件
 * @author Ttt
 */
class StartSearchEvent

/**
 * detail: 回到顶部事件
 * @author Ttt
 */
class TopEvent(val type: TypeEnum)

/**
 * detail: Fragment 切换事件
 * @author Ttt
 */
class FragmentEvent(val type: TypeEnum)

/**
 * detail: 刷新事件
 * @author Ttt
 */
class RefreshEvent(val type: TypeEnum)

/**
 * detail: 搜索事件
 * @author Ttt
 */
class SearchEvent(
    val type: TypeEnum,
    val action: ActionEnum,
    var content: String = ""
)

/**
 * detail: 扫描 SDCard 事件
 * @author Ttt
 */
class ScanSDCardEvent(val lists: ArrayList<FileApkItem>)

/**
 * detail: 应用列表搜索事件
 * @author Ttt
 */
class AppListEvent(
    appType: AppInfoBean.AppType,
    val lists: ArrayList<AppInfoBean>
) {

    val type: TypeEnum = when (appType) {
        AppInfoBean.AppType.USER -> {
            TypeEnum.APP_USER
        }
        AppInfoBean.AppType.SYSTEM -> {
            TypeEnum.APP_SYSTEM
        }
        AppInfoBean.AppType.ALL -> {
            TypeEnum.NONE
        }
    }
}