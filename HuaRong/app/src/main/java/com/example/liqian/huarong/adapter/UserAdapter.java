package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityLogin;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019-07-10.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<EntityLogin> list;
    private Context context;

    public UserAdapter(ArrayList<EntityLogin> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, final int i) {
        EntityLogin login = list.get(i);

        viewHolder.corp_name.setText(login.getcorp_name());    //公司名称
        viewHolder.pers_name.setText(login.getdept_name());    //部门
        viewHolder.pos_name.setText(login.getpos_name());      //职位

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCliclistnner != null) {
                    onItemCliclistnner.onItem(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView corp_name;
        TextView pos_name;
        TextView pers_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            corp_name = itemView.findViewById(R.id.corp_name);
            pos_name = itemView.findViewById(R.id.pos_name);
            pers_name = itemView.findViewById(R.id.pers_name);
        }
    }

    private onItemCliclistnner onItemCliclistnner;

    public void setOnItemCliclistnner(UserAdapter.onItemCliclistnner onItemCliclistnner) {
        this.onItemCliclistnner = onItemCliclistnner;
    }

    public interface onItemCliclistnner {
        void onItem(int position);
    }
}
