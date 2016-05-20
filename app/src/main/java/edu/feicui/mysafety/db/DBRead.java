package edu.feicui.mysafety.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import edu.feicui.mysafety.bean.Telclassbean;
import edu.feicui.mysafety.bean.Telnumberbean;


/**
 * 读取和操作数据库的类
 * Created by Administrator on 2016/4/29.
 */
public class DBRead {
    public static File sFile;
    private static final String dbFileDir = "data/data/edu.feicui.mysafety";
    private static final String TAG       = "DBReader";

    static {
//        程序写入到SD卡
//        String sdcard= Environment.getExternalStorageState();
//        if(sdcard.equals(Environment.MEDIA_MOUNTED)){
//            dbFileDir=Environment.getExternalStorageDirectory()+"/azyphone/cache";
//        }
        File fileDri = new File(dbFileDir);
//        Log.d("TAG", "创建成功: "+fileDri.mkdirs());
        fileDri.mkdirs();

        sFile = new File(dbFileDir, "commonnum.db");
    }

    //    检测文件是否创建成功
    public static boolean isExistDBFile() {

        if (!sFile.exists() || sFile.length() <= 0) {
            return false;
        }
        return true;
    }

    //    读取数据库中classlist表中的数据，并把数据写入到TelclassApdater中
    public static ArrayList<Telclassbean> readTeldbClasslist() {
        ArrayList<Telclassbean> bean = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase(sFile, null);
            cursor = db.rawQuery("select * from classlist", null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int idx = cursor.getInt(cursor.getColumnIndex("idx"));
                    Telclassbean classbean = new Telclassbean(name,idx);
                    bean.add(classbean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public static ArrayList<Telnumberbean> readTeldbtable(int idx) {
        ArrayList<Telnumberbean> numberbean = new ArrayList<>();
        String sql="select * from table"+idx;
        SQLiteDatabase db=null;
        Cursor cursor=null;
        try {
            db=SQLiteDatabase.openOrCreateDatabase(sFile,null);
            cursor=db.rawQuery(sql,null);
            if(cursor.moveToFirst()){
                do{
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    String number=cursor.getString(cursor.getColumnIndex("number"));
                    Telnumberbean num=new Telnumberbean(name,number);
                    numberbean.add(num);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                cursor.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return numberbean;
    }
}
