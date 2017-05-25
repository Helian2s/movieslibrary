package com.globallogic.task.vrogovskiy.movieslibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globallogic.task.vrogovskiy.movieslibrary.R;
import com.globallogic.task.vrogovskiy.movieslibrary.model.MovieObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by vrogovskiy on 5/25/17.
 */

public class CustomView extends LinearLayout {

    private MovieObject movieObject;

    public CustomView(Context context) {
        super(context);
        initViews();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews(){
        LayoutInflater.from(this.getContext()).inflate(R.layout.custom_view, this);
    }

    public void setMovieObject(MovieObject movieObj){
        this.movieObject = movieObj;
        initImageLoader();
        showOverview();
        showPoster();
    }

    private void initImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void showOverview(){
        TextView textView = (TextView) this.findViewById(R.id.movie_overview);
        textView.setText(this.movieObject.getOverview());
    }

    private void showPoster(){
        ImageView imageView = (ImageView) this.findViewById(R.id.movie_image);
        ImageLoader.getInstance().displayImage(this.movieObject.getPosterUrl(), imageView);
    }
}
