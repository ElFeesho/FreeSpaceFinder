package uk.czcz.freespacefinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CarParkListFragment extends Fragment {

    private static class CarParkRecyclerViewListAdapter extends RecyclerView.Adapter<CarParkRecyclerViewListAdapter.ViewHolder> {

        public static class ViewHolder extends RecyclerView.ViewHolder {

            ViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_carpark_view, parent, false));
            }

            void displayCarPark(CarPark carPark) {
                ((CarParkView) itemView).displayCarPark(carPark);
            }
        }

        private List<CarPark> carParkList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
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
    }
    public static String TAG = "CPLF";
    private RecyclerView carParkListView;
    private CarParkRecyclerViewListAdapter carParkListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carparklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carParkListAdapter = new CarParkRecyclerViewListAdapter();
        carParkListView = (RecyclerView) view.findViewById(R.id.carparkRecyclerView);
        carParkListView.setLayoutManager(new LinearLayoutManager(getContext()));
        carParkListView.setAdapter(carParkListAdapter);

        fetchCarParks(0);
    }

    private void fetchCarParks(final int pageNumber) {
        FreeSpaceFinderApplication.getCarParkCore(getActivity()).fetchCarParks(pageNumber, new CarParkCore.CarParkFetchCallback() {
            @Override
            public void noCarParksRetrieved() {

            }

            @Override
            public void carParksFetched(List<CarPark> carParks) {
                carParkListAdapter.addCarParks(carParks);
                fetchCarParks(pageNumber+1);
            }

            @Override
            public void authorisationError() {

            }

            @Override
            public void unknownError() {

            }

            @Override
            public void parseError() {

            }
        });
    }
}
