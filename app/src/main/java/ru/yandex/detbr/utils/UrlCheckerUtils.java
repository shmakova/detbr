package ru.yandex.detbr.utils;

import android.util.Patterns;

import java.util.Locale;

/**
 * Created by shmakova on 28.08.16.
 */

public class UrlCheckerUtils {
    private static final String YANDEX_SEARCH_URL = "https://yandex.ru/yandsearch?family=yes&lr=213&text=";
    private static final String YANDEX_URL = "yandex.";
    private static final String YANDEX_SAFE_PARAMETER = "family=yes";
    private static final String YANDEX_QUERY_PARAMETER = "text=";
    private static final String GOOGLE_URL = "google.";
    private static final String GOOGLE_SAFE_PARAMETER = "safe=high";
    private static final String GOOGLE_QUERY_PARAMETER = "q=";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private UrlCheckerUtils() {
    }

    private static boolean isValidUrl(String query) {
        return Patterns.WEB_URL.matcher(query).matches();
    }

    private static String getUrlFromQuery(String query) {
        String urlString = query.toLowerCase(Locale.getDefault());

        if (isValidUrl(urlString)) {
            if (!(urlString.startsWith(HTTP_PREFIX) || urlString.startsWith(HTTPS_PREFIX))) {
                urlString = HTTP_PREFIX + urlString;
            }
        } else {
            urlString = YANDEX_SEARCH_URL + urlString;
        }

        return urlString;
    }

    public static String getSafeUrlFromQuery(String query) {
        String url = getUrlFromQuery(query);

        if (url.contains(GOOGLE_URL) &&
                !url.contains(GOOGLE_SAFE_PARAMETER) &&
                url.contains(GOOGLE_QUERY_PARAMETER)) {
            url += "&" + GOOGLE_SAFE_PARAMETER;
        } else if (url.contains(YANDEX_URL) &&
                !url.contains(YANDEX_SAFE_PARAMETER) &&
                url.contains(YANDEX_QUERY_PARAMETER)) {
            url += "&" + YANDEX_SAFE_PARAMETER;
        }

        return url;
    }
}
