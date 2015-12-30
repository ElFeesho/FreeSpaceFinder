package uk.czcz.freespacefinder;

import java.util.List;

public class CarParkCore {

    public interface CarParkFetcher
    {
        interface Callback
        {
            void noCarParksRetreieved();
            void carParksRetrieved(List<CarPark> carParks);
        }

        void fetch(int pageNumber, Callback callback);
    }

    public interface CarParkFetchCallback {

        void noCarParksRetrieved();
        void carParksFetched(List<CarPark> carParks);
    }

    private final CarParkFetcher carParkFetcher;

    public CarParkCore(CarParkFetcher carParkFetcher) {
        this.carParkFetcher = carParkFetcher;
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
        });
    }
}
