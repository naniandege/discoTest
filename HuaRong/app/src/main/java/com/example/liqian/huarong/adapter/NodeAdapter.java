package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.util.ArrayList;


public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    private ArrayList<EntityWorkFlow> list;
    private Context context;
    private EntityWorkFlow workFlow;

    public NodeAdapter(ArrayList<EntityWorkFlow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.set_item1, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        workFlow = list.get(i);
        viewHolder.node_code.setText(workFlow.getnode_code());
        viewHolder.node_name.setText(workFlow.getnode_name());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItem(i, viewHolder);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView node_code;
        private TextView node_name;
//        private TextView set_but;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            node_code = itemView.findViewById(R.id.node_code);
            node_name = itemView.findViewById(R.id.node_name);
//            set_but = itemView.findViewById(R.id.set_but);
        }
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(NodeAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItem(int position, ViewHolder viewHolder);
    }
}
