package ru.shmakova.detbr.data.stopwords;

/**
 * Created by shmakova on 17.09.16.
 */

public interface StopWordsRepository {
    boolean isStopWord(String word);
}
