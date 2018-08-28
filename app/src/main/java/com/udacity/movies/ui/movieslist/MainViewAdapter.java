package com.udacity.movies.ui.movieslist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.movies.R;
import com.udacity.movies.data.model.Result;
import com.udacity.movies.shared.Constants;

import java.util.List;

public class MainViewAdapter extends ArrayAdapter<Result> {

    private static final String LOG_TAG = MainViewAdapter.class.getSimpleName();


    public MainViewAdapter(Activity context, List<Result> androidFlavors) {
        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.result_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.result_image);

        try {
            Picasso.with(parent.getContext())
                    .load(Constants.IMAGE_BASE_URL + result.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                    .into(iconView);
        } catch (Exception e) {
            e.toString();
        }

        return convertView;
    }
}

/*
public class MainViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = MainViewAdapter.class.getSimpleName();
    private final Context mContext;
    private List<Result> mMovies;
    private MainViewModel mMainViewModel;

    public MainViewAdapter(Activity context, List<Result> movies, MainViewModel mainViewModel) {
        mContext = context;
        mMovies = movies;
        mMainViewModel = mainViewModel;
        replaceData(movies);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemMovieBinding binding =
                ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new SandwichViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Result movie = mMovies.get(position);
        SandwichViewHolder sandwichViewHolder = (SandwichViewHolder) holder;

        try {
            Picasso.with(mContext)
                    .load(Constants.IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loaded
                    .into(sandwichViewHolder.binding.movieImage);
        } catch (Exception e) {
            e.toString();
        }

        sandwichViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mMainViewModel.getOpenSandwichEvent().setValue(position);
            }
        });

        sandwichViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void replaceData(List<Result> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public class SandwichViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        public SandwichViewHolder(final ItemMovieBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}*/
