package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityInformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SummarAdapter extends RecyclerView.Adapter<SummarAdapter.ViewHoler> {
    private ArrayList<EntityInformation> list;
    private Context context;

    public SummarAdapter(ArrayList<EntityInformation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.summary_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        EntityInformation information = list.get(i);
        viewHoler.infoNvar10011.setText(information.getinfo_nvar100_11());
        viewHoler.infoNvar10010.setText(information.getinfo_nvar100_10());
        viewHoler.infoNvar1001.setText(information.getinfo_nvar100_1());
        viewHoler.infoNvar1002.setText(information.getinfo_nvar100_2().replaceAll("\n",","));
        viewHoler.infoDeci3.setText(information.getinfo_deci_3()+"");
        viewHoler.infoDeci2.setText(information.getinfo_deci_2()+"");
        viewHoler.infoDeci1.setText(information.getinfo_deci_1()+"");

    /*    if (i % 2 == 0) {
            viewHoler.itemView.setBackgroundColor(context.getResources().getColor(R.color.c_e8eef5));
        } else {
            viewHoler.itemView.setBackgroundColor(context.getResources().getColor(R.color.c_d3e4fd));
        }*/
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.info_nvar100_11)
        TextView infoNvar10011;
        @BindView(R.id.info_deci_3)
        TextView infoDeci3;
        @BindView(R.id.info_deci_1)
        TextView infoDeci1;
        @BindView(R.id.info_deci_2)
        TextView infoDeci2;
        @BindView(R.id.info_nvar100_2)
        TextView infoNvar1002;
        @BindView(R.id.info_nvar100_10)
        TextView infoNvar10010;
        @BindView(R.id.info_nvar100_1)
        TextView infoNvar1001;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
