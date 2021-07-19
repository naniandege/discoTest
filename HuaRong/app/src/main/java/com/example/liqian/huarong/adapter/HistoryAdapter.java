package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<EntityWorkFlow> list;
    private Context context;

    public HistoryAdapter(ArrayList<EntityWorkFlow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_check_detail_item, null));
    }


    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder viewHolder, int i) {
        EntityWorkFlow entityWorkFlow = list.get(i);
        viewHolder.tv_node_name.setText(entityWorkFlow.getnode_fullname());
        viewHolder.tv_imp_content.setText(entityWorkFlow.gettask_imp_content());
        viewHolder.tv_imp_result.setText(entityWorkFlow.gettask_imp_result());
        viewHolder.tv_imp_date.setText(entityWorkFlow.gettask_imp_date());
        String impSign = entityWorkFlow.gettask_imp_sign();
        if (impSign.isEmpty()) {
            EntityFile ent_file = new EntityFile();
            ent_file.setcorp_tn(Common.LoginUser.getcorp_tn());
            ent_file.setfile_becode(entityWorkFlow.getpapt_pcode());
            ent_file.settb_code("17");
            ent_file.settype_code("1000");
            ent_file.setkind_code("001");

            Common.httpPost2(BLL.FileSelect(ent_file), 4, viewHolder.tv_imp_sign, context,
                    new DealWithListener() {
                        @Override
                        public void ServerDataDealWith(PubReturn pubReturn) {
                            String str = Common.defDecode(pubReturn.getData());
                            Gson gson0 = new Gson();
                            Type type = new TypeToken<List<EntityFile>>() {
                            }.getType();
                            final List<EntityFile> fileList = Common.defDecodeEntityList((List<EntityFile>) gson0.fromJson(str, type));
                            if (fileList.size() > 0) {
                                EntityFile _file = fileList.get(0);
                                String url = Common.httpAddr + Common.LoginUser.getcorp_tn() + "/Mark/" + _file.getfile_link();
                                Glide.with(context).load(url).into((ImageView) pubReturn.getobj());
                            }
                        }
                    });
        } else {
            String url = Common.httpAddr + Common.LoginUser.getcorp_tn() + "/Mark/" + entityWorkFlow.gettask_imp_sign();
            Glide.with(context).load(url).into(viewHolder.tv_imp_sign);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_node_name;
        private TextView tv_imp_content;
        private TextView tv_imp_result;
        private TextView tv_imp_date;
        private ImageView tv_imp_sign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_node_name = itemView.findViewById(R.id.tv_node_name);
            tv_imp_content = itemView.findViewById(R.id.tv_imp_content);
            tv_imp_result = itemView.findViewById(R.id.tv_imp_result);
            tv_imp_date = itemView.findViewById(R.id.tv_imp_date);
            tv_imp_sign = itemView.findViewById(R.id.tv_imp_sign);
        }
    }
}
