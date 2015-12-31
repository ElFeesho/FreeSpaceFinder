package uk.czcz.freespacefinder;

import java.util.HashMap;
import java.util.Map;

public class CarPark {

    public static final int STATUS_UNKNOWN     = 0;
    public static final int STATUS_ALMOST_FULL = 1;
    public static final int STATUS_CLOSED      = 2;
    public static final int STATUS_ESTIMATED   = 3;
    public static final int STATUS_FULL        = 4;
    public static final int STATUS_OPEN        = 5;
    public static final int STATUS_QUEUING     = 6;
    public static final int STATUS_SPACES      = 7;

    private final static Map<String, Integer> statusMap = new HashMap<String, Integer>()
    {
        {
            put("-", CarPark.STATUS_UNKNOWN);
            put("AlmostFull", CarPark.STATUS_ALMOST_FULL);
            put("Closed", CarPark.STATUS_CLOSED);
            put("Estimated", CarPark.STATUS_ESTIMATED);
            put("Full", CarPark.STATUS_FULL);
            put("Open", CarPark.STATUS_OPEN);
            put("Queing", CarPark.STATUS_QUEUING);
            put("Spaces", CarPark.STATUS_SPACES);
        }
    };


    public final int id;
    public final LastUpdateTimestamp lastUpdateTimestamp;
    public final String carparkName;
    public final int state;
    public final double lattitude;
    public final double longitude;
    public final int capacity;
    public final int spacesAvailable;
    public final int predicted30Mins;
    public final int predicted60Mins;

    public CarPark(int id, String carparkName, String state, LastUpdateTimestamp lastUpdateTimestamp, double lattitude, double longitude, int capacity, int spacesAvailable, int predicted30Mins, int predicted60Mins) {
        this.id = id;
        this.carparkName = carparkName;
        this.state = statusMap.get(state);
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.spacesAvailable = spacesAvailable;
        this.predicted30Mins = predicted30Mins;
        this.predicted60Mins = predicted60Mins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarPark carPark = (CarPark) o;

        if (id != carPark.id) return false;
        if (Double.compare(carPark.lattitude, lattitude) != 0) return false;
        if (Double.compare(carPark.longitude, longitude) != 0) return false;
        if (capacity != carPark.capacity) return false;
        if (spacesAvailable != carPark.spacesAvailable) return false;
        if (predicted30Mins != carPark.predicted30Mins) return false;
        if (predicted60Mins != carPark.predicted60Mins) return false;
        return carparkName.equals(carPark.carparkName) && state == carPark.state;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + carparkName.hashCode();
        temp = Double.doubleToLongBits(lattitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + spacesAvailable;
        result = 31 * result + predicted30Mins;
        result = 31 * result + predicted60Mins;
        return result;
    }
}
