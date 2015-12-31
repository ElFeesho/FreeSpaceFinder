package uk.co.czcz.freespacefinder;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.czcz.freespacefinder.BuildConfig;
import uk.czcz.freespacefinder.CarPark;
import uk.czcz.freespacefinder.CarParkCore;
import uk.czcz.freespacefinder.CarParkParser;

public class AppRoot {

    private static class AndroidLocationProvider implements CarParkCore.LocationProvider {

        private final LocationManager locationProvider;

        public AndroidLocationProvider(Application appContext) {
            locationProvider = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
        public void fetchLocation(Callback location) {
            try {
                Location lastKnownLocation = locationProvider.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                location.locationAvailable(new uk.czcz.freespacefinder.Location(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            }
            catch(SecurityException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class NetworkCarParkFetcher implements CarParkCore.CarParkFetcher {

        @Override
        public void fetch(final int pageNumber, final Callback callback) {
            networkPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://opendata.tfgm.com/api/Carparks?pageIndex=" + pageNumber + "&pageSize=20").openConnection();
                        urlConnection.addRequestProperty("AppKey", BuildConfig.TFGM_APP_KEY);
                        urlConnection.addRequestProperty("DevKey", BuildConfig.TFGM_DEV_KEY);
                        urlConnection.connect();
                        CarParkParser parser = new CarParkParser();
                        try {
                            final List<CarPark> carParkList = parser.parse(urlConnection.getInputStream());
                            if (carParkList.size() > 0) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.carParksRetrieved(carParkList);
                                    }
                                });
                            } else {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.noCarParksRetreieved();
                                    }
                                });
                            }
                        } catch (CarParkParser.CarParkParseAuthorisationException e) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.authorisationError();
                                }
                            });
                        } catch (CarParkParser.CarParkParseNullStreamException | CarParkParser.CarParkParseUnknownErrorException e) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.unknownError();
                                }
                            });
                        } catch (CarParkParser.CarParkParserParseException e) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.parseError();
                                }
                            });
                        }
                    } catch (IOException e) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.parseError();
                            }
                        });
                    }
                }
            });
        }
    }

    private ExecutorService networkPool = Executors.newCachedThreadPool();
    private CarParkCore carParkCore;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public AppRoot(Application appContext) {
        carParkCore = new CarParkCore(new NetworkCarParkFetcher(), new AndroidLocationProvider(appContext));
    }

    public CarParkCore getCarParkCore() {
        return carParkCore;
    }
}
