package uk.czcz.freespacefinder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarParkCoreTest {

    private static class SpyCarParkFetchCallback implements CarParkCore.CarParkFetchCallback {

        public boolean noCarParksRetrieved_called;

        @Override
        public void noCarParksRetrieved()
        {
            noCarParksRetrieved_called = true;
        }
    }

    @Test
    public void theCarParkCoreWillNotifyACallbackObjectWhenNoCarParksWereRetrieved()
    {
        CarParkCore carParkCore = new CarParkCore();

        SpyCarParkFetchCallback spyCarParkFetchCallback = new SpyCarParkFetchCallback();
        carParkCore.fetchCarParks(spyCarParkFetchCallback);

        assertThat(spyCarParkFetchCallback.noCarParksRetrieved_called, is(true));
    }


}
