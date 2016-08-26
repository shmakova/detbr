package ru.yandex.detbr.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import ru.yandex.detbr.R;

/**
 * Created by shmakova on 22.08.16.
 */

@FragmentWithArgs
public class ImageCardFragment extends CardFragment {
    @BindView(R.id.cover)
    ImageView cover;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_image_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (card != null) {
            Glide.with(getActivity())
                    .load(card.getCover())
                    .centerCrop()
                    .crossFade()
                    .into(cover);
        }
    }
}
