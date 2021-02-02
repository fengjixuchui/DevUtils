package afkt.push.base

import afkt.push.R
import afkt.push.router.PushRouterChecker
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import dev.base.expand.content.DevBaseContentViewBindingActivity
import dev.utils.app.ViewUtils

abstract class BaseActivity<VB : ViewBinding> : DevBaseContentViewBindingActivity<VB>() {

    // ToolBar
    var toolbar: Toolbar? = null

    override fun baseLayoutView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 是否需要 ToolBar
        if (isToolBar()) initToolBar()

        initOrder()
    }

    override fun onResume() {
        super.onResume()

        // 检查推送路由
        PushRouterChecker.checker(this, this.javaClass.simpleName)
    }

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 是否需要 ToolBar
     */
    open fun isToolBar(): Boolean = false

    /**
     * 初始化 ToolBar
     */
    private fun initToolBar() {
        val titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null)
        toolbar = titleView.findViewById(R.id.vid_bt_toolbar)
        contentAssist.addTitleView(titleView)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
        }
        // 设置点击事件
        toolbar?.setNavigationOnClickListener { finish() }
    }

    /**
     * 设置 ToolBar 标题
     * @param title 标题
     */
    fun setTitle(title: String?) {
        toolbar?.title = title
    }
}