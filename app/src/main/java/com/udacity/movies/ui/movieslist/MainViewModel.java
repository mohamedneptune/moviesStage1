package com.udacity.movies.ui.movieslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.movies.data.model.Example;
import com.udacity.movies.data.model.Result;
import com.udacity.movies.shared.Constants;
import com.udacity.movies.webservices.RetrofitApiUtils;
import com.udacity.movies.webservices.RetrofitService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();
    private RetrofitService mRetrofitService;
    private final MutableLiveData<Example> movies = new MutableLiveData<>();
    private final MutableLiveData<Result> mSelectedMovie = new MutableLiveData();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRetrofitService = RetrofitApiUtils.getRetrofitService();
    }

    public void getTopRatedMovies() {
        mRetrofitService.getTopRatedMovies(Constants.API_Key, "en-US", "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onNext(Example example) {
                        Log.i(TAG, "onNext ");
                        movies.postValue(example);
                    }
                });
    }

    public void getPopularMovies() {
        mRetrofitService.getPopularMovies(Constants.API_Key, "en-US", "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onNext(Example example) {
                        Log.i(TAG, "onNext ");
                        movies.postValue(example);
                    }
                });
    }


    public LiveData<Example> getMovies() {
        return movies;
    }

    public LiveData<Result> getSelectedMovie() {
        return mSelectedMovie;
    }

    public void setSelectedMovie(Result movie){
        mSelectedMovie.postValue(movie);
    }
}
