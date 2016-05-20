package edu.feicui.mysafety.db;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/4/29.
 */
public class AssetsDBManager {
    private static final String TAG = "Assetsmanage";
    public static void copy(String path, File tofile, Context context){
        Log.d(TAG, "开始拷贝: "+path);
        InputStream inputStream=null;

        BufferedInputStream bis=null;
        BufferedOutputStream bos=null;
        try {
            inputStream=context.getAssets().open(path);
            bis=new BufferedInputStream(inputStream);
            bos=new BufferedOutputStream(new FileOutputStream(tofile,false));
            int len=0;
            byte[] buff=new byte[2*1024];
            while((len=bis.read(buff))!=-1){
                bos.write(buff,0,len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
