package afkt.project.database.green.module.image;

import org.greenrobot.greendao.database.Database;

import afkt.project.database.green.able.GreenDatabase;
import gen.greendao.DaoMaster;
import gen.greendao.DaoSession;

/**
 * detail: 不同模块数据库实现, 演示类
 * @author Ttt
 * <pre>
 *     主要演示区分 note 模块包目录
 * </pre>
 */
public class ImageDatabase extends GreenDatabase {

    @Override
    public DaoMaster.OpenHelper getHelper() {
        return null;
    }

    @Override
    public Database getDatabase() {
        return null;
    }

    @Override
    public DaoMaster getDaoMaster() {
        return null;
    }

    @Override
    public DaoSession getDaoSession() {
        return null;
    }
}