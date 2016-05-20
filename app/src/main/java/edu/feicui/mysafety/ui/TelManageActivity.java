package edu.feicui.mysafety.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.adapter.TelclassApdater;
import edu.feicui.mysafety.bean.Telclassbean;
import edu.feicui.mysafety.db.AssetsDBManager;
import edu.feicui.mysafety.db.DBRead;

public class TelManageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView        mListview;
    private TelclassApdater mApdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_manage);
        mListview = (ListView) findViewById(R.id.iv_list);
        mApdater=new TelclassApdater(this);
        mListview.setAdapter(mApdater);
        mListview.setOnItemClickListener(this);
        Toast.makeText(this,"创建在"+ DBRead.sFile, Toast.LENGTH_SHORT).show();
    }
    public void initAppDBFile(){
        // 检测是否存在通讯大全 DB 文件
        if (!DBRead.isExistDBFile()) {
            // 将本地项目中的 Assets/db/commonnum.db 文件复制写出到DBReader.sFile 文件中
            try {
                AssetsDBManager.copy("db/commonnum.db",DBRead.sFile,getApplicationContext());
            } catch (Exception e) {
                Toast.makeText(this,"初始通讯大全数据库文件异常",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onResume(){
        super.onResume();
        initAppDBFile();
        mApdater.clearDataTOAdapter();
        mApdater.addDatatoadapter(new Telclassbean("本地电话",0));
        try {
            mApdater.addDatatoadapter(DBRead.readTeldbClasslist());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
        mApdater.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        本地通话
        if(position==0){
//            跳转到本机拨号界面
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            startActivity(intent);
            return;
        }
        // 取出当前选择的选项实体内容
        Telclassbean classInfo = mApdater.getItem(position);
// 跳转至电话浏览页面,且传入 idx,并且根据 idx 跳转
        Intent intent = new Intent(this, TellistActivity.class);
        intent.putExtra("idx", classInfo.idx);
        startActivity(intent);
    }
}
