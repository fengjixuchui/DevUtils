package afkt.demo.use.datastore

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import dev.other.DataStoreUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.share.SPUtils
import kotlinx.coroutines.flow.first

object DataStoreUse {

    val TAG: String = DataStoreUse::class.java.simpleName

    private const val spStoreName = "spStore"

    suspend fun use(activity: AppCompatActivity) {
        // 监听数据变化
        listener(activity)
        // 写入数据
        write(activity)
        // 读取数据
        read(activity)
    }

    /**
     * 写入数据
     * @param context [Context]
     */
    private suspend fun write(context: Context) {

        // 首先进行 SP 数据存储存储
        // 会在 /data/data/afkt.demo/shared_prefs/ 创建 **.xml
        // 接着运行 migrationSPToDataStore 则会把 shared_prefs 文件夹内的 xml
        // 存储到 /data/data/afkt.demo/files/datastore/ 中指定的 **.preferences_pb
        // 并且会把 shared_prefs 下迁移成功的文件进行删除

        SPUtils.getPreference(context, "AA").put("type", "AA")

        SPUtils.getPreference(context, "AA").put("errorType", "AA")

        SPUtils.getPreference(context, "BB").put("type", "BB")

        SPUtils.getPreference(context, "BB").put("errorType", 1)

        SPUtils.getPreference(context, "BB").put("abc", "def")

        var dataStore = DataStoreUtils.migrationSPToDataStore(
            spStoreName, "AA", "BB"
        )

        dataStore.put("one", 1)

        DataStoreUtils.get(spStoreName).put("two", "二")

        // =======
        // = TAG =
        // =======

        DataStoreUtils.get(TAG).put("int", 9)

        DataStoreUtils.get(TAG).put("String", "xx")

        DataStoreUtils.get(TAG).put("boolean", true)

        DataStoreUtils.get(TAG).put("float", 0.48791F)

        DataStoreUtils.get(TAG).put("long", 555L)

        DataStoreUtils.get(TAG).put("double", 1.2312)
    }

    /**
     * 读取数据
     * @param activity [Activity]
     */
    private suspend fun read(activity: AppCompatActivity) {
        DevLogger.dTag(
            TAG, "getFlow %s, key : %s, value : %s",
            TAG, "aaaaa", DataStoreUtils.get(TAG).getStringFlow("aaaaa", "不存在该 key 返回指定值")?.first()
        )

        DevLogger.dTag(
            TAG, "getFlow %s, key : %s, value : %s",
            TAG, "double", DataStoreUtils.get(TAG).getDoubleFlow("double")?.first()
        )

        DevLogger.dTag(
            TAG, "getFlow %s, key : %s, value : %s",
            spStoreName, "type", DataStoreUtils.get(spStoreName).getStringFlow("type")?.first()
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            spStoreName, "errorType", DataStoreUtils.get(spStoreName).getString("errorType")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            spStoreName, "errorType", DataStoreUtils.get(spStoreName).getInt("errorType")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            spStoreName, "one", DataStoreUtils.get(spStoreName).getInt("one")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            spStoreName, "two", DataStoreUtils.get(spStoreName).getString("two")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            spStoreName, "abc", DataStoreUtils.get(spStoreName).getString("abc")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            TAG, "double", DataStoreUtils.get(TAG).getDouble("double")
        )

        DevLogger.dTag(
            TAG, "getValue %s, key : %s, value : %s",
            TAG, "double", DataStoreUtils.get(TAG).getBoolean("double")
        )
    }

    /**
     * 监听数据变化
     * @param activity [Activity]
     */
    private fun listener(activity: AppCompatActivity) {
        /**
         * 监听 [TAG] DataStore key "int" 值变化
         */
        DataStoreUtils.get(TAG).getIntFlow("int")?.let {
            it.asLiveData().observe(activity) { value ->
                DevLogger.dTag(
                    TAG, "listener %s, key : %s, value : %s",
                    TAG, "int", value
                )
            }
        }
        /**
         * 监听 [spStoreName] DataStore key "userName" 值变化
         */
        DataStoreUtils.get(spStoreName).getStringFlow("type")?.let {
            it.asLiveData().observe(activity) { value ->
                DevLogger.dTag(
                    TAG, "listener %s, key : %s, value : %s",
                    spStoreName, "type", value
                )
            }
        }
    }
}