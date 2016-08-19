package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.fragments.SchoolFragment;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements SchoolFragment.OnIntroduceCompleteListener {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    public static final String SCHOOL_TAG = "SCHOOL TAG";
    private String school;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        loadDataFromSharedPreference();
        if (school == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new SchoolFragment())
                    .commit();
        }
        else if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment())
                    .commit();
        }
    }

    void loadDataFromSharedPreference() {
        school = getPreferences(MODE_PRIVATE).getString(SCHOOL_TAG, null);
    }

    @Override
    public void onIntroduceComplete() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new ContentFragment())
                .commit();
        loadDataFromSharedPreference();
    }
}
