package ru.yandex.detbr.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.yandex.detbr.R;
import ru.yandex.detbr.ui.fragments.IntroFragmentBuilder;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

/**
 * Created by shmakova on 02.09.16.
 */

public class IntroFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGES = 3;

    public IntroFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IntroFragmentBuilder(R.layout.fragment_intro_first).build();
            case 1:
                return new IntroFragmentBuilder(R.layout.fragment_intro_second).build();
            case 2:
                return new SchoolsFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }
}
