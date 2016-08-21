package ru.yandex.detbr.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.schools.SchoolsAdapter;
import ru.yandex.detbr.ui.presenters.SchoolsPresenter;
import ru.yandex.detbr.ui.views.SchoolsView;

public class SchoolsFragment extends BaseFragment implements SchoolsView {
    @Inject
    SchoolsPresenter presenter;
    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.schools_autocomplete)
    AutoCompleteTextView autoCompleteTextView;

    private SchoolsAdapter schoolsAdapter;

    OnSchoolClickListener onSchoolClickListener;

    public interface OnSchoolClickListener {
        void onSchoolClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().schoolsComponent().inject(this);
        presenter.bindView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnSchoolClickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnSchoolClickListener.class.getName());
        }

        onSchoolClickListener = (OnSchoolClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSchoolClickListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schools, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        schoolsAdapter = new SchoolsAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        autoCompleteTextView.setAdapter(schoolsAdapter);
        presenter.loadSchools();

        autoCompleteTextView.setOnItemClickListener((adapterView, v, i, l) -> {
            hideKeyboard();
            presenter.saveSchool(adapterView.getItemAtPosition(i).toString());
            onSchoolClickListener.onSchoolClick();
        });
    }

    private void hideKeyboard() {
        Activity activity = getActivity();
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = activity.getCurrentFocus();

        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick(R.id.schools_autocomplete)
    public void onSchoolsAutocompleteTextViewClick() {
        autoCompleteTextView.showDropDown();
    }

    @Override
    public void setSchoolsData(List<String> schools) {
        schoolsAdapter.addAll(schools);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }
}
