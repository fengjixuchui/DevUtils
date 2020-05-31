package dev.widget.adjust;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * detail: 自动调节高度 RecyclerView
 * @author Ttt
 */
public class AdjustHeightRecyclerView extends RecyclerView {

    public AdjustHeightRecyclerView(Context context) {
        super(context);
    }

    public AdjustHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustHeightRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}