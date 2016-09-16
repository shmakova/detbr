package ru.yandex.detbr.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.URISyntaxException;

import ru.yandex.detbr.presentation.views.BrowserView;
import timber.log.Timber;

/**
 * Created by user on 16.09.16.
 */

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
        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent.resolveActivity(packageManager) != null) {
                    activity.startActivity(intent);
                    return true;
                }
                String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                if (fallbackUrl != null) {
                    listener.onLoadUrl(fallbackUrl);
                    return true;
                }
                Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(
                        Uri.parse("market://details?id=" + intent.getPackage()));
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
