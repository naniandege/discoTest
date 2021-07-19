package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.net.Common;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private ArrayList<EntityBase> list;
    private Context context;

    public AttendanceAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.atten_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(Common.LoginUser.getpers_name());
        viewHolder.data.setText(list.get(i).getbase_date_1());
        viewHolder.place.setText(list.get(i).getbase_nvar100_1());
        viewHolder.fieldLocation.setText(list.get(i).getbase_nvar100_3());
        viewHolder.fieldThat.setText(list.get(i).getbase_nvarmax_1());
        viewHolder.work.setText(list.get(i).getbase_nvar100_4());
        viewHolder.type.setText(list.get(i).getbase_type());
        BigDecimal bg = new BigDecimal(list.get(i).getbase_deci2_5());
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        viewHolder.distance.setText(f1+"米");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemLisnner != null) {
                    onItemLisnner.onPosition(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView data;
        private TextView place;
        private TextView distance;
        private TextView fieldLocation;
        private TextView fieldThat;
        private TextView work;
        private TextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            data = itemView.findViewById(R.id.data);
            place = itemView.findViewById(R.id.place);
            distance = itemView.findViewById(R.id.distance);
            fieldLocation = itemView.findViewById(R.id.fieldLocation);
            fieldThat = itemView.findViewById(R.id.fieldThat);
            work = itemView.findViewById(R.id.work);
            type = itemView.findViewById(R.id.type);
        }
    }

    private onItemLisnner onItemLisnner;

    public void setOnItemLisnner(AttendanceAdapter.onItemLisnner onItemLisnner) {
        this.onItemLisnner = onItemLisnner;
    }

    public interface onItemLisnner {
        void onPosition(int position);
    }
}
