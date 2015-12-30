package uk.czcz.freespacefinder;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class CarParkParser {

    public class CarParkParseAuthorisationException extends Exception {

    }

    public class CarParkParseNullStreamException extends Exception {

    }

    public void parse(@NotNull InputStream stream) throws CarParkParseAuthorisationException, CarParkParseNullStreamException {
        if (stream == null)
        {
            throw new CarParkParseNullStreamException();
        }
        throw new CarParkParseAuthorisationException();
    }
}
