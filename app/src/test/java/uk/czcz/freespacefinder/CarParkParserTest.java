package uk.czcz.freespacefinder;

import org.junit.Test;

import java.io.InputStream;

public class CarParkParserTest {

    @Test(expected = CarParkParser.CarParkParseAuthorisationException.class)
    public void when_authorisationIsNotValid_anApiErrorIsAdapted() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(testResourceStream("auth_error.json"));
    }

    private InputStream testResourceStream(String filename) {
        return getClass().getResourceAsStream(filename);
    }
}