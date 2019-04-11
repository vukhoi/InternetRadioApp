// DB

package com.example.internetradioapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Channel.class}, version = 1)
public abstract class ChannelRoomDB extends RoomDatabase {

    public abstract ChannelDAO channelDAO();

    private static volatile ChannelRoomDB INSTANCE;

    static ChannelRoomDB getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (ChannelRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChannelRoomDB.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
