package com.cheesywow.sergeysergey;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public final int movingSpeed = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView sergey = findViewById(R.id.img);

        sergey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sergey_scream);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
            }
        });

        addArrowListeners();


    }

    @SuppressLint("ClickableViewAccessibility")
    public void addArrowListeners(){

        final ImageView sergey = findViewById(R.id.img);

        ImageView upArrow = findViewById(R.id.upArrow);
        ImageView downArrow = findViewById(R.id.downArrow);
        ImageView leftArrow = findViewById(R.id.leftArrow);
        ImageView rightArrow = findViewById(R.id.rightArrow);

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        upArrow.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sergey.setY(sergey.getY() - movingSpeed);
                return false;
            }
        });


        downArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { }
        });
        downArrow.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sergey.setY(sergey.getY() + movingSpeed);
                return false;
            }
        });


        rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { }
        });
        rightArrow.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sergey.setX(sergey.getX() + movingSpeed);
                return false;
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { }
        });
        leftArrow.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sergey.setX(sergey.getX() - movingSpeed);
                return false;
            }
        });
    }
}
