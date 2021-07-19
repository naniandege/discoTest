package com.example.liqian.huarong.activity;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.InboxAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.InboxPresenter;
import com.example.liqian.huarong.mvp.v.InboxView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.WeiboDialogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InboxActivity extends BaseActivity<InboxView, InboxPresenter> implements InboxView, InboxAdapter.onClickLisnner {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private Gson gson;
    private int total_page;
    private ArrayList<EntityBase> list;
    private InboxAdapter adapter;
    private int count = 1;
    private List<EntityBase> entityBases;
    private EntityBase mEntityBase;
    private Dialog loadingDialog;

    @Override
    protected InboxPresenter initPresenter() {
        return new InboxPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inbox;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initecyclerView();

        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

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
                    srl.finishLoadMore(false);
                }
            }
        });


        adapter.setOnClickLisnner(this);
    }

    private void initecyclerView() {
        list = new ArrayList<>();
        adapter = new InboxAdapter(list, this);
        lv.setAdapter(adapter);
        //recycleView显示方式:线性
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
        //分割线
//        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
    }

    private void initToolbar() {
        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Email_PageSelect"));
    }

    @Override
    public void getData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityBase entityBase = new EntityBase();
            entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityBase.settb_code("100000");
            entityBase.setbase_pers_codes(Common.LoginUser.getpers_code());
            entityBase.setpage_size(30);
            entityBase.setpage_current(count);
            entityBase.setorder_list("make_date desc");
            General<Object> general = new General<>();
            String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.emailData(encode);
        }
    }

    @Override
    public void emailData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<EntPages<EntityBase>>() {
            }.getType();
            EntPages<EntityBase> entPages = gson.fromJson(decode, type);
            List<EntityBase> pageResults = entPages.getPageResults();
            entityBases = Common.defDecodeEntityList(pageResults);
            total_page = entPages.getTotalPages();
            if (entityBases != null && entityBases.size() > 0) {
                list.addAll(entityBases);
                adapter.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(loadingDialog);
            } else {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


/*
    @Override
    public void onClick(String baseCode, int position) {
        if (list.get(position).getfile_num() > 0) {
            Intent intent = new Intent(this, AttachmentActivity.class);
            intent.putExtra("baseCode", baseCode);
            startActivity(intent);
        } else {
            ToastUtil.showShort("附件数为0");
        }
    }
*/


    @Override
    public void getUpdate(String data) {
        String stringPick = Common.StringPick(data);
        if (stringPick != null) {
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
            if (pubReturn.getState()) {
                EntityBase entityBase = new EntityBase();
                entityBase.setcorp_tn(mEntityBase.getcorp_tn());
                entityBase.setbase_code(mEntityBase.getbase_code());
                entityBase.setis_collect(1);
                entityBase.setpers_code(Common.LoginUser.getpers_code());
                General<Object> general = new General<>();
                String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.sendData(encode);
            }
        }
    }

    @Override
    public void sendData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            ToastUtil.showShort("收藏成功");
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void onCollectionClick(int position) {
        mEntityBase = list.get(position);
        presenter.getUpdate(BLL.BLLJsonByStream("In_BaseMyCollect_Upsert"));
    }
}
