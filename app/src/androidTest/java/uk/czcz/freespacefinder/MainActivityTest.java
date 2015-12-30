package uk.czcz.freespacefinder;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import java.util.Arrays;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWhenAListOfCarParksAreFetched_theyAreDisplayed() throws LastUpdateTimestamp.LastUpdateTimestampException {
        HardcodedData.carParkList.add(Arrays.asList(new CarPark(1, "Car Park One", state, new LastUpdateTimestamp("2015-12-30T15:12:15"), 53, -2, 100, 50, 30, 10)));

        solo = new Solo(getInstrumentation(), getActivity());
        assertTrue(solo.waitForFragmentByTag(CarParkListFragment.TAG));

        assertTrue(solo.waitForText("Car Park One"));
    }
}