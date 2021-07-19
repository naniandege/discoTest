package com.example.liqian.huarong.fragment;


import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.BatchAdapter;
import com.example.liqian.huarong.adapter.MyListAdapter;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.BatchPresenter;
import com.example.liqian.huarong.mvp.v.BatchView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchFragment extends BaseFragment<BatchView, BatchPresenter> implements BatchView {


    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.lin)
    LinearLayout linearLayout;
    @BindView(R.id.audit_img)
    LinearLayout auditimg;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.popShow)
    TextView popShow;
    @BindView(R.id.menu)
    TextView menuStr;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.linbut)
    LinearLayout linbut;

    private ArrayList<EntityWorkFlow> list;
    private BatchAdapter adapter;
    private int count = 1;
    private LinearLayoutManager linearLayoutManager;
    private int total_page;
    private Gson gson;
    private PopupWindow mPopupWindow;
    private ArrayList<EntityWorkFlow> popList;
    private String wfCode = "";
    private List<EntityWorkFlow> entityList;
    private boolean endTag = false;

    public BatchFragment() {
        // Required empty public constructor
    }


    @Override
    protected BatchPresenter initPresenter() {
        return new BatchPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_batch;
    }

    @Override
    protected void initView(View inflate) {
        super.initView(inflate);

        popList = new ArrayList<>();
        //数据源
        this.list = new ArrayList<>();

        //适配器绑定
        adapter = new BatchAdapter(this.list, getContext());
        lv.setAdapter(adapter);

        //recycleView显示方式:线性
        linearLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(linearLayoutManager);

        //分割线
        lv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        presenter.getData(BLL.BLLJsonByStream("In_Workflow_Select"));

        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false); //标记还有更多数据
                count = 1;
                BatchFragment.this.list.clear();
                initData();
                srl.finishRefresh(true);

            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (count < total_page) {
                    ++count;
                    initData();
                    srl.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();  //完成加载并提示没有更多数据
                    refreshLayout.setNoMoreData(true);  //标记没有更多数据
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ToastUtil.showShort("刷新数据" + "batch");
            initData();
        }
    }

    @Override
    protected void initData() {
        String tudoJson = BLL.TaskPageSelect(50, count, "101", wfCode);
        presenter.todu(tudoJson, "");
    }


    @Override
    protected void initListener() {
        super.initListener();
        menuStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                for (int i = 0; i < list.size(); i++) {
                    adapter.setItemChecked(i, true);
                }
                adapter.notifyDataSetChanged();
            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateDataSet(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /*
     * 数据源
     * 列表数据设置
     * */
    @Override
    public void setTudu(String data, String lodTag) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<EntPages<EntityWorkFlow>>() {
            }.getType();
            EntPages<EntityWorkFlow> entPages = gson.fromJson(decode, type);
            List<EntityWorkFlow> pageResults1 = entPages.getPageResults();
            List<EntityWorkFlow> mEntityWork = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            list.clear();
            if (mEntityWork != null && mEntityWork.size() > 0) {
                list.addAll(mEntityWork);
                auditimg.setVisibility(View.GONE);
                linbut.setVisibility(View.VISIBLE);
            } else {
                auditimg.setVisibility(View.VISIBLE);
                linbut.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void dataUp(String data, EntityWorkFlow entity) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            hideLoading();
            if (entity.getis_end() == 1) {
                initData();
                ToastUtil.showShort("审核完成");
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void backData(String data, EntityWorkFlow workFlow) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            hideLoading();
            if (endTag == true) {
                initData();
                ToastUtil.showShort("已退回修改");
                endTag = false;
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @OnClick({R.id.btn_check, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check:
                ArrayList<EntityWorkFlow> selectedItem = adapter.getSelectedItem();
                if (selectedItem != null && selectedItem.size() > 0) {
                    showLoading();
                    int i = 0;
                    for (EntityWorkFlow flow : selectedItem) {
                        i++;
                        if (i == selectedItem.size()) {
                            flow.setis_end(1);
                            adapter.updateDataSet(list);
                            adapter.notifyDataSetChanged();
                        } else {
                            flow.setis_end(0);
                        }
                        if (flow.getnode_code().equals("end")) {
                            final EntityWorkFlow entity = new EntityWorkFlow();
                            entity.settask_id(flow.gettask_id());
                            entity.setstate_code("900");
                            entity.setis_end(flow.getis_end());
                            presenter.dataUp(BLL.Task_UpdateState(entity), entity);
                        } else if (flow.getnode_code().equals("start")) {
                            if (flow.getis_end() == 1) {
                                initData();
                                ToastUtil.showShort("审核完成");
                                hideLoading();
                            }

                        } else {
                            auditData(flow);
                        }
                    }
                } else {
                    ToastUtil.showShort("请选择审核任务");
                }
                break;
            case R.id.btn_back:
                ArrayList<EntityWorkFlow> selectedItem1 = adapter.getSelectedItem();
                int i = 0;
                if (selectedItem1 != null && selectedItem1.size() > 0) {
                    showLoading();
                    for (EntityWorkFlow flow : selectedItem1) {
                        i++;
                        if (i == selectedItem1.size()) {
                            endTag = true;
                            adapter.updateDataSet(list);
                            adapter.notifyDataSetChanged();
                        }

                        if (flow.getnode_code().equals("start")) {
                            if (endTag == true) {
                                initData();
                                endTag = false;
                                ToastUtil.showShort("已修改");
                                hideLoading();
                            }
                        } else {
                            backAudit(flow);
                        }
                    }
                } else {
                    ToastUtil.showShort("请选择审核任务");
                }
                break;
        }
    }

    private void backAudit(EntityWorkFlow workFlow) {
        final EntityWorkFlow mflow = new EntityWorkFlow();
        mflow.setwf_code(workFlow.getwf_code());
        mflow.settask_code(workFlow.gettask_code());
        mflow.setwf_name(workFlow.getwf_name());
        mflow.settask_id(workFlow.gettask_id());
        mflow.setpers_code(Common.LoginUser.getpers_code());
        mflow.settask_name(workFlow.gettask_name());
        mflow.setfront_node_path(workFlow.getflow_path());///这个还要仔细考虑
        mflow.setstate_code("970");
        mflow.settask_imp_result("退回重做！");
        mflow.settask_imp_content("不同意！请重新修改");
        mflow.settask_imp_date(TimeUtils.getFormatedDateTime2(System.currentTimeMillis()));
        mflow.settask_key(workFlow.gettask_key());
        mflow.settb_code(workFlow.gettb_code());
        mflow.settype_code(workFlow.gettype_code());
        mflow.setkind_code(workFlow.getkind_code());
        presenter.backData(BLL.TaskDispose(mflow), mflow);
    }

    private void auditData(EntityWorkFlow workFlow) {
        final EntityWorkFlow mflow = new EntityWorkFlow();
        mflow.setwf_code(workFlow.getwf_code());
        mflow.settask_code(workFlow.gettask_code());
        mflow.setwf_name(workFlow.getwf_name());
        mflow.settask_id(workFlow.gettask_id());
        mflow.setpers_code(Common.LoginUser.getpers_code());
        mflow.settask_name(workFlow.gettask_name());
        mflow.setfront_node_path(workFlow.getflow_path());///这个还要仔细考虑
        mflow.setstate_code("900");
        mflow.settask_imp_result("审核通过！");
        mflow.settask_imp_content("同意！");
        mflow.settask_imp_date(TimeUtils.getFormatedDateTime2(System.currentTimeMillis()));
        mflow.settask_key(workFlow.gettask_key());
        mflow.settb_code(workFlow.gettb_code());
        mflow.settype_code(workFlow.gettype_code());
        mflow.setkind_code(workFlow.getkind_code());
        mflow.setis_end(workFlow.getis_end());
        Common.httpPost4(BLL.TaskDispose(mflow), 13, getActivity(), new DealWithListener() {
            @Override
            public void ServerDataDealWith(PubReturn pubReturn) {

                hideLoading();
//                Gson gson = new Gson();
                String oldModel = pubReturn.getobj().toString();
                oldModel = Common.defDecode(oldModel);
                // EntityWorkFlow m_flow=  gson.fromJson(oldModel,EntityWorkFlow.class);

                if (oldModel.indexOf("\"v_is_end\":MQ==") != -1) {
                    initData();
                    ToastUtil.showShort("审核完成");
                }

            }
        });
    }

    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityWorkFlow mBase = new EntityWorkFlow();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            General<Object> general = new General<>();
            String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }


    @Override
    public void setListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityWorkFlow>>() {
            }.getType();
            List<EntityWorkFlow> entityBases = gson.fromJson(decode, type);
            entityList = Common.defDecodeEntityList(entityBases);
            if (entityList != null && entityList.size() > 0) {
                EntityWorkFlow flow = new EntityWorkFlow();
                flow.setwf_name("未选择");
                popList.add(flow);
                popList.addAll(entityList);
            } else {
                ToastUtil.showShort("暂无数据");
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    ///弹出popupwindow
    private void pop() {
        if (mPopupWindow == null) {
            ListView listView = new ListView(getContext());
            listView.setDivider(null);
            final MyListAdapter listAdapter = new MyListAdapter(popList, getActivity());
            listView.setAdapter(listAdapter);
            //三要素
            mPopupWindow = new PopupWindow(listView, linearLayout.getWidth(), 700);
            //点击外部消失
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.setOutsideTouchable(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPopupWindow.dismiss();
                    popShow.setText(popList.get(position).getwf_name());
                    wfCode = popList.get(position).getwf_code();
                    if (!wfCode.isEmpty()) {
                        initData();
                    } else if (popList.get(position).getwf_name().equals("未选择")) {
                        wfCode = "";
                        initData();
                    }
                }
            });
        }
        //在某个view的下方显示
        mPopupWindow.showAsDropDown(linearLayout);
    }
}
