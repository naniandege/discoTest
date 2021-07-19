package com.example.liqian.huarong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.BudgetActivity;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.widget.ClockDialog;

import java.math.BigDecimal;
import java.util.ArrayList;


public class BudgeAdapter extends RecyclerView.Adapter<BudgeAdapter.ViewHoler> {

    private ArrayList<EntityOA> list;
    private Context context;

    public BudgeAdapter(ArrayList<EntityOA> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHoler(LayoutInflater.from(context).inflate(R.layout.budeg_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, final int position) {
        EntityOA oa = list.get(position);

        viewHoler.oal_deci2_1.setText(oa.getoal_deci2_1() + "");
        viewHoler.oal_nvar100_1.setText(oa.getoal_nvar100_1());
        viewHoler.oal_nvar100_2.setText(oa.getoal_nvar100_2());
        viewHoler.oal_nvar100_3.setText(oa.getoal_nvar100_3());

        //让Double类型完整显示，不用科学计数法显示E
        BigDecimal bigDecima2 = new BigDecimal(oa.getoal_deci2_2());
        String amount2 = bigDecima2.toString();
        viewHoler.oal_deci2_2.setText(amount2);

        //让Double类型完整显示，不用科学计数法显示E
        BigDecimal bigDecima3 = new BigDecimal(oa.getoal_deci2_3());
        String amount3 = bigDecima3.toString();
        viewHoler.oal_deci2_3.setText(amount3);

        viewHoler.oal_deci2_4.setText(oa.getoal_deci2_4() + "");

        //让Double类型完整显示，不用科学计数法显示E
        BigDecimal bigDecima5 = new BigDecimal(oa.getoal_deci2_5());
        String amount5 = bigDecima5.toString();
        viewHoler.oal_deci2_5.setText(amount5);

        //让Double类型完整显示，不用科学计数法显示E
        BigDecimal bigDecima6 = new BigDecimal(oa.getoal_deci2_6());
        String amount6 = bigDecima6.toString();

        viewHoler.oal_deci2_6.setText(amount6);
        viewHoler.oal_nvarmax_1.setText(oa.getoal_nvarmax_1());

        if (position % 2 != 0) {
            viewHoler.itemView.setBackgroundColor(BaseApp.getRes().getColor(R.color.white));
        } else {
            viewHoler.itemView.setBackgroundColor(BaseApp.getRes().getColor(R.color.c_faf5f5));
        }


        viewHoler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListnner != null) {
                    myListnner.onClickMyListnner(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        private TextView oal_nvar100_1;
        private TextView oal_nvar100_2;
        private TextView oal_deci2_1;
        private TextView oal_deci2_2;
        private TextView oal_deci2_3;
        private TextView oal_nvar100_3;
        private TextView oal_deci2_4;
        private TextView oal_deci2_5;
        private TextView oal_deci2_6;
        private TextView oal_nvarmax_1;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            oal_nvar100_1 = itemView.findViewById(R.id.oal_nvar100_1);
            oal_nvar100_2 = itemView.findViewById(R.id.oal_nvar100_2);
            oal_deci2_1 = itemView.findViewById(R.id.oal_deci2_1);
            oal_deci2_2 = itemView.findViewById(R.id.oal_deci2_2);
            oal_deci2_3 = itemView.findViewById(R.id.oal_deci2_3);
            oal_deci2_4 = itemView.findViewById(R.id.oal_deci2_4);
            oal_deci2_5 = itemView.findViewById(R.id.oal_deci2_5);
            oal_deci2_6 = itemView.findViewById(R.id.oal_deci2_6);
            oal_nvarmax_1 = itemView.findViewById(R.id.oal_nvarmax_1);
            oal_nvar100_3 = itemView.findViewById(R.id.oal_nvar100_3);
        }
    }

    private myListnner myListnner;

    public void setMyListnner(BudgeAdapter.myListnner myListnner) {
        this.myListnner = myListnner;
    }

    public interface myListnner {
        void onClickMyListnner(int position);
    }
}
