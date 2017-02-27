package com.eightbitlab.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eightbitlab.bottomnavigationbar.BottomBarItem;
import com.eightbitlab.bottomnavigationbar.BottomNavigationBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);

        BottomBarItem item1 = BottomBarItem.builder().iconId(R.drawable.test_icon).build();
        BottomBarItem item2 = BottomBarItem.builder().iconId(R.drawable.test_icon).title(R.string.app_name).build();

        bottomNavigationBar
                .addTab(item1)
                .addTab(item2)
                .addTab(item2)
                .addTab(item2);
    }
}
