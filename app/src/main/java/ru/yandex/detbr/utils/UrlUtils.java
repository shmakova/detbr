package ru.yandex.detbr.utils;

import android.util.Patterns;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Created by shmakova on 28.08.16.
 */

public final class UrlUtils {
    public static final String YANDEX_SEARCH_URL = "https://yandex.ru/yandsearch?family=yes&lr=213&text=";
    public static final String YANDEX_URL = "yandex.";
    public static final String YANDEX_SAFE_PARAMETER = "family=yes";
    public static final String YANDEX_QUERY_PARAMETER = "text=";
    public static final String GOOGLE_URL = "google.";
    public static final String GOOGLE_SAFE_PARAMETER = "safe=high";
    public static final String GOOGLE_QUERY_PARAMETER = "q=";
    public static final String BROWSER_FALLBACK_URL = "browser_fallback_url";
    public static final String MARKET_URL = "market://details?id=";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final String INTENT_PREFIX = "intent://";

    private UrlUtils() {
    }

    private static boolean isValidUrl(String query) {
        return Patterns.WEB_URL.matcher(query).matches();
    }

    public static String getUrlFromQuery(String query) {
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

    public static String getHost(String url) {
        String host = null;

        try {
            URI uri = new URI(url);
            host = uri.getHost();

            if (host == null) {
                host = "";
            } else {
                host = removeSubdomains(host);
            }
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }

        return host;
    }

    public static boolean isHttpLink(String url) {
        return url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX);
    }

    public static boolean isIntent(String url) {
        return url.startsWith(INTENT_PREFIX);
    }

    private static String removeSubdomains(String link) {
        Pattern pattern = Pattern.compile("^(www\\.|m\\.)(.+)");
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            return link;
        }
    }
}
