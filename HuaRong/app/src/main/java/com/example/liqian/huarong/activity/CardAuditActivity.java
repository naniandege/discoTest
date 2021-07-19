package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.CardAuditPresenter;
import com.example.liqian.huarong.mvp.p.TaskDetailPresenter;
import com.example.liqian.huarong.mvp.v.CardAuditView;
import com.example.liqian.huarong.mvp.v.TaskDetailView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.views.point.HandWriteView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardAuditActivity extends BaseActivity<CardAuditView, CardAuditPresenter> implements CardAuditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_check)
    EditText edCheck;
    @BindView(R.id.ed_show)
    TextView edShow;
    @BindView(R.id.oa_code)
    TextView oaCode;
    @BindView(R.id.make_uname)
    TextView makeUname;
    @BindView(R.id.make_dname)
    TextView makeDname;
    @BindView(R.id.make_pname)
    TextView makePname;
    @BindView(R.id.make_date)
    TextView makeDate;
    @BindView(R.id.oa_nvar100_1)
    TextView oaNvar1001;
    @BindView(R.id.oa_nvar100_2)
    TextView oaNvar1002;
    @BindView(R.id.oa_date_1)
    TextView oaDate1;
    @BindView(R.id.oa_nvar100_3)
    TextView oaNvar1003;
    @BindView(R.id.oa_nvarmax_1)
    TextView oaNvarmax1;
    @BindView(R.id.handWriteView)
    HandWriteView handWriteView;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.layout)
    LinearLayout layout;


    private EntityWorkFlow wfEnt;
    private boolean state;
    private EntityOA entOa;

    @Override
    protected CardAuditPresenter initPresenter() {
        return new CardAuditPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_audit;
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        wfEnt = (EntityWorkFlow) intent.getSerializableExtra("json");

        //限制的最大字数
        final int num = 100;
        //光标移动到末尾
        edCheck.setSelection(edCheck.getText().length());
        edShow.setText("0/100");
        edCheck.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                temp = s;
                edCheck.removeTextChangedListener(this);//**** 注意的地方
                if (s.length() > num) {
                    edCheck.setText(s.toString().substring(0, num));
                    edCheck.setSelection(num);
                    edShow.setText(num + "");
                } else {
                    edShow.setText(s.length() + "/" + num);
                }
                edCheck.addTextChangedListener(this);//****  注意的地方
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //是否为手写签字
        state = (boolean) SpUtil.getParam("pc", false);
        if (state == false) {
            layout.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.taskDetail(BLL.OASelect(wfEnt.gettask_key()));
    }

    @Override
    public void setData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<List<EntityOA>>() {
            }.getType();
            List<EntityOA> oaList = gson.fromJson(decode, type);
            List<EntityOA> list = Common.defDecodeEntityList(oaList);
            entOa = list.get(0);
            oaCode.setText(entOa.getoa_code());
            makeUname.setText(entOa.getmake_uname());
            makeDname.setText(entOa.getmake_dname());
            makePname.setText(entOa.getmake_pname());
            makeDate.setText(entOa.getmake_date());
            oaNvar1001.setText(entOa.getoa_nvar100_1());
            oaNvar1002.setText(entOa.getoa_nvar100_2());
            oaNvar1003.setText(entOa.getoa_nvar100_3());
            oaDate1.setText(entOa.getoa_date_1());
            oaNvarmax1.setText(entOa.getoa_nvarmax_1());

        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void getData(String data) {
        String stringPick = Common.StringPick(data);
        if (stringPick != null) {
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
            if (pubReturn.getState()) {
                EntityBase entityBase = new EntityBase();
                entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());
                entityBase.setbase_code(UUID.randomUUID() + "");
                entityBase.settb_code("9001");
                entityBase.setkind_code("001");
                entityBase.settype_code("001");
                entityBase.setbase_type(oaNvar1001.getText().toString());
                entityBase.setbase_nvar100_4(oaNvar1002.getText().toString());
                entityBase.setbase_number(entOa.getmake_pcode());
                entityBase.setbase_name(makePname.getText().toString());
                entityBase.setbase_date_1(oaDate1.getText().toString());
                entityBase.setbase_nvar100_3(oaNvar1003.getText().toString());
                entityBase.setbase_nvarmax_1(oaNvarmax1.getText().toString());
                entityBase.setbase_state_name("补打卡");
                General<Object> general = new General<>();
                String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.insertData(encode);
            } else {
                ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
            }
        }
    }

    @Override
    public void insertData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            ToastUtil.showShort("提交数据成功");
            if (Common.smsTag == true) {
                presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
            } else {
                Intent intent = new Intent();
                setResult(Constants.RESULTCODE, intent);
                finish();
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @OnClick({R.id.btn_pass, R.id.btn_back, R.id.btn_close, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pass:
                //审核
                audit();
                break;
            case R.id.btn_back:
                //退回重做
                back();
                break;
            case R.id.btn_close:
                //暂时关闭
                finish();
                break;
            case R.id.btn_clear:
                //清除签名
                if (state == true) {
                    handWriteView.clear();
                }
                break;
        }
    }

    private void audit() {
        if (state == true) {
            if (handWriteView.isSign()) {
                if (wfEnt != null) {
                    wfEnt.settask_imp_result("审核通过!");
                    if (edCheck.getText() == null || edCheck.getText().toString().trim().length() == 0) {
                        wfEnt.settask_imp_content("同意");
                    } else {
                        wfEnt.settask_imp_content(edCheck.getText().toString());
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    wfEnt.settask_imp_date(simpleDateFormat.format(new Date(System.currentTimeMillis())));

                    wfEnt.setpers_code(Common.LoginUser.getpers_code());
                    wfEnt.setstate_code("900");

                    final String fileCode = Common.genRandomNum(16);
                    wfEnt.settask_imp_sign(fileCode + ".png");

                    Common.httpPost(BLL.TaskDispose(wfEnt), 3, CardAuditActivity.this, new DealWithListener() {
                        @Override
                        public void ServerDataDealWith(PubReturn pubReturn) {
                            //上传
                            EntityFile ent_upfile = new EntityFile();
                            ent_upfile.setfile_code(fileCode);
                            ent_upfile.setfile_extend(".png");
                            Bitmap bitmap = handWriteView.GetBitmap(10);
                            //再对图片进行压缩
                            Bitmap btm2 = Bitmap.createScaledBitmap(bitmap, 112, 84, false);//缩小
                            ent_upfile.setfile_body(Common.bitmapToBase64(btm2));
                            ent_upfile.setfile_end_flag(true);
                            ent_upfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "/Mark");
                            ent_upfile.setfile_belong("in");

                            Common.httpPost(BLL.UploadFile(ent_upfile), 5, CardAuditActivity.this,
                                    new DealWithListener() {
                                        @Override
                                        public void ServerDataDealWith(PubReturn pubReturn) {
                                            presenter.getData(BLL.BLLJsonByStream("In_Base_Add"));
                                        }
                                    }
                            );
                        }
                    });

                } else {
                    showToast("任务数据为空！");
                }
            } else {
                showToast("请签字");
            }
        } else {
            if (wfEnt != null) {
                wfEnt.settask_imp_result("审核通过!");
                if (edCheck.getText() == null || edCheck.getText().toString().trim().length() == 0) {
                    wfEnt.settask_imp_content("同意");
                } else {
                    wfEnt.settask_imp_content(edCheck.getText().toString());
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                wfEnt.settask_imp_date(simpleDateFormat.format(new Date(System.currentTimeMillis())));

                wfEnt.setpers_code(Common.LoginUser.getpers_code());
                wfEnt.setstate_code("900");

                Common.httpPost(BLL.TaskDispose(wfEnt), 3, CardAuditActivity.this, new DealWithListener() {
                    @Override
                    public void ServerDataDealWith(PubReturn pubReturn) {
                        presenter.getData(BLL.BLLJsonByStream("In_Base_Add"));
                    }
                });

            } else {
                showToast("任务数据为空！");
            }
        }
    }

    private void back() {
        wfEnt.settask_imp_result("退回重做！");
        if (edCheck.getText() == null || edCheck.getText().toString().trim().length() == 0) {
            wfEnt.settask_imp_content("不同意！退出重新制单！");
        } else {
            wfEnt.settask_imp_content(edCheck.getText().toString());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        wfEnt.settask_imp_date(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        wfEnt.setpers_code(Common.LoginUser.getpers_code());
        wfEnt.setstate_code("970");
        final String fileCode = Common.getUniquePsuedoID();
        wfEnt.settask_imp_sign(fileCode + ".png");
        Common.httpPost(BLL.TaskDispose(wfEnt), 3, CardAuditActivity.this, new DealWithListener() {
            @Override
            public void ServerDataDealWith(PubReturn pubReturn) {
                Intent intent = new Intent();
                setResult(Constants.RESULTCODE, intent);
                finish();

            }

        });
    }


    @Override
    public void getStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                EntityWorkFlow workFlow = new EntityWorkFlow();
                workFlow.setcorp_tn(Common.LoginUser.getcorp_tn());
                workFlow.settask_code(wfEnt.gettask_code());
                General<Object> general = new General<>();
                String Json = general.bllInJson(workFlow, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.smsData(encode);
            } else {
                ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
            }
        }
    }

    @Override
    public void smsData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<List<EntityWorkFlow>>() {
            }.getType();
            List<EntityWorkFlow> oaList = gson.fromJson(decode, type);
            List<EntityWorkFlow> listData = Common.defDecodeEntityList(oaList);
            for (EntityWorkFlow listDatum : listData) {
                String taskName = Common.defEncode(listDatum.gettask_name());
                presenter.wfTaskSms(BLL.WFTaskSMS(listDatum.getpapt_pun(), taskName));
            }

        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void wfTaskSms(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Intent intent = new Intent();
            setResult(Constants.RESULTCODE, intent);
            finish();
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
            Intent intent = new Intent();
            setResult(Constants.RESULTCODE, intent);
            finish();
        }
    }
}
