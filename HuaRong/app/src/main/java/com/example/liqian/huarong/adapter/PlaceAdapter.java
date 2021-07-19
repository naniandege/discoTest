package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private ArrayList<EntityBase> list;
    private Context context;

    public PlaceAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.place_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getbase_name());
        viewHolder.longitude.setText(list.get(i).getbase_deci2_1() + "");
        viewHolder.latitude.setText(list.get(i).getbase_deci2_2() + "");

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
        private TextView longitude;
        private TextView latitude;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            longitude = itemView.findViewById(R.id.longitude);
            latitude = itemView.findViewById(R.id.latitude);
        }
    }

    private onItemLisnner onItemLisnner;

    public void setOnItemLisnner(PlaceAdapter.onItemLisnner onItemLisnner) {
        this.onItemLisnner = onItemLisnner;
    }

    public interface onItemLisnner {
        void onPosition(int position);
    }
}
