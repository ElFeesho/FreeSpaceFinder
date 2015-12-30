package uk.czcz.freespacefinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CarParkView extends FrameLayout {

    private TextView carParkName;
    private TextView spacesAvailable;
    private TextView spacesAvailable30mins;
    private TextView spacesAvailable60mins;

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
        spacesAvailable = (TextView) findViewById(R.id.spaces_available);
        spacesAvailable30mins = (TextView) findViewById(R.id.spaces_available_30mins);
        spacesAvailable60mins = (TextView) findViewById(R.id.spaces_available_60mins);
    }

    public void displayCarPark(CarPark carPark)
    {
        carParkName.setText(carPark.carparkName);
        spacesAvailable.setText(carPark.spacesAvailable + "");
        spacesAvailable30mins.setText(carPark.predicted30Mins+"");
        spacesAvailable60mins.setText(carPark.predicted60Mins+"");
    }
}
