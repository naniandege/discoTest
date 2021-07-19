package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.OpenFilesActivity;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.MyImgBean;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.widget.ClockDialog;

import java.util.ArrayList;


public class AttAdapter extends RecyclerView.Adapter<AttAdapter.ViewHoler> {

    private ArrayList<EntityFile> list;
    private Context context;
    private ArrayList<MyImgBean> imgList = new ArrayList<>();
    private ClockDialog clockDialog;
    private ImgAdapter myImgAdapter;


    String url = null;
    String imgUrl = null;

    public AttAdapter(ArrayList<EntityFile> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.att_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int i) {
        final EntityFile entityFile = list.get(i);
        viewHoler.att_name.setText(entityFile.getfile_origname());

        imgList.clear();
        //加载显示图片的数据
        for (EntityFile file : list) {
            imgUrl = Common.httpAddr + "/" + file.getfile_server_map().replace('\\', '/') + "/" + file.getfile_link();
            String substring = imgUrl.toLowerCase().substring(imgUrl.lastIndexOf(".") + 1);
            if (substring.equals("png") || substring.equals("jpg") || substring.equals("jpeg")
                    || substring.equals("jpeg2000")) {
                MyImgBean myImgBean = new MyImgBean();
                myImgBean.setUrl(imgUrl);
                myImgBean.setfile_origname(file.getfile_origname());
                imgList.add(myImgBean);
            }
        }

        viewHoler.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             *  点击附件的时候先判断点击的是不是图片
             *  如果是 则显示出来
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OpenFilesActivity.class);
                String fileName = entityFile.getfile_link();
                String serverMap = entityFile.getfile_server_map();
                serverMap.replace('\\', '/');
                url = Common.httpAddr + "/" + serverMap + "/" + fileName;
                String subUrl = url.toLowerCase().substring(url.lastIndexOf(".") + 1);
                if (subUrl.equals("png") || subUrl.equals("jpg") || subUrl.equals("jpeg")
                        || subUrl.equals("jpeg2000")) {
                    //如果是图片的话弹出显示图片的dialog
                    showImgDialog();
                } else {
                    intent.putExtra("url", url);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void showImgDialog() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.showimg_item, null);
        RecyclerView mListView = inflate.findViewById(R.id.list_view);
        if (myImgAdapter == null) {
            myImgAdapter = new ImgAdapter(imgList, context);
        }
        mListView.setAdapter(myImgAdapter);
        mListView.setLayoutManager(new GridLayoutManager(context, 3));
        mListView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        if (clockDialog == null) {
            clockDialog = new ClockDialog(context, inflate);
        }
        clockDialog.show();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private TextView att_name;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            att_name = itemView.findViewById(R.id.att_name);
        }
    }

    private myOnItemListener myOnItemListener;

    public void setMyOnItemListener(AttAdapter.myOnItemListener myOnItemListener) {
        this.myOnItemListener = myOnItemListener;
    }

    public interface myOnItemListener {
        void onItem(int position);
    }
}
