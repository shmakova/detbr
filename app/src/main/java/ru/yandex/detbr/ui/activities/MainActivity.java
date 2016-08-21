package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.schools.SchoolsRepository;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.fragments.ContentFragmentBuilder;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements
        SchoolsFragment.OnSchoolClickListener,
        ContentFragment.OnBrowserButtonClickListener {

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    @Inject
    SharedPreferences sharedPreferences;

    private FragmentManager supportFragmentManager;
    private String school;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        supportFragmentManager = getSupportFragmentManager();

        loadDataFromSharedPreference();

        if (school == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new SchoolsFragment())
                    .commit();
        } else if (savedInstanceState == null) {
            showContentFragment();
        }
    }

    public void showContentFragment() {
        Fragment fragment = new ContentFragmentBuilder(school).build();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .commit();
    }

    @Override
    public void onBrowserButtonCLick(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }

    void loadDataFromSharedPreference() {
        school = sharedPreferences.getString(SchoolsRepository.SCHOOL_TAG, null);
    }

    @Override
    public void onSchoolClick() {
        loadDataFromSharedPreference();
        showContentFragment();
    }
}
