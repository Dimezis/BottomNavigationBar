package com.eightbitlab.bottomnavigationbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class BottomBarItem {
    @DrawableRes
    final int icon;
    @StringRes
    final int title;

    public BottomBarItem(@DrawableRes int iconId, @StringRes int titleId) {
        this.icon = iconId;
        this.title = titleId;
        if (iconId == 0) {
            throw new RuntimeException("Icon must be provided");
        }
    }

    public BottomBarItem(@DrawableRes int iconId) {
        this(iconId, 0);
    }
}
