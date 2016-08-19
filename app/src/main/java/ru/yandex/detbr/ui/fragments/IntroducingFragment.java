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
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.database_staff.FakeSchoolListGetter;
import ru.yandex.detbr.database_staff.SchoolListGetter;
import ru.yandex.detbr.ui.activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class IntroducingFragment extends BaseFragment {

    @BindView(R.id.intro_text_view)
    AutoCompleteTextView textView;
    private List<String> schoolData;

    private onIntroduceCompleteListener listener;
    private SchoolListGetter listGetter;

    public interface onIntroduceCompleteListener {
        void onIntroduceComplete();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof onIntroduceCompleteListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    onIntroduceCompleteListener.class.getName());
        }
        // TODO убрать эту глупую строку. Использовать dagger?
        listGetter = new FakeSchoolListGetter();
        schoolData = listGetter.getListOfSchools();
        listener = (onIntroduceCompleteListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introducing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, schoolData);
        textView.setAdapter(adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.showDropDown();
            }
        });
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Activity activity = getActivity();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                // возможно есть способ, как получить текст из выбранного элемента, но у меня не получилось
                saveToSharedPreference(schoolData.get(i));
                listener.onIntroduceComplete();
            }
        });
    }
    void saveToSharedPreference(String s) {
        getActivity().getPreferences(MODE_PRIVATE).edit().putString(MainActivity.SCHOOL_TAG, s).apply();
    }

    // можно смело убирать эту кнопку с:
    @OnClick(R.id.intro_btn_continue)
    void onContinueButtonClick() {
        String res = textView.getText().toString();
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
        if (isWrittenCorrect(res, schoolData)) {
            saveToSharedPreference(res);
            listener.onIntroduceComplete();
        } else
            Toast.makeText(getContext(), "School isn't correct", Toast.LENGTH_SHORT).show();
    }

    boolean isWrittenCorrect(String s, List<String> sList) {
        for (int i = 0; i < sList.size(); ++i)
            if (s.equals(sList.get(i)))
                return true;
        return false;
    }
}
