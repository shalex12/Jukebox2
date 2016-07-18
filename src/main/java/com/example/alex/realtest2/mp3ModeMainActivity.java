package com.example.alex.realtest2;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.alex.realtest2.MP3_Service.mp3ServiceBinder;

import java.util.ArrayList;

//TODO: The controller widget stuff
public class mp3ModeMainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    private ArrayList<Song> mp3SongList;//list of songs on the device
    private ListView listOfSongsUI;

    private MP3_Service service;//the service that will do the playing
    private Intent mp3Intent;//we intent to link the service and this activity
    private boolean binded=false;//checks if we are binded to the service or not

    //this activity now has a controller interface. i.e. a widget with mp3 buttons
    private PlaybackController playbackController;

    public boolean activityPaused=false;//
    public boolean playbackPaused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_standard);
        listOfSongsUI=(ListView)findViewById(R.id.songList);//get a  reference to the song list UI
        mp3SongList= new ArrayList<Song>();

        retrieveSongsFromDevice();//fills up the mp3SongList with the songs on the device
        //TODO: reorder the mp3songList by alphabetical order

        SongAdapter adapter= new SongAdapter(this,mp3SongList);//set up the view, fill with the songs
        listOfSongsUI.setAdapter(adapter);

        initPlaybackController();

    }




    @Override
    protected void onStart(){
        super.onStart();//start the activity
        if (mp3Intent==null){//if we havent done anything with our intent yet
            mp3Intent= new Intent(this,MP3_Service.class);//we intend to link this activity to the MP3 service
            bindService(mp3Intent,serviceConnection, Context.BIND_AUTO_CREATE);//what we intend+connection to bridge it
            startService(mp3Intent);//now start running the service code
            //Note the ServiceConnection allowed us to access the service and its methods. This
            //code allows us to simply start t.

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        activityPaused=true;
    }
    @Override
    protected void onResume(){
        super.onPause();
        //reset the controls once the activity resumes
        if(activityPaused){
            initPlaybackController();
            activityPaused=false;
        }
    }
    @Override
    //when the activity stops hide the controller
    protected void onStop(){
        playbackController.hide();
        super.onStop();
    }

    public void initPlaybackController(){

        playbackController= new PlaybackController(this);//make the new controller, this activity is the context
        //set up the listeners for the buttons

        playbackController.setPrevNextListeners(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                playNextTrack();//when user hits the "next" button this will run
            }
        }, new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                playPrevTrack();//when the user hits the prev button
            }
        });
        playbackController.setMediaPlayer(this);//connects the mediaplayer to this activity(it implements
        //media controller, hence "this" is passed in as a valid argument)
        playbackController.setAnchorView(findViewById(R.id.songList));//anchor to the scrolling list
        playbackController.setEnabled(true);//enable it
       // playbackController.show();
        Toast.makeText(getApplicationContext(),"tsty",Toast.LENGTH_LONG).show();
    }

    private void playNextTrack(){
        service.playNextTrack();
        if(playbackPaused){
            initPlaybackController();
            playbackPaused=false;
        }
        playbackController.show(0);//display the controller each time a song is clicked
    }
    private void playPrevTrack(){
        service.playPreviousTrack();
        if(playbackPaused){
            initPlaybackController();
            playbackPaused=false;
        }
        playbackController.show(0);
    }


    //will be used to plug us into the service
    //will let us access the service object and do stuff before it starts
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mp3ServiceBinder binder = (mp3ServiceBinder)iBinder;//the service I think returns an iBinder
            service=binder.getService();//the binder "gives us" the service its holding from us
            //now we can do stuff with the service
            service.setMp3SongList(mp3SongList);
            binded=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded=false;
        }
    };

    //finds all the song metadata on the device, and attaches it to Song objects. it then
    //fills up the mp3SongList array list. Note Song objects have no actual .mp3 files in them
    //they just represent the metadata like artist, title etc.
    public void retrieveSongsFromDevice(){
        ContentResolver findMeMusic=getContentResolver();//create the resolver object. will help us find content on phone.
        Uri musicAddress= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//gets us to the song files.
        Cursor mp3FilesCursor=findMeMusic.query(musicAddress,null,null,null,null);
        //findMeMusic is the tool to search the content. it queries here, pulling up the database. and
        //now the cursor is ready to search through it.

        //if the cursor found the query ok AND the cursor successfullt moved to the first row
        if(mp3FilesCursor!=null&&mp3FilesCursor.moveToFirst()){
            int titleColumnNumber=mp3FilesCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumnNumber=mp3FilesCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int idColumnNumber=mp3FilesCursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do{
                //cursor moves down row by row. columns naturally stay the same
                long id=mp3FilesCursor.getLong(idColumnNumber);
                String title=mp3FilesCursor.getString(titleColumnNumber);
                String artist=mp3FilesCursor.getString(artistColumnNumber);
                //now we have all the metadata for one song. time to make a new song object.
                mp3SongList.add(new Song(id,title,artist));
            }while(mp3FilesCursor.moveToNext());//move to next row. return false if theres no more
            //By this point, there will be a full song list

        }
    }

    //song click listener
    public void songSelectedListener(View view){
        //the numerical positions of the song list match the array list (0,1,2,etc.)
        //we set the track we want to play here
        service.setTrackNumber(Integer.parseInt(view.getTag().toString()));
        service.playSong();//and now we play the track.
        if(playbackPaused){
            initPlaybackController();
            playbackPaused=false;
        }
        playbackController.show(0);
    }
    //TODO shuffle buttons and such maybe

    @Override
    //runs when this activity ends
    protected void onDestroy(){
        stopService(mp3Intent);//
        service=null;//no more service
        super.onDestroy();//superclass takes care of remaining stuff to destroy
    }





    //MEDIA PLAYER WIDGET BUTTON LISTENERS

    @Override
    public void start() {
        service.startPlayback();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        service.pausePlayback();
    }

    @Override
    public int getDuration() {
        if(service!=null&&binded&&service.isPlaying()){
            return service.getDuration();
        }
        return 0;
    }


    @Override
    public int getCurrentPosition() {
        if(service!=null&&binded&&service.isPlaying()){
            return service.getCurrentPosition();
        }
        else{
            return 0;
        }
    }

    @Override
    public void seekTo(int i) {
        service.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        if(service!=null&&binded){
            return service.isPlaying();
        }
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
