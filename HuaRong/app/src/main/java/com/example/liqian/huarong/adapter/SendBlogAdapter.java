package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.PhotoBean;

import java.util.ArrayList;


/**
 * Created by lq
 * on 2021/1/19
 */
public class SendBlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<PhotoBean> data;

    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;

    public void setData(ArrayList<PhotoBean> data) {
        this.data = data;
    }


    public SendBlogAdapter(Context context, ArrayList<PhotoBean> data) {
        this.context = context;
        this.data = data;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == ITEM_TWO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item_final, parent, false);
            holder = new AnoViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item, parent, false);
            holder = new ViewHolder(view);
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            PhotoBean photoBean = data.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(context).load(photoBean.getBitmap()).into(viewHolder.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView6);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onImgItemLong!=null){
                onImgItemLong.delete(getAdapterPosition());
            }
        }
    }

    public class AnoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;

        public AnoViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.defaultimg);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onImgItemLong != null) {
                onImgItemLong.onItem();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > data.size() - 1) {
            return ITEM_TWO;
        } else {
            return ITEM_ONE;
        }
    }


    private onImgItemLong onImgItemLong;

    public void setOnImgItemLong(SendBlogAdapter.onImgItemLong onImgItemLong) {
        this.onImgItemLong = onImgItemLong;
    }

    public interface onImgItemLong {
        void onItem();
        void delete(int position);
    }
}
