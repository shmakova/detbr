package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.OnClick;
import ru.yandex.detbr.R;

/**
 * Created by shmakova on 26.08.16.
 */

@FragmentWithArgs
public class IntroFragment extends BaseFragment {
    @Arg
    int layoutResId;

    private OnStartButtonClickListener onStartButtonClickListener;

    public interface OnStartButtonClickListener {
        void onStartButtonClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnStartButtonClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnStartButtonClickListener.class.getName());
        }

        onStartButtonClickListener = (OnStartButtonClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onStartButtonClickListener = null;
    }

    @OnClick(R.id.start_btn)
    public void onCardClick() {
        if (onStartButtonClickListener != null) {
            onStartButtonClickListener.onStartButtonClick();
        }
    }
}
