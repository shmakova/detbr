package ru.yandex.detbr.managers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import ru.yandex.detbr.R;
import ru.yandex.detbr.data.repository.models.Category;
import ru.yandex.detbr.ui.activities.BrowserActivity;
import ru.yandex.detbr.ui.activities.IntroActivity;
import ru.yandex.detbr.ui.activities.MainActivity;
import ru.yandex.detbr.ui.fragments.CardsPagerFragment;
import ru.yandex.detbr.ui.fragments.CategoryCardsPagerFragmentBuilder;
import ru.yandex.detbr.ui.fragments.FavoritesFragment;
import ru.yandex.detbr.ui.fragments.TabsFragment;
import ru.yandex.detbr.utils.ConnectionChecker;

/**
 * Created by shmakova on 29.08.16.
 */

public class NavigationManager {

    private NavigationListener listener;
    private FragmentManager fragmentManager;
    private Activity activity;
    private ConnectionChecker connectionChecker;

    public interface NavigationListener {
        void onNavigationBack();
    }

    public void init(FragmentManager fragmentManager, Activity activity) {
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        connectionChecker = new ConnectionChecker(activity);
    }

    private void open(Fragment fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void openAsRoot(Fragment fragment) {
        clearBackStack();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    private void clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        if (connectionChecker.isNetworkConnected()) {
            Intent intent = new Intent(activity, BrowserActivity.class);
            intent.setData(Uri.parse(url));
            intent.setAction(Intent.ACTION_VIEW);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "Проверьте интернет соединение", Toast.LENGTH_SHORT).show();
        }
    }

    public void openOnBoarding() {
        Intent intent = new Intent(activity, IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public void openCards() {
        openAsRoot(new CardsPagerFragment());
    }

    public void openTabs() {
        openAsRoot(new TabsFragment());
    }

    public void openFavorites() {
        openAsRoot(new FavoritesFragment());
    }

    public void openCategoryCards(Category category) {
        clearBackStack();
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

    public void selectTabAtPosition(int position) {
        ((MainActivity) activity).selectTabAtPosition(position);
    }
}
