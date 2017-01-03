package ru.shmakova.detbr.utils;

import java.io.IOException;

/**
 * Created by shmakova on 27.08.16.
 */

public class ErrorMessageDeterminer {

    public String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (e instanceof IOException) {
            return "Ошибка сети";
        }

        return "Перезапустите приложение";
    }
}