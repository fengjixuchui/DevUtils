package dev.engine.image.listener;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * detail: 转换图片格式回调接口
 * @author Ttt
 */
public interface OnConvertListener {

    /**
     * 开始转换前调用
     * @param index 当前转换索引
     * @param count 转换总数
     */
    void onStart(
            int index,
            int count
    );

    /**
     * 转换成功后调用
     * @param file  转换成功文件
     * @param index 当前转换索引
     * @param count 转换总数
     */
    void onSuccess(
            File file,
            int index,
            int count
    );

    /**
     * 当转换过程出现问题时触发
     * @param error 异常信息
     * @param index 当前转换索引
     * @param count 转换总数
     */
    void onError(
            Throwable error,
            int index,
            int count
    );

    /**
     * 转换完成 ( 转换结束 )
     * @param lists 转换成功存储 List
     * @param maps  每个索引对应转换存储地址
     * @param count 转换总数
     */
    void onComplete(
            List<File> lists,
            Map<Integer, File> maps,
            int count
    );
}