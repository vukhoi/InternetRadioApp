// RETROFIT

package com.example.internetradioapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelList {

    @SerializedName("channels")
    @Expose
    private List<ChannelAPIObj> channels = null;

    public List<ChannelAPIObj> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelAPIObj> channels) {
        this.channels = channels;
    }

}