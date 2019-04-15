package com.example.internetradioapp.presenter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.internetradioapp.model.Channel;


public interface ChannelListPresenterInterface {

    Channel searchChannel(String s);
    void populateRecyclerView(RecyclerView recyclerView);
    void editRecyclerView(String s, RecyclerView recyclerView);
    Bundle createChannelBundle(Channel searchResult);
}
