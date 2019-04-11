// DB

package com.example.internetradioapp.model;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class ChannelRepository {

    private ChannelDAO channelDAO;
    private List<Channel> allChannels;

    public ChannelRepository(Application application){
        ChannelRoomDB db =ChannelRoomDB.getDatabase(application);
        channelDAO = db.channelDAO();
        allChannels = channelDAO.getAllWords(); // potentially take a while, so may want to use async task
    }

    public List<Channel> getAllChannels(){
        return allChannels;
    }

    public void insert (Channel channel) {
        new insertAsyncTask(channelDAO).execute(channel);
    }

    private static class insertAsyncTask extends AsyncTask<Channel, Void, Void> {

        private ChannelDAO mAsyncTaskDao;

        insertAsyncTask(ChannelDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Channel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
