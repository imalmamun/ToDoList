package com.almamun.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class SecondPageTimer extends AppCompatActivity {
    private long timeWhenStop = 0;
    private int a = 0;
    Button btn_Start,btn_Stop,btn_Pause;
    ImageView anchor,stopwatch;
    Animation round;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page_timer);
        
        //        mapping start here..........
        btn_Start = findViewById(R.id.btn_Start);
        btn_Stop = findViewById(R.id.btn_Stop);
        stopwatch = findViewById(R.id.stopwatch);
        anchor = findViewById(R.id.anchor);
        timer = findViewById(R.id.timer);
        btn_Pause = findViewById(R.id.btn_Pause);


        btn_Stop.setVisibility(View.INVISIBLE);


//        animation stuff here..........
        round = AnimationUtils.loadAnimation(this,R.anim.round);




        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=1;
                anchor.startAnimation(round);
                btn_Pause.setVisibility(View.VISIBLE);
                btn_Stop.setVisibility(View.VISIBLE);
                btn_Start.setVisibility(View.GONE);

                btn_Stop.animate().alpha(1).setDuration(300).start();
//                start time here..........
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                timeWhenStop = timer.getBase() - SystemClock.elapsedRealtime();
                if(a==1){
                    btn_Pause.setText("Pause");
                }
            }
        });
        btn_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.stop();
                anchor.clearAnimation();
                btn_Start.setVisibility(View.VISIBLE);
                btn_Pause.setVisibility(View.GONE);
                a=1;

            }
        });
        btn_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==1){
                    timeWhenStop = timer.getBase() - SystemClock.elapsedRealtime();
                    timer.stop();
                    anchor.clearAnimation();
                    btn_Pause.setText("Paused");
                    a=0;
                }
                else if(a==0){
                    timer.setBase(SystemClock.elapsedRealtime() + timeWhenStop);
                    timer.start();
                    btn_Pause.setText("Pause");
                    anchor.startAnimation(round);

                    a=1;


                }else{

                }


            }
        });

    }
}