package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityPersonal;

import java.util.ArrayList;

public class TurnAdapter extends RecyclerView.Adapter<TurnAdapter.ViewHolder> {
    private ArrayList<EntityPersonal> list;
    private Context context;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;

    public TurnAdapter(ArrayList<EntityPersonal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TurnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.trun_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final TurnAdapter.ViewHolder viewHolder, final int i) {
        EntityPersonal entityPersonal = list.get(i);
        viewHolder.pers_code.setText(entityPersonal.getpers_code());
        viewHolder.pers_un.setText(entityPersonal.getpers_un());
        viewHolder.pers_name.setText(entityPersonal.getpers_name());
        viewHolder.pers_dept.setText(entityPersonal.getpers_dept());

        boolean itemChecked = isItemChecked(i);
        viewHolder.box.setChecked(itemChecked);

        viewHolder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pers_code;
        private CheckBox box;
        private TextView pers_un;
        private TextView pers_name;
        private TextView pers_dept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pers_code = itemView.findViewById(R.id.pers_code);
            pers_un = itemView.findViewById(R.id.pers_un);
            pers_name = itemView.findViewById(R.id.pers_name);
            pers_dept = itemView.findViewById(R.id.pers_dept);
            box = itemView.findViewById(R.id.box);
        }
    }


    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }




    //获得选中条目的结果
    public ArrayList<EntityPersonal> getSelectedItem() {
        ArrayList<EntityPersonal> selectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(list.get(i));
            }
        }
        return selectList;
    }


    //更新adpter的数据和选择状态
    public void updateDataSet(ArrayList<EntityPersonal> mList) {
        this.list = mList;
        mSelectedPositions = new SparseBooleanArray();
    }


    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }


    //根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }

    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }
}
