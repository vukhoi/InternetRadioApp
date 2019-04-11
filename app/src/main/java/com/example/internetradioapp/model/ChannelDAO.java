// DB

package com.example.internetradioapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ChannelDAO {

    @Insert
    void insert(Channel channel);

    @Query("DELETE FROM channel_table")
    void deleteAll();

    @Query("SELECT * FROM channel_table ORDER BY title ASC")
    List<Channel> getAllWords();
}
