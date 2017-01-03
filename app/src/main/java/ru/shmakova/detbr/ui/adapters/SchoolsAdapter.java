package ru.shmakova.detbr.ui.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SchoolsAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> fullList;
    private List<String> originalValues;
    private ArrayFilter filter;

    public SchoolsAdapter(Context context, int resource, int id, List<String> objects) {

        super(context, resource, id, objects);
        fullList = objects;
        originalValues = new ArrayList<>(fullList);

    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    public List<String> getItems() {
        return fullList;
    }

    @Override
    public String getItem(int position) {
        return fullList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    public void addAll(@NonNull List<String> objects) {
        fullList = objects;
        originalValues = new ArrayList<>(fullList);
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (originalValues == null) {
                originalValues = new ArrayList<>(fullList);
            }

            if (prefix == null || prefix.length() == 0) {
                List<String> list = new ArrayList<>(originalValues);
                results.values = list;
                results.count = list.size();
            } else {
                Locale locale = Locale.getDefault();
                final String prefixString = prefix.toString().toLowerCase(locale);
                int count = originalValues.size();
                List<String> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    String item = originalValues.get(i);

                    if (item.toLowerCase(locale).contains(prefixString)) {
                        newValues.add(item);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values == null) {
                fullList = new ArrayList<>();
            } else {
                fullList = (List<String>) results.values;
            }

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}