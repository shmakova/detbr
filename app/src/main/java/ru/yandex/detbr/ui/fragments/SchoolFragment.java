package ru.yandex.detbr.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.school.SchoolArrayAdapter;
import ru.yandex.detbr.school.SchoolsData;
import ru.yandex.detbr.ui.activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class SchoolFragment extends BaseFragment {

    @BindView(R.id.intro_text_view)
    AutoCompleteTextView autoCompleteTextView;
    private List<String> schoolData;

    OnIntroduceCompleteListener listener;
    @Inject
    SchoolsData dataProvider;

    public interface OnIntroduceCompleteListener {
        void onIntroduceComplete();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().schoolDataComponent().inject(this);
        schoolData = dataProvider.getSchoolsList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnIntroduceCompleteListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnIntroduceCompleteListener.class.getName());
        }
        listener = (OnIntroduceCompleteListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> adapter = new SchoolArrayAdapter(getContext(),
                android.R.layout.simple_dropdown_item_1line, schoolData);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.showDropDown();
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Activity activity = getActivity();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocusView = activity.getCurrentFocus();
                if (currentFocusView != null)
                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                saveToSharedPreference(adapterView.getItemAtPosition(i).toString());
                listener.onIntroduceComplete();
            }
        });
    }
    void saveToSharedPreference(String s) {
        getActivity().getPreferences(MODE_PRIVATE).edit().putString(MainActivity.SCHOOL_TAG, s).apply();
    }
}
