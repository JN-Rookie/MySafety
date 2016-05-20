package edu.feicui.mysafety.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.mysafety.R;
import edu.feicui.mysafety.bean.Telnumberbean;

/**
 * Created by Administrator on 2016/5/11.
 */
public class Tellistadapter extends BaseAdapter {
    private ArrayList<Telnumberbean> madapterDatas =new ArrayList<>();
    private LayoutInflater mInflater;

    //    在构造方法中传入一个上下文
    public Tellistadapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //    将数据添加适配器adapter的方法
    public void addDatatoadapter(Telnumberbean telnumberbean){
        if(telnumberbean!=null){
            madapterDatas.add(telnumberbean);
        }
    }

    //    添加数据到adapter
    public void addDatatoadapter(List<Telnumberbean> telnumberbean){
        if(telnumberbean!=null){
            madapterDatas.addAll(telnumberbean);
        }
    }

    public void replaceDatatoadapter(List<Telnumberbean> telnumberbeen){
        madapterDatas.clear();
        madapterDatas.addAll(telnumberbeen);
    }
    //    添加从适配器adapter中获取数据的方法
    public ArrayList<Telnumberbean> getDataFromAdapter(){
        return madapterDatas;
    }

//    @Override
    public int getCount() {
        return madapterDatas.size();
    }

//    @Override
    public Telnumberbean getItem(int position) {
        return madapterDatas.get(position);
    }

//    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=mInflater.inflate(R.layout.inflate_tellist_listitem,null);
        }
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_number= (TextView) convertView.findViewById(R.id.tv_number);
        tv_name.setText(getItem(position).name);
        tv_number.setText(getItem(position).number+"");
        return convertView;
    }
}
