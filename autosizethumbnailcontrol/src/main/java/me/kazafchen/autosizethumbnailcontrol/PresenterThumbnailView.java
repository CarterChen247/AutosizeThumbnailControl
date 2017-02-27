package me.kazafchen.autosizethumbnailcontrol;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;

/**
 * Created by kazaf on 2017/2/24.
 */

public class PresenterThumbnailView implements ThumbnailViewAdapter.Contract.Presenter {

    private final String TAG = PresenterThumbnailView.class.getSimpleName();

    ThumbnailViewAdapter.Contract.ViewDisplayed view;

    float scale;
    int offset;

    int adjustedWidth = -1;
    int adjustedHeight = -1;

    int currentPosition = 0;

    int distanceCompensate = 0;

    public PresenterThumbnailView(ThumbnailViewAdapter.Contract.ViewDisplayed view) {
        this.view = view;
    }

    @Override
    public void calculateRecyclerViewLength(final RecyclerView recyclerView, final int screenWidth, final int screenHeight) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int resizedWidth = recyclerView.getWidth();
                int resizedHeight = recyclerView.getHeight();

                if (resizedWidth == screenWidth) {
                    scale = (float) resizedHeight / screenHeight * 1.0f;
                    offset = Math.round((float) screenWidth / 2 - resizedWidth * scale / 2);
                    adjustedWidth = Math.round((float) resizedWidth * scale);
                    adjustedHeight = resizedHeight;
                } else if (resizedHeight == screenHeight) { // not in use
                    scale = (float) resizedWidth / screenWidth * 1.0f;
                    offset = Math.round((float) screenHeight / 2 - resizedHeight * scale / 2);
                    adjustedHeight = Math.round((float) resizedHeight * scale);
                    adjustedWidth = resizedWidth;
                }
            }
        });
    }

    @Override
    public int getAdjustedRecyclerViewItemWidth() {
        if (adjustedWidth == -1) {
            Log.e(TAG, "you need to calculate length first");
            return 0;
        }
        return adjustedWidth;
    }

    @Override
    public int getAdjustedRecyclerViewItemHeight() {
        if (adjustedHeight == -1) {
            Log.e(TAG, "you need to calculate length first");
            return 0;
        }
        return adjustedHeight;
    }

    @Override
    public void addSpaceBeforeFirstItemAndAfterLastItem(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new ThumbnailViewItemDecoration(offset));
    }


    @Override
    public void scrollRecyclerViewByPosition(RecyclerView recyclerView, int newPosition) {

        int diff = Math.abs(newPosition - currentPosition);

        if (newPosition > currentPosition) {
            recyclerView.scrollBy(distanceCompensate*(-1), 0);
            recyclerView.scrollBy(adjustedWidth * diff, 0);
        } else {
            recyclerView.scrollBy(distanceCompensate*(-1), 0);
            recyclerView.scrollBy(adjustedWidth * diff * (-1), 0);
        }

        currentPosition = newPosition;
        distanceCompensate = 0; // reset
    }

    @Override
    public void detectScrollEvent(RecyclerView rv_ebook_thumbnail) {
        rv_ebook_thumbnail.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isCalculateEnabled = false;

            int distanceCalculated = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {

                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        isCalculateEnabled = true;
                        break;

                    case RecyclerView.SCROLL_STATE_IDLE:
                        isCalculateEnabled = false;

                        distanceCompensate = distanceCalculated;

                        distanceCalculated = 0; // reset temp
                        break;

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isCalculateEnabled) {
                    distanceCalculated = distanceCalculated + dx;
                }
            }
        });
    }
}
