package edu.feicui.mysafety.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.feicui.mysafety.R;

/**
 * Created by Administrator on 2016/5/16.
 */
public class Mydialog extends Dialog implements View.OnClickListener{
    private Context context;
    private OnCancelListener listener;
    private Button mBtn_login;
    private static EditText mEt_pwd;
    private static EditText mEt_pwd1;

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public interface  OnCancelListener{
        public void onClick(View view );
    }
    public Mydialog(Context context) {
        super(context);
        this.context=context;
    }

    protected Mydialog(Context context, OnCancelListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item_phonepot);
        mEt_pwd = (EditText) findViewById(R.id.et_pwd);
        mEt_pwd1 = (EditText) findViewById(R.id.et_pwd1);
//        mBtn_login = (Button) findViewById(R.id.btn_login);
//        mBtn_login.setOnClickListener(this);
    }
    public static boolean getpwd() {

        if (mEt_pwd.getText().toString().equals(mEt_pwd1.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
