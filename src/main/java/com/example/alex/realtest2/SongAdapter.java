package com.example.alex.realtest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        return null;
    }
}
