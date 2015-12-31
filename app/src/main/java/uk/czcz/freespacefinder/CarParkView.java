package uk.czcz.freespacefinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CarParkView extends FrameLayout {

    public interface Listener
    {
        void directionsClicked(CarPark forCarPark);
    }

    private TextView carParkName;
    private TextView carParkState;
    private TextView spacesAvailable;
    private TextView spacesAvailable30mins;
    private TextView spacesAvailable60mins;
    private TextView lastUpdated;
    private TextView distance;
    private DirectionsDelegate listener;

    public CarParkView(Context context) {
        this(context, null);
    }

    public CarParkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        carParkName = (TextView)findViewById(R.id.carpark_name);
        carParkState = (TextView)findViewById(R.id.carpark_state);
        spacesAvailable = (TextView) findViewById(R.id.spaces_available);
        spacesAvailable30mins = (TextView) findViewById(R.id.spaces_available_30mins);
        spacesAvailable60mins = (TextView) findViewById(R.id.spaces_available_60mins);
        distance = (TextView) findViewById(R.id.carpark_distance);
        lastUpdated = (TextView) findViewById(R.id.last_update);
    }

    public void displayCarPark(final CarPark carPark)
    {
        carParkName.setText(carPark.carparkName);
        carParkState.setText(getResources().getStringArray(R.array.status_list)[carPark.state]);
        spacesAvailable.setText(carPark.spacesAvailable + "");
        spacesAvailable30mins.setText(carPark.predicted30Mins+"");
        spacesAvailable60mins.setText(carPark.predicted60Mins+"");

        lastUpdated.setText(formatTimestamp(carPark));

        findViewById(R.id.navigate_to_carpark).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.directionsForCarPark(carPark);
            }
        });
    }

    public void setDirectionsDelegate(DirectionsDelegate listener) {
        this.listener = listener;
    }

    @NonNull
    private String formatTimestamp(CarPark carPark) {
        return TimeUnit.MINUTES.convert(System.currentTimeMillis()-carPark.lastUpdateTimestamp.timestampMilliseconds, TimeUnit.MILLISECONDS)+"m ago";
    }
}
