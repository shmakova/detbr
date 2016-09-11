package ru.yandex.detbr.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.lce.LceAnimator;

/**
 * Created by shmakova on 31.08.16.
 */

public final class CardsAnimator extends LceAnimator {

    private CardsAnimator() {
    }

    public static void hideLoading(@NonNull View loadingView, @NonNull View contentView,
                                   @NonNull View errorView) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    public static void showLoading(@NonNull View loadingView, @NonNull View contentView,
                                   @NonNull View errorView) {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    public static void showContent(@NonNull final View loadingView, @NonNull final View contentView,
                                   @NonNull final View errorView, @NonNull final View backwardView,
                                   final int color) {

        if (contentView.getVisibility() == View.VISIBLE) {
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        } else {
            errorView.setVisibility(View.GONE);

            AnimatorSet set = new AnimatorSet();
            ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);

            ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f);

            set.playTogether(contentFadeIn, loadingFadeOut);
            set.setDuration(500);

            set.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    contentView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingView.setVisibility(View.GONE);
                    loadingView.setAlpha(1f);
                }
            });

            set.start();
        }
    }
}