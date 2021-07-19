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
import com.example.liqian.huarong.activity.TaskCheckHistoryActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.io.Serializable;
import java.util.ArrayList;


public class ToduAdapter extends RecyclerView.Adapter<ToduAdapter.ViewHolder> {
    private ArrayList<EntityWorkFlow> list;
    private Context context;
    public ToduAdapter(ArrayList<EntityWorkFlow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.w_task_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final EntityWorkFlow _Ent = list.get(i);
        viewHolder.wf_name.setText(_Ent.getwf_name());   //流程名称
        viewHolder.task_name.setText(_Ent.gettask_name());  //任务标题
        viewHolder.node_fullname.setText(_Ent.getnode_fullname());   //审核节点
        viewHolder.init_pname.setText(_Ent.getinit_pname());   //发起人
        viewHolder.init_date.setText(_Ent.getinit_date());    //发起时间
        viewHolder.task_arrvdate.setText(_Ent.gettask_arrvdate());   //任务到达时间


        viewHolder.btn_check.setClickable(false);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItem(i);
                }
            }
        });

        /*审核记录
         * */
        viewHolder.btn_check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TaskCheckHistory = new Intent(context, TaskCheckHistoryActivity.class);
                TaskCheckHistory.putExtra("json", (Serializable) _Ent);
                context.startActivity(TaskCheckHistory);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wf_name;
        private TextView task_name;
        private TextView node_fullname;
        private TextView init_pname;
        private TextView init_date;
        private TextView task_arrvdate;
        private Button btn_check;
        private Button btn_check1;
//        private TextView reason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wf_name = itemView.findViewById(R.id.wf_name);
            task_name = itemView.findViewById(R.id.task_name);
            node_fullname = itemView.findViewById(R.id.node_fullname);
            init_pname = itemView.findViewById(R.id.init_pname);
            init_date = itemView.findViewById(R.id.init_date);
            task_arrvdate = itemView.findViewById(R.id.task_arrvdate);
            btn_check = itemView.findViewById(R.id.btn_check);
            btn_check1 = itemView.findViewById(R.id.btn_check1);
//            reason = itemView.findViewById(R.id.reason);
        }

    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(ToduAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItem(int position);
    }
}
