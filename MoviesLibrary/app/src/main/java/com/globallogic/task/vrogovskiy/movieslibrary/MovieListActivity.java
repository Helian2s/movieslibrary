package com.globallogic.task.vrogovskiy.movieslibrary;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


import com.globallogic.task.vrogovskiy.movieslibrary.model.MovieObject;
import com.globallogic.task.vrogovskiy.movieslibrary.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    public enum ListMode {
        LIST,
        SEARCH
    }

    private ListMode currentMode = ListMode.LIST;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ResponseFromDownloadServiceReceiver receiver;
    private SimpleItemRecyclerViewAdapter mAdapter;
    private int currentPage = 1;

    public static final String MOVIE_ITEM_EXTRA_NAME = "MOVIE_ITEM_EXTRA_NAME";

    private void switchToSearchMode(String searchString){
        currentMode = ListMode.SEARCH;
        View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        Intent msgIntent = new Intent(this, DownloadService.class);
        msgIntent.putExtra(DownloadService.SEARCH_STRING_EXTRA_NAME, searchString);
        startService(msgIntent);
    }

    private void switchToListMode(){
        currentMode = ListMode.LIST;
        View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        Intent msgIntent = new Intent(this, DownloadService.class);
        msgIntent.putExtra(DownloadService.PAGE_NUMBER_EXTRA_NAME, 1);
        startService(msgIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMode == ListMode.LIST){
                    final Dialog dialog = new Dialog(MovieListActivity.this);
                    dialog.setContentView(R.layout.search_dialog);
                    dialog.setTitle("Search Movies");
                    Button okButton = (Button) dialog.findViewById(R.id.ok_button);
                    Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
                    final EditText editText = (EditText) dialog.findViewById(R.id.edit_text);
                    okButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String searchString = editText.getText().toString();
                            if(searchString.length()>0){
                                dialog.dismiss();
                                switchToSearchMode(searchString);
                            }
                        }
                    });
                    cancelButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else if(currentMode == ListMode.SEARCH){
                    switchToListMode();
                }
            }
        });

        View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        registerBroadcastReceiver();
        Intent msgIntent = new Intent(this, DownloadService.class);
        msgIntent.putExtra(DownloadService.PAGE_NUMBER_EXTRA_NAME, 1);
        startService(msgIntent);
    }

    private void registerBroadcastReceiver(){
        IntentFilter filter = new IntentFilter(ResponseFromDownloadServiceReceiver.MOVIES_DOWNLOADED_ACTION_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseFromDownloadServiceReceiver();
        registerReceiver(receiver, filter);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SimpleItemRecyclerViewAdapter(new ArrayList<MovieObject>());
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<MovieObject> mValues;

        public SimpleItemRecyclerViewAdapter(List<MovieObject> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if(currentMode==ListMode.LIST){
                if((position+1)>=(15+(currentPage-1)*20)){
                    Intent msgIntent = new Intent(MovieListActivity.this, DownloadService.class);
                    msgIntent.putExtra(DownloadService.PAGE_NUMBER_EXTRA_NAME, ++currentPage);
                    startService(msgIntent);
                }
            }
            holder.mItem = mValues.get(position);
            holder.mTitle.setText(mValues.get(position).getTitle());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(MOVIE_ITEM_EXTRA_NAME, holder.mItem);
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(MOVIE_ITEM_EXTRA_NAME, holder.mItem);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addListOfMovies(List<MovieObject> newMovieObjectList){
            mValues.addAll(newMovieObjectList);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitle;
            public MovieObject mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitle = (TextView) view.findViewById(R.id.title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitle.getText() + "'";
            }
        }
    }

    public class ResponseFromDownloadServiceReceiver extends BroadcastReceiver {
        public static final String MOVIES_DOWNLOADED_ACTION_RESPONSE =
                "com.globallogic.task.vrogovskiy.movieslibrary.movies_downloaded";
        public static final String FAILED_TO_DOWNLOAD_ACTION_RESPONSE =
                "com.globallogic.task.vrogovskiy.movieslibrary.failed_to_";
        public static final String MOVIES_LIST_EXTRA_NAME =
                "MOVIES_LIST_EXTRA_NAME";

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MOVIES_DOWNLOADED_ACTION_RESPONSE)){
                List<MovieObject> moviesList = intent.getParcelableArrayListExtra(MOVIES_LIST_EXTRA_NAME);
                mAdapter.addListOfMovies(moviesList);
            } else if(intent.getAction().equals(FAILED_TO_DOWNLOAD_ACTION_RESPONSE)){
                Toast.makeText(MovieListActivity.this, "Failed to download data from server",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
