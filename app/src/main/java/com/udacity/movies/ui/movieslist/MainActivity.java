package com.udacity.movies.ui.movieslist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.movies.R;
import com.udacity.movies.data.model.Example;
import com.udacity.movies.data.model.Result;
import com.udacity.movies.ui.moviedetails.DetailsActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mViewModel;
    private MainViewAdapter mainViewAdapter;
    private GridView mGridView;
    private Menu mMenu;
    private List<Result> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.result_grid);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setupViewModel();

        mViewModel.getPopularMovies();
        setTitle(getResources().getString(R.string.popular));


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailsActivity.SELECTED_MOVIE_ID, mMovies.get(position).getId());
                startActivity(intent);
            }

        });

    }

    private void setupViewModel() {

        // Observe the LiveData object in the ViewModel
        mViewModel.getMovies().observe(this, new Observer<Example>() {
            @Override
            public void onChanged(@Nullable Example moviesModel) {
                Log.d(TAG, "Updating list of movies from LiveData in ViewModel");
                mMovies = moviesModel.getResults();
                mainViewAdapter = new MainViewAdapter(MainActivity.this, mMovies);
                mGridView.setAdapter(mainViewAdapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_popular:
                mViewModel.getPopularMovies();
                setTitle(getResources().getString(R.string.popular));
                return true;

            case R.id.menu_top_rated:
                mViewModel.getTopRatedMovies();
                setTitle(getResources().getString(R.string.top_rated));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
