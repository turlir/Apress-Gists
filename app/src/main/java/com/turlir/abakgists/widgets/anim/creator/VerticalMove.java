package com.turlir.abakgists.widgets.anim.creator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.turlir.abakgists.widgets.anim.base.AnimationCreator;

/**
 * Анимация перемещения view по вертикали с изменением прозрачности
 */
public class VerticalMove implements AnimationCreator {

    private final Timing mSetting;
    private final float mDistance, mAlpha;

    /**
     * @param setting параметры анимации
     * @param distance изменение положение в {@code px}, может быть отрицательным
     */
    public VerticalMove(Timing setting, float distance, float alpha) {
        this.mSetting = setting;
        mDistance = distance;
        mAlpha = alpha;
    }

    @Override
    public ObjectAnimator createAnimation(View view) {
        PropertyValuesHolder t = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mDistance);
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat(View.ALPHA, mAlpha);
        return ObjectAnimator.ofPropertyValuesHolder(view, t, a);
    }

    @Override
    public void settingAnimation(ObjectAnimator view) {
        view.setDuration(mSetting.duration).setStartDelay(mSetting.delay);
    }

    @Override
    public void grouping(AnimatorSet.Builder builder, ObjectAnimator animator) {
        builder.with(animator);
    }
}
