package ru.yandex.detbr.utils;

import android.util.Patterns;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by shmakova on 28.08.16.
 */

public final class UrlUtils {
    private static final String YANDEX_SEARCH_URL = "https://yandex.ru/yandsearch?family=yes&lr=213&text=";
    private static final String YANDEX_URL = "yandex.";
    private static final String YANDEX_SAFE_PARAMETER = "family=yes";
    private static final String YANDEX_QUERY_PARAMETER = "text=";
    private static final String GOOGLE_URL = "google.";
    private static final String GOOGLE_SAFE_PARAMETER = "safe=high";
    private static final String GOOGLE_QUERY_PARAMETER = "q=";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private UrlUtils() {
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
            urlString = urlString.replace(' ', '+');
        }

        return urlString;
    }

    public static String getSafeUrlFromQuery(String query) {
        String safeUrl = getUrlFromQuery(query);

        if (query.contains(GOOGLE_URL) &&
                !query.contains(GOOGLE_SAFE_PARAMETER) &&
                query.contains(GOOGLE_QUERY_PARAMETER)) {
            safeUrl += "&" + GOOGLE_SAFE_PARAMETER;
        } else if (query.contains(YANDEX_URL) &&
                !query.contains(YANDEX_SAFE_PARAMETER) &&
                query.contains(YANDEX_QUERY_PARAMETER)) {
            safeUrl += "&" + YANDEX_SAFE_PARAMETER;
        }

        return safeUrl;
    }

    public static String getHost(String url) {
        String host = "";

        try {
            URI uri = new URI(url);
            host = uri.getHost();
            if (host.startsWith("www.")) {
                host = host.substring(4);
            }
            if (host.startsWith("m.")) {
                host = host.substring(2);
            }
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }

        return host;
    }

    public static boolean isHttpLink(String url) {
        return url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX);
    }
}
