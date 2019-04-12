// DB

package com.example.internetradioapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "channel_table")
public class Channel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "dj")
    private String dj;
    @NonNull
    @ColumnInfo(name = "thumbnail_url_small")
    private String thumbnailUrlSmall;
    @NonNull
    @ColumnInfo(name = "thumbnail_url_large")
    private String thumbnailUrlLarge;
    @NonNull
    @ColumnInfo(name = "dj_email")
    private String djEmail;
    @NonNull
    @ColumnInfo(name = "number_of_listeners")
    private String listeners;
    @NonNull
    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "preview_url")
    private String previewUrl;

    public Channel(String title,
                   String description,
                   String dj,
                   String thumbnailUrlSmall,
                   String thumbnailUrlLarge,
                   String djEmail,
                   String listeners,
                   String genre,
                   String previewUrl) {
        this.title = title;
        this.description = description;
        this.dj = dj;
        this.thumbnailUrlSmall = thumbnailUrlSmall;
        this.thumbnailUrlLarge = thumbnailUrlLarge;
        this.djEmail = djEmail;
        this.listeners = listeners;
        this.genre = genre;
        this.previewUrl = previewUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getThumbnailUrlSmall() {
        return thumbnailUrlSmall;
    }

    public void setThumbnailUrlSmall(String thumbnailUrlSmall) {
        this.thumbnailUrlSmall = thumbnailUrlSmall;
    }

    public String getThumbnailUrlLarge() {
        return thumbnailUrlLarge;
    }

    public void setThumbnailUrlLarge(String thumbnailUrlLarge) {
        this.thumbnailUrlLarge = thumbnailUrlLarge;
    }

    public String getDjEmail() {
        return djEmail;
    }

    public void setDjEmail(String djEmail) {
        this.djEmail = djEmail;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
