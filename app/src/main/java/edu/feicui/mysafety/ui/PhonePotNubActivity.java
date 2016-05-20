package edu.feicui.mysafety.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.utils.Constants;
import edu.feicui.mysafety.utils.SpUtils;

public class PhonePotNubActivity extends BasePhoneProtectActivity {

    private EditText mEDTNub;
    private Button   mBtnlogin,mBtnSIMnum;
    private String username, usernumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initEvent();
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_phone_pot_nub);
        mBtnlogin = (Button) findViewById(R.id.btn_numlogin);
        mBtnSIMnum= (Button) findViewById(R.id.btn_simnum);
        mEDTNub = (EditText) findViewById(R.id.edt_num);
    }

    @Override
    public void initEvent() {
        mBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = mEDTNub.getText().toString();

                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                SpUtils.putString(getApplicationContext(), Constants.NUM, num);
                SpUtils.getString(getApplicationContext(), Constants.NUM);
                Toast.makeText(getApplicationContext(), "您绑定的号码为" + num, Toast.LENGTH_LONG).show();
            }
        });

        mBtnSIMnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI), 0);
            }
        });
        super.initEvent();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            // ContentProvider展示数据类似一个单个数据库表
            // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver mCR=getContentResolver();
            // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData=data.getData();
            // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor=managedQuery(contactData,null,null,null,null);
            cursor.moveToFirst();
            // 获得DATA表中的名字
            username=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // 条件为联系人ID
            String contactID=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone=mCR.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactID, null, null);
            while (phone.moveToNext()) {
                usernumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               mEDTNub.setText(usernumber + " (" + username + ")");
            }
        }
    }

    @Override
    protected void nextActivity() {
        start(PhonePotSoundActivity.class);
    }

    @Override
    protected void prevActivity() {
        start(PhonePotSIMActivity.class);
    }


}
