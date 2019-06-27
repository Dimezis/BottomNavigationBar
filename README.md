# BottomNavigationBar
Simple Bottom Navigation Bar for Android.

![alt tag](https://github.com/Dimezis/BottomNavigationBar/blob/master/BottomBar.png)

![alt tag](https://github.com/Dimezis/BottomNavigationBar/blob/master/BottomBar.gif)

## How to use it
```XML
  <com.eightbitlab.bottomnavigationbar.BottomNavigationBar
      android:id="@+id/bottom_bar"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:background="@color/colorPrimary"
      app:activeTabColor="@android:color/white"
      app:inactiveTabColor="@color/bottomBarDefaultTextColor"/>
```

```Java
  BottomBarItem item = new BottomBarItem(R.drawable.test_icon, R.string.title);

  bottomNavigationBar.addTab(item);
  
  // Optional badge 
  bottomNavigationBar.showBadge(position, badge);
```

```Java
  bottomBar.setOnSelectListener(new BottomNavigationBar.OnSelectListener() {
      @Override
      public void onSelect(int position) {
          doStuff(position);
      }
  });
```

## Gradle
```Groovy
compile 'com.eightbitlab:bottomnavigationbar:0.9.6'
```

## Why is it better than library X?
1) Tabs without titles are supported

2) Properly displayed in layout editor

3) Simple layout, no nested containers

4) Simple API and code

5) Smooth native animations, no alpha or requestLayout() calls

6) Properly scales to any height

7) It's tiny. Much smaller than some average bottom bar lib

## Why is it worse than library X?
1) There's only a single behavior - fixed tabs. No shifting mode, no dynamic color change of bottom bar

2) No special tablet mode

3) Currently no animation customization
