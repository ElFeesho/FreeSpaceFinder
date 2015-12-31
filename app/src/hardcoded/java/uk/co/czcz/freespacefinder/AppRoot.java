package uk.co.czcz.freespacefinder;

import uk.czcz.freespacefinder.CarParkCore;
import uk.czcz.freespacefinder.HardcodedData;
import uk.czcz.freespacefinder.Location;

public class AppRoot {

    private CarParkCore carParkCore;


    public AppRoot(Application appContext)
    {
        carParkCore = new CarParkCore(new CarParkCore.CarParkFetcher() {
            @Override
            public void fetch(int pageNumber, Callback callback) {
                callback.carParksRetrieved(HardcodedData.carParkList.get(pageNumber));
            }
        }, new CarParkCore.LocationProvider(){
            @Override
            public void fetchLocation(Callback location) {
                location.locationAvailable(new Location(0, 0));
            }
        });
    }

    public CarParkCore getCarParkCore() {
        return carParkCore;
    }
}
