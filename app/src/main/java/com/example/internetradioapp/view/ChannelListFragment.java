package com.example.internetradioapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.internetradioapp.R;
import com.example.internetradioapp.model.Channel;
import com.example.internetradioapp.presenter.ChannelListFragmentPresenter;


public class ChannelListFragment extends Fragment {
    RecyclerView recyclerView;
    SearchView searchView;
    ChannelListFragmentPresenter presenter;


    public ChannelListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        recyclerView = view.findViewById(R.id.rv_channel_list);
        searchView = view.findViewById(R.id.search_view);

        presenter = new ChannelListFragmentPresenter(this.getContext());
        presenter.populateRecyclerView(recyclerView);

        setUpSearchView(searchView);

        return view;
    }

    private void setUpSearchView(android.widget.SearchView searchView){
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Channel searchResult = presenter.searchChannel(s);
                if (searchResult != null){
                    addChannelDetailFragment(searchResult);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.editRecyclerView(s, recyclerView);
                return false;
            }
        });
    }

    public void addChannelDetailFragment(Channel searchResult) {
        Fragment fragment = new ChannelDetailFragment();
        Bundle bundle = createChannelBundle(searchResult);
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(true, fragment);
    }

    public static Bundle createChannelBundle(Channel searchResult) {
        Bundle bundle = new Bundle();
        bundle.putString("title", searchResult.getTitle());
        bundle.putString("dj", searchResult.getDj());
        bundle.putString("description", searchResult.getDescription());
        bundle.putString("djEmail", searchResult.getDjEmail());
        bundle.putString("listeners", searchResult.getListeners());
        bundle.putString("genre", searchResult.getGenre());
        bundle.putString("thumbnailUrlLarge", searchResult.getThumbnailUrlLarge());
        bundle.putString("previewUrl", searchResult.getPreviewUrl());
        return bundle;
    }
}
