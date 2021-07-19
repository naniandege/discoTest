package com.example.liqian.huarong.email;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.DraftBoxAdapter;
import com.example.liqian.huarong.adapter.OutBoxAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.DraftPresenter;
import com.example.liqian.huarong.mvp.v.DraftView;
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

public class OutboxActivity extends BaseActivity<DraftView, DraftPresenter> implements DraftView, OutBoxAdapter.onClickLisnner {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;

    private OutBoxAdapter adapter;
    private ArrayList<EntityBase> list;
    private EntityBase mEntityBase;

    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private int count = 1;
    private int total_page;
    private Dialog loadingDialog;

    @Override
    protected DraftPresenter initPresenter() {
        return new DraftPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outbox;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);


        list = new ArrayList<>();
        adapter = new OutBoxAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
//        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        adapter.setOnClickLisnner(this);
    }

    @Override
    protected void initListener() {
        super.initListener();

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
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Email_PageSelect"));
    }

    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        if (stringPick != null) {
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
            if (pubReturn.getState()) {
                EntityBase entityBase = new EntityBase();
                entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());
                entityBase.setif_base_check_1(1);
                entityBase.setbase_check_1(0);
                entityBase.setpage_size(20);
                entityBase.setpage_current(count);
                entityBase.settb_code("100000");
                entityBase.setorder_list("make_date desc");
                entityBase.setmake_pcode(Common.LoginUser.getpers_code());
                entityBase.setread_pers_code(Common.LoginUser.getpers_code());
                General<Object> general = new General<>();
                String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getListData(encode);
            }
        }
    }

    @Override
    public void setListData(String data) {
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
            List<EntityBase> pageResults1 = entPages.getPageResults();
            List<EntityBase> entityBases = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            if (entityBases != null && entityBases.size() > 0) {
                list.addAll(entityBases);
                adapter.notifyDataSetChanged();
                WeiboDialogUtils.closeDialog(loadingDialog);
            }else {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
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

    /*
     * 收藏
     * */
    @Override
    public void onCollectionClick(int position) {
        mEntityBase = list.get(position);
        presenter.getUpdate(BLL.BLLJsonByStream("In_BaseMyCollect_Upsert"));
    }
}
