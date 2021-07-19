package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.DetailedInformationActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OutBoxAdapter extends RecyclerView.Adapter<OutBoxAdapter.ViewHoler> {

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    private ArrayList<EntityBase> list;
    private Context context;

    public OutBoxAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.dradit_item, null));
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHoler viewHoler, final int i) {
        final EntityBase entityBase = list.get(i);
        viewHoler.baseName.setText(entityBase.getbase_name());
        viewHoler.makeDate.setText(entityBase.getmake_date());
        viewHoler.makePname.setText(entityBase.getbase_pers_names());
        viewHoler.fileNum.setText(entityBase.getfile_num() + "");
        boolean itemChecked = isItemChecked(i);
        viewHoler.box.setChecked(itemChecked);

        viewHoler.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isItemChecked(i)) {
                    setItemChecked(i, true);
                } else {
                    setItemChecked(i, true);
                }
                if (onClickLisnner != null) {
                    onClickLisnner.onCollectionClick(i);
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
        @BindView(R.id.collection)
        LinearLayout collection;
        @BindView(R.id.box)
        CheckBox box;
        @BindView(R.id.query_information)
        Button query_information;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    private onClickLisnner onClickLisnner;

    public void setOnClickLisnner(OutBoxAdapter.onClickLisnner onClickLisnner) {
        this.onClickLisnner = onClickLisnner;
    }

    public interface onClickLisnner {
        void onCollectionClick(int position);
    }
}
