package uk.co.czcz.freespacefinder;

import java.util.ArrayList;
import java.util.List;

import uk.czcz.freespacefinder.CarPark;
import uk.czcz.freespacefinder.CarParkCore;

public class AppRoot {

    private CarParkCore carParkCore;

    public static List<List<CarPark>> carParkList = new ArrayList<>();

    public AppRoot()
    {
        carParkCore = new CarParkCore(new CarParkCore.CarParkFetcher() {
            @Override
            public void fetch(int pageNumber, Callback callback) {
                callback.carParksRetrieved(carParkList.get(pageNumber));
            }
        });
    }

    public CarParkCore getCarParkCore() {
        return carParkCore;
    }
}
