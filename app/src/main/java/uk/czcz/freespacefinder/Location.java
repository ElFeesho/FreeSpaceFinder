package uk.czcz.freespacefinder;

public class Location {

    public static final double DEG_TO_RAD_COEFF = 0.0174533;
    public final double latitude;
    public final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distance(Location point) {
        final int EARTH_RADIUS_METRES = 6371;
        double latDistance = Math.toRadians(point.latitude - latitude) / 2;
        double lonDistance = Math.toRadians(point.longitude - longitude) / 2;
        double a = Math.sin(latDistance) * Math.sin(latDistance) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(point.latitude)) *
                        Math.sin(lonDistance) * Math.sin(lonDistance);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METRES * c;
    }
}
