package dev.engine.compress.listener;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * detail: 压缩回调接口
 * @author Ttt
 */
public interface OnCompressListener {

    /**
     * 开始压缩前调用
     * @param index 当前压缩索引
     * @param count 压缩总数
     */
    void onStart(
            int index,
            int count
    );

    /**
     * 压缩成功后调用
     * @param file  压缩成功文件
     * @param index 当前压缩索引
     * @param count 压缩总数
     */
    void onSuccess(
            File file,
            int index,
            int count
    );

    /**
     * 当压缩过程出现问题时触发
     * @param error 异常信息
     * @param index 当前压缩索引
     * @param count 压缩总数
     */
    void onError(
            Throwable error,
            int index,
            int count
    );

    /**
     * 压缩完成 ( 压缩结束 )
     * @param lists 压缩成功存储 List
     * @param maps  每个索引对应压缩存储地址
     * @param count 压缩总数
     */
    void onComplete(
            List<File> lists,
            Map<Integer, File> maps,
            int count
    );
}