package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.DetailedInformationActivity;
import com.example.liqian.huarong.activity.TurnActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.POST;


public class DraftBoxAdapter extends RecyclerView.Adapter<DraftBoxAdapter.ViewHoler> {


    private ArrayList<EntityBase> list;
    private Context context;

    public DraftBoxAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.draftnox_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final EntityBase entityBase = list.get(i);
        viewHoler.baseName.setText(entityBase.getbase_name());
        viewHoler.makeDate.setText(entityBase.getmake_date());
        viewHoler.makePname.setText(entityBase.getmake_pname());
        viewHoler.fileNum.setText(entityBase.getfile_num() + "");

        viewHoler.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickLisnner != null) {
                    onClickLisnner.onClick(i);
                }
            }
        });


        viewHoler.query_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedInformationActivity.class);
                intent.putExtra("entityBase", (Serializable) entityBase);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.base_name)
        TextView baseName;
        @BindView(R.id.make_date)
        TextView makeDate;
        @BindView(R.id.make_pname)
        TextView makePname;
        @BindView(R.id.file_num)
        TextView fileNum;
        @BindView(R.id.send)
        Button send;
        @BindView(R.id.query_information)
        Button query_information;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private onClickLisnner onClickLisnner;

    public void setOnClickLisnner(DraftBoxAdapter.onClickLisnner onClickLisnner) {
        this.onClickLisnner = onClickLisnner;
    }

    public interface onClickLisnner {
        void onClick(int position);
    }
}
