package ru.shmakova.detbr.browser;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import ru.shmakova.detbr.R;
import timber.log.Timber;

public class SafeVpnService extends VpnService {
    private VpnService.Builder builder;
    private ParcelFileDescriptor mInterface;
    private boolean shouldRun;
    private DatagramChannel tunnel;

    public SafeVpnService() {
        this.shouldRun = true;
        this.builder = new VpnService.Builder();
    }

    public int onStartCommand(final Intent intent, final int n, final int n2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mInterface = builder
                            .setSession(getString(R.string.app_name))
                            .addAddress("192.168.0.1", 24)
                            .addDnsServer("77.88.8.7")
                            .addDnsServer("77.88.8.3")
                            .establish();
                    tunnel = DatagramChannel.open();
                    tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
                    protect(tunnel.socket());

                    while (shouldRun) {
                        Thread.sleep(100L);
                    }
                } catch (Exception e) {
                    Timber.e(e);
                } finally {
                    try {
                        if (mInterface != null) {
                            mInterface.close();
                            mInterface = null;
                        }
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                }
            }
        }, getString(R.string.app_name)).start();
        return START_STICKY;
    }
}
