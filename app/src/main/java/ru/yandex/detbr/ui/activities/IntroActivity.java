package ru.yandex.detbr.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.adapters.IntroFragmentStatePagerAdapter;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

/**
 * Created by shmakova on 26.08.16.
 */

public class IntroActivity extends AppCompatActivity implements SchoolsFragment.OnSchoolClickListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator circlePageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        viewPager.setAdapter(new IntroFragmentStatePagerAdapter(getSupportFragmentManager()));
        circlePageIndicator.setViewPager(viewPager);
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSchoolClick() {
        loadMainActivity();
    }
}
