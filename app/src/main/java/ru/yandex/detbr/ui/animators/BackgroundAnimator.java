package ru.yandex.detbr.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by shmakova on 14.09.16.
 */

public final class BackgroundAnimator {
    private BackgroundAnimator() {
    }

    public static void setCategoryCardsBackwardBackgroundColor(View backgroundView,
                                                               View previousBackgroundView,
                                                               int color, int x, int y) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            backgroundView.setBackgroundColor(color);
            int finalRadius = Math.max(backgroundView.getWidth(), backgroundView.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(backgroundView, x, y, 0, finalRadius);
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    previousBackgroundView.setBackgroundColor(color);
                }
            });
        } else {
            backgroundView.setBackgroundColor(color);
        }
    }
}
