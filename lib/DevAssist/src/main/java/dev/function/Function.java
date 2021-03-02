package dev.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.base.DevObject;
import dev.utils.LogPrintUtils;
import dev.utils.common.thread.DevThreadPool;

/**
 * detail: 执行方法类
 * @author Ttt
 */
public final class Function {

    private Function() {
    }

    // 默认线程池对象
    private static final ExecutorService sThreadPool = Executors.newFixedThreadPool(DevThreadPool.getCalcThreads());

    /**
     * detail: 方法体
     * @author Ttt
     */
    public interface Method {

        void method(Operation operation);
    }

    /**
     * detail: 方法体 ( 存在异常触发 )
     * @author Ttt
     * <pre>
     *     前提属于调用 try-catch 方法
     * </pre>
     */
    public interface Method2
            extends Method {

        void error(
                Operation operation,
                Throwable error
        );
    }

    // =========
    // = 包装类 =
    // =========

    /**
     * detail: Function 操作包装类
     * @author Ttt
     */
    public static final class Operation {

        // 日志 TAG
        private final String    TAG;
        // 存储数据
        private       DevObject mObject;

        public Operation() {
            this(Operation.class.getSimpleName());
        }

        public Operation(final String tag) {
            this.TAG = tag;
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 获取 Object
         * @return {@link DevObject}
         */
        public DevObject getObject() {
            return mObject;
        }

        /**
         * 设置 Object
         * @param object {@link DevObject}
         * @return {@link Operation}
         */
        public Operation setObject(final DevObject object) {
            this.mObject = object;
            return this;
        }

        // =

        /**
         * 获取 Operation
         * @return {@link Operation}
         */
        public Operation operation() {
            return new Operation();
        }

        /**
         * 获取 Operation
         * @param tag 日志 TAG
         * @return {@link Operation}
         */
        public Operation operation(final String tag) {
            return new Operation(tag);
        }

        // ===========
        // = 捕获异常 =
        // ===========

        /**
         * 捕获异常处理
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation tryCatch(final Method method) {
            return tryCatch(TAG, method);
        }

        /**
         * 捕获异常处理
         * @param tag    输出日志
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation tryCatch(
                final String tag,
                final Method method
        ) {
            if (method != null) {
                try {
                    method.method(Operation.this);
                } catch (Throwable e) {
                    LogPrintUtils.eTag(tag, e, "tryCatch");
                    if (method instanceof Method2) {
                        ((Method2) method).error(Operation.this, e);
                    }
                }
            }
            return this;
        }

        // ===========
        // = 线程方法 =
        // ===========

        /**
         * 后台线程执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation thread(final Method method) {
            if (method != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        method.method(Operation.this);
                    }
                }).start();
            }
            return this;
        }

        /**
         * 后台线程池执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPool(final Method method) {
            return threadPool(sThreadPool, method);
        }

        /**
         * 后台线程池执行
         * @param pool   线程池
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPool(
                final ExecutorService pool,
                final Method method
        ) {
            if (pool != null && method != null) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        method.method(Operation.this);
                    }
                });
            }
            return this;
        }

        // ==================
        // = 线程捕获异常方法 =
        // ==================

        /**
         * 后台线程执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadCatch(final Method method) {
            return threadCatch(TAG, method);
        }

        /**
         * 后台线程执行
         * @param tag    日志 TAG
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadCatch(
                final String tag,
                final Method method
        ) {
            if (method != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.method(Operation.this);
                        } catch (Throwable e) {
                            LogPrintUtils.eTag(tag, e, "threadCatch");
                            if (method instanceof Method2) {
                                ((Method2) method).error(Operation.this, e);
                            }
                        }
                    }
                }).start();
            }
            return this;
        }

        // =

        /**
         * 后台线程池执行
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(final Method method) {
            return threadPoolCatch(TAG, sThreadPool, method);
        }

        /**
         * 后台线程池执行
         * @param tag    日志 TAG
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final String tag,
                final Method method
        ) {
            return threadPoolCatch(tag, sThreadPool, method);
        }

        /**
         * 后台线程池执行
         * @param pool   线程池
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final ExecutorService pool,
                final Method method
        ) {
            return threadPoolCatch(TAG, pool, method);
        }

        /**
         * 后台线程池执行
         * @param tag    日志 TAG
         * @param pool   线程池
         * @param method 执行方法
         * @return {@link Operation}
         */
        public Operation threadPoolCatch(
                final String tag,
                final ExecutorService pool,
                final Method method
        ) {
            if (pool != null && method != null) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.method(Operation.this);
                        } catch (Throwable e) {
                            LogPrintUtils.eTag(tag, e, "threadPoolCatch");
                            if (method instanceof Method2) {
                                ((Method2) method).error(Operation.this, e);
                            }
                        }
                    }
                });
            }
            return this;
        }
    }

    // ===========
    // = 快捷方法 =
    // ===========

    // ===========
    // = 捕获异常 =
    // ===========

    /**
     * 捕获异常处理
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation tryCatch(final Method method) {
        return new Operation().tryCatch(method);
    }

    /**
     * 捕获异常处理
     * @param tag    输出日志
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation tryCatch(
            final String tag,
            final Method method
    ) {
        return new Operation().tryCatch(tag, method);
    }

    // ===========
    // = 线程方法 =
    // ===========

    /**
     * 后台线程执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation thread(final Method method) {
        return new Operation().thread(method);
    }

    /**
     * 后台线程池执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPool(final Method method) {
        return new Operation().threadPool(method);
    }

    /**
     * 后台线程池执行
     * @param pool   线程池
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPool(
            final ExecutorService pool,
            final Method method
    ) {
        return new Operation().threadPool(pool, method);
    }

    // ==================
    // = 线程捕获异常方法 =
    // ==================

    /**
     * 后台线程执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadCatch(final Method method) {
        return new Operation().threadCatch(method);
    }

    /**
     * 后台线程执行
     * @param tag    日志 TAG
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadCatch(
            final String tag,
            final Method method
    ) {
        return new Operation().threadCatch(tag, method);
    }

    // =

    /**
     * 后台线程池执行
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(final Method method) {
        return new Operation().threadPoolCatch(method);
    }

    /**
     * 后台线程池执行
     * @param tag    日志 TAG
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final String tag,
            final Method method
    ) {
        return new Operation().threadPoolCatch(tag, method);
    }

    /**
     * 后台线程池执行
     * @param pool   线程池
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final ExecutorService pool,
            final Method method
    ) {
        return new Operation().threadPoolCatch(pool, method);
    }

    /**
     * 后台线程池执行
     * @param tag    日志 TAG
     * @param pool   线程池
     * @param method 执行方法
     * @return {@link Operation}
     */
    public static Operation threadPoolCatch(
            final String tag,
            final ExecutorService pool,
            final Method method
    ) {
        return new Operation().threadPoolCatch(tag, pool, method);
    }
}