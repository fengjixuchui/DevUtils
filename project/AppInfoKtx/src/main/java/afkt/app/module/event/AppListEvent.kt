package afkt.app.module.event

import afkt.app.module.TypeEnum
import dev.utils.app.info.AppInfoBean

/**
 * detail: 应用列表搜索事件
 * @author Ttt
 */
class AppListEvent(appType: AppInfoBean.AppType, lists: ArrayList<AppInfoBean>) {

    val type: TypeEnum
    val appType: AppInfoBean.AppType = appType
    val lists: ArrayList<AppInfoBean> = lists

    init {
        when (appType) {
            AppInfoBean.AppType.USER -> {
                type = TypeEnum.APP_USER
            }
            AppInfoBean.AppType.SYSTEM -> {
                type = TypeEnum.APP_SYSTEM
            }
            AppInfoBean.AppType.ALL -> {
                type = TypeEnum.NONE
            }
        }
    }
}