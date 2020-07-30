package dev.widget.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import dev.utils.app.WidgetUtils;
import dev.widget.R;

/**
 * detail: 自定义 Gallery 滑动控制
 * @author Ttt
 * <pre>
 *     app:dev_slide=""
 *     app:dev_maxWidth=""
 *     app:dev_maxHeight=""
 * </pre>
 */
public class CustomGallery extends Gallery {

    // 是否允许滑动
    private boolean mIsSlide   = true;
    // 最大显示宽度
    private int     mMaxWidth  = WidgetUtils.DEF_VALUE;
    // 最大显示高度
    private int     mMaxHeight = WidgetUtils.DEF_VALUE;

    public CustomGallery(Context context) {
        super(context);
    }

    public CustomGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomGallery(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mIsSlide = a.getBoolean(R.styleable.DevWidget_dev_slide, true);
            mMaxWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_maxWidth, WidgetUtils.DEF_VALUE);
            mMaxHeight = a.getLayoutDimension(R.styleable.DevWidget_dev_maxHeight, WidgetUtils.DEF_VALUE);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] measureSpecs = WidgetUtils.viewMeasure(this, widthMeasureSpec, heightMeasureSpec, mMaxWidth, mMaxHeight);
        super.onMeasure(measureSpecs[0], measureSpecs[1]);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int keyCode;
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mIsSlide) return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!this.mIsSlide) return false;
        return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 判断是否向左滑动
     * @param e1 {@link MotionEvent}
     * @param e2 {@link MotionEvent}
     * @return {@code true} yes, {@code false} no
     */
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    /**
     * 获取 View 最大显示宽度
     * @return View 最大显示宽度
     */
    public int getMaxWidth() {
        return mMaxWidth;
    }

    /**
     * 设置 View 最大显示宽度
     * @param maxWidth View 最大显示宽度
     * @return {@link CustomGallery}
     */
    public CustomGallery setMaxWidth(int maxWidth) {
        this.mMaxWidth = maxWidth;
        return this;
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link CustomGallery}
     */
    public CustomGallery setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }

    /**
     * 是否允许滑动
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSlide() {
        return mIsSlide;
    }

    /**
     * 设置是否允许滑动
     * @param isSlide {@code true} yes, {@code false} no
     * @return {@link CustomGallery}
     */
    public CustomGallery setSlide(boolean isSlide) {
        this.mIsSlide = isSlide;
        return this;
    }

    /**
     * 切换滑动控制状态
     * @return {@link CustomGallery}
     */
    public CustomGallery toggleSlide() {
        this.mIsSlide = !this.mIsSlide;
        return this;
    }
}