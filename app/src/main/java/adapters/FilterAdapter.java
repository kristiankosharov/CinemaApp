package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import models.AllCinemasFilters;
import models.AllDaysFilters;
import models.AllGenresFilters;
import models.Filters;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-3-31.
 */
public class FilterAdapter extends ArrayAdapter<Filters> {
    private Activity context;
    private ArrayList<Filters> filtersArrayList;
    private Button button;
    private String from;

    public FilterAdapter(Activity mContext,
                         ArrayList<Filters> filtersArrayList, Button button, String from) {
        super(mContext, R.layout.filter_item_layout, filtersArrayList);
        this.context = mContext;
        this.filtersArrayList = filtersArrayList;
        this.button = button;
        this.from = from;
    }

    public int getCount() {
        return filtersArrayList.size();
    }

    public Filters getItem(int position) {
        return filtersArrayList.get(position);
    }

    static class ViewHolder {
        TextView filterItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.filter_item_layout,
                    null);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.filterItem = (TextView) rowView.findViewById(R.id.filter);

            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        final Filters item = filtersArrayList.get(position);


        holder.filterItem.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        if (item.isSelect()) {
            holder.filterItem.setBackgroundColor(context.getResources().getColor(R.color.seat_selected));
        }
        switch (from) {
            case "all days":
                holder.filterItem.setText(item.getDayFilter());
                break;
            case "all cinemas":
                holder.filterItem.setText(item.getCinemaFilter());
                break;
            case "all genres":
                holder.filterItem.setText(item.getGenreFilter());
                break;
        }
        holder.filterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < filtersArrayList.size(); i++) {
                    if (filtersArrayList.get(i).isSelect()) {
                        filtersArrayList.get(i).setSelect(false);
                    }
                }
                switch (from) {
                    case "all days":
                        AllDaysFilters.allDays.get(position).setSelect(true);
                        break;
                    case "all cinemas":
                        AllCinemasFilters.allCinemas.get(position).setSelect(true);
                        break;
                    case "all genres":
                        AllGenresFilters.allGenres.get(position).setSelect(true);
                        break;
                }
                item.setSelect(true);
                button.setText(item.getDayFilter());
                notifyDataSetChanged();
            }
        });
        return rowView;
    }
}
