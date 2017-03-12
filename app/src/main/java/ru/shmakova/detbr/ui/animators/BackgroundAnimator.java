package ru.shmakova.detbr.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;

public final class BackgroundAnimator {
    private BackgroundAnimator() {
    }

    public static void changeBackgroundColor(View backgroundView,
                                             View previousBackgroundView,
                                             View divider,
                                             int color,
                                             int dividerColor,
                                             int x, int y) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            previousBackgroundView.setVisibility(View.VISIBLE);
            backgroundView.setBackgroundColor(color);
            int finalRadius = Math.max(backgroundView.getWidth(), backgroundView.getHeight());
            AnimatorSet set = new AnimatorSet();
            Animator backgroundAnimator = ViewAnimationUtils.createCircularReveal(backgroundView, x, y, 0, finalRadius);
            divider.setBackgroundColor(dividerColor);
            ObjectAnimator dividerFadeInAnimator = ObjectAnimator.ofFloat(divider, "alpha", 0f, 1f);

            set.playTogether(backgroundAnimator, dividerFadeInAnimator);
            set.setDuration(300);
            set.setInterpolator(new FastOutSlowInInterpolator());

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    previousBackgroundView.setBackgroundColor(color);
                    previousBackgroundView.setVisibility(View.GONE);
                }
            });
            set.start();
        } else {
            backgroundView.setBackgroundColor(color);
        }
    }
}
