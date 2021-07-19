package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.AttAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.TaskDetailPresenter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TopUpActivity extends BaseActivity<TaskDetailView, TaskDetailPresenter> implements TaskDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.tv_make_pname)
    TextView tvMakePname;
    @BindView(R.id.tv_make_date)
    TextView tvMakeDate;
    @BindView(R.id.tv_oa_nvar100_1)
    TextView tvOaNvar1001;
    @BindView(R.id.oa_nvar100_1)
    TextView oaNvar1001;
    @BindView(R.id.oa_nvar100_2)
    TextView oaNvar1002;
    @BindView(R.id.oa_nvar100_31)
    TextView oaNvar10031;
    @BindView(R.id.oa_nvar100_4)
    TextView oaNvar1004;
    @BindView(R.id.oa_deci2_1)
    TextView oaDeci21;
    @BindView(R.id.oa_nvarmax_1)
    TextView oaNvarmax1;
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
    @BindView(R.id.lv1)
    RecyclerView lv1;
    @BindView(R.id.showData)
    TextView showData;

    private EntityWorkFlow wfEnt;
    private boolean state;
    private ArrayList<EntityFile> attList;
    private AttAdapter attAdapter;

    @Override
    protected TaskDetailPresenter initPresenter() {
        return new TaskDetailPresenter();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_top_up;
    }

    @Override
    public void initView() {
        super.initView();

//        ed_check.setBackgroundColor(getResources().getColor(R.color.c_cecece));
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

            EntityOA _entOa = list.get(0);
            tvOaCode.setText(_entOa.getoa_code());
            tvMakeUname.setText(_entOa.getmake_uname());
            tvMakeDname.setText(_entOa.getmake_dname());
            tvMakePname.setText(_entOa.getmake_pname());
            tvMakeDate.setText(_entOa.getmake_date());
            tvOaNvar1001.setText(_entOa.getoa_date_1());
            oaNvar1001.setText(_entOa.getoa_nvar100_1());
            oaNvar1002.setText(_entOa.getoa_nvar100_2());
            oaNvar10031.setText(_entOa.getoa_nvar100_3());
            oaNvar1004.setText(_entOa.getoa_nvar100_4());
            oaDeci21.setText(_entOa.getoa_deci2_1() + "");
            oaNvarmax1.setText(_entOa.getoa_nvarmax_1());

            //附件列表
            EntityFile entityFile = new EntityFile();
            entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityFile.setfile_becode(_entOa.getoa_code());
            entityFile.settb_code("17");
            entityFile.setfile_belong("in");
            presenter.attList(BLL.FileSelectList(entityFile));
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void oaListData(String data) {

    }

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
                showData.setVisibility(View.GONE);
            } else {
                showData.setVisibility(View.VISIBLE);
            }

        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void initData() {
        presenter.taskDetail(BLL.OASelect(wfEnt.gettask_key()));
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

                    Common.httpPost(BLL.TaskDispose(wfEnt), 3, TopUpActivity.this, new DealWithListener() {
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

                            Common.httpPost(BLL.UploadFile(ent_upfile), 5, TopUpActivity.this,
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

                Common.httpPost(BLL.TaskDispose(wfEnt), 3, TopUpActivity.this, new DealWithListener() {
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

        Common.httpPost(BLL.TaskDispose(wfEnt), 3, TopUpActivity.this, new DealWithListener() {
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
