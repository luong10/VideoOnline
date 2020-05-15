package com.example.mywatch;

public class VideoYouTube {
    private String title;
    private String thumbnails;
    private String idvideo;
    private String publishedAt;
    private String channelTitle;

    public VideoYouTube(String title, String thumbnails, String idvideo, String publishedAt, String channelTitle) {
        this.title = title;
        this.thumbnails = thumbnails;
        this.idvideo = idvideo;
        this.publishedAt = publishedAt;
        this.channelTitle = channelTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public VideoYouTube(String title, String thumbnails, String idvideo) {
        this.title = title;
        this.thumbnails = thumbnails;
        this.idvideo = idvideo;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(String idvideo) {
        this.idvideo = idvideo;
    }
}
