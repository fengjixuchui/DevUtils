package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.module.ActionEnum
import afkt.app.module.TypeEnum
import afkt.app.module.event.*
import afkt.app.ui.adapter.ApkListAdapter
import afkt.app.utils.AppSearchUtils
import afkt.app.utils.EventBusUtils
import afkt.app.utils.ScanSDCardUtils
import afkt.app.widget.BaseRefreshView
import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.tt.whorlviewlibrary.WhorlView
import dev.utils.app.ListViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.ViewUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.permission.PermissionUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils
import dev.utils.common.HtmlUtils
import dev.utils.common.StringUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class ScanSDCardFragment : BaseFragment() {

    // = View =

    @JvmField
    @BindView(R.id.vid_fa_refresh)
    var baseRefreshView: BaseRefreshView? = null

    @JvmField
    @BindView(R.id.vid_fa_state)
    var stateLayout: StateLayout? = null

    var whorlView: WhorlView? = null

    // = Object =

    var type = TypeEnum.QUERY_APK
    var searchContent: String = ""

    override fun layoutId(): Int {
        return R.layout.fragment_app
    }

    override fun layoutInit(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view!!)

        whorlView = ViewUtils.findViewById(
            stateLayout!!.getView(ViewAssist.TYPE_ING),
            R.id.vid_sli_load_view
        )
        // 设置监听
        stateLayout!!.setListener(object : StateLayout.Listener {
            override fun onRemove(layout: StateLayout, type: Int, removeView: Boolean) {
            }

            override fun onNotFound(layout: StateLayout, type: Int) {
                if (type == ViewAssist.TYPE_SUCCESS) {
                    ViewUtils.reverseVisibilitys(true, baseRefreshView, stateLayout)
                    whorlView?.stop()
                    baseRefreshView?.finishRefresh()
                }
            }

            override fun onChange(layout: StateLayout, type: Int, oldType: Int, view: View) {
                if (ViewUtils.reverseVisibilitys(
                        type == ViewAssist.TYPE_SUCCESS,
                        baseRefreshView, stateLayout
                    )
                ) {
                    whorlView?.stop()
                    baseRefreshView?.finishRefresh()
                } else {
                    if (type == ViewAssist.TYPE_ING) {
                        if (!whorlView!!.isCircling()) {
                            whorlView?.start()
                        }
                    } else {
                        whorlView?.stop()
                        // 无数据处理
                        if (type == ViewAssist.TYPE_EMPTY_DATA) {
                            baseRefreshView?.finishRefresh()
                            var tips = if (searchContent.isEmpty()) {
                                ResourceUtils.getString(R.string.str_search_noresult_tips_1)
                            } else {
                                ResourceUtils.getString(
                                    R.string.str_search_noresult_tips,
                                    HtmlUtils.addHtmlColor(searchContent, "#359AFF")
                                )
                            }
                            TextViewUtils.setHtmlText(
                                view.findViewById<TextView>(R.id.vid_slnd_tips_tv), tips
                            )
                        }
                    }
                }
            }
        })
        stateLayout!!.showIng()

        // 设置刷新事件
        baseRefreshView?.setOnRefreshListener(OnRefreshListener {
            type?.let { requestReadWrite(true) }
        })

        var itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    var adapter: ApkListAdapter? = baseRefreshView?.getAdapterT()
                    try {
                        val position = viewHolder.adapterPosition
                        FileUtils.deleteFile(adapter?.getItem(position)?.uri)
                        adapter?.removeAt(position)
                    } catch (e: Exception) {
                        DevLogger.e(e)
                    }
                    adapter?.let {
                        if (it.getDefItemCount() == 0) {
                            stateLayout?.showEmptyData()
                        }
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(baseRefreshView?.recyclerView)
    }

    // ============
    // = 事件相关 =
    // ============

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    fun onEvent(event: FragmentEvent) {
        event.type?.let {
            if (it == type) {
                searchContent = "" // 切换 Fragment 重置搜索内容
                requestReadWrite()
                EventBusUtils.removeStickyEvent(FragmentEvent::class.java)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ScanSDCardEvent) {
        var lists = if (searchContent.isEmpty()) {
            event.lists
        } else {
            AppSearchUtils.filterApkList(event.lists, searchContent)
        }
        if (lists.isEmpty()) {
            stateLayout?.showEmptyData()
        } else {
            baseRefreshView?.setAdapter(ApkListAdapter(lists))
            stateLayout?.showSuccess()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: SearchEvent) {
        event.type?.let {
            when (event.action) {
                ActionEnum.COLLAPSE -> { // 搜索合并
                    if (it == type) {
                        if (searchContent.isNotEmpty()) { // 输入内容才刷新列表
                            searchContent = ""
                            requestReadWrite()
                        }
                    }
                }
                ActionEnum.EXPAND -> { // 搜索展开
                }
                ActionEnum.CONTENT -> { // 搜索输入内容
                    if (it == type) {
                        searchContent = event.content.toString()
                        requestReadWrite()
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: RefreshEvent) {
        event.type?.let {
            if (it == type) baseRefreshView?.smartRefreshLayout?.autoRefresh()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: TopEvent) {
        event.type?.let {
            if (it == type) ListViewUtils.smoothScrollToTop(baseRefreshView?.recyclerView)
            //ListViewUtils.scrollToTop(baseRefreshView?.recyclerView)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FileDeleteEvent) {
        baseRefreshView?.recyclerView?.adapter?.notifyDataSetChanged()
    }

    // =

    /**
     * 请求读写权限
     */
    fun requestReadWrite() {
        requestReadWrite(false)
    }

    /**
     * 请求读写权限
     * @param refresh 是否刷新
     */
    fun requestReadWrite(refresh: Boolean) {
        PermissionUtils.permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).callBack(object : PermissionUtils.PermissionCallBack {
            override fun onGranted() {
                ScanSDCardUtils.instance.query(refresh) // 扫描 SDCard
            }

            override fun onDenied(
                grantedList: List<String>,
                deniedList: List<String>,
                notFoundList: List<String>
            ) {
                var builder = StringBuilder()
                    .append("申请通过的权限" + Arrays.toString(grantedList.toTypedArray()))
                    .append(StringUtils.NEW_LINE_STR)
                    .append("拒绝的权限" + deniedList.toString())
                    .append(StringUtils.NEW_LINE_STR)
                    .append("未找到的权限" + notFoundList.toString())
                if (deniedList.isNotEmpty()) {
                    DevLogger.d(builder.toString())
                    ToastTintUtils.info(ResourceUtils.getString(R.string.str_read_write_request_tips))
                } else {
                    onGranted()
                }
            }
        }).request(activity)
    }
}