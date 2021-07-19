package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityOA;

import java.util.ArrayList;


public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHoler> {

    private ArrayList<EntityOA> list;
    private Context context;

    public TravelAdapter(ArrayList<EntityOA> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.travel_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        EntityOA oa = list.get(i);
        viewHoler.oal_deci2_1.setText(oa.getoal_deci2_1()+"");
        viewHoler.oal_nvar100_1.setText(oa.getoal_nvar100_1());
        viewHoler.oal_nvarmax_1.setText(oa.getoal_nvarmax_1());

        if (i % 2 != 0) {
            viewHoler.itemView.setBackgroundColor(BaseApp.getRes().getColor(R.color.white));
        } else {
            viewHoler.itemView.setBackgroundColor(BaseApp.getRes().getColor(R.color.c_faf5f5));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private TextView oal_nvar100_1;
        private TextView oal_deci2_1;
        private TextView oal_nvarmax_1;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            oal_nvar100_1 = itemView.findViewById(R.id.oal_nvar100_1);
            oal_deci2_1 = itemView.findViewById(R.id.oal_deci2_1);
            oal_nvarmax_1 = itemView.findViewById(R.id.oal_nvarmax_1);
        }
    }
}
