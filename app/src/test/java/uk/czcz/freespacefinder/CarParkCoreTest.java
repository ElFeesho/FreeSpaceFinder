package uk.czcz.freespacefinder;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarParkCoreTest {

    private static class SpyCarParkFetchCallback implements CarParkCore.CarParkFetchCallback {

        public boolean noCarParksRetrieved_called;
        public List<CarPark> carParksFetched_called;

        @Override
        public void noCarParksRetrieved()
        {
            noCarParksRetrieved_called = true;
        }

        @Override
        public void carParksFetched(List<CarPark> carParks) {
            carParksFetched_called = carParks;
        }
    }

    private class NoCarParkFetcher implements CarParkCore.CarParkFetcher {

        @Override
        public void fetch(int pageNumber, Callback callback) {
            callback.noCarParksRetreieved();
        }
    }

    private class FakeCarParkFetcher implements CarParkCore.CarParkFetcher {
        private List<CarPark>[] carParksToRetrievePages;

        public FakeCarParkFetcher(List<CarPark> ...carParkList)
        {
            carParksToRetrievePages = carParkList;
        }

        @Override
        public void fetch(int pageNumber, Callback callback) {
            callback.carParksRetrieved(carParksToRetrievePages[pageNumber]);
        }
    }

    @Test
    public void theCarParkCoreWillNotifyACallbackObjectWhenNoCarParksWereRetrieved()
    {
        CarParkCore carParkCore = new CarParkCore(new NoCarParkFetcher());

        SpyCarParkFetchCallback spyCarParkFetchCallback = new SpyCarParkFetchCallback();
        carParkCore.fetchCarParks(0, spyCarParkFetchCallback);

        assertThat(spyCarParkFetchCallback.noCarParksRetrieved_called, is(true));
    }

    @Test
    public void theCarParkCoreWillNotifyACallbackWhenCarParksAreRetrieved() throws LastUpdateTimestamp.LastUpdateTimestampException {
        CarPark expectedCarPark = new CarPark(1, "Name", new LastUpdateTimestamp("2015-12-30T15:12:15"), 53, -2, 10, 5, 2, 1);
        CarParkCore carParkCore = new CarParkCore(new FakeCarParkFetcher(Arrays.asList(expectedCarPark)));

        SpyCarParkFetchCallback spyCarParkFetchCallback = new SpyCarParkFetchCallback();
        carParkCore.fetchCarParks(0, spyCarParkFetchCallback);

        assertThat(spyCarParkFetchCallback.carParksFetched_called, hasItem(expectedCarPark));
    }

    @Test
    public void theCarParkCoreWillNotifyACallbackWhenCarParksAreRetrievedForDifferentPages() throws LastUpdateTimestamp.LastUpdateTimestampException {
        CarPark expectedCarPark = new CarPark(1, "Name", new LastUpdateTimestamp("2015-12-30T15:12:15"), 53, -2, 10, 5, 2, 1);
        CarPark expectedCarParkTwo = new CarPark(2, "Name", new LastUpdateTimestamp("2015-12-30T15:12:15"), 53, -2, 10, 5, 2, 1);
        CarParkCore carParkCore = new CarParkCore(new FakeCarParkFetcher(Arrays.asList(expectedCarPark), Arrays.asList(expectedCarParkTwo)));

        SpyCarParkFetchCallback spyCarParkFetchCallback = new SpyCarParkFetchCallback();
        carParkCore.fetchCarParks(0, spyCarParkFetchCallback);
        assertThat(spyCarParkFetchCallback.carParksFetched_called, hasItem(expectedCarPark));
        carParkCore.fetchCarParks(1, spyCarParkFetchCallback);
        assertThat(spyCarParkFetchCallback.carParksFetched_called, hasItem(expectedCarParkTwo));
    }
}
