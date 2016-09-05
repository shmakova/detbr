package ru.yandex.detbr.ui.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by shmakova on 04.09.16.
 */

public interface CardView extends MvpView {
    void setTitle(String title);

    void setLike(boolean like);

    void setDescription(String description);

    void setFavicon(String favicon);

    void setSite(String site);

    void setImage(String url);
}
