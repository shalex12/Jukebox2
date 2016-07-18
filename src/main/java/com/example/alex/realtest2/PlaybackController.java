package com.example.alex.realtest2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Created by Alex on 7/16/2016.
 */
//this class is basically the same as MediaController. only reason we extend it is to change the
    //hide method
public class PlaybackController extends MediaController {
    //TODO may need to change this if the widget is being funky
    public PlaybackController(Context context) {
        super(context);
    }


    //override the hide method. simply so the widget doesnt disappear.
    public void hide(){

    }
}
