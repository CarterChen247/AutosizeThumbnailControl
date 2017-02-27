package me.kazafchen.autosizethumbnailcontrol;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kazaf on 2016/11/11.
 */


public class ThumbnailViewItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public ThumbnailViewItemDecoration(int dp) {
        this.verticalSpaceHeight = dp;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);


        if (position == 0) {

            outRect.left = verticalSpaceHeight;

        }else if (position == (state.getItemCount()-1)){
            outRect.right = verticalSpaceHeight;
        }
    }
}
