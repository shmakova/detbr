package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import ru.yandex.detbr.R;

public class ContentFragment extends BaseFragment {
    private OnBrowserButtonClickListener onBrowserButtonClickListener;

    public interface OnBrowserButtonClickListener {
        void onBrowserButtonCLick(String url);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @OnClick(R.id.browser_button)
    public void onSearchButtonClick() {
        if (onBrowserButtonClickListener != null) {
            onBrowserButtonClickListener.onBrowserButtonCLick("http://yandex.ru");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnBrowserButtonClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnBrowserButtonClickListener.class.getName());
        }

        onBrowserButtonClickListener = (OnBrowserButtonClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBrowserButtonClickListener = null;
    }
}
