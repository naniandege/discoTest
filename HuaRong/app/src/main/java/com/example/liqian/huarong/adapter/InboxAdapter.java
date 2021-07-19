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
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.AttachmentActivity;
import com.example.liqian.huarong.activity.DetailedInformationActivity;
import com.example.liqian.huarong.activity.TurnActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHoler> {


    private ArrayList<EntityBase> list;
    private Context context;

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;

    public InboxAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.inbox_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {

        final EntityBase entityBase = list.get(i);
        viewHoler.baseName.setText(entityBase.getbase_name());
        viewHoler.makeDate.setText(entityBase.getmake_date());
        viewHoler.makePname.setText(entityBase.getmake_pname());
        viewHoler.fileNum.setText(entityBase.getfile_num() + "");

        boolean itemChecked = isItemChecked(i);
        viewHoler.box.setChecked(itemChecked);


        /*
         * 收藏
         * */
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



 /*       *//*      * 查看附件
         **//*

        viewHoler.query_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickLisnner != null) {
                    onClickLisnner.onClick(entityBase.getbase_code(), i);

                }
            }
        });
*/


        /*  * 邮件详情
         * */

        viewHoler.query_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedInformationActivity.class);
                intent.putExtra("entityBase", (Serializable) entityBase);
                context.startActivity(intent);
            }
        });


        /*
         * 邮件转发
         **/
        viewHoler.turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TurnActivity.class);
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
/*        @BindView(R.id.query_file)
        Button query_file;*/
        @BindView(R.id.query_information)
        Button query_information;
        @BindView(R.id.turn)
        Button turn;
        @BindView(R.id.box)
        CheckBox box;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private onClickLisnner onClickLisnner;

    public void setOnClickLisnner(InboxAdapter.onClickLisnner onClickLisnner) {
        this.onClickLisnner = onClickLisnner;
    }

    public interface onClickLisnner {
//        void onClick(String baseCode, int position);

        void onCollectionClick(int position);
    }

    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

}
