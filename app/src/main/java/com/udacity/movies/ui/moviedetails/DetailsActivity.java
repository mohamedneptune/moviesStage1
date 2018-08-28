package com.udacity.movies.ui.moviedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movies.R;
import com.udacity.movies.data.model.Example;
import com.udacity.movies.data.model.Result;
import com.udacity.movies.ui.movieslist.MainActivity;
import com.udacity.movies.ui.movieslist.MainViewAdapter;
import com.udacity.movies.ui.movieslist.MainViewModel;


public class DetailsActivity extends AppCompatActivity {

    // Constant for logging
    private static final String TAG = DetailsActivity.class.getSimpleName();
    //private ActivityDetailsBinding mBinding;
    private MainViewModel mViewModel;
    private Result mMovie;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Observe the LiveData object in the ViewModel
        mViewModel.getSelectedMovie().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result moviesModel) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                showDetails(moviesModel);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showDetails(Result movie) {

    }

    private void closeOnError() {
        finish();
    }
}
