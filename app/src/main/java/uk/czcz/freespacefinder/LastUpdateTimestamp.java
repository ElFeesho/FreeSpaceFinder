package uk.czcz.freespacefinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LastUpdateTimestamp {

    public static class LastUpdateTimestampException extends Exception
    {

    }

    public final long timestampMilliseconds;

    public LastUpdateTimestamp(String lastUpdate) throws LastUpdateTimestampException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            timestampMilliseconds = dateFormat.parse(lastUpdate).getTime();
        } catch (ParseException e) {
            throw new LastUpdateTimestampException();
        }
    }
}
