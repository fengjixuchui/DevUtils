package dev.base.mvp

/**
 * detail: MVP Contract 类
 * @author Ttt
 */
class MVP private constructor() {

    /**
     * detail: MVP 模式的 Model ( 通常作为获取数据 )
     * @author Ttt
     */
    interface IModel

    /**
     * detail: MVP 模式的 View ( 通过 Presenter 将数据传入到该层, 负责 View 的展示相关 )
     * @author Ttt
     */
    interface IView

    /**
     * detail: MVP 模式 P 层接口类
     * @author Ttt
     */
    interface IPresenter<V : IView> {

        /**
         * 设置 View 层与 P 层 关联持有
         * @param view [IView]
         */
        fun attachView(view: V)

        /**
         * 销毁 View 与 P 层 关联关系
         */
        fun detachView()
    }

    /**
     * detail: MVP 模式的指挥者 ( 连接 View 和 Model)
     * @author Ttt
     */
    open class Presenter<V : IView, M : IModel> : IPresenter<V> {

        // IView
        @JvmField
        protected var mView: V? = null

        // IModel
        @JvmField
        protected var mModel: M? = null

        override fun attachView(view: V) {
            mView = view
        }

        override fun detachView() {
            mView = null
        }
    }
}