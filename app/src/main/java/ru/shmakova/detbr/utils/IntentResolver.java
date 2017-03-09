package ru.shmakova.detbr.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URISyntaxException;

import ru.shmakova.detbr.browser.BrowserView;
import timber.log.Timber;

public final class IntentResolver {
    private IntentResolver() {
    }

    public static boolean resolveUrl(Activity activity, String url, BrowserView.LoadUrlListener listener) {
        Uri parsedUri = Uri.parse(url);
        PackageManager packageManager = activity.getPackageManager();
        Intent browseIntent = new Intent(Intent.ACTION_VIEW).setData(parsedUri);
        if (browseIntent.resolveActivity(packageManager) != null) {
            activity.startActivity(browseIntent);
            return true;
        }
        if (UrlUtils.isIntent(url)) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent.resolveActivity(packageManager) != null) {
                    activity.startActivity(intent);
                    return true;
                }
                String fallbackUrl = intent.getStringExtra(UrlUtils.BROWSER_FALLBACK_URL);
                if (fallbackUrl != null) {
                    listener.onLoadUrl(fallbackUrl);
                    return true;
                }
                Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(
                        Uri.parse(UrlUtils.MARKET_URL + intent.getPackage()));
                if (marketIntent.resolveActivity(packageManager) != null) {
                    activity.startActivity(marketIntent);
                    return true;
                }
            } catch (URISyntaxException e) {
                Timber.e(e, "Parse URI from not http-url");
            }
        }
        return true;
    }
}
