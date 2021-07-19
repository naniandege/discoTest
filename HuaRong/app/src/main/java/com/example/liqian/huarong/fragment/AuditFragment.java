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
        //数据源
        list = new ArrayList<>();
        //适配器绑定
        adapter = new ToduAdapter(list, getContext());
        lv.setAdapter(adapter);

        //recycleView显示方式:线性
        linearLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(linearLayoutManager);

        //分割线
        lv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false); //标记还有更多数据
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
                    refreshLayout.finishLoadMoreWithNoMoreData();  //完成加载并提示没有更多数据
                    refreshLayout.setNoMoreData(true);  //标记没有更多数据
                }
            }
        });
        adapter.setOnItemClickListener(this);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ToastUtil.showShort("刷新数据" + "audit-------------------");
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
     * 数据源
     * 列表数据设置
     * */
    @Override
    public void setTudu(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<EntPages<EntityWorkFlow>>() {
            }.getType();
            EntPages<EntityWorkFlow> entPages = gson.fromJson(decode, type);
            List<EntityWorkFlow> pageResults1 = entPages.getPageResults();
            entityWorkFlows = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            list.clear();
            if (entityWorkFlows != null && entityWorkFlows.size() > 0) {
                //添加列表数据
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
     * 列表点击事件
     * 审核
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
                            //刷新数据
                            list.clear();
                            initData();
                            adapter.notifyDataSetChanged();
                        }
                    }
            );

        } else if (entityWorkFlow.getnode_code().equals("start")) {
            //删除刷新数据
            list.remove(position);
            adapter.notifyDataSetChanged();

        } else {
            //请假审核
            if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("001")) {
                Intent TaskDetail = new Intent(getActivity(), TaskDetailActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //出公差审批.....
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("004")) {
                Intent TaskDetail = new Intent(getActivity(), ToleranceActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //临时预算
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("006")) {
                Intent TaskDetail = new Intent(getActivity(), BudgetActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //合同审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("005")) {
                Intent TaskDetail = new Intent(getActivity(), ContractActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //燃油卡充值审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("003")) {
                Intent TaskDetail = new Intent(getActivity(), TopUpActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //车辆维修申请
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("002")) {
                Intent TaskDetail = new Intent(getActivity(), MaintenanceActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //党员积分审核
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("007")) {
                Intent TaskDetail = new Intent(getActivity(), IntegralActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //差旅报销.......
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("008")) {
                Intent TaskDetail = new Intent(getActivity(), TravelActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //付款审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("009")) {
                Intent TaskDetail = new Intent(getActivity(), PayMentActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //费用报销
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("010")) {
                Intent TaskDetail = new Intent(getActivity(), CostActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //物业维修
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("011")) {
                Intent TaskDetail = new Intent(getActivity(), PropertyActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //借款审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("012")) {
                Intent TaskDetail = new Intent(getActivity(), BorrowingActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //融资担保
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("013")) {   //13
                Intent TaskDetail = new Intent(getActivity(), BearActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //外勤打卡审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("014")) {   //14
                Intent TaskDetail = new Intent(getActivity(), FieldActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //补卡审批
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("015")) {    //15
                Intent TaskDetail = new Intent(getActivity(), CardAuditActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //印章申请
            } else if (list.get(position).gettb_code().equals("9") &&
                    list.get(position).gettype_code().equals("016")) {
                Intent TaskDetail = new Intent(getActivity(), TheSealActivity.class);
                TaskDetail.putExtra("json", (Serializable) this.entityWorkFlow);
                startActivityForResult(TaskDetail, Constants.REQUESTCODE);
                //普通付款审批
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
     * 页面数据刷新 回调
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
