package afkt.push.ui.activity

import afkt.push.R
import afkt.push.base.BaseActivity
import android.content.Intent
import androidx.viewbinding.ViewBinding
import dev.utils.app.AppUtils
import dev.utils.app.HandlerUtils

class SplashActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_splash

    override fun initOther() {
        super.initOther()
        HandlerUtils.postRunnable({
            if (isFinishing) return@postRunnable
            AppUtils.startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1200)
    }
}