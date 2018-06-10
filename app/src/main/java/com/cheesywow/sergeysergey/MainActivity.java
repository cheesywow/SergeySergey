package com.cheesywow.sergeysergey;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final int movingSpeed = 5;
    boolean down = true;

    float cursorX;
    float cursorY;

    float hSpeed;
    float vSpeed;

    float centreX;
    float centreY;

    float hDifference;
    float vDifference;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

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
        //view = new Coordinate_View(this);

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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void addJoystick(){
        final ImageView circle = findViewById(R.id.circle);
        final ImageView control = findViewById(R.id.control);

//        control.setX(circle.getX() + circle.getWidth() / 2 - control.getWidth() / 2);
//        control.setY(circle.getY() + circle.getHeight() / 2 - control.getHeight() / 2);

        centreX = circle.getX() + circle.getWidth() / 2 - control.getWidth() / 2;
        centreY = circle.getY() + circle.getHeight() / 2 - control.getHeight() / 2;


        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });


        circle.setOnTouchListener(new View.OnTouchListener(){
            Coordinate_View view = findViewById(R.id.drawing_screen);

            @Override
            public boolean onTouch(View v, final MotionEvent event){
                view.setCentreCircle(circle.getX()+ circle.getWidth() / 2,
                                     circle.getY()+ circle.getHeight() / 2);
                if(event.getAction() == MotionEvent.ACTION_UP){
                    cursorX = circle.getX() + circle.getWidth() / 2 - control.getWidth() / 2;
                    cursorY = circle.getY() + circle.getHeight() / 2 - control.getHeight() / 2;
                    down = false;
                    view.setCentreCircle(0,0);
                    view.setCentreControl(0,0);
                    view.setTouchCoordinates(0,0);
                    view.updateOverlay();
                }

                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    down = true;
                    cursorX = event.getX() + circle.getX();
                    cursorY = event.getY() + circle.getY();
                    new Thread(new Runnable() {
                        public void run() {
                            while(down){
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                control.setX(cursorX);
                                control.setY(cursorY);

                                hSpeed = movingSpeed * hDifference / circle.getWidth() * 2;
                                vSpeed = movingSpeed * vDifference / circle.getHeight() * 2;

                                // System.out.println(hSpeed + " " + vSpeed);
                                final ImageView sergey = findViewById(R.id.img);
                                sergey.setX(sergey.getX() + hSpeed);
                                sergey.setY(sergey.getY() + vSpeed);
                            }

                        }
                    }).start();
                }

                else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float x = event.getX()-circle.getWidth()/2-control.getWidth()/2;
                    float y = event.getY()-circle.getHeight()/2-control.getHeight()/2;
                    double distance = Math.sqrt(Math.pow(x,2)+ (float) Math.pow(y,2));
                    //Log.d("Distance",""+distance);
                    if (distance > circle.getWidth()/2){
                        double angle  = Math.atan2(x,y);
                        cursorX = (float) ((circle.getWidth()/2)*Math.sin(angle));
                        cursorY = (float) ((circle.getHeight()/2)*Math.cos(angle));
                        cursorX+=circle.getX()+circle.getWidth()/2-control.getWidth()/2;
                        cursorY+=circle.getY()+circle.getHeight()/2-control.getHeight()/2;
                    }
                    else{
                        cursorX = x+circle.getX()+circle.getWidth()/2-control.getWidth()/2;
                        cursorY = y+circle.getY()+circle.getHeight()/2-control.getHeight()/2;
                    }
                    control.setX(cursorX);
                    control.setY(cursorY);

                    hDifference = control.getX() - circle.getX() - circle.getWidth() / 2;
                    if (hDifference > 0)
                        hDifference = control.getX() + control.getWidth() - circle.getX() - circle.getWidth() / 2;

                    vDifference = control.getY() - circle.getY() - circle.getHeight() / 2;
                    if (vDifference > 0)
                        vDifference = control.getY() + control.getHeight() - circle.getY() - circle.getHeight() / 2;

                    view.setCentreControl(  cursorX+control.getWidth()/2,
                                            cursorY+control.getHeight()/2);
                    view.setTouchCoordinates(event.getRawX(), event.getRawY());
                    view.updateOverlay();
                    //Log.d("LOC START","X: "+cursorX+",Y: "+cursorY);
                    //Log.d("LOC END","X: "+event.getRawX()+",Y: "+event.getRawY());
                }
                return false;
            }
        });
    }

}
