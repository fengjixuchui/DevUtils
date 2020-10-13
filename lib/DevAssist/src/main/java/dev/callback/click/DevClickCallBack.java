package dev.callback.click;

import dev.callback.AbstractCommonCallBack;

/**
 * detail: 通用 Click 回调
 * @author Ttt
 */
public class DevClickCallBack<T> extends AbstractCommonCallBack<T> {

    public DevClickCallBack() {
    }

    public DevClickCallBack(T value) {
        super(value);
    }

    public DevClickCallBack(T value, Object object) {
        super(value, object);
    }

    public DevClickCallBack(T value, Object object, String tag) {
        super(value, object, tag);
    }

    // ===========
    // = 通用方法 =
    // ===========

    /**
     * 点击回调
     */
    public void onClick() {
    }

    /**
     * 长按回调
     */
    public void onLongClick() {
    }

    // =

    /**
     * 点击回调
     * @param param 回调参数
     */
    public void onClick(int param) {
    }

    /**
     * 长按回调
     * @param param 回调参数
     */
    public void onLongClick(int param) {
    }

    // =

    /**
     * 点击回调
     * @param param 回调参数
     * @param value 回调值
     */
    public void onClick(int param, T value) {
    }

    /**
     * 长按回调
     * @param param 回调参数
     * @param value 回调值
     */
    public void onLongClick(int param, T value) {
    }

    // =

    /**
     * 点击回调
     * @param param   回调参数
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void onClick(int param, T value, Object[] objects) {
    }

    /**
     * 长按回调
     * @param param   回调参数
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void onLongClick(int param, T value, Object[] objects) {
    }
}