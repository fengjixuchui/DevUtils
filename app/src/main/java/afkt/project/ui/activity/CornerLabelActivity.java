package afkt.project.ui.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.SizeUtils;
import dev.utils.common.RandomUtils;
import dev.widget.ui.CornerLabelView;

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
public class CornerLabelActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_acl_labelview)
    CornerLabelView labelView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_corner_label;
    }

    @OnClick({R.id.btn_color, R.id.btn_left, R.id.btn_top, R.id.btn_triangle,
            R.id.btn_text1_minus, R.id.btn_text1_plus, R.id.btn_height1_minus, R.id.btn_height1_plus,
            R.id.btn_text2_minus, R.id.btn_text2_plus, R.id.btn_height2_minus, R.id.btn_height2_plus})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_color:
                labelView.setFillColor(0xff000000 | RandomUtils.getRandom(0, 0xffffff));
                break;
            case R.id.btn_left:
                if (mIsLeft) {
                    labelView.right();
                } else {
                    labelView.left();
                }
                mIsLeft = !mIsLeft;
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) labelView.getLayoutParams();
                lp.gravity = (mIsLeft ? Gravity.LEFT : Gravity.RIGHT) | (mIsTop ? Gravity.TOP : Gravity.BOTTOM);
                labelView.setLayoutParams(lp);
                break;
            case R.id.btn_top:
                if (mIsTop) {
                    labelView.bottom();
                } else {
                    labelView.top();
                }
                mIsTop = !mIsTop;
                FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) labelView.getLayoutParams();
                lp2.gravity = (mIsLeft ? Gravity.LEFT : Gravity.RIGHT) | (mIsTop ? Gravity.TOP : Gravity.BOTTOM);
                labelView.setLayoutParams(lp2);
                break;
            case R.id.btn_triangle:
                mIsTriangle = !mIsTriangle;
                labelView.triangle(mIsTriangle);
                break;
            case R.id.btn_text1_minus:
                mText1Index = (mText1Index - 1 + TEXTS.length) % TEXTS.length;
                labelView.setText1(TEXTS[mText1Index]);
                break;
            case R.id.btn_text1_plus:
                mText1Index = (mText1Index + 1) % TEXTS.length;
                labelView.setText1(TEXTS[mText1Index]);
                break;
            case R.id.btn_height1_minus:
                if (mText1Height < 8) break;
                mText1Height -= 2;
                convertPx = SizeUtils.spConvertPx(mText1Height);
                labelView.setTextHeight1(convertPx);
                labelView.setPaddingTop(convertPx);
                labelView.setPaddingCenter(convertPx / 3);
                labelView.setPaddingBottom(convertPx / 3);
                break;
            case R.id.btn_height1_plus:
                if (mText1Height > 30) break;
                mText1Height += 2;
                convertPx = SizeUtils.spConvertPx(mText1Height);
                labelView.setTextHeight1(convertPx);
                labelView.setPaddingTop(convertPx);
                labelView.setPaddingCenter(convertPx / 3);
                labelView.setPaddingBottom(convertPx / 3);
                break;
            case R.id.btn_text2_minus:
                mText2Index = (mText2Index + 5 - 1) % 5;
                labelView.setText2("1234567890".substring(0, mText2Index));
                break;
            case R.id.btn_text2_plus:
                mText2Index = (mText2Index + 5 + 1) % 5;
                labelView.setText2("1234567890".substring(0, mText2Index));
                break;
            case R.id.btn_height2_minus:
                if (mText2Height < 4) break;
                mText2Height -= 2;
                convertPx = SizeUtils.spConvertPx(mText2Height);
                labelView.setTextHeight2(convertPx);
                break;
            case R.id.btn_height2_plus:
                if (mText2Height > 20) break;
                mText2Height += 2;
                convertPx = SizeUtils.spConvertPx(mText2Height);
                labelView.setTextHeight2(convertPx);
                break;
        }
    }

    // =

    static final String[] TEXTS = {"滿減", "赠品", "满送", "包邮", "拼图", "新人", "砍价", "预售", "众筹"};

    float convertPx;

    int   mText1Index  = 3;
    float mText1Height = 12;

    int   mText2Index  = 3;
    float mText2Height = 8;

    boolean mIsLeft     = true;
    boolean mIsTop      = true;
    boolean mIsTriangle = false;
}