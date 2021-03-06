package com.example.liqian.huarong.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.BearActivity;
import com.example.liqian.huarong.activity.BorrowingActivity;
import com.example.liqian.huarong.activity.BudgetActivity;
import com.example.liqian.huarong.activity.CardAuditActivity;
import com.example.liqian.huarong.activity.ContractActivity;
import com.example.liqian.huarong.activity.CostActivity;
import com.example.liqian.huarong.activity.FieldActivity;
import com.example.liqian.huarong.activity.IntegralActivity;
import com.example.liqian.huarong.activity.MaintenanceActivity;
import com.example.liqian.huarong.activity.OrdinaryActivity;
import com.example.liqian.huarong.activity.PayMentActivity;
import com.example.liqian.huarong.activity.PropertyActivity;
import com.example.liqian.huarong.activity.TaskDetailActivity;
import com.example.liqian.huarong.activity.TheSealActivity;
import com.example.liqian.huarong.activity.ToleranceActivity;
import com.example.liqian.huarong.activity.TopUpActivity;
import com.example.liqian.huarong.activity.TravelActivity;
import com.example.liqian.huarong.adapter.ToduAdapter;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.ToduPresenter;
import com.example.liqian.huarong.mvp.v.ToduView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuditFragment extends BaseFragment<ToduView, ToduPresenter> implements ToduView, ToduAdapter.onItemClickListener {


    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.audit_img)
    LinearLayout auditimg;

    private ArrayList<EntityWorkFlow> list;
    private ToduAdapter adapter;
    private int count = 1;
    private List<EntityWorkFlow> entityWorkFlows;
    private EntityWorkFlow entityWorkFlow;
    private LinearLayoutManager linearLayoutManager;
    private int total_page;

    public AuditFragment() {
        // Required empty public constructor
    }


    @Override
    protected ToduPresenter initPresenter() {
        return new ToduPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audit;
    }

    @Override
    protected void initView(View inflate) {
        super.initView(inflate);
        //?????????
        list = new ArrayList<>();
        //???????????????
        adapter = new ToduAdapter(list, getContext());
        lv.setAdapter(adapter);

        //recycleView????????????:??????
        linearLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(linearLayoutManager);

        //?????????
        lv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        //????????????  ????????????
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false); //????????????????????????
                count = 1;
                list.clear();
                initData();
                adapter.notifyDataSetChanged();
                srl.finishRefresh(true);

            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (count < total_page) {
                    ++count;
                    initData();
                    adapter.notifyDataSetChanged();
                    srl.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();  //???????????????????????????????????????
                    refreshLayout.setNoMoreData(true);  //????????????????????????
                }
            }
        });
        adapter.setOnItemClickListener(this);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ToastUtil.showShort("????????????" + "audit-------------------");
            initData();
        }
    }

    @Override
    protected void initData() {
        super.initData();

        String tudoJson = BLL.TaskPageSelect(5, count, "101");
        presenter.todu(tudoJson);
    }

    /*
     * ?????????
     * ??????????????????
     * */
    @Override
    public void setTudu(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //??????
            String decode = Common.defDecode(pubReturn.getData());
            //??????????????????
            Type type = new TypeToken<EntPages<EntityWorkFlow>>() {
            }.getType();
            EntPages<EntityWorkFlow> entPages = gson.fromJson(decode, type);
            List<EntityWorkFlow> pageResults1 = entPages.getPageResults();
            entityWorkFlows = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            list.clear();
            if (entityWorkFlows != null && entityWorkFlows.size() > 0) {
                //??????????????????
                list.addAll(entityWorkFlows);
                auditimg.setVisibility(View.GONE);
            } else {
                auditimg.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    /*
     * ??????????????????
     * ??????
     * */
    @Override
    public void onItem(int position) {
        entityWorkFlow = list.get(position);
        if (entityWorkFlow.getnode_code().equals("end")) {
            EntityWorkFlow entity = new EntityWorkFlow();
            entity.settask_id(entityWorkFlow.gettask_id());
            entity.setstate_code("900");

            Common.httpPost(BLL.Task_UpdateState(entity), 9, getContext(),
                    new DealWithListener() {
                        @Override
                        public void ServerDataDealWith(PubReturn pubReturn) {
                            //????????????
                            list.clear();
                            initData();
                            adapter.notifyDataSetChanged();
                        }
                    }
            );

        } else if (entityWorkFlow.getnode_code().equals("start")) {
            //??????????????????
            list.remove(position);
            adapter.notifyDataSetChanged();

        } else {
            //????????????
            if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("001")) {
                Intent TaskDetail = new Intent(getActivity(), TaskDetailActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //???????????????.....
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("004")) {
                Intent TaskDetail = new Intent(getActivity(), ToleranceActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("006")) {
                Intent TaskDetail = new Intent(getActivity(), BudgetActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("005")) {
                Intent TaskDetail = new Intent(getActivity(), ContractActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //?????????????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("003")) {
                Intent TaskDetail = new Intent(getActivity(), TopUpActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //??????????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("002")) {
                Intent TaskDetail = new Intent(getActivity(), MaintenanceActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //??????????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("007")) {
                Intent TaskDetail = new Intent(getActivity(), IntegralActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????.......
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("008")) {
                Intent TaskDetail = new Intent(getActivity(), TravelActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("009")) {
                Intent TaskDetail = new Intent(getActivity(), PayMentActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("010")) {
                Intent TaskDetail = new Intent(getActivity(), CostActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("011")) {
                Intent TaskDetail = new Intent(getActivity(), PropertyActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("012")) {
                Intent TaskDetail = new Intent(getActivity(), BorrowingActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("013")) {   //13
                Intent TaskDetail = new Intent(getActivity(), BearActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //??????????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("014")) {   //14
                Intent TaskDetail = new Intent(getActivity(), FieldActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("015")) {    //15
                Intent TaskDetail = new Intent(getActivity(), CardAuditActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("016")) {
                Intent TaskDetail = new Intent(getActivity(), TheSealActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //??????????????????
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("018")) {
                Intent TaskDetail = new Intent(getActivity(), OrdinaryActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
            }
        }
    }


    /*
     *
     * ?????????????????? ??????
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUESTCODE && resultCode == Constants.RESULTCODE) {
            list.clear();
            initData();
            adapter.notifyDataSetChanged();
        }
    }

}
