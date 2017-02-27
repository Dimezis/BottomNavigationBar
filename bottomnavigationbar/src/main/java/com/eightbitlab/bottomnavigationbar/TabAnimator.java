package com.eightbitlab.bottomnavigationbar;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

class TabAnimator {

    private static final int ANIMATION_DURATION = 200;

    static void animateMargin(@NonNull final View view, int to) {
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                view.requestLayout();
            }
        });
        animator.setDuration(ANIMATION_DURATION);
        animator.start();
    }
}
