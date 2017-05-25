package com.globallogic.task.vrogovskiy.movieslibrary.service;

import com.globallogic.task.vrogovskiy.movieslibrary.model.MovieObject;
import com.globallogic.task.vrogovskiy.movieslibrary.model.MoviesListApiResponse;
import com.globallogic.task.vrogovskiy.movieslibrary.model.SearchApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by vrogovskiy on 5/23/17.
 */

public interface RetrofitService {

    @GET("/3/movie/top_rated")
    Call<MoviesListApiResponse> listMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("/3/search/movie")
    Call<SearchApiResponse> searchMovies(@Query("api_key") String api_key, @Query("query") String query);

    @GET("/3/movie/{movie_id}")
    Call<MovieObject> getMovie(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

}
