package com.eightbitlab.test;

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
                .build();

        bottomNavigationBar
                .addTab(item)
                .addTab(item)
                .addTab(item)
                .addTab(item);

        bottomNavigationBar.setOnTabClickListener(new BottomNavigationBar.OnSelectListener() {
            @Override
            public void onClick(int position) {
                showContent(position, textView);
            }
        });
    }

    void showContent(int position, @NonNull TextView textView) {
        textView.setText("Tab " + position + " selected");
    }
}
