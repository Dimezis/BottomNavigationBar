package com.eightbitlab.test;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;

import com.eightbitlab.bottomnavigationbar.BottomBarItem;
import com.eightbitlab.bottomnavigationbar.BottomNavigationBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = ((TextView) findViewById(R.id.textView));
        showContent(0, textView);
        setupBottomBar(textView);
    }

    private void setupBottomBar(@NonNull final TextView textView) {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);

        BottomBarItem item = new BottomBarItem(R.drawable.test_icon, R.string.title);

        bottomNavigationBar
                .addTab(item)
                .addTab(item)
                .addTab(item)
                .addTab(item);

        bottomNavigationBar.setOnSelectListener(new BottomNavigationBar.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                showContent(position, textView);
            }
        });

        //only for translucent system navbar
        if (shouldAddNavigationBarPadding()) {
            //if your bottom bar has fixed height
            //you'll need to increase its height as well
            bottomNavigationBar.setPadding(0, 0, 0, getSystemNavigationBarHeight());
        }
    }

    private boolean shouldAddNavigationBarPadding() {
        return atLeastKitkat() && isInPortrait() && hasSystemNavigationBar();
    }

    private boolean isInPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean atLeastKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    void showContent(int position, @NonNull TextView textView) {
        textView.setText("Tab " + position + " selected");
    }

    private int getSystemNavigationBarHeight() {
        Resources res = getResources();

        int id = res.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = 0;

        if (id > 0) {
            height = res.getDimensionPixelSize(id);
        }

        return height;
    }

    private boolean hasSystemNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !hasMenuKey && !hasBackKey;
        }
    }
}
