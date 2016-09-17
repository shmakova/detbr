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
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final String YOUTUBE_URL_REGEX = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    private static final String[] VIDEO_ID_REGEX = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

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
        } catch (URISyntaxException e) {
            Timber.e(e, "Error while parsing url");
        }

        return host;
    }

    public static boolean isHttpLink(String url) {
        return url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX);
    }

    public static String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for (String regex : VIDEO_ID_REGEX) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private static String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(YOUTUBE_URL_REGEX);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }
}
