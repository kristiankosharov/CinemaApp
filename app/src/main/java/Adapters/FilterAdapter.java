package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Models.Filter;
import mycinemaapp.com.mycinemaapp.R;

/**
 * Created by kristian on 15-3-31.
 */
public class FilterAdapter extends ArrayAdapter<Filter> {
    private Activity context;
    private ArrayList<Filter> filterArrayList;

    public FilterAdapter(Activity mContext,
                         ArrayList<Filter> filterArrayList) {
        super(mContext, R.layout.filter_item_layout, filterArrayList);
        this.context = mContext;
        this.filterArrayList = filterArrayList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.filter_item_layout,
                    null);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.filterItem = (TextView) rowView.findViewById(R.id.filter);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Filter item = filterArrayList.get(position);

        if (position == 0) {
            holder.filterItem.setBackgroundColor(context.getResources().getColor(R.color.circul_element_sort_selected));
        }
        holder.filterItem.setText(item.getFilter());

        return rowView;
    }

}
