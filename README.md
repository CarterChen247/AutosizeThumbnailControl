# AutosizeThumbnailControl
a widget for autosize thumbnail picture. (It only supports horizontal scrolling now)

![](https://github.com/KazafChen/AutosizeThumbnailControl/blob/master/Screenshot_20170227-165532.jpg)


##Dependencies
it depends on RecyclerView
```java
compile 'com.android.support:recyclerview-v7:25.1.0'
```

##Usage
1. Make your View(Activity or Fragment) implements `ThumbnailViewAdapter.Contract.ViewDisplayed`

2. Add `OnItemTouchListener` to your `RecyclerView`, make it selected the target page whenever your touch the item in your RecyclerView

    ```java
mRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener(mRecyclerView) {
      @Override
      public void onItemClick(RecyclerView.ViewHolder vh) {
          int positionChosen = vh.getAdapterPosition();
          mViewPager.setCurrentItem(positionChosen);

      }
  });
    ```

3. In your `onCreate` or `onCreateView` lifecyle of your view, 
    ```java
  presenterThumbnailView =new PresenterThumbnailView(this);
  presenterThumbnailView.calculateRecyclerViewLength(
          mRecyclerView,
          mScreenWidth,
          mScreenHeight
  );
  presenterThumbnailView.detectScrollEvent(mRecyclerView);
    ```

4. Set up Adapter after your network request is done or files are prepared
   ```
  ThumbnailViewAdapter adapter = new ThumbnailViewAdapter(this, picUrls, presenterThumbnailView.getAdjustedRecyclerViewItemWidth(), presenterThumbnailView.getAdjustedRecyclerViewItemHeight());
  mRecyclerView.setAdapter(adapter);
  presenterThumbnailView.addSpaceBeforeFirstItemAndAfterLastItem(mRecyclerView);
    ```

5. Enjoy.

