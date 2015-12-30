package uk.czcz.freespacefinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarParkView extends LinearLayout {

    private TextView carParkName;

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
    }

    public void displayCarPark(CarPark carPark)
    {
        carParkName.setText(carPark.carparkName);
    }
}
