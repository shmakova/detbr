package ru.yandex.detbr.ui.adapters;


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
    private List<String> mOriginalValues;
    private ArrayFilter mFilter;

    public SchoolsAdapter(Context context, int resource, int id, List<String> objects) {

        super(context, resource, id, objects);
        fullList = objects;
        mOriginalValues = new ArrayList<>(fullList);

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
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    public void addAll(@NonNull List<String> objects) {
        fullList = objects;
        mOriginalValues = new ArrayList<>(fullList);
    }

    private class ArrayFilter extends Filter {
        private final Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList<>(fullList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<String> list = new ArrayList<>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                Locale locale = Locale.getDefault();
                final String prefixString = prefix.toString().toLowerCase(locale);

                int count = mOriginalValues.size();

                ArrayList<String> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    String item = mOriginalValues.get(i);
                    if (item.toLowerCase(locale).contains(prefixString)) {
                        newValues.add(item);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values == null) {
                fullList = new ArrayList<>();
            } else {
                fullList = (ArrayList<String>) results.values;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}