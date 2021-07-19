package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityFriend;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHoler> {

    private ArrayList<EntityFriend> list;
    private Context context;

    public HomeAdapter(ArrayList<EntityFriend> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.home_item, null));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final EntityFriend entityFriend = list.get(i);
        viewHoler.chatContent.setText(entityFriend.getchat_content());
        viewHoler.chatTime.setText(entityFriend.getchat_time());
        viewHoler.frnd_name.setText(entityFriend.getfrnd_name());

        viewHoler.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClick != null) {
                    onItemLongClick.onItemLongListenner(i);
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

        @BindView(R.id.chat_content)
        TextView chatContent;
        @BindView(R.id.chat_time)
        TextView chatTime;
        @BindView(R.id.frnd_name)
        TextView frnd_name;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private onItemLongClick onItemLongClick;

    public void setOnItemLongClick(HomeAdapter.onItemLongClick onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    public interface onItemLongClick {
        void onItemLongListenner(int position);
    }
}
