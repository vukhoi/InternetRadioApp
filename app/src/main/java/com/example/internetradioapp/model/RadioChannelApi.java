// RETROFIT

package com.example.internetradioapp.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RadioChannelApi {
    @GET("jvanaria/jvanaria.github.io/master/channels.json")
    Call<ChannelList> getChannelList();
}
