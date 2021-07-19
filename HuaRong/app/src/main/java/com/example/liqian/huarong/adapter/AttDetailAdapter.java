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
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityOA;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AttDetailAdapter extends RecyclerView.Adapter<AttDetailAdapter.ViewHoler> {


    private ArrayList<EntityOA> list;
    private Context context;

    public AttDetailAdapter(ArrayList<EntityOA> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.attdetail_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        final EntityOA entityOA = list.get(i);
        viewHoler.oalDate1.setText(entityOA.getoal_date_1());
        viewHoler.oalDate2.setText(entityOA.getoal_date_2());
        viewHoler.oalDeci21.setText(entityOA.getoal_deci2_1()+"");
        viewHoler.oalNvar1001.setText(entityOA.getoal_nvar100_1());
        viewHoler.oalNvarmax1.setText(entityOA.getoal_nvarmax_1());

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
        @BindView(R.id.oal_nvar100_1)
        TextView oalNvar1001;
        @BindView(R.id.oal_date_1)
        TextView oalDate1;
        @BindView(R.id.oal_date_2)
        TextView oalDate2;
        @BindView(R.id.oal_nvarmax_1)
        TextView oalNvarmax1;
        @BindView(R.id.oal_deci2_1)
        TextView oalDeci21;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
