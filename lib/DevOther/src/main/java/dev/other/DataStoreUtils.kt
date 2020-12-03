package dev.other

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * detail: DataStore 工具类
 * @author Ttt
 * [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore
 * @see <a href="https://juejin.im/post/6881442312560803853"/>
 * [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore ( 二 )
 * @see <a href="https://juejin.im/post/6888847647802097672"/>
 * <p></p>
 * DataStore 文件存放目录: /data/data/<包名>/files/datastore
 * 仅支持 Int、String、Boolean、Float、Long、Double
 * 具体查看 [androidx.datastore.preferences.core.preferencesKey]
 */
object DataStoreUtils {

    // 日志 TAG
    val TAG = DataStoreUtils::class.java.simpleName

    // Map
    private val cacheMap = HashMap<String, InnerDataStore>()

    // 默认值
    const val INT_VALUE: Int = -1
    const val STRING_VALUE: String = ""
    const val BOOLEAN_VALUE: Boolean = false
    const val FLOAT_VALUE: Float = -1F
    const val LONG_VALUE: Long = -1L
    const val DOUBLE_VALUE: Double = -1.0

    /**
     * 获取 DataStore 操作类
     * @param context [Context]
     * @param storeName DataStore Name
     * @return [InnerDataStore]
     */
    fun get(context: Context, storeName: String?): InnerDataStore {
        var key = if (TextUtils.isEmpty(storeName)) TAG else storeName!!
        var value = cacheMap[key]
        if (value != null) return value
        value = InnerDataStore(context, key)
        cacheMap[key] = value
        return value
    }

    /**
     * 获取 DataStore 操作类
     * @param dataStore [DataStore]
     * @return [InnerDataStore]
     */
    fun get(dataStore: DataStore<Preferences>?): InnerDataStore {
        return InnerDataStore(dataStore)
    }

    /**
     * SharedPreferences 迁移到 DataStore
     * @param context [Context]
     * @param storeName DataStore Name
     * @param spNames SharedPreferences Name Array
     * @return [InnerDataStore]
     */
    @Throws(Exception::class)
    fun migrationSPToDataStore(
        context: Context,
        storeName: String,
        vararg spNames: String
    ): InnerDataStore {
        if (spNames.isEmpty()) throw Exception("spNames size is zero")

        var lists = ArrayList<DataMigration<Preferences>>()
        for (name in spNames) {
            if (!TextUtils.isEmpty(name)) {
                lists.add(SharedPreferencesMigration(context, name))
            }
        }
        // 传入 migrations 参数, 构建一个 DataStore 之后
        // 需要执行一次读或写, DataStore 才会自动合并 SharedPreference 文件内容
        var dataStore = context.createDataStore(
            name = storeName,
            migrations = lists
        )
        return InnerDataStore(dataStore)
    }

    // =========
    // = 内部类 =
    // =========

    /**
     * detail: DataStore 内部操作类
     * @author Ttt
     */
    class InnerDataStore private constructor() {

        private var dataStore: DataStore<Preferences>? = null

        constructor(context: Context, storeName: String) : this() {
            this.dataStore = context.createDataStore(
                name = storeName
            )
        }

        constructor(dataStore: DataStore<Preferences>?) : this() {
            this.dataStore = dataStore
        }

        // ===========
        // = 内部方法 =
        // ===========

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        private suspend fun <T> _put(key: Key<T>, value: T) {
            dataStore?.edit { mutablePreferences ->
                mutablePreferences[key] = value
            }
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 不存在 key 返回默认 Value
         * @return [Flow]
         */
        private fun <T> _get(key: Key<T>, defaultValue: T): Flow<T>? {
            return dataStore?.data?.catch {
                // 当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences，来重新使用
                // 但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }?.map { preferences -> preferences[key] ?: defaultValue }
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 获取 DataStore
         * @return [DataStore]
         */
        fun getDataStore(): DataStore<Preferences>? {
            return dataStore
        }

        // =======
        // = 存储 =
        // =======

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Int) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Int>, value: Int) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: String) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<String>, value: String) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Boolean) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Boolean>, value: Boolean) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Float) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Float>, value: Float) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Long) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Long>, value: Long) {
            _put(key = key, value = value)
        }

        /**
         * 保存数据
         * @param key Key
         * @param value Value
         */
        suspend fun put(key: String, value: Double) {
            _put(key = preferencesKey(key), value = value)
        }

        /**
         * 保存数据
         * @param key [Preferences.Key]
         * @param value Value
         */
        suspend fun put(key: Key<Double>, value: Double) {
            _put(key = key, value = value)
        }

        // =======
        // = 读取 =
        // =======

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getInt(key: String): Flow<Int>? {
            return _get(key = preferencesKey(key), defaultValue = INT_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getInt(key: Key<Int>): Flow<Int>? {
            return _get(key = key, defaultValue = INT_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getInt(key: String, defaultValue: Int): Flow<Int>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getInt(key: Key<Int>, defaultValue: Int): Flow<Int>? {
            return _get(key = key, defaultValue = defaultValue)
        }

        // =

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getString(key: String): Flow<String>? {
            return _get(key = preferencesKey(key), defaultValue = STRING_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getString(key: Key<String>): Flow<String>? {
            return _get(key = key, defaultValue = STRING_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getString(key: String, defaultValue: String): Flow<String>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getString(key: Key<String>, defaultValue: String): Flow<String>? {
            return _get(key = key, defaultValue = defaultValue)
        }

        // =

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getBoolean(key: String): Flow<Boolean>? {
            return _get(key = preferencesKey(key), defaultValue = BOOLEAN_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getBoolean(key: Key<Boolean>): Flow<Boolean>? {
            return _get(key = key, defaultValue = BOOLEAN_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getBoolean(key: Key<Boolean>, defaultValue: Boolean): Flow<Boolean>? {
            return _get(key = key, defaultValue = defaultValue)
        }

        // =

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getFloat(key: String): Flow<Float>? {
            return _get(key = preferencesKey(key), defaultValue = FLOAT_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getFloat(key: Key<Float>): Flow<Float>? {
            return _get(key = key, defaultValue = FLOAT_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getFloat(key: String, defaultValue: Float): Flow<Float>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getFloat(key: Key<Float>, defaultValue: Float): Flow<Float>? {
            return _get(key = key, defaultValue = defaultValue)
        }

        // =

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getLong(key: String): Flow<Long>? {
            return _get(key = preferencesKey(key), defaultValue = LONG_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getLong(key: Key<Long>): Flow<Long>? {
            return _get(key = key, defaultValue = LONG_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getLong(key: String, defaultValue: Long): Flow<Long>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getLong(key: Key<Long>, defaultValue: Long): Flow<Long>? {
            return _get(key = key, defaultValue = defaultValue)
        }

        // =

        /**
         * 获取数据
         * @param key Key
         * @return [Flow]
         */
        fun getDouble(key: String): Flow<Double>? {
            return _get(key = preferencesKey(key), defaultValue = DOUBLE_VALUE)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @return [Flow]
         */
        fun getDouble(key: Key<Double>): Flow<Double>? {
            return _get(key = key, defaultValue = DOUBLE_VALUE)
        }

        /**
         * 获取数据
         * @param key Key
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getDouble(key: String, defaultValue: Double): Flow<Double>? {
            return _get(key = preferencesKey(key), defaultValue = defaultValue)
        }

        /**
         * 获取数据
         * @param key [Preferences.Key]
         * @param defaultValue 默认 Value
         * @return [Flow]
         */
        fun getDouble(key: Key<Double>, defaultValue: Double): Flow<Double>? {
            return _get(key = key, defaultValue = defaultValue)
        }
    }
}