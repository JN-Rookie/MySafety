package edu.feicui.mysafety.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.service.MusicService;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Animation mAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.iv_show);
        mAnimation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        mImageView.startAnimation(mAnimation);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent musicIntent=new Intent(MainActivity.this,MusicService.class);
                stopService(musicIntent);
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
