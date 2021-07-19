package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.PhotoBean;
import com.example.liqian.huarong.utils.ImageLoader;
import com.example.liqian.huarong.widget.ClockDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private ArrayList<PhotoBean> list;
    private Context context;

    public PhotoAdapter(ArrayList<PhotoBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.camera, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        PhotoBean bean = list.get(i);
        Glide.with(context).load(bean.getBitmap()).into(viewHolder.cameraImg);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onImgItemLong!=null){
                    onImgItemLong.onImgLong(i);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.camera_img)
        ImageView cameraImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private onImgItemLong onImgItemLong;

    public void setOnImgItemLong(PhotoAdapter.onImgItemLong onImgItemLong) {
        this.onImgItemLong = onImgItemLong;
    }

    public interface onImgItemLong{
        void onImgLong(int position);
    }
}
