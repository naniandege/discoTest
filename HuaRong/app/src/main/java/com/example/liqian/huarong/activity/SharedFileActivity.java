package com.example.liqian.huarong.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.SharedFileAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.SharedFilePresenter;
import com.example.liqian.huarong.mvp.v.SharedFileView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SharedFileActivity extends BaseActivity<SharedFileView, SharedFilePresenter> implements SharedFileView {

    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.show_ed)
    EditText showEd;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.query)
    Button query;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    private ArrayList<EntityFile> list;
    private SharedFileAdapter adapter;
    private int totalPages;
    private String stringPick;
    private int pageCount = 1;
    private Gson gson;

    @Override
    protected SharedFilePresenter initPresenter() {
        return new SharedFilePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared_file;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        adapter = new SharedFileAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initToolbar() {
        toolbar.setTitle(" ");
        toolbarName.setText("共享文件");
        toolbarName.setTextColor(getResources().getColor(R.color.c_000000));
        setSupportActionBar(toolbar);
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Mongo_File_PageSelect"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageCount = 1;
                list.clear();
                getFileData();
                adapter.notifyDataSetChanged();
                srl.finishRefresh(true);

            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (pageCount < totalPages) {
                    ++pageCount;
                    getFileData();
                    adapter.notifyDataSetChanged();
                    srl.finishLoadMore(true);
                } else {
                    srl.finishLoadMore(false);
                }
            }
        });
    }

    @OnClick(R.id.query)
    public void onViewClicked() {
        getFileData();
    }

    private void getFileData() {
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityFile mBase = new EntityFile();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            mBase.settb_code("9999");
            mBase.settype_code("001");
            mBase.setpage_size(20);
            mBase.setpage_current(pageCount);
            mBase.setfile_origname(showEd.getText().toString());
            General<Object> general = new General<>();
            String Json = general.bllMonJson(mBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }


    @Override
    public void setData(String data) {
        stringPick = Common.StringPick(data);
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
            Type type = new TypeToken<EntPages<EntityFile>>() {
            }.getType();
            EntPages<EntityFile> entPages = gson.fromJson(decode, type);
            List<EntityFile> pageResults1 = entPages.getPageResults();
            List<EntityFile> entityFiles = Common.defDecodeEntityList(pageResults1);
            totalPages = entPages.getTotalPages();
            if (entityFiles != null && entityFiles.size() > 0) {
                list.clear();
                list.addAll(entityFiles);
                adapter.notifyDataSetChanged();
                //关闭软键盘
                Tools.closeKeyBoard(this);
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
