package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.AttachmentActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHoler> {


    private ArrayList<EntityBase> list;
    private Context context;

    public WorkAdapter(ArrayList<EntityBase> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.work_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        final EntityBase entityBase = list.get(i);
        viewHoler.basename.setText(entityBase.getbase_name());
        viewHoler.makepname.setText(entityBase.getmake_pname());
        viewHoler.baseNvarmax1.setText(entityBase.getbase_nvarmax_1());
        viewHoler.makedate.setText(entityBase.getmake_date());
        viewHoler.fileNum.setText(entityBase.getfile_num() + "");

        viewHoler.query_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entityBase.getfile_num() > 0) {
                    Intent intent = new Intent(context, AttachmentActivity.class);
                    intent.putExtra("baseCode", entityBase.getbase_code());
                    context.startActivity(intent);
                } else {
                    ToastUtil.showShort("附件数为0");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.base_name)
        TextView basename;
        @BindView(R.id.make_pname)
        TextView makepname;
        @BindView(R.id.base_nvarmax_1)
        TextView baseNvarmax1;
        @BindView(R.id.make_date)
        TextView makedate;
        @BindView(R.id.file_num)
        TextView fileNum;
        @BindView(R.id.query_file)
        Button query_file;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
