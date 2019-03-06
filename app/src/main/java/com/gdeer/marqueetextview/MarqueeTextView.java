package com.gdeer.marqueetextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 跑马灯效果 TextView，可自定义 gap 大小（即滚动时的头尾间距）
 * @author gdeer
 */
@SuppressLint("AppCompatCustomView")
public class MarqueeTextView extends TextView {

    /**
     * 上一次设置的时间，用于过滤多余操作
     */
    private long mLastSetTime;
    /**
     * 自定义 gap
     */
    private float mCustomGap;
    /**
     * 是否使用自定义 gap
     */
    private boolean mUseCustomGap;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        mCustomGap = typedArray.getDimension(R.styleable.MarqueeTextView_customGap, 10);
        mUseCustomGap = typedArray.getBoolean(R.styleable.MarqueeTextView_useCustomGap, false);
        typedArray.recycle();
    }

    @Override
    public void invalidate() {
        reflectToChangeGap();

        super.invalidate();
    }

    private void reflectToChangeGap() {
        if (!mUseCustomGap) {
            return;
        }
        if (System.currentTimeMillis() - mLastSetTime < 1000) {
            // 1s 内不重新设置，过滤多余操作
            return;
        }
        try {
            Class marqueClass = null;
            Class[] innerClazz = TextView.class.getDeclaredClasses();
            for (Class clazz : innerClazz) {
                if ("Marquee".equals(clazz.getSimpleName())) {
                    marqueClass = clazz;
                }
            }

            if (marqueClass == null) {
                return;
            }

            Field field1 = FieldUtils.getDeclaredField(marqueClass, "mGhostStart", true);

            if (field1 == null) {
                return;
            }

            final int textWidth = getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight();
            final float lineWidth = getLayout().getLineWidth(0);
            final float gap = mCustomGap;
            float ghostStart = lineWidth - textWidth + gap;
            float maxScroll = ghostStart + textWidth;
            float ghostOffset = lineWidth + gap;
            float maxFadeScroll = ghostStart + lineWidth + lineWidth;

            final Field field = FieldUtils.getDeclaredField(TextView.class, "mMarquee", true);
            if (field != null) {
                Object mMarque = field.get(this);
                if (mMarque != null) {
                    mLastSetTime = System.currentTimeMillis();
                    float mGhostStart = (float) field1.get(mMarque);
                    if (mGhostStart != ghostStart) {
                        // 需要设置的 mGhostStart 与当前 ghostStart 不相等时才去设置
                        Field field2 = FieldUtils.getDeclaredField(marqueClass, "mMaxScroll", true);
                        Field field3 = FieldUtils.getDeclaredField(marqueClass, "mGhostOffset", true);
                        Field field4 = FieldUtils.getDeclaredField(marqueClass, "mMaxFadeScroll", true);

                        if (field2 == null || field3 == null || field4 == null) {
                            return;
                        }

                        field1.set(mMarque, ghostStart);
                        field2.set(mMarque, maxScroll);
                        field3.set(mMarque, ghostOffset);
                        field4.set(mMarque, maxFadeScroll);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
