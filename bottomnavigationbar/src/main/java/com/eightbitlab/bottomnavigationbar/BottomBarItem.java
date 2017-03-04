package com.eightbitlab.bottomnavigationbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class BottomBarItem {
    @DrawableRes
    final int icon;
    @StringRes
    final int title;

    public BottomBarItem(@DrawableRes int icon, @StringRes int titleId) {
        this.icon = icon;
        this.title = titleId;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        @DrawableRes
        private int icon;
        @StringRes
        private int title;

        public Builder iconId(@DrawableRes int iconId) {
            this.icon = iconId;
            return this;
        }

        public Builder title(@StringRes int titleId) {
            this.title = titleId;
            return this;
        }

        @NonNull
        public BottomBarItem build() {
            if (icon == 0) {
                throw new RuntimeException("Icon must be provided");
            }
            return new BottomBarItem(icon, title);
        }
    }
}
