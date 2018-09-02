package com.udacity.movies.ui.moviedetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.movies.data.model.Result;
import com.udacity.movies.shared.Constants;
import com.udacity.movies.webservices.RetrofitApiUtils;
import com.udacity.movies.webservices.RetrofitService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = DetailsViewModel.class.getSimpleName();
    private RetrofitService mRetrofitService;
    private final MutableLiveData<Result> mMovie = new MutableLiveData();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        mRetrofitService = RetrofitApiUtils.getRetrofitService();
    }

    public void getMovieById(int id) {
        mRetrofitService.getMovieById(id, Constants.API_Key, "en-US")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onNext(Result movie) {
                        Log.i(TAG, "onNext ");
                        mMovie.postValue(movie);
                    }
                });
    }

    public LiveData<Result> getMovie() {
        return mMovie;
    }
}
