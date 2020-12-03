package afkt.demo.use.datastore

import android.content.Context
import dev.other.DataStoreUtils
import dev.utils.app.share.SPUtils
import dev.utils.app.share.SharedUtils

object DataStoreUse {

    suspend fun use(context: Context) {
        // SPConfig
        SharedUtils.put("userName", "u123456")

        SharedUtils.put("tips", "没有密码")

        SPUtils.getPreference(context, "AA").put("type", "AA")

        SPUtils.getPreference(context, "BB").put("typeB", "BB")

        SPUtils.getPreference(context, "CC").put("typeC", "CC")

        SPUtils.getPreference(context, "DD").put("type", "DD")

        SPUtils.getPreference(context, "DD").put("abc", "abc")

//        var dataStore = DataStoreUtils.migrationSPToDataStore(
//            context, "store",
//            "AA", "BB", "CC", "DD"
//        )
//
//        dataStore.put("value", 1)

//        DataStoreUtils.get(context, "store").put("key", "vk")
//
//        // ==========
//        // = store1 =
//        // ==========
//
//        DataStoreUtils.get(context, "store1").put("int", 9)
//
//        DataStoreUtils.get(context, "store1").put("String", "xx")
//
//        DataStoreUtils.get(context, "store1").put("boolean", true)
//
//        DataStoreUtils.get(context, "store1").put("float", 0.48791F)
//
//        DataStoreUtils.get(context, "store1").put("long", 555L)
//
//        DataStoreUtils.get(context, "store1").put("double", 1.2312)
    }
}