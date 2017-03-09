package ru.shmakova.detbr.card;

import com.hannesdorfmann.mosby.mvp.MvpView;

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
