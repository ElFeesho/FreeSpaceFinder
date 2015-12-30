package uk.czcz.freespacefinder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarParkParser {

    public class CarParkParseAuthorisationException extends Exception {

    }

    public class CarParkParseNullStreamException extends Exception {

    }

    public class CarParkParserParseException extends Exception {

    }

    public class CarParkParseUnknownErrorException extends Exception {

    }

    public List<CarPark> parse(@NotNull InputStream stream) throws CarParkParseAuthorisationException, CarParkParseNullStreamException, CarParkParserParseException, CarParkParseUnknownErrorException {
        if (stream == null) {
            throw new CarParkParseNullStreamException();
        }

        String jsonText = null;
        try {
            jsonText = readStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (jsonText.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonText);
                String messageText = jsonObject.getString("Message");
                if (messageText.equals("Authorization has been denied for this request.")) {
                    throw new CarParkParseAuthorisationException();
                } else {
                    throw new CarParkParseUnknownErrorException();
                }
            }
            else
            {
                List<CarPark> carParkList = new ArrayList<>();
                JSONArray carParks = new JSONArray(jsonText);
                for (int i = 0; i < carParks.length(); i++) {
                    JSONObject carParkObject = carParks.getJSONObject(i);
                    carParkList.add(new CarPark(carParkObject.getInt("Id"), carParkObject.getString("Name"), new LastUpdateTimestamp(carParkObject.getString("LastUpdated")), carParkObject.getDouble("Latitude"), carParkObject.getDouble("Longitude"), carParkObject.getInt("Capacity"), carParkObject.getInt("SpacesNow"), carParkObject.getInt("PredictedSpaces30Mins"), carParkObject.getInt("PredictedSpaces60Mins")));
                }
                return carParkList;
            }
        } catch (JSONException | LastUpdateTimestamp.LastUpdateTimestampException e) {
            throw new CarParkParserParseException();
        }
    }

    private static String readStream(InputStream stream) throws IOException {
        byte[] buffer = new byte[4096];
        StringBuffer stringBuffer = new StringBuffer();

        int read = 0;
        while ((read = stream.read(buffer)) > 0)
        {
            stringBuffer.append(new String(buffer, 0, read));
        }

        return stringBuffer.toString();
    }
}
