package uk.co.czcz.freespacefinder;

import uk.czcz.freespacefinder.CarParkCore;
import uk.czcz.freespacefinder.HardcodedData;

public class AppRoot {

    private CarParkCore carParkCore;


    public AppRoot()
    {
        carParkCore = new CarParkCore(new CarParkCore.CarParkFetcher() {
            @Override
            public void fetch(int pageNumber, Callback callback) {
                callback.carParksRetrieved(HardcodedData.carParkList.get(pageNumber));
            }
        });
    }

    public CarParkCore getCarParkCore() {
        return carParkCore;
    }
}
