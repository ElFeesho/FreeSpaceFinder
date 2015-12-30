package uk.czcz.freespacefinder;

public class CarPark {

    public final int id;
    public final LastUpdateTimestamp lastUpdateTimestamp;
    public final String carparkName;
    public final double lattitude;
    public final double longitude;
    public final int capacity;
    public final int spacesAvailable;
    public final int predicted30Mins;
    public final int predicted60Mins;

    public CarPark(int id, String carparkName, LastUpdateTimestamp lastUpdateTimestamp, double lattitude, double longitude, int capacity, int spacesAvailable, int predicted30Mins, int predicted60Mins) {
        this.id = id;
        this.carparkName = carparkName;
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
        return carparkName.equals(carPark.carparkName);
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
