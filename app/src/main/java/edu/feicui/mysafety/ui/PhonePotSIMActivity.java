package edu.feicui.mysafety.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.utils.Constants;
import edu.feicui.mysafety.utils.SpUtils;

public class PhonePotSIMActivity extends BasePhoneProtectActivity {
    private static final String TAG = "PhoneProectActivity";
    private Button    mBtnBindSim;
    private ImageView mIvBind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_phone_pot_sim);
        mBtnBindSim = (Button) findViewById(R.id.btn_bind_sim);
        mIvBind = (ImageView) findViewById(R.id.iv_lock);
        if(TextUtils.isEmpty(SpUtils.getString(getApplicationContext(),Constants.SIM))){
           mIvBind.setImageResource(R.mipmap.ic_lock_on);
        }else{
            mIvBind.setImageResource(R.mipmap.ic_lock_lock);
        }
    }

    @Override
    public void initEvent() {
        mBtnBindSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                点击按钮绑定/解锁SIM卡，需要判断状态，卸载SP里
                if (TextUtils.isEmpty(SpUtils.getString(getApplicationContext(), Constants.SIM))) {
//                没有绑定SIM卡
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simSerialNumber = tm.getSimSerialNumber();//SIM卡的唯一ID
//                把SIM卡ID保存
                    SpUtils.putString(getApplicationContext(), Constants.SIM, simSerialNumber);
                    Toast.makeText(PhonePotSIMActivity.this,"绑定了SIM卡",Toast.LENGTH_SHORT).show();
                   mIvBind.setImageResource(R.mipmap.ic_lock_lock);
                }else{
//                    已绑定SIM卡
                    SpUtils.putString(getApplicationContext(),Constants.SIM,"");
                    Toast.makeText(PhonePotSIMActivity.this,"解绑了SIM卡",Toast.LENGTH_SHORT).show();
                    mIvBind.setImageResource(R.mipmap.ic_lock_on);
                }

            }
        });
        super.initEvent();
    }


    @Override
    protected void nextActivity() {
        if(TextUtils.isEmpty(SpUtils.getString(getApplicationContext(),Constants.SIM))){
            Toast.makeText(PhonePotSIMActivity.this,"请先绑定SIM卡再继续操作",Toast.LENGTH_SHORT).show();
            return;
        }
        start(PhonePotNubActivity.class);
    }

    @Override
    protected void prevActivity() {
        start(PhoneProectActivity.class);
    }
}
