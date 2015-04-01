package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import Models.AllCinemasFilters;
import Models.AllDaysFilters;
import Models.AllGenresFilters;
import Models.Filter;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-3-31.
 */
public class FilterAdapter extends ArrayAdapter<Filter> {
    private Activity context;
    private ArrayList<Filter> filterArrayList;
    private Button button;
    private String from;

    public FilterAdapter(Activity mContext,
                         ArrayList<Filter> filterArrayList, Button button, String from) {
        super(mContext, R.layout.filter_item_layout, filterArrayList);
        this.context = mContext;
        this.filterArrayList = filterArrayList;
        this.button = button;
        this.from = from;
    }

    public int getCount() {
        return filterArrayList.size();
    }

    public Filter getItem(int position) {
        return filterArrayList.get(position);
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

        final Filter item = filterArrayList.get(position);


        holder.filterItem.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        if (item.isSelect()) {
            holder.filterItem.setBackgroundColor(context.getResources().getColor(R.color.seat_selected));
        }

        holder.filterItem.setText(item.getFilter());
        holder.filterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < filterArrayList.size(); i++) {
                    if (filterArrayList.get(i).isSelect()) {
                        filterArrayList.get(i).setSelect(false);
                        switch (from) {
                            case "all days":
                                AllDaysFilters.allDays.get(i).setSelect(true);
                                break;
                            case "all cinemas":
                                AllCinemasFilters.allCinemas.get(i).setSelect(true);
                                break;
                            case "all genres":
                                AllGenresFilters.allGenres.get(i).setSelect(true);
                                break;
                        }
                    }
                }
                item.setSelect(true);
                button.setText(item.getFilter());
                notifyDataSetChanged();
            }
        });
        return rowView;
    }
}
