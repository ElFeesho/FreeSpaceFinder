package uk.czcz.freespacefinder;

import java.util.List;

public class CarParkCore {

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
}
