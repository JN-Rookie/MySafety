package edu.feicui.mysafety.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.feicui.mysafety.R;

public class PhoneProectActivity extends BasePhoneProtectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_proect);
        initView();
    }

    @Override
    public void initView() {

    }

    @Override
    protected void nextActivity() {
        start(PhonePotSIMActivity.class);
    }

    @Override
    protected void prevActivity() {

    }
}
