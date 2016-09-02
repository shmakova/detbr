package ru.yandex.detbr.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;

import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.fragments.IntroFragmentBuilder;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

/**
 * Created by shmakova on 26.08.16.
 */

public class IntroActivity extends AppIntro implements SchoolsFragment.OnSchoolClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSkipButton(false);
        setProgressButtonEnabled(false);
        showStatusBar(true);
        setFlowAnimation();
        setSeparatorColor(ContextCompat.getColor(this, android.R.color.transparent));
        addSlide(new IntroFragmentBuilder(R.layout.fragment_intro_first).build());
        addSlide(new IntroFragmentBuilder(R.layout.fragment_intro_second).build());
        addSlide(new SchoolsFragment());
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
