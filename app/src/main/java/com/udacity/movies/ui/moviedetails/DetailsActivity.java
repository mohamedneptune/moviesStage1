package com.udacity.movies.ui.moviedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movies.R;
import com.udacity.movies.data.model.Example;
import com.udacity.movies.data.model.Result;
import com.udacity.movies.databinding.ActivityDetailsBinding;
import com.udacity.movies.shared.Constants;
import com.udacity.movies.ui.movieslist.MainActivity;
import com.udacity.movies.ui.movieslist.MainViewAdapter;
import com.udacity.movies.ui.movieslist.MainViewModel;


public class DetailsActivity extends AppCompatActivity {

    // Constant for logging
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private ActivityDetailsBinding mBinding;
    private DetailsViewModel mDetailsViewModel;
    private Result mMovie;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static final String SELECTED_MOVIE_ID = "selected_movie_id";
    private static final int DEFAULT_SELECTED_MOVIE = -1;
    private final int mSelectedMovieId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        setTitle(getString(R.string.movie_details));

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        final int mSelectedMovieId = intent.getIntExtra(SELECTED_MOVIE_ID, DEFAULT_SELECTED_MOVIE);
        if (mSelectedMovieId == DEFAULT_SELECTED_MOVIE) {
            closeOnError();
            return;
        } else {
            mDetailsViewModel.getMovieById(mSelectedMovieId);
            setupViewModel();
        }

    }

    private void setupViewModel() {

        // Observe the LiveData object in the ViewModel
        mDetailsViewModel.getMovie().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result movieModel) {
                Log.d(TAG, "Updating movie from LiveData in ViewModel");
                mMovie = movieModel;
                showDetails(mMovie);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showDetails(Result movie) {
        Log.i(TAG, "");

        try {
            Picasso.with(DetailsActivity.this)
                    .load(Constants.IMAGE_BASE_URL_500 + movie.getBackdropPath())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                    .into(mBinding.movieBackdropImage);
        } catch (Exception e) {
            e.toString();
        }

        try {
            Picasso.with(DetailsActivity.this)
                    .load(Constants.IMAGE_BASE_URL_342 + movie.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                    .into(mBinding.moviePosterImage);
        } catch (Exception e) {
            e.toString();
        }

        mBinding.movieTitle.setText(movie.getOriginalTitle());
        mBinding.movieReleaseDate.setText(movie.getReleaseDate());
        mBinding.movieOversView.setText(movie.getOverview());
        Double voteAverage = movie.getVoteAverage();
        mBinding.movieVoteAverage.setRating(voteAverage.floatValue() / 2);
    }

    private void closeOnError() {
        finish();
    }
}
