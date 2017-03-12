package com.eightbitlab.test;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

        BottomBarItem item = BottomBarItem.builder()
                .iconId(R.drawable.test_icon)
                .title(R.string.title)
                .build();

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

        if (atLeastKitkat() && isInPortrait()) {
            bottomNavigationBar.setPadding(0, 0, 0, getSystemNavigationBarHeight());
        }
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

        int identifier = res.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = 0;

        if (identifier > 0) {
            height = res.getDimensionPixelSize(identifier);
        }

        return height;
    }
}
