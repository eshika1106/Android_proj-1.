package com.example.bullsappandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //TextView insurance1;
    //Button binsurance1;

    //private String name [] = {"text1", "text3", "text2"};

    private static int SPLASH_SCREEN = 5000;
    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //insurance1 = (TextView) findViewById(R.id.insurance);
        //binsurance1 = (Button) findViewById(R.id.binsurance);
        //binsurance1.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //Random random = new Random();
                //int num = random.nextInt(name.length);
                //insurance1.setText((name[num]));

        //});



        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogon = findViewById(R.id.textView2);


        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogon.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(image, "logo_image");
                pairs[1] = new Pair<View,String>(logo, "logo_text");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        },SPLASH_SCREEN);

    }
}