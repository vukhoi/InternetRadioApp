// RETROFIT

package com.example.internetradioapp.model;

import android.app.Application;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private final String LOG_TAG = RetrofitHelper.this.getClass().getSimpleName();
    private RadioChannelApi radioChannelApi;
    private ChannelRepository channelRepo;

    public RetrofitHelper(ChannelRepository channelRepo){
        this.channelRepo = channelRepo;
        initializeRetrofit();

    }

    public void makeRetrofitCall(){
        radioChannelApi.getChannelList().enqueue(new Callback<ChannelList>() {
            @Override
            public void onResponse(Call<ChannelList> call, Response<ChannelList> response) {
                saveResponseToDb(response, channelRepo);
            }

            @Override
            public void onFailure(Call<ChannelList> call, Throwable t) {
                Log.d(LOG_TAG, "makeRetrofitCall failed");
            }
        });
    }


    private void saveResponseToDb(Response<ChannelList> response, ChannelRepository channelRepo){
        for (ChannelAPIObj channelAPIObj: response.body().getChannels()){
            channelRepo.insert(new Channel(channelAPIObj.getTitle(),
                    channelAPIObj.getDescription(),
                    channelAPIObj.getDj(),
                    channelAPIObj.getImage(),
                    channelAPIObj.getLargeimage(),
                    channelAPIObj.getDjmail(),
                    channelAPIObj.getListeners(),
                    channelAPIObj.getGenre()));
        }
    }



    private void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://movies-sample.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();

        this.radioChannelApi = retrofit.create(RadioChannelApi.class);
    }
}
