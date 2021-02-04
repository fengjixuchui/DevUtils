package afkt.app.ui.activity

import afkt.app.R
import afkt.app.base.BaseActivity
import afkt.app.base.model.ActionEnum
import afkt.app.base.model.AppListBean
import afkt.app.base.model.SearchEvent
import afkt.app.base.model.TypeEnum
import afkt.app.databinding.ActivityMainBinding
import afkt.app.ui.fragment.AppListFragment
import afkt.app.ui.fragment.InfoFragment
import afkt.app.ui.fragment.ScanSDCardFragment
import afkt.app.ui.fragment.SettingFragment
import afkt.app.utils.AppListUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dev.callback.DevCallback
import dev.utils.app.ClickUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.DelayAssist
import dev.utils.app.toast.ToastTintUtils

class MainActivity : BaseActivity<ActivityMainBinding>(),
    View.OnClickListener {

    private var searchView: SearchView? = null

    private var mFragments: HashMap<TypeEnum, Fragment> = HashMap()

    // 当前 Fragment 类型
    private var mFragmentType = TypeEnum.NONE

    // 默认 Fragment Type
    private val DISPLAY_FRAGMENT_TYPE = TypeEnum.APP_USER

    // 延迟触发辅助类
    private val searchAssist = DelayAssist(350) {
        searchView?.let {
            val search = SearchEvent(mFragmentType, ActionEnum.CONTENT)
            search.content = it.query.toString()
            viewModel.postSearch(search)
        }
    }

    override fun baseContentId(): Int = R.layout.activity_main

    override fun initValue() {
        super.initValue()
        // APP 搜索回调
        AppListUtils.setCallback(object : DevCallback<AppListBean>() {
            override fun callback(value: AppListBean?) {
                value?.let {
                    when (it.type) {
                        TypeEnum.APP_USER -> {
                            viewModel.postUserApp(value)
                        }
                        TypeEnum.APP_SYSTEM -> {
                            viewModel.postSystemApp(value)
                        }
                        else -> {

                        }
                    }
                }
            }
        })
        initFragment()
        // 设置标题
        binding.vidAmNavView.setCheckedItem(getNavItemId())
        binding.vidAmToolbar.setTitle(DISPLAY_FRAGMENT_TYPE.titleId)
        binding.vidAmTopBtn.setOnClickListener {
            viewModel.postBackTop(mFragmentType)
        }
        // 设置侧边栏
        setSupportActionBar(binding.vidAmToolbar)
        // 设置切换动画事件等
        val toggle = ActionBarDrawerToggle(
            this, binding.vidAmDrawerLayout, binding.vidAmToolbar,
            R.string.str_navigation_drawer_open,
            R.string.str_navigation_drawer_close
        )
        binding.vidAmDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // 显示 Fragment Type
        toggleFragment(DISPLAY_FRAGMENT_TYPE)
    }

    override fun initListener() {
        super.initListener()
        // 设置 NavigationView Item 点击事件
        binding.vidAmNavView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_user_apps -> toggleFragment(TypeEnum.APP_USER)
                R.id.nav_system_apps -> toggleFragment(TypeEnum.APP_SYSTEM)
                R.id.nav_phone_info -> toggleFragment(TypeEnum.DEVICE_INFO)
                R.id.nav_screen_info -> toggleFragment(TypeEnum.SCREEN_INFO)
                R.id.nav_query_apk -> toggleFragment(TypeEnum.QUERY_APK)
                R.id.nav_setting -> toggleFragment(TypeEnum.SETTING)
            }
            binding.vidAmDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun initFragment() {
        mFragments.putAll(
            mapOf(
                // 用户应用信息
                TypeEnum.APP_USER to AppListFragment.get(TypeEnum.APP_USER),
                // 系统应用信息
                TypeEnum.APP_SYSTEM to AppListFragment.get(TypeEnum.APP_SYSTEM),
                // 设备信息
                TypeEnum.DEVICE_INFO to InfoFragment.get(TypeEnum.DEVICE_INFO),
                // 屏幕信息
                TypeEnum.SCREEN_INFO to InfoFragment.get(TypeEnum.SCREEN_INFO),
                // 扫描 SDK
                TypeEnum.QUERY_APK to ScanSDCardFragment(),
                // 设置
                TypeEnum.SETTING to SettingFragment()
            )
        )

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (item in mFragments.values) {
            transaction.add(R.id.vid_am_linear, item, item.toString()).hide(item)
        }
        transaction.commit()
    }

    /**
     * 切换 Fragment
     * @param type Fragment Type
     */
    private fun toggleFragment(type: TypeEnum) {
        if (type != mFragmentType) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            // 隐藏当前显示的 Fragment
            mFragments[mFragmentType]?.let { transaction.hide(it) }
            // 准备显示的 Fragment
            mFragments[type]?.let { transaction.show(it).commit() }
            // 保存 type
            mFragmentType = type

            // 回到顶部按钮控制
            when (type) {
                TypeEnum.APP_USER, TypeEnum.APP_SYSTEM, TypeEnum.QUERY_APK -> {
                    ViewUtils.setVisibility(true, binding.vidAmTopBtn)
                }
                else -> ViewUtils.setVisibility(false, binding.vidAmTopBtn)
            }
            // 通知系统更新菜单
            invalidateOptionsMenu()
            // 发送 Fragment 切换通知事件
            viewModel.postFragmentChange(type)
            // 设置标题
            binding.vidAmToolbar.setTitle(type.titleId)
            binding.vidAmNavView.setCheckedItem(getNavItemId())
            // 失去焦点 ( 解决存在键盘, 点击侧边导航栏切换, 软键盘还存在 )
            searchView?.clearFocus()
        }
    }

    private fun getNavItemId(): Int {
        return when (DISPLAY_FRAGMENT_TYPE) {
            TypeEnum.APP_USER -> R.id.nav_user_apps
            TypeEnum.APP_SYSTEM -> R.id.nav_system_apps
            TypeEnum.DEVICE_INFO -> R.id.nav_phone_info
            TypeEnum.SCREEN_INFO -> R.id.nav_screen_info
            TypeEnum.QUERY_APK -> R.id.nav_query_apk
            TypeEnum.SETTING -> R.id.nav_setting
            else -> R.id.nav_user_apps
        }
    }

    // ========
    // = Back =
    // ========

    override fun onBackPressed() {
        // 如果显示了侧边栏, 则关闭
        if (binding.vidAmDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.vidAmDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            searchView?.let {
                if (!it.isIconified) {
                    if (it.query.toString().isNotEmpty()) {
                        it.setQuery("", false)
                    }
                    it.isIconified = true
                    return
                }
            }
            // 判断是否默认 Fragment Type 不是的话, 则进行切换
            if (mFragmentType != DISPLAY_FRAGMENT_TYPE) {
                toggleFragment(DISPLAY_FRAGMENT_TYPE)
                return
            }
            if (ClickUtils.isFastDoubleClick(R.string.str_click_return)) {
                super.onBackPressed()
            } else {
                ToastTintUtils.info(ResourceUtils.getString(R.string.str_click_return))
                return
            }
        }
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menu_apps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 准备显示 Menu
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        when (mFragmentType) {
            TypeEnum.DEVICE_INFO, TypeEnum.SCREEN_INFO -> menuInflater.inflate(
                R.menu.bar_menu_device, menu
            )
            TypeEnum.APP_USER, TypeEnum.APP_SYSTEM, TypeEnum.QUERY_APK -> {
                menuInflater.inflate(R.menu.bar_menu_apps, menu)
                // 初始化搜索操作
                initSearchOperate(menu)
            }
            else -> {
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bma_refresh -> viewModel.postRefresh(mFragmentType)
            R.id.bmd_export_item -> viewModel.postExportEvent(mFragmentType)
        }
        return true
    }

    // 初始化搜索操作
    private fun initSearchOperate(menu: Menu) {
        val searchItem = menu.findItem(R.id.bma_search)
        // 初始化搜索 View
        searchView = searchItem.actionView as SearchView
        searchView?.let {
            // 默认提示
            it.queryHint = getString(R.string.str_input_packname_aname_query)
            // 搜索框展开时后面 X 按钮点击事件
            it.setOnCloseListener {
                searchAssist.remove()
                // 发送搜索合并通知事件
                viewModel.postSearch(SearchEvent(mFragmentType, ActionEnum.COLLAPSE))
                false
            }
            // 搜索图标按钮 ( 打开搜索框的按钮 ) 点击事件
            it.setOnSearchClickListener {
                searchAssist.remove()
                // 发送搜索展开通知事件
                viewModel.postSearch(SearchEvent(mFragmentType, ActionEnum.EXPAND))
            }
            // 搜索文本监听
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // 当点击搜索按钮时触发该方法
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                // 当搜索内容改变时触发该方法
                override fun onQueryTextChange(newText: String): Boolean {
                    searchAssist.post()
                    return false
                }
            })
        }
    }
}