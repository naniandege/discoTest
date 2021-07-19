package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.AttachmentActivity;
import com.example.liqian.huarong.activity.OpenFilesActivity;
import com.example.liqian.huarong.activity.ShowImgActivity;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.MyImgBean;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SharedFileAdapter extends RecyclerView.Adapter<SharedFileAdapter.ViewHoler> {

    private ArrayList<EntityFile> list;
    private Context context;
    private ArrayList<MyImgBean> imgList;


    private ClockDialog clockDialog;
    private MyImgAdapter myImgAdapter;

    String url = null;
    String imgUrl = null;
    public SharedFileAdapter(ArrayList<EntityFile> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.sharedfile_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        final EntityFile entityFile = list.get(i);
        viewHoler.fileOrigname.setText(entityFile.getfile_origname());
        viewHoler.fileUptime.setText(entityFile.getfile_uptime());
        viewHoler.persName.setText(entityFile.getpers_name());

        viewHoler.queryFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(context, OpenFilesActivity.class);
                String fileName = entityFile.getfile_link();
                String url = Common.httpAddr + Common.LoginUser.getcorp_tn() + "/File/"+entityFile.getkind_code()+"/" + fileName;
                intent.putExtra("url",url);
                context.startActivity(intent);*/

                if (imgList == null) {
                    imgList = new ArrayList<>();
                }

                Intent intent = new Intent(context, OpenFilesActivity.class);
                String fileName = entityFile.getfile_link();
                String serverMap = entityFile.getfile_server_map();
                serverMap.replace('\\', '/');
                url = Common.httpAddr + "/" + serverMap + "/" + fileName;

                String subUrl = url.toLowerCase().substring(url.lastIndexOf(".") + 1);
                if (subUrl.equals("png") || subUrl.equals("jpg") || subUrl.equals("jpeg")
                        || subUrl.equals("jpeg2000")) {
                    imgList.clear();
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

                    View inflate = LayoutInflater.from(context).inflate(R.layout.showimg_item, null);
                    ListView mListView = inflate.findViewById(R.id.list_view);
                    if (myImgAdapter==null){
                        myImgAdapter = new MyImgAdapter(imgList, context);
                    }
                    mListView.setAdapter(myImgAdapter);
                    if (clockDialog==null){
                        clockDialog = new ClockDialog(context, inflate);
                    }
                    clockDialog.show();


                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if (clockDialog.isShowing()) {
                                clockDialog.dismiss();
                            }

                            Gson gson = new Gson();
                            String json = gson.toJson(imgList);
                            Intent imgIntent = new Intent(context, ShowImgActivity.class);
                            imgIntent.putExtra("url", json);
                            imgIntent.putExtra("position", position);
                            context.startActivity(imgIntent);
                        }
                    });

                } else {
                    intent.putExtra("url", url);
                    context.startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.file_origname)
        TextView fileOrigname;
        @BindView(R.id.file_uptime)
        TextView fileUptime;
        @BindView(R.id.pers_name)
        TextView persName;
        @BindView(R.id.query_file)
        Button queryFile;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
