package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.AttchmenAdapter;
import com.example.liqian.huarong.adapter.EmailAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.DetailsInformationPresenter;
import com.example.liqian.huarong.mvp.v.DetailsInformationView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailedInformationActivity extends BaseActivity<DetailsInformationView, DetailsInformationPresenter> implements DetailsInformationView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.lv1)
    RecyclerView lv1;
    private EntityBase mEntityBase;
    private ArrayList<String> list;
    private EmailAdapter adapter;
    private String stringPick;
    private StringBuffer sb = new StringBuffer();
    private StringBuffer sb1 = new StringBuffer();
    private List<EntityBase> mEntityList;
    private Gson gson;


    private ArrayList<EntityFile> listFile;
    private AttchmenAdapter adapterFile;

    @Override
    protected DetailsInformationPresenter initPresenter() {
        return new DetailsInformationPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detailed_information;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        initRecyclerView();

        Intent intent = getIntent();
        mEntityBase = (EntityBase) intent.getSerializableExtra("entityBase");
    }


    private void initRecyclerView() {
        list = new ArrayList<>();
        adapter = new EmailAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
//        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        listFile = new ArrayList<>();
        adapterFile = new AttchmenAdapter(listFile, this);
        lv1.setAdapter(adapterFile);
        lv1.setLayoutManager(new LinearLayoutManager(this));
        lv1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }


    private void initToolbar() {
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Base_Select"));   //邮件详情
    }

    @Override
    public void setData(String data) {
        stringPick = Common.StringPick(data);
        gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            if (mEntityBase.gettype_code().equals("001")) {
                //回复邮件
                EntityBase entityBase = new EntityBase();
                entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());   //登录的corp
                entityBase.setbase_upcode(mEntityBase.getbase_code()); //该记录的 base_code
                entityBase.setread_pers_code(Common.LoginUser.getpers_code());
                entityBase.setorder_list("read_state,make_date desc");
                if (mEntityBase.getdirect_state().equals("收")) {
                    entityBase.setmake_pcodes("," + Common.LoginUser.getpers_code() + ","
                            + mEntityBase.getmake_pcode() + ",");
                }
                General<Object> general = new General<>();
                String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getListData(encode);
            } else {
                //回复邮件
                getReplyData();
                //主邮件
                EntityBase mBase = new EntityBase();
                mBase.setcorp_tn(Common.LoginUser.getcorp_tn());       //登录的corp
                mBase.setbase_code(mEntityBase.getbase_upcode());  //该记录的 base_code
                General<Object> general = new General<>();
                String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
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
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityBase>>() {
            }.getType();
            List<EntityBase> entityBases = gson.fromJson(decode, type);
            mEntityList = Common.defDecodeEntityList(entityBases);
            if (mEntityBase.gettype_code().equals("001")) {
                for (EntityBase entityBase : mEntityList) {
                    sb.append(" Re：" + entityBase.getbase_name());
                    sb.append("\n\r");
                    sb.append("发件人：" + entityBase.getmake_pname() + "  发送时间：" + entityBase.getmake_date());
                    sb.append("\n\r");
                    sb.append(entityBase.getbase_nvarmax_1());
                    sb.append("\n\r");
                    sb.append("———————————————————————————————");
                    sb.append("\n\r");
                }
                sb.append("标题：" + mEntityBase.getbase_name());
                sb.append("\n\r");
                sb.append("发件人：" + mEntityBase.getmake_pname() + "  发送时间：" + mEntityBase.getmake_date());
                sb.append("\n\r");
                sb.append(mEntityBase.getbase_nvarmax_1());
                sb.append("\n\r");
                sb.append("———————————————————————————————");
                sb.append("\n\r");
                list.add(sb.toString());
                adapter.notifyDataSetChanged();
            }

            if (mEntityBase.getfile_num()>0){
                presenter.getfile(BLL.BLLJsonByStream("In_Mongo_File_Select")); //附件
            }else {
                ToastUtil.showShort("附件数为0");
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    private void getReplyData() {
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityBase mBase = new EntityBase();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            mBase.setbase_upcode(mEntityBase.getbase_upcode());
            mBase.setread_pers_code(Common.LoginUser.getpers_code());
            mBase.setmake_pcodes("," + Common.LoginUser.getpers_code() + ","
                    + mEntityBase.getmake_pcode() + ",");
            mBase.setorder_list("read_state,make_date desc");
            General<Object> general = new General<>();
            String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getreplyData(encode);
        }
    }


    @Override
    public void getreplyData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityBase>>() {
            }.getType();
            List<EntityBase> entityBases = gson.fromJson(decode, type);
            List<EntityBase> entityList = Common.defDecodeEntityList(entityBases);
            if (entityList != null && entityList.size() > 0) {
                for (EntityBase entityBase : entityList) {
                    sb1.append(" Re：" + entityBase.getbase_name());
                    sb1.append("\n\r");
                    sb1.append("发件人：" + entityBase.getmake_pname() + "  发送时间：" + entityBase.getmake_date());
                    sb1.append("\n\r");
                    sb1.append(entityBase.getbase_nvarmax_1());
                    sb1.append("\n\r");
                    sb1.append("———————————————————————————————");
                    sb1.append("\n\r");
                }

                sb1.append("标题：" + mEntityList.get(0).getbase_name());
                sb1.append("\n\r");
                sb1.append("发件人：" + mEntityList.get(0).getmake_pname() + "  发送时间：" + mEntityList.get(0).getmake_date());
                sb1.append("\n\r");
                sb1.append(mEntityList.get(0).getbase_nvarmax_1());
                sb1.append("\n\r");
                sb1.append("———————————————————————————————");
                sb1.append("\n\r");
                list.add(sb1.toString());
                adapter.notifyDataSetChanged();
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void getfile(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityFile entityFile = new EntityFile();
            entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityFile.setfile_becode(mEntityBase.getbase_code());
            entityFile.settb_code("100");
            entityFile.settype_code("001");
            entityFile.setkind_code("003");
            General<Object> general = new General<>();
            String Json = general.bllMonJson(entityFile, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getfileList(encode);
        }
    }

    @Override
    public void getfileList(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityFile>>() {
            }.getType();
            List<EntityFile> entityFiles = gson.fromJson(decode, type);
            List<EntityFile> entityFileList = Common.defDecodeEntityList(entityFiles);
            if (entityFileList != null && entityFileList.size() > 0) {
                listFile.addAll(entityFileList);
                adapterFile.notifyDataSetChanged();
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
