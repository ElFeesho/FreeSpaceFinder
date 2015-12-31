package uk.czcz.freespacefinder;

import android.app.Application;
import android.content.Context;

import uk.co.czcz.freespacefinder.AppRoot;

public class FreeSpaceFinderApplication extends Application {

    private CarParkCore carParkCore;

    @Override
    public void onCreate() {
        super.onCreate();
        carParkCore = new AppRoot(this).getCarParkCore();
    }

    public static CarParkCore getCarParkCore(Context context)
    {
        return ((FreeSpaceFinderApplication)context.getApplicationContext()).carParkCore;
    }
}
