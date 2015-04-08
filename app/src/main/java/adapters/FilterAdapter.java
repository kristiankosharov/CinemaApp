package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import models.Filters;
import mycinemaapp.com.mycinemaapp.MainActivity;
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
        if (item.isCinemaSelected() || item.isDaySelected() || item.isGenreSelected()) {
//            Toast.makeText(context, "SET BACKGROUND", Toast.LENGTH_SHORT).show();
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
                ((MainActivity) context).clearListOfSelected(filtersArrayList);
                switch (from) {
                    case "all days":
                        item.setDaySelected(true);
                        button.setText(item.getDayFilter());
//                        AllDaysFilters.allDays.get(position).setSelect(true);
                        break;
                    case "all cinemas":
                        item.setCinemaSelected(true);
                        button.setText(item.getCinemaFilter());
                        break;
                    case "all genres":
                        item.setGenreSelected(true);
                        button.setText(item.getGenreFilter());
                        break;
                }
                notifyDataSetChanged();

            }
        });
        return rowView;
    }
}
