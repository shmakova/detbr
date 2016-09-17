package ru.yandex.detbr.data.stopwords;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shmakova on 17.09.16.
 */

public class StopWordsRepositoryImpl implements StopWordsRepository {
    private List<String> stopWords;

    public StopWordsRepositoryImpl() {
        stopWords = new ArrayList<>();
        stopWords.add("порно");
        stopWords.add("хуй");
        stopWords.add("пизда");
        stopWords.add("ебать");
        stopWords.add("блядь");
        stopWords.add("блять");
        stopWords.add("сиськи");
        stopWords.add("passion hd vk");
    }


    @Override
    public boolean isStopWord(String word) {
        return stopWords.indexOf(word.toLowerCase(Locale.getDefault())) >= 0;
    }
}
