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
import android.widget.Toast;

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
        addJoystick();
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
            public void onClick(View v) { }
        });

        upArrow.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.isPressed())
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
                if (v.isPressed())
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
                if (v.isPressed())
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
                if (v.isPressed())
                    sergey.setX(sergey.getX() - movingSpeed);
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addJoystick(){
        final ImageView circle = findViewById(R.id.circle);
        final ImageView control = findViewById(R.id.control);

//        control.setX(circle.getX() + circle.getWidth() / 2 - control.getWidth() / 2);
//        control.setY(circle.getY() + circle.getHeight() / 2 - control.getHeight() / 2);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });


        circle.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 float cursorX;
                 float cursorY;

                if (event.getAction() != MotionEvent.ACTION_UP) {
                    cursorX = event.getX() + circle.getX();
                    cursorY = event.getY() + circle.getY();
                }else{
                    cursorX = circle.getX() + circle.getWidth() / 2 - control.getWidth() / 2;
                    cursorY = circle.getY() + circle.getHeight() / 2 - control.getHeight() / 2;
                }

                if (cursorX < circle.getX())
                    cursorX = circle.getX();
                if (cursorX > circle.getX() + circle.getWidth() - control.getWidth())
                    cursorX = circle.getX() + circle.getWidth() - control.getWidth();

                if (cursorY < circle.getY())
                    cursorY = circle.getY();
                if (cursorY > circle.getY() + circle.getHeight() - control.getHeight())
                    cursorY = circle.getY() + circle.getHeight() - control.getHeight();

                control.setX(cursorX);
                control.setY(cursorY);

                float hSpeed;
                float vSpeed;

                float hDifference = control.getX() - circle.getX() - circle.getWidth() / 2;
                if (hDifference > 0)
                    hDifference = control.getX() + control.getWidth() - circle.getX() - circle.getWidth() / 2;

                float vDifference = control.getY() - circle.getY() - circle.getHeight() / 2;
                if (vDifference > 0)
                    vDifference = control.getY() + control.getHeight() - circle.getY() - circle.getHeight() / 2;

                hSpeed = movingSpeed * hDifference / circle.getWidth() * 2;
                vSpeed = movingSpeed * vDifference / circle.getHeight() * 2;

                // System.out.println(hSpeed + " " + vSpeed);
                final ImageView sergey = findViewById(R.id.img);
                sergey.setX(sergey.getX() + hSpeed);
                sergey.setY(sergey.getY() + vSpeed);


                return false;
            }
        });
    }

}
