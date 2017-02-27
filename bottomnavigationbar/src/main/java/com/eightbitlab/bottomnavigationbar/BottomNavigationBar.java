package com.eightbitlab.bottomnavigationbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

//TODO:
//clean up
//resolve title padding issue
//test on 4.1, 4.4, 5, 5, 6 and 7
public class BottomNavigationBar extends LinearLayout {

    public static final String TAG = "LOL";

    private List<Tab> tabs = new ArrayList<>(5);
    private LayoutInflater inflater = LayoutInflater.from(getContext());
    private int selectedPosition;

    private OnTabClickListener userClickListener = new OnTabClickListener() {
        @Override
        public void onClick(int position) {
        }
    };
    private ReselectListener reselectListener = new ReselectListener() {
        @Override
        public void onReselect(int position) {
        }
    };

    private OnTabClickListener internalListener = new OnTabClickListener() {
        @Override
        public void onClick(int position) {
            if (position == selectedPosition) {
                reselectListener.onReselect(position);
                return;
            }
            selectTab(position, true);
            userClickListener.onClick(position);
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
        init();
    }

    @NonNull
    private Tab getCurrent() {
        return tabs.get(selectedPosition);
    }

    public void selectTab(int position, boolean animate) {
        if (position != selectedPosition) {
            getCurrent().deselect(animate);
            selectedPosition = position;
            getCurrent().select(animate);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initFromAttributes(Context context, AttributeSet attrs) {
        int[] set = {android.R.attr.elevation};
        TypedArray a = context.obtainStyledAttributes(attrs, set);

        int defaultElevation = getResources().getDimensionPixelSize(R.dimen.bottom_bar_eleveation);
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
        //initial selection
        if (position == 0) {
            getCurrent().select(false);
        }
        return this;
    }

    public void setOnTabClickListener(@NonNull OnTabClickListener listener) {
        userClickListener = listener;
    }

    public void setOnReselectListener(@NonNull ReselectListener listener) {
        reselectListener = listener;
    }

    @NonNull
    private Tab createTab(@NonNull BottomBarItem item, @NonNull View tabView, final int position) {
        Tab tab = new Tab(item, tabView, position);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                internalListener.onClick(position);
            }
        });
        return tab;
    }

    private void init() {
        setBackgroundForEditMode();
        setOrientation(HORIZONTAL);
        if (getBackground() == null) {
            setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        }

        if (isLollipop()) {
            setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
    }

    private void setBackgroundForEditMode() {
        if (isInEditMode()) {
            setBackgroundColor(0xffffffff);
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        selectTab(ss.selectedPosition, false);
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

    interface OnTabClickListener {
        void onClick(int position);
    }

    interface ReselectListener {
        void onReselect(int position);
    }
}
