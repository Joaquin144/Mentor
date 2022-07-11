package com.joaquin.hackathon.Classes;

public class videoModel {

    String VideoUrl,VideoTitle;
    int icon;

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public videoModel(String videoUrl, String videoTitle, int icon) {
        VideoUrl = videoUrl;
        VideoTitle = videoTitle;
        this.icon = icon;
    }
}
