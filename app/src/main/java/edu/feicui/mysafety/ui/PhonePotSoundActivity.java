package edu.feicui.mysafety.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.feicui.mysafety.R;

public class PhonePotSoundActivity extends BasePhoneProtectActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pot_sound);
    }

    @Override
    public void initView() {

    }

    @Override
    protected void nextActivity() {
        Toast.makeText(this,"设置完毕",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void prevActivity() {
        start(PhonePotNubActivity.class);
    }
}
