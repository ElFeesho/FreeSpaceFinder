package uk.czcz.freespacefinder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CarParkListFragment extends Fragment {

    private static class CarParkRecyclerViewListAdapter extends RecyclerView.Adapter<CarParkRecyclerViewListAdapter.ViewHolder> {

        public static class ViewHolder extends RecyclerView.ViewHolder {

            ViewHolder(ViewGroup parent, final DirectionsDelegate directionsListener) {
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_carpark_view, parent, false));
                ((CarParkView)itemView).setDirectionsDelegate(directionsListener);
            }

            void displayCarPark(CarPark carPark) {
                ((CarParkView) itemView).displayCarPark(carPark);
            }
        }

        private final DirectionsDelegate directionsDelegate;
        private List<CarPark> carParkList = new ArrayList<>();

        public CarParkRecyclerViewListAdapter(DirectionsDelegate directionsDelegate)
        {
            this.directionsDelegate = directionsDelegate;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent, directionsDelegate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.displayCarPark(carParkList.get(position));
        }

        @Override
        public int getItemCount() {
            return carParkList.size();
        }

        public void addCarParks(List<CarPark> carParks) {
            carParkList.addAll(carParks);
            notifyDataSetChanged();
        }

        public void clear() {
            carParkList.clear();
            notifyDataSetChanged();
        }
    }

    public static String TAG = "CPLF";
    private RecyclerView carParkListView;
    private CarParkRecyclerViewListAdapter carParkListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carparklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                fetchCarParks(0);
            }
        });
        carParkListAdapter = new CarParkRecyclerViewListAdapter(new DirectionsDelegate() {
            @Override
            public void directionsForCarPark(CarPark carPark) {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+carPark.location.latitude+","+carPark.location.longitude)));
                }
                catch(ActivityNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Google Maps not found", Toast.LENGTH_LONG).show();
                }
            }
        });
        carParkListView = (RecyclerView) view.findViewById(R.id.carparkRecyclerView);
        carParkListView.setLayoutManager(new LinearLayoutManager(getContext()));
        carParkListView.setAdapter(carParkListAdapter);

        fetchCarParks(0);
    }

    private void fetchCarParks(final int pageNumber) {
        if (pageNumber == 0)
        {
            carParkListAdapter.clear();
        }
        FreeSpaceFinderApplication.getCarParkCore(getActivity()).fetchCarParks(pageNumber, new CarParkCore.CarParkFetchCallback() {
            @Override
            public void noCarParksRetrieved() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void carParksFetched(List<CarPark> carParks) {
                carParkListAdapter.addCarParks(carParks);
                fetchCarParks(pageNumber + 1);
            }

            @Override
            public void authorisationError() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "APP or DEV key are invalid.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void unknownError() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Unknown error occurred.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void parseError() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Failed to retrieve data. Check network.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
