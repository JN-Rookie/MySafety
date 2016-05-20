package edu.feicui.mysafety.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.bean.Telclassbean;

/**
 * Created by Administrator on 2016/5/11.
 */
public class TelclassApdater extends BaseAdapter{
    private ArrayList<Telclassbean> adapterDatas =new ArrayList<>();
    private LayoutInflater mInflater;

    //    在构造方法中传入一个上下文
    public TelclassApdater(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //    将数据添加适配器adapter的方法
    public void addDatatoadapter(Telclassbean telclassbean){
        if(telclassbean!=null){
            adapterDatas.add(telclassbean);
        }
    }

    public void addDatatoadapter(List<Telclassbean> telclassbeen){
        if(telclassbeen!=null){
            adapterDatas.addAll(telclassbeen);
        }
    }
    //    添加从适配器adapter中获取数据的方法
    public ArrayList<Telclassbean> getDataFromAdapter(){
        return adapterDatas;
    }
    //    添加一个删除适配器中数据的方法
    public void clearDataTOAdapter(){
        adapterDatas.clear();
    }
//    @Override
    public int getCount() {
        return adapterDatas.size();
    }

//    @Override
    public Telclassbean getItem(int position) {
        return adapterDatas.get(position);
    }

//    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=mInflater.inflate(R.layout.inflate_telmanage_listitem,null);
        }
        TextView tv_text= (TextView) convertView.findViewById(R.id.textview);
        tv_text.setText(getItem(position).name);
        return convertView;
    }
}
