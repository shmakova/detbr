package ru.yandex.detbr.ui.managers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.ui.activities.BrowserActivity;
import ru.yandex.detbr.ui.fragments.CardsPagerFragment;
import ru.yandex.detbr.ui.fragments.CategoryCardsPagerFragmentBuilder;
import ru.yandex.detbr.ui.fragments.FavoritesFragment;
import ru.yandex.detbr.ui.fragments.SchoolsFragment;

/**
 * Created by shmakova on 29.08.16.
 */

public class NavigationManager {

    private NavigationListener listener;
    private FragmentManager fragmentManager;
    private Activity activity;

    public interface NavigationListener {
        void onNavigationBack();
    }

    public void init(FragmentManager fragmentManager, Activity activity) {
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    private void open(Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }

    private void openAsRoot(Fragment fragment) {
        popEveryFragment();
        open(fragment);
    }

    private void popEveryFragment() {
        int backStackCount = fragmentManager.getBackStackEntryCount();

        for (int i = 0; i < backStackCount; i++) {
            int backStackId = fragmentManager.getBackStackEntryAt(i).getId();
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void navigateBack(Activity activity) {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            activity.finish();
        } else {
            fragmentManager.popBackStackImmediate();
        }
    }

    public void openBrowser(String url) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivity(intent);
    }

    public void openSchools() {
        openAsRoot(new SchoolsFragment());
    }

    public void openCards() {
        openAsRoot(new CardsPagerFragment());
    }

    public void openFavorites() {
        openAsRoot(new FavoritesFragment());
    }

    public void openCategoryCards(Category category) {
        Fragment fragment = new CategoryCardsPagerFragmentBuilder(category).build();
        open(fragment);
    }

    public void onBackPressed() {
        if (listener != null) {
            listener.onNavigationBack();
        }
    }

    public void finish(Activity activity) {
        activity.finish();
    }

    public boolean isRootFragmentVisible() {
        return fragmentManager.getBackStackEntryCount() <= 1;
    }

    public NavigationListener getNavigationListener() {
        return listener;
    }

    public void setNavigationListener(NavigationListener listener) {
        this.listener = listener;
    }
}