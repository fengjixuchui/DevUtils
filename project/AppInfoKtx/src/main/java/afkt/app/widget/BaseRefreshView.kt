package afkt.app.widget

import afkt.app.R
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * detail: Base Refresh、Load View
 * @author Ttt
 */
class BaseRefreshView : LinearLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private var body: FrameLayout? = null
    var smartRefreshLayout: SmartRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    var adapter: BaseQuickAdapter<*, *>? = null

    /**
     * 默认初始化操作
     */
    private fun init() {
        orientation = VERTICAL
        // 初始化 View
        val view = LayoutInflater.from(context).inflate(R.layout.base_refresh_view, null)
        body = view.findViewById(R.id.vid_brv_frame)
        smartRefreshLayout = view.findViewById(R.id.vid_brv_refresh)
        recyclerView = view.findViewById(R.id.vid_brv_recy)

        smartRefreshLayout!!.let {
            // 设置刷新 View
            it.setRefreshHeader(ClassicsHeader(context))
            // 打开刷新、禁止加载
            it.setEnableRefresh(true)
            it.setEnableLoadMore(false)
            // 不需要阻尼效果
            it.setEnableOverScrollDrag(false)
        }
        addView(
            view,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
    // ===========
    // = get/set =
    // ===========

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?): BaseRefreshView {
        recyclerView!!.layoutManager = layoutManager
        return this
    }

    fun <T : BaseQuickAdapter<*, *>?> getAdapterT(): T? {
        return adapter as T?
    }

    fun setAdapter(adapter: BaseQuickAdapter<*, *>?): BaseRefreshView {
        if (adapter != null) {
            this.adapter = adapter
            recyclerView!!.adapter = adapter
        }
        return this
    }

    // ============
    // = 对外公开 =
    // ============

    /**
     * 是否启用下拉刷新 ( 默认启用 )
     * @param enabled 是否启用
     * @return [BaseRefreshView]
     */
    fun setEnableRefresh(enabled: Boolean): BaseRefreshView {
        smartRefreshLayout!!.setEnableRefresh(enabled)
        return this
    }

    /**
     * 设置是否启用上拉加载更多 ( 默认启用 )
     * @param enabled 是否启用
     * @return [BaseRefreshView]
     */
    fun setEnableLoadMore(enabled: Boolean): BaseRefreshView {
        smartRefreshLayout!!.setEnableLoadMore(enabled)
        return this
    }

    /**
     * 设置数据全部加载完成, 将不能再次触发加载功能
     * @param noMoreData 是否有更多数据 `true` 无数据, `false` 还有数据
     * @return [BaseRefreshView]
     */
    fun setNoMoreData(noMoreData: Boolean): BaseRefreshView {
        smartRefreshLayout!!.setNoMoreData(noMoreData)
        return this
    }

    /**
     * 单独设置刷新监听器
     * @param listener OnRefreshListener 刷新监听器
     * @return [BaseRefreshView]
     */
    fun setOnRefreshListener(listener: OnRefreshListener?): BaseRefreshView {
        if (listener != null) smartRefreshLayout!!.setOnRefreshListener(listener)
        return this
    }

    /**
     * 单独设置加载监听器
     * @param listener OnLoadMoreListener 加载监听器
     * @return [BaseRefreshView]
     */
    fun setOnLoadMoreListener(listener: OnLoadMoreListener?): BaseRefreshView {
        if (listener != null) smartRefreshLayout!!.setOnLoadMoreListener(listener)
        return this
    }

    /**
     * 同时设置刷新和加载监听器
     * @param listener OnRefreshLoadMoreListener 刷新加载监听器
     * @return [BaseRefreshView]
     */
    fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener?): BaseRefreshView {
        if (listener != null) smartRefreshLayout!!.setOnRefreshLoadMoreListener(listener)
        return this
    }

    /**
     * 完成刷新
     * @return [BaseRefreshView]
     */
    fun finishRefresh(): BaseRefreshView {
        if (smartRefreshLayout!!.isRefreshing) smartRefreshLayout!!.finishRefresh()
        return this
    }

    /**
     * 完成加载
     * @return [BaseRefreshView]
     */
    fun finishLoadMore(): BaseRefreshView {
        smartRefreshLayout!!.finishLoadMore()
        return this
    }

    /**
     * 完成刷新、加载
     * @return [BaseRefreshView]
     */
    fun finishRefreshAndLoad(): BaseRefreshView {
        return finishRefresh().finishLoadMore()
    }

    /**
     * 完成刷新或加载
     * @param refresh 是否刷新
     * @return [BaseRefreshView]
     */
    fun finishRefreshOrLoad(refresh: Boolean): BaseRefreshView {
        return if (refresh) finishRefresh() else finishLoadMore()
    }

    /**
     * 获取添加头部 View 数量
     * @return 头部 View 数量
     */
    val headerLayoutCount: Int
        get() = if (adapter != null) adapter!!.headerLayoutCount else 0

    /**
     * 添加头部 View
     * @param header header View
     * @return [BaseRefreshView]
     */
    fun addHeaderView(header: View?): BaseRefreshView {
        if (adapter != null) adapter!!.addHeaderView(header!!)
        return this
    }

    /**
     * 添加头部 View 到指定位置
     * @param header header View
     * @param index  index
     * @return [BaseRefreshView]
     */
    fun addHeaderView(header: View?, index: Int): BaseRefreshView {
        if (adapter != null && index >= 0) adapter!!.addHeaderView(header!!, index)
        return this
    }

    /**
     * 获取添加底部 View 数量
     * @return 底部 View 数量
     */
    val footerLayoutCount: Int
        get() = if (adapter != null) adapter!!.footerLayoutCount else 0

    /**
     * 添加底部 View
     * @param header header View
     * @return [BaseRefreshView]
     */
    fun addFooterView(header: View?): BaseRefreshView {
        if (adapter != null) adapter!!.addFooterView(header!!)
        return this
    }

    /**
     * 添加底部 View 到指定位置
     * @param header header View
     * @param index  index
     * @return [BaseRefreshView]
     */
    fun addFooterView(header: View?, index: Int): BaseRefreshView {
        if (adapter != null && index >= 0) adapter!!.addFooterView(header!!, index)
        return this
    }
}