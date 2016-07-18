package com.example.alex.realtest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


//This is the "Main Menu"
public class MainActivity extends AppCompatActivity {

    private Intent switchToMp3;
    private Intent switchToDj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //listens for the mp3 button being clicked
    public void mp3ModeClicked(View view){
        switchToMp3=new Intent(this,mp3ModeMainActivity.class);
        startActivity(switchToMp3);
    }

    //listens for dj button being clicked
    public void djModeClicked(View view){
        switchToDj=new Intent(this,DJ_Login.class);
        startActivity(switchToDj);
    }
}
