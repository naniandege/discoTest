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
import com.example.liqian.huarong.bean.EntityLeaveBean;
import com.example.liqian.huarong.bean.EntityOA;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class askAdapter extends RecyclerView.Adapter<askAdapter.ViewHoler> {
    private ArrayList<EntityLeaveBean> list;
    private Context context;

    public askAdapter(ArrayList<EntityLeaveBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.leavedate_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final EntityLeaveBean entityBase = list.get(i);
        viewHoler.oalDate1.setText(entityBase.getOal_date_1());
        viewHoler.oalDate2.setText(entityBase.getOal_date_2());
        viewHoler.oalDate11.setText(entityBase.getOal_date_1_1());
        viewHoler.oalDate21.setText(entityBase.getOal_date_2_1());


        viewHoler.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemLong!=null){
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
        @BindView(R.id.oal_date_1_1)
        TextView oalDate11;
        @BindView(R.id.oal_date_2)
        TextView oalDate2;
        @BindView(R.id.oal_date_2_1)
        TextView oalDate21;
        @BindView(R.id.Delete)
        LinearLayout Delete;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private onItemLong onItemLong;

    public void setOnItemLong(askAdapter.onItemLong onItemLong) {
        this.onItemLong = onItemLong;
    }

    public interface onItemLong{
        void onItemLongLisner(int position);
    }
}
