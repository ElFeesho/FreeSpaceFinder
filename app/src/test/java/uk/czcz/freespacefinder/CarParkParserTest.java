package uk.czcz.freespacefinder;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CarParkParserTest {

    @Test(expected = CarParkParser.CarParkParseAuthorisationException.class)
    public void when_authorisationIsNotValid_anApiErrorIsAdapted() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(testResourceStream("{\"Message\": \"Authorization has been denied for this request.\"}"));
    }

    @Test(expected = CarParkParser.CarParkParseNullStreamException.class)
    public void when_aStreamIsNull_CarParkParseNullStreamException() throws Exception {
        CarParkParser parser = new CarParkParser();
        parser.parse(null);
    }


    private InputStream testResourceStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}