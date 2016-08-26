package ru.yandex.detbr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.R;

@FragmentWithArgs
public class ContentFragment extends BaseFragment {
    @BindView(R.id.school)
    TextView schoolTextView;

    @Arg
    String school;

    private OnBrowserButtonClickListener onBrowserButtonClickListener;

    public interface OnBrowserButtonClickListener {
        void onBrowserButtonCLick(String url);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        schoolTextView.setText(school);
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
