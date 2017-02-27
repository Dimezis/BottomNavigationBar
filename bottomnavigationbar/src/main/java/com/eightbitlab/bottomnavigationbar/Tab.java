package com.eightbitlab.bottomnavigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.GONE;
import static com.eightbitlab.bottomnavigationbar.TabAnimator.animateMargin;

class Tab {
    private final int position;
    private final BottomBarItem item;
    private final View root;
    private final TextView title;
    private final ImageView icon;
    private final Context context;

    private final int activeTopMargin;
    private final int inactiveTopMargin;
    @ColorInt
    private final int activeColor;
    @ColorInt
    private final int inactiveColor;
    private Drawable iconDrawable;

    Tab(@NonNull BottomBarItem item, @NonNull View root, int position) {
        this.item = item;
        this.root = root;
        this.position = position;
        title = (TextView) root.findViewById(R.id.tab_title);
        icon = (ImageView) root.findViewById(R.id.tab_icon);
        context = root.getContext();

        activeTopMargin = getSizeInPx(R.dimen.bottom_bar_icon_top_margin_active);
        inactiveTopMargin = getSizeInPx(R.dimen.bottom_bar_icon_top_margin_inactive);
        activeColor = colorToInt(item.activeColor);
        inactiveColor = colorToInt(item.inactiveColor);
        //wrapped for tinting
        iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, item.icon)).mutate();

        setUp();
    }

    private int getSizeInPx(@DimenRes int res) {
        return context.getResources().getDimensionPixelSize(res);
    }

    void select(boolean animate) {
        title.setTextColor(activeColor);
        DrawableCompat.setTint(iconDrawable, activeColor);

        if (animate) {
            animateMargin(icon, activeTopMargin);
        } else {
            setIconTopMargin(activeTopMargin);
        }
    }

    private void setIconTopMargin(int value) {
        MarginLayoutParams params = (MarginLayoutParams) icon.getLayoutParams();
        if (params.topMargin != value) {
            params.topMargin = value;
            icon.requestLayout();
        }
    }

    void deselect(boolean animate) {
        title.setTextColor(inactiveColor);
        DrawableCompat.setTint(iconDrawable, inactiveColor);

        if (animate) {
            animateMargin(icon, inactiveTopMargin);
        } else {
            setIconTopMargin(inactiveTopMargin);
        }
    }

    int getPosition() {
        return position;
    }

    @NonNull
    BottomBarItem getItem() {
        return item;
    }

    @NonNull
    View getRoot() {
        return root;
    }

    @NonNull
    TextView getTitle() {
        return title;
    }

    @NonNull
    ImageView getIcon() {
        return icon;
    }

    private void setUp() {
        if (item.title == 0) {
            title.setVisibility(GONE);
        } else {
            title.setText(item.title);
        }
        DrawableCompat.setTint(iconDrawable, inactiveColor);
        icon.setImageDrawable(iconDrawable);
        title.setTextColor(inactiveColor);
    }

    @ColorInt
    private int colorToInt(@ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }
}
