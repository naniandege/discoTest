package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.ShowImgActivity;
import com.example.liqian.huarong.bean.EntityLeaveBean;
import com.example.liqian.huarong.bean.MyImgBean;
import com.example.liqian.huarong.utils.ImageLoader;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHoler> {
    private ArrayList<MyImgBean> list;
    private Context context;

    public ImgAdapter(ArrayList<MyImgBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.img_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final MyImgBean entityBase = list.get(i);
        ImageLoader.setImage(context, entityBase.getUrl(), viewHoler.img, R.drawable.placeholder);

        viewHoler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(list);
                Intent imgIntent = new Intent(context, ShowImgActivity.class);
                imgIntent.putExtra("url", json);
                imgIntent.putExtra("position", i);
                context.startActivity(imgIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
