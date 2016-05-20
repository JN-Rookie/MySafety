package edu.feicui.mysafety.ui;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.adapter.PhoneAdapter;

public class PhonemgrActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    private static final String TAG = "PhonemgrActivity";

    private View                     layout_battery;
    private TextView                 tv_battery;
    private ProgressBar              pb_battery;
    private Integer                  currentBattery;
    private Integer                  temperatureBattery;
    private ListView mListview;
    private PhoneAdapter mAdapter;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemgr);
        initMainButton();
        mContext = PhonemgrActivity.this;
        mListview.setOnItemClickListener(PhonemgrActivity.this);
        mAdapter=new PhoneAdapter(this);
        mListview.setAdapter(mAdapter);
    }

    private void initMainButton() {
        layout_battery = findViewById(R.id.ll_layout_battery);
        layout_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bu=new AlertDialog.Builder(PhonemgrActivity.this);
                bu.setTitle("电池电量信息");
                bu.setItems(new String[]{"当前电量："+currentBattery,"电池温度"+temperatureBattery},null);
                bu.show();
            }
        });
        tv_battery = (TextView) findViewById(R.id.tv_battery);
        pb_battery = (ProgressBar) findViewById(R.id.pb_battery);
        mListview = (ListView) findViewById(R.id.iv_info);
        // 注册电池电量广播接收器(放在控件 findView 后面)
        BatteryBroadcastReceiver broadcastReceiver = new BatteryBroadcastReceiver();
        IntentFilter filter = new
                IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    switch (position){
        case 0:
            TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            // 设备ID
            String deviceId = telManager.getDeviceId();
            // 拿到电话号码
            String line1Number = telManager.getLine1Number();
            // 网络状态编号
            int networkType = telManager.getNetworkType();
            // 国家代码
            String countryIso = telManager.getSimCountryIso();
            // 运营商名
            String operatorName = telManager.getSimOperatorName();
            int phoneType = telManager.getPhoneType();//电话类型
            AlertDialog.Builder bu=new AlertDialog.Builder(PhonemgrActivity.this);
            bu.setTitle("手机卡相关信息");
            bu.setItems(new String[]{"设备ID："+deviceId,"\n电话号码"+line1Number,"\n网络状态编号"+networkType
                +"\n国家代码"+countryIso+"\n运营商名"+operatorName+"\n电话类型"+phoneType},null);
            bu.show();
            break;
        case 1:
            WifiManager wifi = (WifiManager) mContext.getSystemService(WIFI_SERVICE);

            int wifiState = wifi.getWifiState(); // WIFI_STATE_ENABLED = 3
            Log.d(TAG, "WifiManager: wifiState == " + wifiState);

            WifiInfo wifiInfo = wifi.getConnectionInfo();
            String bssid = wifiInfo.getBSSID();
            int ipAddress = wifiInfo.getIpAddress(); // 503425216 --> byte 255.255.255.255
            int linkSpeed = wifiInfo.getLinkSpeed(); // 43 --> mbps /8  3.2  2.5G
            String macAddress = wifiInfo.getMacAddress();
            String ssid = wifiInfo.getSSID();  // 指的是链接的wifi名称
            int networkId = wifiInfo.getNetworkId();
            Log.d(TAG, "WifiManager: bssid == " + bssid);
            Log.d(TAG, "WifiManager: ipAddress == " + ipAddress);
            Log.d(TAG, "WifiManager: linkSpeed == " + linkSpeed);
            Log.d(TAG, "WifiManager: macAddress == " + macAddress);
            Log.d(TAG, "WifiManager: ssid == " + ssid);
            Log.d(TAG, "WifiManager: networkId == " + networkId);

            int sdkInt = Build.VERSION.SDK_INT;
            Log.d(TAG, "SDK: sdkInt == " + sdkInt); // sdkInt == 23 --> 6.0
//            String os = Build.VERSION.BASE_OS;
//            Log.d(TAG, "SDK: os == " + os);
            String release = Build.VERSION.RELEASE; // release == 6.0.1
            Log.d(TAG, "SDK: release == " + release);


            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            Log.d(TAG, "CPU: " + files.length); // 4 4核心的CPU


//            String[] string1=Build.SUPPORTED_ABIS;
            String brand = Build.BRAND;
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            String radioVersion = Build.getRadioVersion();

//            Log.d(TAG, "abis: CPU品牌" + string1);
            Log.d(TAG, "abis: radioVersion == " + radioVersion);
            Log.d(TAG, "abis: 设备 brand " + brand); // 手机品牌
            Log.d(TAG, "abis: 设备 manufacturer " + manufacturer); // 制造商
            Log.d(TAG, "abis: 设备 model " + model); // 具体型号
            AlertDialog.Builder bu1=new AlertDialog.Builder(PhonemgrActivity.this);
            bu1.setTitle("CPU相关信息");
            bu1.setItems(new String[]{"IP地址："+ipAddress,"\n链接的wifi名称"+ ssid
                    +"\n手机品牌"+brand+"\n制造商名"+manufacturer+"\n手机型号"+model},null);
            bu1.show();
            break;
        case 2:
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            ActivityManager manager = (ActivityManager) mContext.getSystemService(
                    ACTIVITY_SERVICE);
            manager.getMemoryInfo(info);
            long availMem = info.availMem;
            Log.d(TAG, "getMemoryInfo: " + availMem / 1024 / 1024); // 272158720  拿到的是瞬间值


            String sdCardPath = MemoryManager.getPhoneInSDCardPath();
            Log.d(TAG, "sdCardPath: " + sdCardPath);

            long phoneSelfSize = MemoryManager.getPhoneSelfSize(); // 1397428224 byte值 /1024 kb /1024 mb
            long mb = phoneSelfSize / 1024 / 1024;
            Log.d(TAG, "phoneSelfSize: " + mb); //phoneSelfSize: 1332

            long phoneSelfFreeSize = MemoryManager.getPhoneSelfFreeSize();
            Log.d(TAG, "phoneSelfFreeSize: " + phoneSelfFreeSize);  //681328640 --> 649

            float phoneSize = MemoryManager.getPhoneSelfSDCardSize(); // 手机内部储存 不是SD卡
            Log.d(TAG, "phoneSize: " + (phoneSize / 1024 / 1024 / 1024));  // 5204983808  约等于5G
            //  4963MB 4.847519GB

            float phoneFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize(); // 约900MB
            Log.d(TAG,
                    "phoneFreeSize: " + (phoneFreeSize / 1024 / 1024 / 1024));  //947101696   phoneFreeSize: 0.8819313

            long phoneAllSize = MemoryManager.getPhoneAllSize();
            long phoneAllFreeSize = MemoryManager.getPhoneAllFreeSize();
            Log.d(TAG,
                    "phoneAllSize: ==" + phoneAllSize / 1024 / 1024 + "   phoneAllFreeSize:==" +
                            phoneAllFreeSize / 1024 / 1024); //phoneAllSize: ==6296   phoneAllFreeSize:==1552
            AlertDialog.Builder bu2=new AlertDialog.Builder(PhonemgrActivity.this);
            bu2.setTitle("CPU相关信息");
            bu2.setItems(new String[]{"自身存储全部大小："+ phoneSelfSize/1024/1024,"\n手机内存"+phoneSize/ 1024 / 1024,
                    "\n手机闲置内存"+phoneSelfFreeSize/ 1024 / 1024
                    +"\n手机总内存"+phoneAllSize/ 1024 / 1024+"\n手机总闲置内存"+phoneAllFreeSize/ 1024 / 1024
                    +"\nSD卡闲置内存" +phoneFreeSize/ 1024 / 1024+"\nSD卡总内存"+phoneSize/ 1024 / 1024},null);
            bu2.show();
            break;
        case 3:
            AlertDialog.Builder bu3=new AlertDialog.Builder(PhonemgrActivity.this);
            bu3.setTitle("是否ROOT,true代表ROOT,FLASE代表为ROOT");
            bu3.setItems(new String[]{""+isRoot()},null);
            bu3.show();
            break;
        case 4:
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay()
                    .getMetrics(metrics);
            int widthPixels = metrics.widthPixels;
            int heightPixels = metrics.heightPixels;
            Log.d(TAG, "DisplayMetrics: " + widthPixels + "===" + heightPixels);  // width
            // 720 height 1184


            // Camera
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            Camera.Size s = null;
            for (Camera.Size size : sizes) {
                if (s == null) {
                    s = size;
                } else if (s.width * s.height < size.width * size.height) {
                    s = size;
                }
            }
            camera.release();
            Log.d(TAG, "onClick: " + s.width + "---" + s.height); // 3264px---2448px 1920*1080
            AlertDialog.Builder bu4=new AlertDialog.Builder(PhonemgrActivity.this);
            bu4.setTitle("分辨率相关信息");
            bu4.setItems(new String[]{"手机屏幕分辨率："+widthPixels+heightPixels,
                   "\n相机分辨率"+s.width+s.height},null);
            bu4.show();
            break;

    }
    }
    public boolean isRoot() {
        boolean bool = false;

        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    public class BatteryBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Bundle bundle = intent.getExtras();
                int maxBattery = (Integer)
                        bundle.get(BatteryManager.EXTRA_SCALE); // 总电量
                currentBattery = (Integer)
                        bundle.get(BatteryManager.EXTRA_LEVEL); // 当前电量
                temperatureBattery = (Integer)
                        bundle.get(BatteryManager.EXTRA_TEMPERATURE); // 电池温度
                pb_battery.setMax(maxBattery);
                pb_battery.setProgress(currentBattery);
                int current100 = currentBattery * 100 / maxBattery;
                tv_battery.setText(current100 + "%");//TextView 显示电量
            }
        }
    }

    class CpuFilter implements FileFilter {
        public boolean accept(File pathname) {
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }
}