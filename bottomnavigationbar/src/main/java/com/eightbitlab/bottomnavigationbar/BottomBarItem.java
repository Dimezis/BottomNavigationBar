package com.eightbitlab.bottomnavigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class BottomBarItem {
    @DrawableRes
    private final int iconId;
    @Nullable
    private final Drawable iconDrawable;
    @StringRes
    private final int title;

    public BottomBarItem(@DrawableRes int iconId, @StringRes int titleId) {
        this.iconId = iconId;
        this.title = titleId;
        iconDrawable = null;
        if (iconId == 0) {
            throw new RuntimeException("Icon must be provided");
        }
    }

    public BottomBarItem(@NonNull Drawable iconDrawable, @StringRes int titleId) {
        this.iconDrawable = iconDrawable;
        this.title = titleId;
        this.iconId = 0;
    }

    public BottomBarItem(@NonNull Drawable iconDrawable) {
        this(iconDrawable, 0);
    }

    public BottomBarItem(@DrawableRes int iconId) {
        this(iconId, 0);
    }

    @NonNull
    Drawable getIconDrawable(@NonNull Context context) {
        Drawable drawable;
        if (iconDrawable != null) {
            drawable = iconDrawable;
        } else {
            drawable = ContextCompat.getDrawable(context, iconId);
        }
        //wrapped for tinting
        return DrawableCompat.wrap(drawable).mutate();
    }

    @StringRes
    public int getTitle() {
        return title;
    }
}
