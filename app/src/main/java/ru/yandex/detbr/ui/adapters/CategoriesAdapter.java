package ru.yandex.detbr.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Category;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by shmakova on 23.08.16.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private final PublishSubject<Category> onClickSubject = PublishSubject.create();

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
        return new CategoryViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    public Observable<Category> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView category;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onClickSubject.onNext(categories.get(getAdapterPosition())));
        }

        public void bind(Category category) {
            this.category.setText(category.title());
        }
    }
}
