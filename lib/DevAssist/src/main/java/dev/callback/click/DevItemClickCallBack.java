package dev.callback.click;

import dev.callback.AbstractCommonCallBack;

/**
 * detail: 通用 Item Click 回调
 * @author Ttt
 */
public class DevItemClickCallBack<T> extends AbstractCommonCallBack<T> {

    public DevItemClickCallBack() {
    }

    public DevItemClickCallBack(T value) {
        super(value);
    }

    public DevItemClickCallBack(T value, Object object) {
        super(value, object);
    }

    public DevItemClickCallBack(T value, Object object, String tag) {
        super(value, object, tag);
    }

    // ===========
    // = 通用方法 =
    // ===========

    /**
     * 点击 Item 回调
     */
    public void onItemClick() {
    }

    /**
     * 长按 Item 回调
     */
    public void onItemLongClick() {
    }

    // =

    /**
     * 点击 Item 回调
     * @param param 回调参数
     */
    public void onItemClick(int param) {
    }

    /**
     * 长按 Item 回调
     * @param param 回调参数
     */
    public void onItemLongClick(int param) {
    }

    // =

    /**
     * 点击 Item 回调
     * @param param 回调参数
     * @param value 回调值
     */
    public void onItemClick(int param, T value) {
    }

    /**
     * 长按 Item 回调
     * @param param 回调参数
     * @param value 回调值
     */
    public void onItemLongClick(int param, T value) {
    }

    // =

    /**
     * 点击 Item 回调
     * @param param   回调参数
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void onItemClick(int param, T value, Object[] objects) {
    }

    /**
     * 长按 Item 回调
     * @param param   回调参数
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void onItemLongClick(int param, T value, Object[] objects) {
    }
}