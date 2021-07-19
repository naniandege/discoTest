package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityWorkFlow;

import java.util.ArrayList;


public class NodeSelectAdapter extends RecyclerView.Adapter<NodeSelectAdapter.ViewHolder> {

    private ArrayList<EntityWorkFlow> list;
    private Context context;

    public NodeSelectAdapter(ArrayList<EntityWorkFlow> list, Context context) {
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
        final EntityWorkFlow _Ent = list.get(i);
        viewHolder.node_code.setText(_Ent.getnode_code());
        viewHolder.node_name.setText(_Ent.getnode_name());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView node_code;
        private TextView node_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            node_code = itemView.findViewById(R.id.node_code);
            node_name = itemView.findViewById(R.id.node_name);
        }
    }
}
