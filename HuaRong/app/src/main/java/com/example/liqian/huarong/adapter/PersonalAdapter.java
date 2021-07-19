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

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder> {
    private ArrayList<EntityPersonal> list;
    private Context context;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;

    public PersonalAdapter(ArrayList<EntityPersonal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.personl_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonalAdapter.ViewHolder viewHolder, final int i) {
        EntityPersonal entityPersonal = list.get(i);
        viewHolder.personl_code.setText(entityPersonal.getpers_code());
        viewHolder.personl_name.setText(entityPersonal.getpers_name());
        viewHolder.box.setChecked(isItemChecked(i));
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
                if (onItemListnner != null) {
                    onItemListnner.onMyItem(viewHolder, i);
                }

              /*  if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }*/
//                notifyItemChanged(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView personl_code;
        private TextView personl_name;
        private CheckBox box;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            personl_code = itemView.findViewById(R.id.personl_code);
            personl_name = itemView.findViewById(R.id.personl_name);
            box = itemView.findViewById(R.id.box);
        }
    }


    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }


   /* //获得选中条目的结果
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(list.get(i).getpers_name());
            }
        }
        return selectList;
    }*/

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

    private onItemListnner onItemListnner;

    public void setOnItemListnner(PersonalAdapter.onItemListnner onItemListnner) {
        this.onItemListnner = onItemListnner;
    }

    public interface onItemListnner {
        void onMyItem(ViewHolder viewHolder, int position);
    }
}
