package com.example.alex.realtest2;

/**
 * Created by Alex on 7/9/2016.
 */

public class Song {
    private long id;
    private String songTitle;
    private String songArtist;
    private int order;//misc assignment
    private int priority;
    private boolean canPlay;

    public Song(long id,String songTitle, String songArtist){
        this.setId(id);
        this.setSongTitle(songTitle);
        this.setSongArtist(songArtist);
        canPlay=true;
    }

    public Song(long id,String songTitle, String songArtist,int order,int priority){
        this.setId(id);
        this.setSongTitle(songTitle);
        this.setSongArtist(songArtist);
        this.setOrder(order);
        this.setPriority(priority);
        canPlay=true;
    }

    public void setPlayable(boolean bool){
        this.canPlay=bool;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    ///////



}
