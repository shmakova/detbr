package ru.yandex.detbr.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.categories.Category;

/**
 * Created by shmakova on 23.08.16.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final CategoryViewHolder.OnItemCLickListener onItemClickListener;

    public CategoriesAdapter(List<Category> categories, CategoryViewHolder.OnItemCLickListener onItemClickListener) {
        this.categories = categories;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
        return new CategoryViewHolder(convertView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.category.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView category;

        private final OnItemCLickListener listener;

        public interface OnItemCLickListener {
            void onItemClick(int position);
        }

        CategoryViewHolder(View itemView, OnItemCLickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listener = onItemClickListener;
        }

        @OnClick(R.id.category)
        void onCategoryItemClick() {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition());
            }
        }
    }
}
