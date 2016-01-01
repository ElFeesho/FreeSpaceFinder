package uk.czcz.freespacefinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CarParkView extends FrameLayout {

    private TextView carParkName;
    private TextView carParkState;
    private TextView spacesAvailable;
    private TextView spacesAvailable30mins;
    private TextView spacesAvailable60mins;
    private TextView lastUpdated;
    private TextView distance;
    private DirectionsDelegate listener;
    private TextView totalSpaces;
    private ProgressBar spacesProgressBar;
    private ProfileImageView image;

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
        totalSpaces = (TextView) findViewById(R.id.spaces_total);
        spacesAvailable = (TextView) findViewById(R.id.spaces_available);
        spacesAvailable30mins = (TextView) findViewById(R.id.spaces_available_30mins);
        spacesAvailable60mins = (TextView) findViewById(R.id.spaces_available_60mins);
        distance = (TextView) findViewById(R.id.carpark_distance);
        lastUpdated = (TextView) findViewById(R.id.last_update);
        spacesProgressBar = (ProgressBar) findViewById(R.id.spaces_progress_bar);
        image = (ProfileImageView) findViewById(R.id.image);
    }

    public void displayCarPark(final CarPark carPark)
    {
        carParkName.setText(carPark.carparkName);
        carParkState.setText(getResources().getStringArray(R.array.status_list)[carPark.state]);
        spacesAvailable.setText(carPark.spacesAvailable + "");
        spacesAvailable30mins.setText(carPark.predicted30Mins+"");
        spacesAvailable60mins.setText(carPark.predicted60Mins+"");
        totalSpaces.setText(carPark.capacity+"");
        lastUpdated.setText(formatTimestamp(carPark));

        spacesProgressBar.setMax(carPark.capacity);
        spacesProgressBar.setProgress(carPark.capacity-carPark.spacesAvailable);

        findViewById(R.id.navigate_to_carpark).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.directionsForCarPark(carPark);
            }
        });
        FreeSpaceFinderApplication.getCarParkCore(getContext()).fetchLocation(new CarParkCore.LocationFetchCallback() {
            @Override
            public void locationFetched(Location location) {
                distance.setText(String.format("%.2f km", location.distance(carPark.location)));
            }
        });

        if (carPark.id == 21905)
        {
            image.setImageResource(R.mipmap.aquatics_centre);
            image.updateBlur();
        }
        else if (carPark.id == 21906)
        {
            image.setImageResource(R.mipmap.men);
            image.updateBlur();
        }
        else if (carPark.id == 21907)
        {
            image.setImageResource(R.mipmap.arndale);
            image.updateBlur();
        }
        else if (carPark.id == 21908)
        {
            image.setImageResource(R.mipmap.bridgewater);
            image.updateBlur();
        }
        else if (carPark.id == 21909)
        {
            image.setImageResource(R.mipmap.charles_street);
            image.updateBlur();
        }
        else if (carPark.id == 21911)
        {
            image.setImageResource(R.mipmap.chortlon_street);
            image.updateBlur();
        }
        else if (carPark.id == 21912)
        {
            image.setImageResource(R.mipmap.church_street);
            image.updateBlur();
        }
        else if (carPark.id == 21913)
        {
            image.setImageResource(R.mipmap.dale_street);
            image.updateBlur();
        }
        else
        {
            image.setImageResource(R.mipmap.placeholder);
            image.updateBlur();
        }

    }

    public void setDirectionsDelegate(DirectionsDelegate listener) {
        this.listener = listener;
    }

    @NonNull
    private String formatTimestamp(CarPark carPark) {
        return TimeUnit.MINUTES.convert(System.currentTimeMillis()-carPark.lastUpdateTimestamp.timestampMilliseconds, TimeUnit.MILLISECONDS)+"m ago";
    }
}
