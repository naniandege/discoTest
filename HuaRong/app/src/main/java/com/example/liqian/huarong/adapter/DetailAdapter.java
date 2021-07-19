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
import com.example.liqian.huarong.bean.EntityInformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHoler> {

    private ArrayList<EntityInformation> list;
    private Context context;

    public DetailAdapter(ArrayList<EntityInformation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.detail_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        EntityInformation information = list.get(i);
        viewHoler.infoNvar1001.setText(information.getinfo_nvar100_1());
        viewHoler.infoNvar1002.setText(information.getinfo_nvar100_2());
        viewHoler.infoNvar1003.setText(information.getinfo_nvar100_3());
        viewHoler.infoNvar1004.setText(information.getinfo_nvar100_4());
        viewHoler.infoNvar1005.setText(information.getinfo_nvar100_5());
        viewHoler.infoNvar1006.setText(information.getinfo_nvar100_6());
        viewHoler.infoNvar1007.setText(information.getinfo_nvar100_7());
        viewHoler.infoNvar1008.setText(information.getinfo_nvar100_8());
        viewHoler.infoNvar1009.setText(information.getinfo_nvar100_9());
        viewHoler.infoNvar10010.setText(information.getinfo_nvar100_10());
        viewHoler.infoNvar10011.setText(information.getinfo_nvar100_11());
        viewHoler.infoNvar10012.setText(information.getinfo_nvar100_12());
        viewHoler.infoNvar10013.setText(information.getinfo_nvar100_13());
        viewHoler.nfoNvar10014.setText(information.getinfo_nvar100_14());
        viewHoler.infoNvar10015.setText(information.getinfo_nvar100_15());
        viewHoler.infoNvar10016.setText(information.getinfo_nvar100_16());
        viewHoler.info_key_2.setText(information.getinfo_key_2());

        /*if (i % 2 == 0) {
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

        @BindView(R.id.info_nvar100_1)
        TextView infoNvar1001;
        @BindView(R.id.info_nvar100_2)
        TextView infoNvar1002;
        @BindView(R.id.info_nvar100_3)
        TextView infoNvar1003;
        @BindView(R.id.info_nvar100_4)
        TextView infoNvar1004;
        @BindView(R.id.info_nvar100_5)
        TextView infoNvar1005;
        @BindView(R.id.info_nvar100_6)
        TextView infoNvar1006;
        @BindView(R.id.info_nvar100_7)
        TextView infoNvar1007;
        @BindView(R.id.info_nvar100_8)
        TextView infoNvar1008;
        @BindView(R.id.info_nvar100_9)
        TextView infoNvar1009;
        @BindView(R.id.info_nvar100_10)
        TextView infoNvar10010;
        @BindView(R.id.info_nvar100_11)
        TextView infoNvar10011;
        @BindView(R.id.info_nvar100_12)
        TextView infoNvar10012;
        @BindView(R.id.info_nvar100_13)
        TextView infoNvar10013;
        @BindView(R.id.nfo_nvar100_14)
        TextView nfoNvar10014;
        @BindView(R.id.info_nvar100_15)
        TextView infoNvar10015;
        @BindView(R.id.info_nvar100_16)
        TextView infoNvar10016;
        @BindView(R.id.info_key_2)
        TextView info_key_2;
        @BindView(R.id.sj)
        LinearLayout sj;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
