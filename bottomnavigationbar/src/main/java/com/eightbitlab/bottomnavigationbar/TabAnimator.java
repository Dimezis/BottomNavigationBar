package com.eightbitlab.bottomnavigationbar;

import android.support.annotation.NonNull;
import android.view.View;

class TabAnimator {

    private static final int ANIMATION_DURATION = 200;

    static void animateTranslationY(@NonNull final View view, int to) {
        view.animate()
                .translationY(to)
                .setDuration(ANIMATION_DURATION);
    }
}
