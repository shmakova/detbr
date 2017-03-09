package ru.shmakova.detbr.card;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import ru.shmakova.detbr.R;
import ru.shmakova.detbr.base.BaseFragment;

public class LastCardFragment extends BaseFragment {
    private OnBackButtonClickListener onBackButtonClickListener;

    public interface OnBackButtonClickListener {
        void onBackButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_card, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getParentFragment() instanceof OnBackButtonClickListener)) {
            throw new ClassCastException(getParentFragment().toString() + " must implement " +
                    OnBackButtonClickListener.class.getName());
        }

        onBackButtonClickListener = (OnBackButtonClickListener) getParentFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
    }

    @OnClick(R.id.back_btn)
    public void onBackButtonClick() {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonClick();
        }
    }

}
