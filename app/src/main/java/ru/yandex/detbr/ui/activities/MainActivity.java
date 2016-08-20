package ru.yandex.detbr.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.detbr.App;
import ru.yandex.detbr.R;
import ru.yandex.detbr.developer_settings.DeveloperSettingsModule;
import ru.yandex.detbr.ui.fragments.ContentFragment;
import ru.yandex.detbr.ui.fragments.SchoolFragment;
import ru.yandex.detbr.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements
        SchoolFragment.OnIntroduceCompleteListener,
        ContentFragment.OnBrowserButtonClickListener {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    public static final String SCHOOL_TAG = "SCHOOL TAG";
    private String school;

    @SuppressLint("InflateParams")
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

    @Override
    public void onBrowserButtonCLick(String url) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
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
