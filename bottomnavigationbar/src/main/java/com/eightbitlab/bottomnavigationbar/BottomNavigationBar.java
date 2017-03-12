package com.eightbitlab.bottomnavigationbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationBar extends LinearLayout {

    private final List<Tab> tabs = new ArrayList<>(5);
    private final LayoutInflater inflater = LayoutInflater.from(getContext());
    @ColorInt
    private int inactiveColorId;
    @ColorInt
    private int activeColorId;
    private int selectedPosition;
    private boolean shouldTriggerListenerOnLayout;

    private OnSelectListener onSelectListener = new OnSelectListener() {
        @Override
        public void onClick(int position) {
        }
    };
    private OnReselectListener onReselectListener = new OnReselectListener() {
        @Override
        public void onReselect(int position) {
        }
    };

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(context, attrs);
        initFromCustomAttributes(context, attrs);
        init();
    }

    private void initFromCustomAttributes(@NonNull Context context, @NonNull AttributeSet attrs) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar);
        int defaultInactiveColor = colorToInt(R.color.bottomBarDefaultTextColor);
        int defaultActiveColor = colorToInt(R.color.colorPrimary);
        inactiveColorId = array.getColor(R.styleable.BottomNavigationBar_inactiveTabColor, defaultInactiveColor);
        activeColorId = array.getColor(R.styleable.BottomNavigationBar_activeTabColor, defaultActiveColor);
        array.recycle();
    }

    @ColorInt
    private int colorToInt(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    @NonNull
    private Tab getCurrent() {
        return tabs.get(selectedPosition);
    }

    /**
     * Selects tab, not triggering listener
     * @param position position to select
     * @param animate indicates wheter selection should  be animated
     */
    public void selectTab(int position, boolean animate) {
        if (position != selectedPosition) {
            getCurrent().deselect(animate);
            selectedPosition = position;
            getCurrent().select(animate);
        }
    }

    /**
     * Selects tab, triggering listener
     * @param position position to select
     * @param animate indicates wheter selection should  be animated
     */
    public void selectTabAndTriggerListener(int position, boolean animate) {
        selectTab(position, animate);
        if (position != selectedPosition) {
            onSelectListener.onClick(position);
        } else {
            onReselectListener.onReselect(position);
        }
    }

    /**
     * Enables or disables automatic invocation of click listener during layout.
     * Disabled by default.
     * @param shouldTrigger indicates whether selection listener should be triggered
     */
    public void setTriggerListenerOnLayout(boolean shouldTrigger) {
        shouldTriggerListenerOnLayout = shouldTrigger;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        int[] set = {android.R.attr.elevation};
        TypedArray a = context.obtainStyledAttributes(attrs, set);

        int defaultElevation = getResources().getDimensionPixelSize(R.dimen.bottom_bar_elevation);
        float elevation = a.getDimensionPixelSize(0, defaultElevation);
        ViewCompat.setElevation(this, elevation);

        a.recycle();
    }

    public BottomNavigationBar addTab(@NonNull BottomBarItem item) {
        View tabView = inflater.inflate(R.layout.bottom_bar_item, this, false);
        addView(tabView);
        int position = tabs.size();
        Tab tab = createTab(item, tabView, position);
        tabs.add(tab);
        return this;
    }

    public void setOnTabClickListener(@NonNull OnSelectListener listener) {
        onSelectListener = listener;
    }

    public void setOnReselectListener(@NonNull OnReselectListener listener) {
        onReselectListener = listener;
    }

    @NonNull
    private Tab createTab(@NonNull BottomBarItem item, @NonNull View tabView, final int position) {
        Tab tab = new Tab(item, tabView, activeColorId, inactiveColorId);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == selectedPosition) {
                    onReselectListener.onReselect(position);
                    return;
                }
                selectTab(position, true);
                onSelectListener.onClick(position);
            }
        });
        return tab;
    }

    private void init() {
        int minHeight = getResources().getDimensionPixelSize(R.dimen.bottom_bar_min_height);
        setMinimumHeight(minHeight);
        setOrientation(HORIZONTAL);

        if (getBackground() == null) {
            setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        }

        if (isLollipop()) {
            setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        getCurrent().select(false);
        if (shouldTriggerListenerOnLayout) {
            onSelectListener.onClick(selectedPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        selectedPosition = ss.selectedPosition;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.selectedPosition = selectedPosition;
        return ss;
    }

    private boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public interface OnSelectListener {
        void onClick(int position);
    }

    public interface OnReselectListener {
        void onReselect(int position);
    }
}
