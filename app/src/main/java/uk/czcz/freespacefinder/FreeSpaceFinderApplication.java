package uk.czcz.freespacefinder;

import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;

import java.io.File;
import java.io.IOException;

import uk.co.czcz.freespacefinder.AppRoot;

public class FreeSpaceFinderApplication extends Application {

    private CarParkCore carParkCore;

    public static CarParkCore getCarParkCore(Context context) {
        return ((FreeSpaceFinderApplication) context.getApplicationContext()).carParkCore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024;
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        carParkCore = new AppRoot(this).getCarParkCore();
    }
}
