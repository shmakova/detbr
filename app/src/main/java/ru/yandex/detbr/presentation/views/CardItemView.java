package ru.yandex.detbr.presentation.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by shmakova on 04.09.16.
 */

public interface CardItemView extends MvpView {
    void setTitle(String title);

    void setLike(boolean like);

    void setDescription(String description);

    void setFavicon(String favicon);

    void setSite(String site);

    void setImage(String url);

    void setBackgroundColor(String color);

    void setWhiteText();
}
