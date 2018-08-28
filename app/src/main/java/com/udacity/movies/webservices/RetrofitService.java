package com.udacity.movies.webservices;



import com.udacity.movies.data.model.Example;
import com.udacity.movies.shared.Constants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {

    @GET(Constants.URL_POPULAR_MOVIES)
    Observable<Example> getPopularMovies(@Query("api_key") String api_key,
                                                        @Query("language") String language,
                                                        @Query("page") String page);

    @GET(Constants.URL_TOP_RATED)
    Observable<Example> getTopRatedMovies(@Query("api_key") String api_key,
                                                           @Query("language") String language,
                                                           @Query("page") String page);





}