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
import com.example.liqian.huarong.bean.EntityLeaveBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IntegralAdapter extends RecyclerView.Adapter<IntegralAdapter.ViewHoler> {

    private ArrayList<EntityLeaveBean> list;
    private Context context;

    public IntegralAdapter(ArrayList<EntityLeaveBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.integra_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final EntityLeaveBean entityBase = list.get(i);
        viewHoler.oalDate1.setText(entityBase.getOal_date_1());
        viewHoler.oalDate2.setText(entityBase.getOal_date_2());
        viewHoler.oalNvar1001.setText(entityBase.getOal_nvar100_1());
        viewHoler.oalDeci21.setText(entityBase.getOal_deci2_1());
        viewHoler.oalNvarmax1.setText(entityBase.getOal_nvarmax_1());


        viewHoler.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemLong != null) {
                    onItemLong.onItemLongLisner(i);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.oal_date_1)
        TextView oalDate1;
        @BindView(R.id.oal_date_2)
        TextView oalDate2;
        @BindView(R.id.oal_nvar100_1)
        TextView oalNvar1001;
        @BindView(R.id.oal_deci2_1)
        TextView oalDeci21;
        @BindView(R.id.oal_nvarmax_1)
        TextView oalNvarmax1;
        @BindView(R.id.Delete)
        LinearLayout Delete;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private onItemLong onItemLong;

    public void setOnItemLong(IntegralAdapter.onItemLong onItemLong) {
        this.onItemLong = onItemLong;
    }

    public interface onItemLong {
        void onItemLongLisner(int position);
    }
}
