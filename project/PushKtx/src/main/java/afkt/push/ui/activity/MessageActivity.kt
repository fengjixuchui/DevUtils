package afkt.push.ui.activity

import afkt.push.R
import afkt.push.base.BaseActivity
import afkt.push.databinding.ActivityMessageBinding
import afkt.push.jpush.PushMessage
import afkt.push.jpush.toJsonFormat
import dev.utils.DevFinal

/**
 * detail: 推送消息 Activity
 * @author Ttt
 * 点击推送后根据, 推送消息跳转对应的路由页
 */
class MessageActivity : BaseActivity<ActivityMessageBinding>() {

    override fun isToolBar(): Boolean = true

    override fun baseLayoutId(): Int = R.layout.activity_message

    override fun initValue() {
        super.initValue()
        setTitle(TAG) // MessageActivity

        intent?.getParcelableExtra<PushMessage>(DevFinal.DATA)?.run {
            binding.vidAmMessageTv.text = toJsonFormat(this)
        }
    }
}