package ru.yandex.detbr.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.di.components.SchoolsComponent;
import ru.yandex.detbr.di.modules.SchoolsModule;
import ru.yandex.detbr.ui.adapters.SchoolsAdapter;
import ru.yandex.detbr.ui.presenters.SchoolsPresenter;
import ru.yandex.detbr.ui.views.SchoolsView;

public class SchoolsFragment
        extends BaseLceFragment<FrameLayout, List<String>, SchoolsView, SchoolsPresenter>
        implements SchoolsView {

    @BindView(R.id.schools_autocomplete)
    AutoCompleteTextView autoCompleteTextView;

    private SchoolsAdapter schoolsAdapter;
    private SchoolsComponent schoolsComponent;
    private OnSchoolClickListener onSchoolClickListener;

    public interface OnSchoolClickListener {
        void onSchoolClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    private void injectDependencies() {
        schoolsComponent = App.get(getContext()).applicationComponent().plus(new SchoolsModule());
        schoolsComponent.inject(this);
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

    @NonNull
    @Override
    public SchoolsPresenter createPresenter() {
        return schoolsComponent.presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        schoolsAdapter = new SchoolsAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        autoCompleteTextView.setAdapter(schoolsAdapter);

        autoCompleteTextView.setOnItemClickListener((adapterView, v, i, l) -> {
            hideKeyboard();
            presenter.saveSchool(adapterView.getItemAtPosition(i).toString());
            if (onSchoolClickListener != null) {
                onSchoolClickListener.onSchoolClick();
            }
        });
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
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
    public void setData(List<String> data) {
        if (schoolsAdapter != null) {
            schoolsAdapter.addAll(data);
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadSchools(pullToRefresh);
    }

    @Override
    public LceViewState<List<String>, SchoolsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<String> getData() {
        return (schoolsAdapter == null) ? null : schoolsAdapter.getItems();
    }
}
