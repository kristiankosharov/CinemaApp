package Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import Helpers.ImageCacheManager;
import Models.Movie;
import mycinemaapp.com.mycinemaapp.MovieDetailActivity;
import mycinemaapp.com.mycinemaapp.R;
import mycinemaapp.com.mycinemaapp.RateActivity;

/**
 * Created by kristian on 15-2-25.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Activity context;
    private ArrayList<Movie> list;
    private boolean isList, isRated, isBought;

    public MovieAdapter(Activity context, int textViewResourceId, ArrayList<Movie> list, boolean isList, boolean isRated, boolean isBought) {
        super(context, textViewResourceId, list);
        this.list = list;
        this.context = context;
        this.isList = isList;
        this.isRated = isRated;
        this.isBought = isBought;
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
            viewHolder.dash = (View) rowView.findViewById(R.id.dash_line);
            viewHolder.rateMovie = (Button) rowView.findViewById(R.id.rate_movie);
            viewHolder.rateLayout = (RelativeLayout) rowView.findViewById(R.id.rate_layout);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        final Movie item = list.get(position);

        if (item.getNewForWeek() == null || item.getNewForWeek().equals("")) {
            holder.newForWeek.setVisibility(View.GONE);
        } else {
            holder.newForWeek.setText(item.getNewForWeek());
            holder.newForWeek.setVisibility(View.VISIBLE);
        }

        holder.movieImage.setImageUrl(item.getImageUrl(), ImageCacheManager.getInstance().getImageLoader());
        holder.movieImage.setDefaultImageResId(R.drawable.image);

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starFullySelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);

        holder.ratingBar.setMax(5);

        holder.ratingBar.setClickable(false);
        holder.ratingBar.setStepSize(0.5f);
        holder.ratingBar.setRating(item.getMovieProgress());

        holder.movieTitle.setText(item.getMovieTitle());

        holder.master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
//                if (item.getPosition() != 0) {
//                    intent.putExtra("POSITION", item.getPosition());
//                } else {
                intent.putExtra("POSITION", position);
//                }
                intent.putExtra("ISLIST", isList);
                intent.putExtra("ISRATED", isRated);
                intent.putExtra("ISBOUGHT", isBought);
                context.startActivity(intent);
            }
        });

        holder.rateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra("POSITION", position);
                Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
                intent.putExtra("ISRATED", isRated);
                intent.putExtra("ISLIST", isList);
                context.startActivity(intent);
            }
        });

        if (isList) {
            holder.dash.setVisibility(View.GONE);
            holder.rateLayout.setVisibility(View.GONE);
            holder.rateMovie.setVisibility(View.VISIBLE);
        } else if (isRated) {
            holder.dash.setVisibility(View.GONE);
            holder.rateLayout.setVisibility(View.GONE);
            holder.rateMovie.setVisibility(View.VISIBLE);
            holder.rateMovie.setText(item.getUserRating() + " - YOUR RATING");
        }
        int height = rowView.getMeasuredHeight();
        item.setHeightView(height * ((int) (context.getResources().getDisplayMetrics().density)));
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
        RelativeLayout master, rateLayout;
        View dash;
        Button rateMovie;
    }

}
