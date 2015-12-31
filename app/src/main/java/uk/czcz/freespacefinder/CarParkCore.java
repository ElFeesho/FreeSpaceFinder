package uk.czcz.freespacefinder;

import java.util.List;

public class CarParkCore {

    public interface LocationProvider
    {
        interface Callback
        {
            void locationAvailable(Location location);
        }

        void fetchLocation(Callback location);
    }

    public interface CarParkFetcher
    {
        interface Callback
        {
            void noCarParksRetreieved();
            void carParksRetrieved(List<CarPark> carParks);
            void authorisationError();
            void unknownError();
            void parseError();
        }

        void fetch(int pageNumber, Callback callback);
    }

    public interface CarParkFetchCallback {

        void noCarParksRetrieved();
        void carParksFetched(List<CarPark> carParks);
        void authorisationError();
        void unknownError();
        void parseError();
    }

    public interface LocationFetchCallback {
        void locationFetched(Location location);
    }

    private final CarParkFetcher carParkFetcher;
    private final LocationProvider locationProvider;

    public CarParkCore(CarParkFetcher carParkFetcher, LocationProvider locationProvider) {
        this.carParkFetcher = carParkFetcher;
        this.locationProvider = locationProvider;
    }

    public void fetchCarParks(int pageNumber, final CarParkFetchCallback fetchCallback) {
        carParkFetcher.fetch(pageNumber, new CarParkFetcher.Callback() {
            @Override
            public void noCarParksRetreieved() {
                fetchCallback.noCarParksRetrieved();
            }

            @Override
            public void carParksRetrieved(List<CarPark> carParks) {
                fetchCallback.carParksFetched(carParks);
            }

            @Override
            public void authorisationError() {
                fetchCallback.authorisationError();
            }

            @Override
            public void unknownError() {
                fetchCallback.unknownError();
            }

            @Override
            public void parseError() {
                fetchCallback.parseError();
            }
        });
    }

    public void fetchLocation(final LocationFetchCallback locationFetchCallback)
    {
        locationProvider.fetchLocation(new LocationProvider.Callback() {
            @Override
            public void locationAvailable(Location location) {
                locationFetchCallback.locationFetched(location);
            }
        });
    }
}
