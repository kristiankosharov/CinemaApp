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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Models.AddMovies;
import Models.Movie;
import Models.RatedMovies;
import mycinemaapp.com.mycinemaapp.MovieDetailActivity;
import mycinemaapp.com.mycinemaapp.MyProfileActivity;
import mycinemaapp.com.mycinemaapp.R;
import mycinemaapp.com.mycinemaapp.RateActivity;

/**
 * Created by kristian on 15-2-25.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Activity context;
    private ArrayList<Movie> list;
    private boolean isList, isRated, isBought, isFirstLongPress = true;

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
            viewHolder.movieImage = (ImageView) rowView.findViewById(R.id.movie_image);
            viewHolder.ratingBar = (RatingBar) rowView
                    .findViewById(R.id.rating_bar);
            viewHolder.master = (RelativeLayout) rowView.findViewById(R.id.master);
            viewHolder.dash = (View) rowView.findViewById(R.id.dash_line);
            viewHolder.rateMovie = (Button) rowView.findViewById(R.id.rate_movie);
            viewHolder.rateLayout = (RelativeLayout) rowView.findViewById(R.id.rate_layout);
            viewHolder.deleteItem = (ImageView) rowView.findViewById(R.id.delete);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        final Movie item = list.get(position);

//        isFirstLongPress = true;

        if (item.getNewForWeek() == null || item.getNewForWeek().equals("")) {
            holder.newForWeek.setVisibility(View.GONE);
        } else {
            holder.newForWeek.setText(item.getNewForWeek());
            holder.newForWeek.setVisibility(View.VISIBLE);
        }

        Picasso.with(context)
                .load(item.getImageUrl())
                .noPlaceholder()
                .into(holder.movieImage);

//        holder.movieImage.setImageUrl(item.getImageUrl(), ImageCacheManager.getInstance().getImageLoader());
//        holder.movieImage.setDefaultImageResId(R.drawable.image);

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starFullySelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);

        holder.ratingBar.setMax(5);

        holder.ratingBar.setClickable(false);
        holder.ratingBar.setStepSize(0.5f);
        holder.ratingBar.setRating(item.getMovieProgress());

        holder.movieTitle.setText(item.getMovieTitle());

        holder.master.setOnClickListener(listenerMaster(position));

        holder.rateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("ISRATED", isRated);
                intent.putExtra("ISLIST", isList);
                context.startActivity(intent);
            }
        });

        if (isList) {
            holder.dash.setVisibility(View.GONE);
            holder.rateLayout.setVisibility(View.GONE);
            holder.rateMovie.setVisibility(View.VISIBLE);
            holder.master.setOnLongClickListener(longPressOfMaster(holder, position, item, isList));
        } else if (isRated) {
            holder.dash.setVisibility(View.GONE);
            holder.rateLayout.setVisibility(View.GONE);
            holder.rateMovie.setVisibility(View.VISIBLE);
            holder.rateMovie.setText(item.getUserRating() + " - YOUR RATING");
            holder.master.setOnLongClickListener(longPressOfMaster(holder, position, item, !isRated));
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
        ImageView movieImage;
        RatingBar ratingBar;
        RelativeLayout master, rateLayout;
        View dash;
        Button rateMovie;
        ImageView deleteItem;
    }

    private View.OnLongClickListener longPressOfMaster(final ViewHolder holder, final int position, final Movie item, final boolean isList) {
        final View.OnLongClickListener listener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isFirstLongPress) {
                    holder.deleteItem.setVisibility(View.VISIBLE);
                    holder.deleteItem.setOnClickListener(listenerForDelete(position, item, holder, isList));
                    isFirstLongPress = false;
                } else {
                    holder.deleteItem.setVisibility(View.GONE);
                    isFirstLongPress = true;
                }
                return true;
            }

        };
        return listener;
    }

    private View.OnClickListener listenerForDelete(final int position, final Movie item, final ViewHolder holder, final boolean isList) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isList) {
                    AddMovies.addMovie.remove(position);
                } else if(isRated) {
                    RatedMovies.ratedMovies.remove(position);
                } else {

                }

                MovieAdapter.this.notifyDataSetChanged();
                holder.deleteItem.setVisibility(View.GONE);
                item.setAdd(false);
                ((MyProfileActivity) context).resetAdapter(isList, isRated, isBought);
            }
        };
        return listener;
    }

    private View.OnClickListener listenerMaster(final int position) {
        View.OnClickListener listener = new View.OnClickListener() {
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
        };
        return listener;
    }
}
