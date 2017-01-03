package ru.shmakova.detbr.ui.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.data.categories.Category;
import ru.shmakova.detbr.data.categories.CategoryClick;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by shmakova on 23.08.16.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private final PublishSubject<CategoryClick> onClickSubject = PublishSubject.create();
    private int selectedItem = -1;

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoryViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, position);
    }

    public Observable<CategoryClick> getPositionClicks() {
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
        @BindView(R.id.category_text)
        TextView categoryText;
        @BindView(R.id.radio)
        RadioButton radioButton;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            View.OnClickListener onClickListener = view -> {
                int[] values = new int[2];
                view.getLocationOnScreen(values);
                int x = values[0] + view.getWidth() / 2;
                int y = values[1] + view.getHeight() / 4;

                onClickSubject.onNext(CategoryClick.create(
                        categories.get(getAdapterPosition()),
                        x,
                        y));

                if (selectedItem == getAdapterPosition()) {
                    selectedItem = -1;
                    categoryText.setTextColor(
                            ContextCompat.getColor(categoryText.getContext(), R.color.dark_grey));
                } else {
                    selectedItem = getAdapterPosition();
                    categoryText.setTextColor(
                            ContextCompat.getColor(categoryText.getContext(), R.color.white));
                }
                notifyDataSetChanged();
            };

            itemView.setOnClickListener(onClickListener);
            radioButton.setOnClickListener(onClickListener);
        }

        public void bind(Category category, int position) {
            categoryText.setText(category.title());
            radioButton.setChecked(position == selectedItem);


            if (selectedItem == -1) {
                categoryText.setTextColor(
                        ContextCompat.getColor(categoryText.getContext(), R.color.dark_grey));
            } else {
                categoryText.setTextColor(
                        ContextCompat.getColor(categoryText.getContext(), R.color.white));
            }

            setRadioButtonIconAndColor(ContextCompat.getDrawable(radioButton.getContext(), category.getDrawableIcon()),
                    Color.parseColor(category.color()));
        }

        private void setRadioButtonIconAndColor(Drawable drawable, int color) {
            StateListDrawable stateListDrawable =
                    (StateListDrawable) CompoundButtonCompat.getButtonDrawable(radioButton);

            LayerDrawable layerDrawable = (LayerDrawable) stateListDrawable.getCurrent();

            if (layerDrawable.getNumberOfLayers() < 3) {
                GradientDrawable iconBackground = (GradientDrawable) layerDrawable.getDrawable(0);
                iconBackground.setColor(color);
                layerDrawable.setDrawableByLayerId(R.id.icon, drawable);
            }
        }
    }
}
