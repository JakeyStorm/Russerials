package com.example.russerials;

public class SerialView {
    private String imageId;
    private String name;
    private String url;
    private String key;
    private String shortDes;

    public String getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(String episodeList) {
        this.episodeList = episodeList;
    }

    private String episodeList;

    public String getFullDes() {
        return FullDes;
    }

    public void setFullDes(String fullDes) {
        FullDes = fullDes;
    }

    private String FullDes;
    public String getShortDes() {
        return shortDes;
    }

    public void setShortDes(String shortDes) {
        this.shortDes = shortDes;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SerialView(String imageId, String name, String url, String key, String shortDes, String FullDes, String episodeList){
        this.imageId = imageId;
        this.name = name;
        this.url = url;
        this.key = key;
        this.shortDes = shortDes;
        this.FullDes = FullDes;
        this.episodeList = episodeList;
    }

    public SerialView(){

    }
}
