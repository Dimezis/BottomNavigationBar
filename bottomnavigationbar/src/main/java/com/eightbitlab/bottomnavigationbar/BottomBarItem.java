package com.eightbitlab.bottomnavigationbar;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class BottomBarItem {
    @ColorRes
    final int inactiveColor;
    @ColorRes
    final int activeColor;
    @DrawableRes
    final int icon;
    @StringRes
    final int title;

    private BottomBarItem(@ColorRes int inactiveColor, @ColorRes int activeColor,
                          @DrawableRes int icon, @StringRes int titleId) {
        this.inactiveColor = inactiveColor;
        this.activeColor = activeColor;
        this.icon = icon;
        this.title = titleId;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @ColorRes
        private int inactiveColorId = R.color.bottomBarDefaultTextColor;
        @ColorRes
        private int activeColorId = R.color.colorPrimary;
        @DrawableRes
        private int icon;
        @StringRes
        private int title;

        public Builder inactiveColorId(@ColorRes int inactiveColorId) {
            this.inactiveColorId = inactiveColorId;
            return this;
        }

        public Builder activeColorId(@ColorRes int activeColorId) {
            this.activeColorId = activeColorId;
            return this;
        }

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
            return new BottomBarItem(inactiveColorId, activeColorId, icon, title);
        }
    }
}
