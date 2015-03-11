package Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import Helpers.ImageCacheManager;
import Models.Movie;
import mycinemaapp.com.mycinemaapp.MovieDetailActivity;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-2-25.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Activity context;
    private ArrayList<Movie> list;

    public MovieAdapter(Activity context, int textViewResourceId, ArrayList<Movie> list) {
        super(context, textViewResourceId, list);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.movie_layout,
                    null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.newForWeek = (TextView) rowView
                    .findViewById(R.id.new_movie);
            viewHolder.movieTitle = (TextView) rowView
                    .findViewById(R.id.movie_title);
            viewHolder.movieImage = (NetworkImageView) rowView.findViewById(R.id.movie_image);
            viewHolder.ratingBar = (RatingBar) rowView
                    .findViewById(R.id.rating_bar);
            viewHolder.master = (RelativeLayout) rowView.findViewById(R.id.master);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        final Movie item = list.get(position);

        if (item.getNewForWeek() == null || item.getNewForWeek().equals("")) {
            holder.newForWeek.setVisibility(View.GONE);
        } else {
            holder.newForWeek.setText(item.getNewForWeek());
            holder.newForWeek.setVisibility(View.VISIBLE);
            //Toast.makeText(context,"Vliza",Toast.LENGTH_LONG).show();
        }

        holder.movieImage.setImageUrl(item.getImageUrl(), ImageCacheManager.getInstance().getImageLoader());
        holder.movieImage.setDefaultImageResId(R.drawable.example);

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starFullySelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);

        holder.ratingBar.setMax(300);

        holder.ratingBar.setClickable(false);
//        holder.ratingBar.setEnabled(false);


        holder.ratingBar.setProgress(item.getMovieProgress());

        holder.movieTitle.setText(item.getMovieTitle());

        holder.master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("PROGRESS", item.getMovieProgress());
                intent.putExtra("URL", item.getImageUrl());
                intent.putExtra("TITLE", item.getMovieTitle());
//                intent.putExtra("PROJECTIONS",item.getAllProjections());
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    public int getCount() {
        return list.size();
    }

    public Movie getItem(int position) {
        return list.get(position);
    }

    static class ViewHolder {
        TextView newForWeek, movieTitle;
        NetworkImageView movieImage;
        RatingBar ratingBar;
        RelativeLayout master;
    }

}
