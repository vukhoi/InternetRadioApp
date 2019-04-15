// DB

package com.example.internetradioapp.model;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import java.util.List;

public class ChannelRepository {

    private ChannelDAO channelDAO;
    private List<Channel> allChannels;
    private static ChannelRepository INSTANCE;

    private ChannelRepository(Application application){
        ChannelRoomDB db =ChannelRoomDB.getDatabase(application);
        channelDAO = db.channelDAO();
        allChannels = channelDAO.getAllWords(); // potentially take a while, so may want to use async task
    }

    public static ChannelRepository getINSTANCE(Application application){
        if (INSTANCE == null){
            synchronized (ChannelRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new ChannelRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public List<Channel> getAllChannels(){
        return INSTANCE.allChannels;
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
            try {
                mAsyncTaskDao.insert(params[0]);
            }
            catch (SQLiteConstraintException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
