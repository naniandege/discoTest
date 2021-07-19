package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.AttAdapter;
import com.example.liqian.huarong.adapter.BudgeAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.BudgetPresenter;
import com.example.liqian.huarong.mvp.v.BudgetView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
import com.example.liqian.huarong.views.point.HandWriteView;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;


import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class BudgetActivity extends BaseActivity<BudgetView, BudgetPresenter> implements BudgetView, BudgeAdapter.myListnner {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_oa_code_title)
    TextView tvOaCodeTitle;
    @BindView(R.id.tv_oa_code)
    TextView tvOaCode;
    @BindView(R.id.tv_make_uname_title)
    TextView tvMakeUnameTitle;
    @BindView(R.id.tv_make_uname)
    TextView tvMakeUname;
    @BindView(R.id.tv_make_dname_title)
    TextView tvMakeDnameTitle;
    @BindView(R.id.tv_make_dname)
    TextView tvMakeDname;
    @BindView(R.id.tv_make_pname_title)
    TextView tvMakePnameTitle;
    @BindView(R.id.tv_make_pname)
    TextView tvMakePname;
    @BindView(R.id.tv_make_date_title)
    TextView tvMakeDateTitle;
    @BindView(R.id.tv_make_date)
    TextView tvMakeDate;
    @BindView(R.id.oa_title)
    TextView oaTitle;
    @BindView(R.id.tv_oa_sum_nvarmax_1_title)
    TextView tvOaSumNvarmax1Title;
    @BindView(R.id.oa_nvarmax_1)
    TextView oaNvarmax1;
    @BindView(R.id.tv_oa_sum_deci2_1_title)
    TextView tvOaSumDeci21Title;
    @BindView(R.id.oa_sum_deci2_1)
    TextView oaSumDeci21;
    @BindView(R.id.oa_sum_deci2_2)
    TextView oaSumDeci22;
    @BindView(R.id.ed_check)
    EditText edCheck;
    @BindView(R.id.ed_show)
    TextView edShow;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.handWriteView)
    HandWriteView handWriteView;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.btn_pass)
    Button btnPass;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.but)
    LinearLayout but;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.lv1)
    RecyclerView lv1;
    @BindView(R.id.showData)
    TextView showData;
    @BindView(R.id.showData1)
    TextView showData1;

    private EntityWorkFlow wfEnt;
    private boolean state;
    private ArrayList<EntityOA> list;   //报销明细费用
    private BudgeAdapter adapter;
    private ArrayList<EntityFile> attList;
    private AttAdapter attAdapter;
    public static String mNodeCode;
    private EditText deci26;
    private EntityOA entOa;
    private EditText oanvar1003;
    private EditText deci24;
    private EditText deci25;
    private EditText nvarmax_1;

    @Override
    protected BudgetPresenter initPresenter() {
        return new BudgetPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_budget;
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        wfEnt = (EntityWorkFlow) intent.getSerializableExtra("json");
        mNodeCode = wfEnt.getnode_code();

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

        //报销明细费用
        list = new ArrayList<>();
        //适配器绑定
        adapter = new BudgeAdapter(list, this);
        lv.setAdapter(adapter);
        //recycleView显示方式:线性
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
        //分割线
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter.setMyListnner(this);

        //附件列表
        attList = new ArrayList<>();
        //适配器绑定
        attAdapter = new AttAdapter(attList, this);
        lv1.setAdapter(attAdapter);
        //recycleView显示方式:线性
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        lv1.setLayoutManager(linearLayoutManager1);
        //分割线
        lv1.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager1.VERTICAL));
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.taskDetail(BLL.OASelect(wfEnt.gettask_key()));

    }


    /*
     * 详情
     * */
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
            tvOaCode.setText(entOa.getoa_code());
            tvMakeUname.setText(entOa.getmake_uname());
            tvMakeDname.setText(entOa.getmake_dname());
            tvMakePname.setText(entOa.getmake_pname());
            tvMakeDate.setText(entOa.getmake_date());
            oaTitle.setText(entOa.getoa_nvar100_1());
            oaNvarmax1.setText(entOa.getoa_nvarmax_1());

            //让Double类型完整显示，不用科学计数法显示E
            BigDecimal bigDecimal = new BigDecimal(entOa.getoa_sum_deci2_1());
            String amount = bigDecimal.toString();
            oaSumDeci21.setText(amount);

            BigDecimal bigDecimal2 = new BigDecimal(entOa.getoa_sum_deci2_2());
            String amount2 = bigDecimal2.toString();
            oaSumDeci22.setText(amount2);

            //费用报销明细
            EntityOA oa = new EntityOA();
            oa.setoa_code(entOa.getoa_code());
            presenter.oaList(BLL.OAListSelect(oa));

            //附件列表
            EntityFile entityFile = new EntityFile();
            entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityFile.setfile_becode(entOa.getoa_code());
            entityFile.settb_code("17");
            entityFile.setfile_belong("in");
            presenter.attList(BLL.FileSelectList(entityFile));
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }

    /*
         费用报销明细
    * */
    @Override
    public void oaListData(String data) {
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
            List<EntityOA> listBean = Common.defDecodeEntityList(oaList);
            if (listBean != null && listBean.size() > 0) {
                list.addAll(listBean);
                adapter.notifyDataSetChanged();
                showData.setVisibility(View.GONE);
            } else {
                showData.setVisibility(View.VISIBLE);
            }
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }

    /*
     * 附件列表
     * */
    @Override
    public void attList(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityFile>>() {
            }.getType();
            List<EntityFile> fileList = gson.fromJson(decode, type);
            List<EntityFile> files = Common.defDecodeEntityList(fileList);
            if (files != null && files.size() > 0) {
                attList.addAll(files);
                attAdapter.notifyDataSetChanged();
                showData1.setVisibility(View.GONE);
            } else {
                showData1.setVisibility(View.VISIBLE);
            }

        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    /*暂时关闭
     * */
    @OnClick({R.id.btn_close})
    public void close(View view) {
        finish();
    }

    /*审核
     * */
    @OnClick({R.id.btn_pass})
    public void click(View view) {
        if (BudgetActivity.mNodeCode.equals("001")) {
            //重新提交数据
            submitData();
        } else {
            attData();
        }

    }

    private void attData() {
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

                    Common.httpPost(BLL.TaskDispose(wfEnt), 3, BudgetActivity.this, new DealWithListener() {
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

                            Common.httpPost(BLL.UploadFile(ent_upfile), 5, BudgetActivity.this,
                                    new DealWithListener() {
                                        @Override
                                        public void ServerDataDealWith(PubReturn pubReturn) {

                                            if (Common.smsTag == true) {
                                                presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
                                            } else {
                                                Intent intent = new Intent();
                                                setResult(Constants.RESULTCODE, intent);
                                                finish();
                                            }
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

                Common.httpPost(BLL.TaskDispose(wfEnt), 3, BudgetActivity.this, new DealWithListener() {
                    @Override
                    public void ServerDataDealWith(PubReturn pubReturn) {

                        if (Common.smsTag == true) {
                            presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
                        } else {
                            Intent intent = new Intent();
                            setResult(Constants.RESULTCODE, intent);
                            finish();
                        }
                    }
                });

            } else {
                showToast("任务数据为空！");
            }

        }
    }

    /*清除签名
     * */
    @OnClick({R.id.btn_clear})
    public void clear(View view) {
        if (state == true) {
            handWriteView.clear();
        }
    }


    /*退回重做
     * */
    @OnClick({R.id.btn_back})
    public void back(View view) {
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

        Common.httpPost(BLL.TaskDispose(wfEnt), 3, BudgetActivity.this, new DealWithListener() {
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

    /**
     * 修改批准数
     *
     * @param position
     */
    @Override
    public void onClickMyListnner(final int position) {
        if (!BudgetActivity.mNodeCode.equals("001")) {
            return;
        }

        final EntityOA entityOA = list.get(position);

        View inflate = LayoutInflater.from(this).inflate(R.layout.budge_set_item, null);
        Button cancel = inflate.findViewById(R.id.btn_cancel);
        Button cance2 = inflate.findViewById(R.id.cancel);
        oanvar1003 = inflate.findViewById(R.id.oal_nvar100_3); //单位
        deci24 = inflate.findViewById(R.id.oal_deci2_4);   //单价
        deci25 = inflate.findViewById(R.id.oal_deci2_5);  //数量
        deci26 = inflate.findViewById(R.id.oal_deci2_6);   //金额
        nvarmax_1 = inflate.findViewById(R.id.oal_nvarmax_1);  //备注


        final ClockDialog dialog = new ClockDialog(this, inflate);
        dialog.show();

        oanvar1003.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        oanvar1003.setText(entityOA.getoal_nvar100_3());
        deci24.setText(entityOA.getoal_deci2_4() + "");
        deci25.setText(entityOA.getoal_deci2_5() + "");
        deci26.setText(entityOA.getoal_deci2_6() + "");
        nvarmax_1.setText(entityOA.getoal_nvarmax_1());


        oanvar1003.setSelection(oanvar1003.getText().length());
        deci24.setSelection(deci24.getText().length());
        deci25.setSelection(deci25.getText().length());
        deci26.setSelection(deci26.getText().length());
        nvarmax_1.setSelection(nvarmax_1.getText().length());


        deci25.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(deci25.getText().toString()) &&
                        !TextUtils.isEmpty(deci24.getText().toString())) {
                    deci26.setText(Double.toString(Double.valueOf(deci25.getText().toString()) * Double.valueOf(deci24.getText().toString())));
                } else {
                    deci26.setText(0 + "");
                }
            }
        });
        deci24.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(deci25.getText().toString()) &&
                        !TextUtils.isEmpty(deci24.getText().toString())) {
                    deci26.setText(Double.toString(Double.valueOf(deci25.getText().toString()) * Double.valueOf(deci24.getText().toString())));
                } else {
                    deci26.setText(0 + "");
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oanvar1003Str = oanvar1003.getText().toString();
                String deci24Str = deci24.getText().toString();
                String deci25Str = deci25.getText().toString();
                String nvarmax1Str = nvarmax_1.getText().toString();

                if (oanvar1003Str.isEmpty()) {
                    ToastUtil.showShort("请输入单位");
                    return;
                }
                if (deci24Str.isEmpty()) {
                    ToastUtil.showShort("请输入数量");
                    return;
                }
                if (deci25Str.isEmpty()) {
                    ToastUtil.showShort("请输入单价");
                    return;
                }

                //修改数据
                EntityOA listBean = list.get(position);
                listBean.setoal_nvar100_3(oanvar1003Str);
                listBean.setoal_deci2_4(Double.parseDouble(deci24Str));
                listBean.setoal_deci2_5(Double.parseDouble(deci25Str));
                listBean.setoal_deci2_6(Double.parseDouble(deci26.getText().toString()));
                listBean.setoal_nvarmax_1(nvarmax1Str);
                list.set(position, listBean);
                adapter.notifyDataSetChanged();

                presenter.upDateStr(BLL.BLLJsonByStream("In_OAList_Update"), listBean);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        cance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void upDateStr(String data, EntityOA listBean) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                listBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                newApproval = 0;
                for (EntityOA entityOA : list) {
                    double approval = entityOA.getoal_deci2_6();
                    newApproval += approval;
                }

                listBean.setoa_sum_deci2_2(newApproval);
                General<Object> general = new General<>();
                String Json = general.bllInJson(listBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.upDate(encode);
            }
        }
    }


    private double newApproval = 0;

    @Override
    public void upDate(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            newApproval = 0;
            for (EntityOA entityOA : list) {
                double approval = entityOA.getoal_deci2_6();
                newApproval += approval;
            }
            BigDecimal bigDecimal = new BigDecimal(newApproval);
            oaSumDeci22.setText(bigDecimal.toString());
            ToastUtil.showShort("已修改");
            Tools.closeKeyBoard(this);
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    private void submitData() {
        showLoading();
        presenter.oaMainStr(BLL.BLLJsonByStream("In_OAMain_UpdateInTask"));
//        presenter.oaMainStr(BLL.BLLJsonByStream("In_OAMain_Update"));
    }


    @Override
    public void oaMainStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            newApproval = 0;
            for (EntityOA entityOA : list) {
                double approval = entityOA.getoal_deci2_6();
                newApproval += approval;
            }

            entOa.setoa_sum_deci2_2(newApproval);
            entOa.setcorp_tn(Common.LoginUser.getcorp_tn());
            entOa.settb_code("9");
            entOa.settype_code("006");
            entOa.setkind_code("001");
            entOa.setwf_code("0006");
            entOa.settask_code(UUID.randomUUID().toString());
            //entityOA转换为json
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson1 = gsonBuilder.create();
            String json = gson1.toJson(entOa, EntityOA.class);
            entOa.settask_condition(json);
            General<Object> general = new General<>();
            String Json = general.bllInJson(entOa, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.oaMainData(encode);
        }
    }

    @Override
    public void oaMainData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            ToastUtil.showShort("修改完成");
            attData();
//            presenter.MyinitiateStr(BLL.BLLJsonByStream("In_MyinitiateTask_Update"));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void MyinitiateStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            wfEnt.setcorp_tn(Common.LoginUser.getcorp_tn());
            wfEnt.settask_condition("");
            //entityOA转换为json
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson1 = gsonBuilder.create();
            String json = gson1.toJson(entOa, EntityOA.class);
            wfEnt.settask_condition(json);
            wfEnt.setif_check(1);
            String task_name = Common.LoginUser.getpers_name() + "于" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "提出临时预算申请";
            wfEnt.settask_name(task_name);
            General<Object> general = new General<>();
            String Json = general.bllInJson(wfEnt, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.MyinitiateData(encode);
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void MyinitiateData(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            ToastUtil.showShort("修改完成");
            Intent intent = new Intent();
            setResult(Constants.RESULTCODE, intent);
            finish();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }

        hideLoading();
    }


    private ClockDialog mDialog;

    @Override
    protected void onResume() {
        super.onResume();
        if (BudgetActivity.mNodeCode.equals("001")) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.budge_msg_item, null);
            Button know = inflate.findViewById(R.id.btn_know);
            mDialog = new ClockDialog(this, inflate);
            mDialog.show();
            know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
            });
        }
    }
}
