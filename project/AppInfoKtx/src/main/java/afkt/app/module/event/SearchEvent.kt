package afkt.app.module.event

import afkt.app.module.ActionEnum
import afkt.app.module.TypeEnum

/**
 * detail: 搜索事件
 * @author Ttt
 */
class SearchEvent(type: TypeEnum, action: ActionEnum) {

    val type: TypeEnum = type
    val action: ActionEnum = action
    var content: String? = null
}