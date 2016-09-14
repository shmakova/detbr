package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import ru.yandex.detbr.R;


/**
 * Created by shmakova on 26.08.16.
 */

public class IntroFragment extends BaseFragment {

    private OnStartClickListener onStartClickListener;

    public interface OnStartClickListener {
        void onStartClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnStartClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnStartClickListener.class.getName());
        }

        onStartClickListener = (OnStartClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onStartClickListener = null;
    }

    @OnClick(R.id.start_btn)
    public void onStartButtonClick() {
        if (onStartClickListener != null) {
            onStartClickListener.onStartClick();
        }
    }
}
