package uk.czcz.freespacefinder;

import java.io.InputStream;

public class CarParkParser {

    public class CarParkParseAuthorisationException extends Exception {

    }

    public void parse(InputStream stream) throws CarParkParseAuthorisationException {
        throw new CarParkParseAuthorisationException();
    }
}
