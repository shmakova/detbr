package ru.shmakova.detbr.utils;

import java.io.IOException;

public class ErrorMessageDeterminer {

    public String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (e instanceof IOException) {
            return "Ошибка сети";
        }

        return "Перезапустите приложение";
    }
}
