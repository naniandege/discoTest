package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    private ArrayList<EntityWorkFlow> list;
    private Context context;


    public MyListAdapter(ArrayList<EntityWorkFlow> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    //这个方法返回值是几,ListView的条目就画几个
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //ListView每画一个条目,就会调用一次这个方法
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=null;
        if(convertView==null){
            //把自条目布局加载进来
            convertView = LayoutInflater.from(context).inflate(R.layout.my_list_tem, parent, false);
            holder = new ViewHolder();
            holder.Wf_name = (TextView) convertView.findViewById(R.id.Wf_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        EntityWorkFlow flow = list.get(position);

        holder.Wf_name.setText(flow.getwf_name());

        return convertView;
    }
    class ViewHolder{
        TextView Wf_name;
    }
}
