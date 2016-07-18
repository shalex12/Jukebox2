package com.example.alex.realtest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 7/9/2016.
 */

public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> userSongList;
    private LayoutInflater inflateList;

    public SongAdapter(Context c, ArrayList<Song> userSongList){
        this.userSongList=userSongList;
        inflateList=LayoutInflater.from(c);//what were filling up with the inflate.

    }

    @Override
    public int getCount() {
        return userSongList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    //TODO: study this for later use, it will change for the other modes.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //set up that song layour
        LinearLayout songLayout=(LinearLayout)inflateList.inflate(R.layout.song_layout,viewGroup,false);
        //recall that within the song_layout xml theres two textViews
        TextView title=(TextView)songLayout.findViewById(R.id.title);//find artist and title, pull the view.
        TextView artist=(TextView)songLayout.findViewById(R.id.artist);
        //^reference to them! hence why we use songLayout.findViewByID

        Song currentSong=userSongList.get(i);//the listview rows go 0,1 etc. thus we just map it
        //we have the listview thing, but its empty. Time to change that.
        title.setText(currentSong.getSongTitle());
        artist.setText(currentSong.getSongArtist());

        songLayout.setTag(i);//when a person clicks on this specific row, we have a tag association with it

        return songLayout;//and of course here's the actual view object. useful for onClick later(songSelected_
    }
}
