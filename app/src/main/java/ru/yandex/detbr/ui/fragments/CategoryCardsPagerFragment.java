package ru.yandex.detbr.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Category;

/**
 * Created by shmakova on 21.08.16.
 */

@FragmentWithArgs
public class CategoryCardsPagerFragment extends BaseCardsPagerFragment {
    @Arg
    Category category;

    @BindView(R.id.category_cards_wrapper)
    LinearLayout categoryCardsWrapper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_cards, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCover();

        if (savedInstanceState == null) {
            CategoriesFragment categoriesFragment = new CategoriesFragmentBuilder()
                    .category(category)
                    .build();
            if (fragmentManager != null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.categories_frame_layout, categoriesFragment)
                        .commit();
            }
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadCardsByCategory(category, pullToRefresh);
    }

    private void setCover() {
        if (category.color() != null) {
            categoryCardsWrapper.setBackgroundColor(Color.parseColor(category.color()));
        }
    }
}
