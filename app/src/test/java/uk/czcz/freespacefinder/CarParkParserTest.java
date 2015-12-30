package uk.czcz.freespacefinder;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CarParkParserTest {

    @Test(expected = CarParkParser.CarParkParseAuthorisationException.class)
    public void when_authorisationIsNotValid_anApiErrorIsAdapted() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(testResourceStream("{\"Message\": \"Authorization has been denied for this request.\"}"));
    }

    @Test(expected = CarParkParser.CarParkParseUnknownErrorException.class)
    public void when_anUnknownErrorOccurs_anExceptionIsThrown() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(testResourceStream("{\"Message\": \"Unknown error.\"}"));
    }

    @Test(expected = CarParkParser.CarParkParseNullStreamException.class)
    public void when_aStreamIsNull_CarParkParseNullStreamException() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(null);
    }

    @Test (expected = CarParkParser.CarParkParserParseException.class)
    public void when_anInvalidResponseIsReceived_aParseExceptionIsThrown() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(testResourceStream("Invalid JSON"));
    }

    @Test
    public void when_aStreamContainsACarPark_itWillBeParsed() throws Exception {
        CarParkParser parser = new CarParkParser();
        int expectedId = 1;
        String expectedCarparkName = "Carpark Name";
        String expectedLastUpdate = "2015-12-30T12:00:00";
        double expectedLattitude = 53.0000000000000;
        double expectedLongitude = -2.0000000000000;
        String state = "Spaces";
        int expectedCapacity = 100;
        int expectedSpacesAvailable = 80;
        int expectedPredicted30Mins = 60;
        int expectedPredicted60Mins = 30;
        LastUpdateTimestamp lastUpdateTimestamp = new LastUpdateTimestamp(expectedLastUpdate);

        CarPark expectedCarPark = new CarPark(expectedId, expectedCarparkName, state, lastUpdateTimestamp, expectedLattitude, expectedLongitude, expectedCapacity, expectedSpacesAvailable, expectedPredicted30Mins, expectedPredicted60Mins);

        List<CarPark> carParks = parser.parse(testResourceStream("[{ \"Id\": " + expectedId + ", \"Name\": \"" + expectedCarparkName + "\", \"State\": \"" + state + "\", \"LastUpdated\": \"" + expectedLastUpdate + "\", \"Latitude\": " + expectedLattitude + ", \"Longitude\": " + expectedLongitude + ", \"SCN\": \"ignored\", \"Capacity\": " + expectedCapacity + ", \"SpacesNow\": " + expectedSpacesAvailable + ", \"PredictedSpaces30Mins\": " + expectedPredicted30Mins + ", \"PredictedSpaces60Mins\": " + expectedPredicted60Mins + "}]"));

        assertThat(expectedCarPark, is(carParks.get(0)));
    }

    private InputStream testResourceStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}