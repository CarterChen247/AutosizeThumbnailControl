package me.kazafchen.autosizethumbnailcontrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.starwing.proevent.R;
import com.starwing.proevent.utils.Utils;

import java.util.List;

/**
 * Created by kazaf on 2017/2/23.
 */

public class ThumbnailViewAdapter extends RecyclerView.Adapter<ThumbnailViewAdapter.ViewHolder> {


    private Context context;
    private List<String> urls;

    int adjustedWidth, adjustedHeight;


    public ThumbnailViewAdapter(Context context, List<String> urls, int adjustedWidth, int adjustedHeight) {
        this.context = context;
        this.urls = urls;
        this.adjustedWidth = adjustedWidth;
        this.adjustedHeight = adjustedHeight;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = adjustedWidth;
        layoutParams.height = adjustedHeight;
        view.setLayoutParams(layoutParams);

        ViewHolder viewHolder = new ViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ThumbnailViewAdapter.ViewHolder holder, int position) {
        ImageView img_item_thumbnail = holder.img_item_thumbnail;
        Picasso.with(context).load(urls.get(position)).into(img_item_thumbnail);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_item_thumbnail;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.img_item_thumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return urls.size();
    }

    public interface Contract {

        interface ViewDisplayed {



        }

        interface Presenter {

            void calculateRecyclerViewLength(RecyclerView recyclerView, int screenWidth, int screenHeight);

            int getAdjustedRecyclerViewItemWidth();

            int getAdjustedRecyclerViewItemHeight();

            void addSpaceBeforeFirstItemAndAfterLastItem(RecyclerView recyclerView);

            void scrollRecyclerViewByPosition(RecyclerView recyclerView, int newPosition);

            void detectScrollEvent(RecyclerView recyclerView);
        }
    }



}