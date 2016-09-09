package ru.yandex.detbr.ui.other;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

import com.arlib.floatingsearchview.util.Util;

import ru.yandex.detbr.R;


/**
 * Created by shmakova on 09.09.16.
 */

public class CarouselPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final float MAX_ELEVATION = Util.dpToPx(4);

    @Override
    public void transformPage(View view, float position) {
        CardView cardView = (CardView) view.findViewById(R.id.card);

        if (position < -1) { // [-Infinity,-1)
            view.setScaleX(0.75f);
            view.setScaleY(0.75f);
            cardView.setCardElevation(Util.dpToPx(1));
        } else if (position <= 0) { // [-1,0]
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            float elevationFactor = MAX_ELEVATION + (1 - MAX_ELEVATION) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            cardView.setCardElevation(elevationFactor * MAX_ELEVATION);
        } else if (position <= 1) { // [0,1]
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            float elevationFactor = MAX_ELEVATION + (1 - MAX_ELEVATION) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            cardView.setCardElevation(elevationFactor * MAX_ELEVATION);
        } else { // (1,+Infinity]
            view.setScaleX(0.75f);
            view.setScaleY(0.75f);
            cardView.setCardElevation(Util.dpToPx(1));
        }

    }
}