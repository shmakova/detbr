package ru.shmakova.detbr.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import ru.shmakova.detbr.R;
import ru.shmakova.detbr.browser.BrowserActivity;
import ru.shmakova.detbr.card.IntroFragment;
import ru.shmakova.detbr.cards.CardsPagerFragment;
import ru.shmakova.detbr.favorites.FavoritesFragment;
import ru.shmakova.detbr.tabs.TabsFragment;
import ru.shmakova.detbr.utils.ConnectionChecker;
import ru.shmakova.detbr.youtube.YouTubeActivity;

public class NavigationManager {
    public final static String TAB_KEY = "TAB_KEY";
    public final static int CARDS_TAB_POSITION = 0;
    public final static int FAVORITE_TAB_POSITION = 1;
    public final static int TABS_TAB_POSITION = 2;

    private FragmentManager fragmentManager;
    private Activity activity;
    private ConnectionChecker connectionChecker;

    public void init(FragmentManager fragmentManager, Activity activity) {
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        connectionChecker = new ConnectionChecker(activity);
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


    public void openBrowser(String url) {
        if (connectionChecker.isNetworkConnected()) {
            Intent intent = new Intent(activity, BrowserActivity.class);
            intent.setData(Uri.parse(url));
            intent.setAction(Intent.ACTION_VIEW);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void openYoutubePlayer(String videoId) {
        Intent intent = new Intent(activity, YouTubeActivity.class);
        intent.putExtra(YouTubeActivity.KEY_VIDEO_ID, videoId);
        activity.startActivity(intent);
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

    public void openIntro() {
        openAsRoot(new IntroFragment());
    }
}
