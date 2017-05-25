package com.globallogic.task.vrogovskiy.movieslibrary.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.globallogic.task.vrogovskiy.movieslibrary.MovieListActivity;
import com.globallogic.task.vrogovskiy.movieslibrary.model.MovieObject;
import com.globallogic.task.vrogovskiy.movieslibrary.model.MoviesListApiResponse;
import com.globallogic.task.vrogovskiy.movieslibrary.model.SearchApiResponse;
import com.globallogic.task.vrogovskiy.movieslibrary.model.SearchMovieObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vrogovskiy on 5/23/17.
 */

public class DownloadService extends IntentService {

    public static final String PAGE_NUMBER_EXTRA_NAME =
            "PAGE_NUMBER_EXTRA_NAME";
    public static final String SEARCH_STRING_EXTRA_NAME =
            "SEARCH_STRING_EXTRA_NAME";

    private static final String API_KEY = "08ff75ec1646f6c84dbdbcbf9e21731d";

    public DownloadService() {
        super("DownloadService");
    }

    public DownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int pageNumber = intent.getIntExtra(PAGE_NUMBER_EXTRA_NAME, 0);
        String searchString = intent.getStringExtra(SEARCH_STRING_EXTRA_NAME);
        if(pageNumber>0&&searchString==null){
            ArrayList<MovieObject> moviesList =  getMovies(pageNumber);
            if(moviesList!=null) {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MovieListActivity.ResponseFromDownloadServiceReceiver.MOVIES_DOWNLOADED_ACTION_RESPONSE);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putParcelableArrayListExtra(MovieListActivity.ResponseFromDownloadServiceReceiver.MOVIES_LIST_EXTRA_NAME,
                        moviesList);
                sendBroadcast(broadcastIntent);
            }
        } else if(pageNumber==0&&searchString!=null){
            ArrayList<SearchMovieObject> searchList =  searchMovies(searchString);

            ArrayList<MovieObject> resultList = new ArrayList<MovieObject>();
            for(SearchMovieObject searchMovieObject : searchList) {
                MovieObject movieObject = getMovieDetails(searchList.get(0));
                resultList.add(movieObject);
            }
                if(resultList!=null) {
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(MovieListActivity.ResponseFromDownloadServiceReceiver.MOVIES_DOWNLOADED_ACTION_RESPONSE);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    broadcastIntent.putParcelableArrayListExtra(MovieListActivity.ResponseFromDownloadServiceReceiver.MOVIES_LIST_EXTRA_NAME,
                            resultList);
                    sendBroadcast(broadcastIntent);
                }

        }
    }

    private MovieObject getMovieDetails(SearchMovieObject searchMovieObject){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<MovieObject> call = service.getMovie(searchMovieObject.getId(), API_KEY);

        MovieObject movieObject = null;
        try {
            movieObject = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieObject;
    }

    public ArrayList<SearchMovieObject> searchMovies(String searchString){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<SearchApiResponse> call = service.searchMovies(API_KEY, searchString);
        SearchApiResponse searchApiResponse = null;
        try {
            searchApiResponse = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchApiResponse.getResults();
    }

    public ArrayList<MovieObject> getMovies(int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<MoviesListApiResponse> call = service.listMovies(API_KEY, page);
        MoviesListApiResponse moviesList = null;
        try {
            moviesList = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList.getResults();
    }
}
