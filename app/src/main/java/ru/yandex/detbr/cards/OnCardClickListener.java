package ru.yandex.detbr.cards;

/**
 * Created by shmakova on 25.08.16.
 */

public interface OnCardClickListener {
    void onCardItemClick(int position);

    void onLikeClick(int position);
}