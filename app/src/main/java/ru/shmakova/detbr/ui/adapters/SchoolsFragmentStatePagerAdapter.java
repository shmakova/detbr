package ru.shmakova.detbr.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.shmakova.detbr.ui.fragments.SchoolsFragment;

/**
 * Created by shmakova on 22.08.16.
 */

public class SchoolsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    public SchoolsFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new SchoolsFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}