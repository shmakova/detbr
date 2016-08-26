package ru.yandex.detbr.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

import butterknife.OnClick;
import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.fragments.IntroFragment;
import ru.yandex.detbr.ui.fragments.IntroFragmentBuilder;

/**
 * Created by shmakova on 26.08.16.
 */

public class IntroActivity extends AppIntro implements IntroFragment.OnStartButtonClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSkipButton(false);
        setProgressButtonEnabled(false);
        showStatusBar(false);
        addSlide(new IntroFragmentBuilder(R.layout.fragment_intro).build());
    }

    @OnClick(R.id.start_btn)
    public void onStartButton() {
        loadMainActivity();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStartButtonClick() {
        loadMainActivity();
    }
}
