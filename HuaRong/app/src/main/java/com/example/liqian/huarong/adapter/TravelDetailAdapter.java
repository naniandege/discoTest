package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.TravelBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TravelDetailAdapter extends RecyclerView.Adapter<TravelDetailAdapter.ViewHoler> {

    private ArrayList<TravelBean> list;
    private Context context;

    public TravelDetailAdapter(ArrayList<TravelBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.travel_detail_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final TravelBean entityBase = list.get(i);
        viewHoler.amount.setText(entityBase.getAmount());
        viewHoler.note.setText(entityBase.getNote());
        viewHoler.project.setText(entityBase.getProjectName());

        viewHoler.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (myOnItemListener!=null){
                    myOnItemListener.onItem(i);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.project)
        TextView project;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.note)
        TextView note;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private TravelDetailAdapter.myOnItemListener myOnItemListener;

    public void setMyOnItemListener(TravelDetailAdapter.myOnItemListener myOnItemListener) {
        this.myOnItemListener = myOnItemListener;
    }

    public interface myOnItemListener {
        void onItem(int position);
    }
}
