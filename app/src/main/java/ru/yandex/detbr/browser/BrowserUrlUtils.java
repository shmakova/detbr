package ru.yandex.detbr.browser;

import android.util.Patterns;

/**
 * Created by shmakova on 19.08.16.
 */


public class BrowserUrlUtils {
    private static final String YANDEX_SEARCH_URL = "https://yandex.ru/yandsearch?family=yes&lr=213&text=";
    private static final String YANDEX_URL = "yandex.";
    private static final String YANDEX_SAFE_PARAMETER = "family=yes";
    private static final String YANDEX_QUERY_PARAMETER = "text=";
    private static final String GOOGLE_URL = "google.";
    private static final String GOOGLE_SAFE_PARAMETER = "safe=high";
    private static final String GOOGLE_QUERY_PARAMETER = "q=";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private static boolean isValidUrl(String query) {
        return Patterns.WEB_URL.matcher(query).matches();
    }

    public static String getSafeUrlFromQuery(String query) {
        String urlString = query;

        if (isValidUrl(query)) {
            if (!query.startsWith(HTTP_PREFIX) && !query.startsWith(HTTPS_PREFIX)) {
                urlString = HTTP_PREFIX + query;
            }
        } else {
            urlString = YANDEX_SEARCH_URL + query;
        }

        return urlString;
    }

    static String getSafeUrl(String url) {
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
