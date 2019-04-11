package com.example.internetradioapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.internetradioapp.R;
import com.example.internetradioapp.presenter.ChannelListFragmentPresenter;


public class ChannelListFragment extends Fragment {
    RecyclerView recyclerView;
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

        presenter = new ChannelListFragmentPresenter(getContext());
        presenter.populateRecyclerView(recyclerView);


        return view;
    }

}
