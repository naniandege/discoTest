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
import com.example.liqian.huarong.activity.TaskCheckHistoryActivity;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.io.Serializable;
import java.util.ArrayList;


public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> {
    private ArrayList<EntityWorkFlow> list;
    private Context context;
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    private int newPosition = 0;

    public BatchAdapter(ArrayList<EntityWorkFlow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.batch_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final EntityWorkFlow _Ent = list.get(i);
        viewHolder.wf_name.setText(_Ent.getwf_name());   //流程名称
        viewHolder.task_name.setText(_Ent.gettask_name());  //任务标题
        viewHolder.init_pname.setText(_Ent.getinit_pname());   //发起人
        viewHolder.init_date.setText(_Ent.getinit_date());    //发起时间
        viewHolder.task_arrvdate.setText(_Ent.gettask_arrvdate());   //任务到达时间
        viewHolder.node_fullname.setText(_Ent.getnode_fullname());

        boolean itemChecked = isItemChecked(i);
        viewHolder.selected.setChecked(itemChecked);

        viewHolder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
                newPosition = i;
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
                newPosition = i;
                notifyDataSetChanged();
            }
        });
    }


    public int getPosition(){
        return newPosition;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wf_name;
        private TextView task_name;
        private TextView init_pname;
        private TextView init_date;
        private TextView task_arrvdate;
        private CheckBox selected;
        private TextView node_fullname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wf_name = itemView.findViewById(R.id.wf_name);
            task_name = itemView.findViewById(R.id.task_name);
            init_pname = itemView.findViewById(R.id.init_pname);
            init_date = itemView.findViewById(R.id.init_date);
            task_arrvdate = itemView.findViewById(R.id.task_arrvdate);
            selected = itemView.findViewById(R.id.selected);
            node_fullname = itemView.findViewById(R.id.node_fullname);
        }

    }



    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }




    //获得选中条目的结果
    public ArrayList<EntityWorkFlow> getSelectedItem() {
        ArrayList<EntityWorkFlow> selectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(list.get(i));
            }
        }
        return selectList;
    }


    //更新adpter的数据和选择状态
    public void updateDataSet(ArrayList<EntityWorkFlow> mList) {
        this.list = mList;
        mSelectedPositions = new SparseBooleanArray();
    }


    //设置给定位置条目的选择状态
    public void setItemChecked(int position, boolean isChecked) {
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



    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(BatchAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItem(int position);
    }
}
