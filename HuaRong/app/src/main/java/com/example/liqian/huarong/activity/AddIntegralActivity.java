package com.example.liqian.huarong.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.IntegralAdapter;
import com.example.liqian.huarong.adapter.PhotoAdapter;
import com.example.liqian.huarong.adapter.SendBlogAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityLeaveBean;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityTable;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PhotoBean;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.TravelPresenter;
import com.example.liqian.huarong.mvp.v.TravelView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.Amount;
import com.example.liqian.huarong.utils.ImageFactory;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AddIntegralActivity extends BaseActivity<TravelView, TravelPresenter> implements TravelView, IntegralAdapter.onItemLong, PhotoAdapter.onImgItemLong, SendBlogAdapter.onImgItemLong {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.oa_code)
    TextView oaCode;
    @BindView(R.id.make_uname)
    TextView makeUname;
    @BindView(R.id.oa_nvar100_2)
    TextView oaNvar1002;
    @BindView(R.id.make_dname)
    TextView makeDname;
    @BindView(R.id.make_pname)
    TextView makePname;
    @BindView(R.id.make_date)
    TextView makeDate;
    @BindView(R.id.select_date)
    LinearLayout selectDate;
    @BindView(R.id.oa_nvar100_1)
    Spinner oaNvar1001;
    @BindView(R.id.oa_deci2_1)
    TextView oaDeci21;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.Camera_cancel)
    Button CameraCancel;
    @BindView(R.id.Camera_ok)
    Button CameraOk;
    @BindView(R.id.Detail)
    Button Detail;
    @BindView(R.id.attachment)
    Button attachment;
    @BindView(R.id.lv)
    RecyclerView lv;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String selectPosition;
    private ArrayList<EntityLeaveBean> detailList;
    private IntegralAdapter adapter;
    private String contentCode;


    private static final int ALBUM_CODE = 200;
    private ArrayList<PhotoBean> photoBeans = new ArrayList<>();
    private SendBlogAdapter photoAdapter;
    private ClockDialog photoDialog;
    private EntityWorkFlow workFlow;
    private EntityOA entityOA;
    private Gson gson;

    @Override
    protected TravelPresenter initPresenter() {
        return new TravelPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_integral;
    }


    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //初始化年月日
        mYear = TimeUtils.getYear();
        mMonth = TimeUtils.getMonth();
        mDay = TimeUtils.getDay();

        makeDate.setText(TimeUtils.getFormatedDateTime(System.currentTimeMillis()));//申请时间
        makePname.setText(Common.LoginUser.getpers_name());//申请人
        makeUname.setText(Common.LoginUser.getunit_name()); //单位
        makeDname.setText(Common.LoginUser.getdept_name()); //部门
        makePname.setText(Common.LoginUser.getpers_name()); //申请人

        //初始化下拉选择框
        initSpinern();

        //细表明细数据源
        detailList = new ArrayList<>();
        //适配器
        adapter = new IntegralAdapter(detailList, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter.setOnItemLong(this);
    }

    private void initSpinern() {
        final ArrayList<String> mSpinner = new ArrayList<>();
        String yearAdvance = TimeUtils.yearAdvance();
        mSpinner.add(yearAdvance + "年12月奉献积分申报");

        for (int i = 1; i <= 12; i++) {
            int year = TimeUtils.getYear();
            mSpinner.add(year + "年" + i + "月" + "奉献积分申报");
        }

        String advance = TimeUtils.addAdvance();
        mSpinner.add(advance + "年1月奉献积分申报");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, R.id.sp, mSpinner);
        oaNvar1001.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(R.layout.my_spinner_item);
        oaNvar1001.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = mSpinner.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.select_date, R.id.Camera_cancel, R.id.Camera_ok, R.id.Detail, R.id.attachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_date:
                //选择申请时间
                mySelectDate(makeDate);
                break;
            case R.id.Camera_cancel:
                finish();
                break;
            case R.id.Camera_ok:
                submitData();
                break;
            case R.id.Detail:
                //选择细表明细
                selectDetail();
                break;
            case R.id.attachment:
                openAttchment();
                break;
        }
    }


    private void openAttchment() {
        if (photoAdapter == null) {
            photoAdapter = new SendBlogAdapter(this, photoBeans);
        }

        View inflate = LayoutInflater.from(this).inflate(R.layout.photo_item, null);
        RecyclerView photoLv = inflate.findViewById(R.id.phpto_lv);
        Button determine = inflate.findViewById(R.id.determine);
        photoLv.setAdapter(photoAdapter);
        photoLv.setLayoutManager(new GridLayoutManager(BaseApp.getInstance(), 3));
        photoLv.addItemDecoration(new DividerItemDecoration(BaseApp.getInstance(), LinearLayoutManager.VERTICAL));
        photoAdapter.setOnImgItemLong(this);

        if (photoDialog == null) {
            photoDialog = new ClockDialog(this, inflate);
        }
        photoDialog.show();
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoDialog.dismiss();
            }
        });
    }


    private void takePick() {//相册Sd卡权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    /**
     * 开启相册
     * 先判断所用的sdk是否大于19;
     * 如果大于19则使用Intent.ACTION_PICK来选择图片;
     * 小于19使用Intent.ACTION_GET_CONTENt来选择图片;
     */
    private void openAlbum() {
    /*    Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, ALBUM_CODE);*/

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ALBUM_CODE);
    }


    private void selectDetail() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.detailitem, null);
        final TextView oal_date_1 = inflate.findViewById(R.id.oal_date_1);
        final TextView oal_date_2 = inflate.findViewById(R.id.oal_date_2);
        final EditText oal_nvar100_1 = inflate.findViewById(R.id.oal_nvar100_1);
        final EditText oal_deci2_1 = inflate.findViewById(R.id.oal_deci2_1);
        final EditText oal_nvarmax_1 = inflate.findViewById(R.id.oal_nvarmax_1);
        Button btn_exit = inflate.findViewById(R.id.btn_exit);
        final Button btn_cancel = inflate.findViewById(R.id.btn_cancel);

        final ClockDialog detaildDialog = new ClockDialog(this, inflate);
        detaildDialog.show();

        oal_date_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mySelectDate(oal_date_1);
            }
        });


        oal_date_2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mySelectDate(oal_date_2);
            }
        });

        //添加明细
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(oal_date_1.getText().toString())) {
                    ToastUtil.showShort("请选择开始日期");
                    return;
                }
                if (TextUtils.isEmpty(oal_date_2.getText().toString())) {
                    ToastUtil.showShort("请选择结束日期");
                    return;
                }
                if (TextUtils.isEmpty(oal_nvar100_1.getText().toString())) {
                    ToastUtil.showShort("请填写申请项目");
                    return;
                }
                if (TextUtils.isEmpty(oal_deci2_1.getText().toString())) {
                    ToastUtil.showShort("请填写申请加分");
                    return;
                }

                Date startDate = TimeUtils.stringToDate(oal_date_1.getText().toString(), "yyyy-MM-dd");
                Date endDate = TimeUtils.stringToDate(oal_date_2.getText().toString(), "yyyy-MM-dd");

                if (endDate.getTime() < startDate.getTime()) {
                    ToastUtil.showShort("结束时间不能小于开始时间");
                    return;
                }

                EntityLeaveBean entityLeaveBean = new EntityLeaveBean();
                entityLeaveBean.setOal_date_1(oal_date_1.getText().toString());
                entityLeaveBean.setOal_date_2(oal_date_2.getText().toString());
                entityLeaveBean.setOal_deci2_1(oal_deci2_1.getText().toString());
                entityLeaveBean.setOal_nvar100_1(oal_nvar100_1.getText().toString());
                entityLeaveBean.setOal_nvarmax_1(oal_nvarmax_1.getText().toString());
                detailList.add(entityLeaveBean);
                adapter.notifyDataSetChanged();

                //计算申请加分
                String deci21 = sumDeci2_1();
                oaDeci21.setText(deci21);
                detaildDialog.dismiss();
            }
        });

        //取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detaildDialog.isShowing()) {
                    detaildDialog.dismiss();
                }
            }
        });

    }


    /**
     * 计算申请加分
     *
     * @return
     */
    private String sumDeci2_1() {
        Tools.closeKeyBoard(AddIntegralActivity.this);
        double sum = 0;
        for (EntityLeaveBean bean : detailList) {
            Double deci2_1 = Double.valueOf(bean.getOal_deci2_1());
            sum += deci2_1;
        }

        return String.valueOf(sum);
    }

    /**
     * 时间选择
     *
     * @param mView
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mySelectDate(final TextView mView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;
                mView.setText(year + "-" + mMonth + "-" + mDay);
            }
        });
        datePickerDialog.show();
    }

    /**
     * 删除申请积分
     *
     * @param position
     */
    @Override
    public void onItemLongLisner(int position) {
        detailList.remove(position);
        adapter.notifyDataSetChanged();
        oaDeci21.setText(sumDeci2_1());

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getCodeStr(BLL.BLLJsonByStream("In_Code_Create"));
    }


    private void submitData() {
        if (oaDeci21.getText().toString().isEmpty()) {
            ToastUtil.showShort("申请加分不能为空");
            return;
        }

        showLoading();

        entityOA = new EntityOA();
        entityOA.setcorp_tn(Common.LoginUser.getcorp_tn());
        entityOA.settb_code("9");
        entityOA.settype_code("007");
        entityOA.setkind_code("001");
        if (!contentCode.isEmpty()) {
            entityOA.setoa_code(contentCode);
        }
        entityOA.setmake_ucode(Common.LoginUser.getunit_code());
        entityOA.setmake_uname(Common.LoginUser.getunit_name());
        entityOA.setmake_dcode(Common.LoginUser.getdept_code());
        entityOA.setmake_dname(Common.LoginUser.getdept_name());
        entityOA.setmake_pcode(Common.LoginUser.getpers_code());
        entityOA.setmake_pname(Common.LoginUser.getpers_name());
        entityOA.setmake_date(makeDate.getText().toString());
        entityOA.setoa_nvar100_2(oaNvar1002.getText().toString());
        entityOA.setoa_nvar100_1(selectPosition);
        entityOA.setoa_deci2_1(Double.valueOf(oaDeci21.getText().toString()));
        presenter.reciPients(BLL.OAMainAdd(entityOA));
    }


    @Override
    public void addData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            workFlow = new EntityWorkFlow();
            workFlow.setwf_code("0007");//
            workFlow.setwf_name("党员奉献积分");//
            workFlow.settask_code(UUID.randomUUID() + "");//uid
            String task_name = Common.LoginUser.getpers_name() + "于" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "党员积分申请";
            workFlow.settask_name(task_name);
            workFlow.settask_key(entityOA.getoa_code());
            workFlow.settb_code(entityOA.gettb_code());
            workFlow.settype_code(entityOA.gettype_code());
            workFlow.setkind_code(entityOA.getkind_code());
            workFlow.setstage_code("0101");
            workFlow.setinit_pers_ucode(Common.LoginUser.getunit_code());
            workFlow.setinit_pers_uname(Common.LoginUser.getunit_name());
            workFlow.setinit_pers_dcode(Common.LoginUser.getdept_code());
            workFlow.setinit_pers_dname(Common.LoginUser.getdept_name());
            workFlow.setinit_pcode(Common.LoginUser.getpers_code());
            workFlow.setinit_pname(Common.LoginUser.getpers_name());
            workFlow.setinit_pun(Common.LoginUser.getpers_un());
            workFlow.setinit_date(entityOA.getmake_date());
            workFlow.setif_check(1);

            String prmValue = "{" +
                    "  \"oa_code\": \"" + entityOA.getoa_code() + "\"," +
                    "  \"type_code\": \"" + entityOA.gettype_code() + "\"," +
                    "  \"kind_code\": \"" + entityOA.getkind_code() + "\"," +
                    "  \"tb_code\": \"" + entityOA.gettb_code() + "\"" +
                    "}";
            workFlow.settask_prmvalue(prmValue);

            //entityOA转换为json
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson1 = gsonBuilder.create();
            String json = gson1.toJson(entityOA, EntityOA.class);
            workFlow.settask_condition(json);
            presenter.taskAdd(BLL.MyinitiateTaskAdd(workFlow));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void taskAdd(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            EntityOA entityOA1 = new EntityOA();
            entityOA1.setoa_code(workFlow.gettask_key());
            entityOA1.setwf_code(workFlow.getwf_code());
            entityOA1.settask_code(workFlow.gettask_code());
            presenter.mainUpdateTask(BLL.OAMainUpdateTask(entityOA1));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void mainUpTaskData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            if (photoBeans.size() > 0) {
                addFileImg();
            }

            //提交明细
            presenter.getFineStr(BLL.BLLJsonByStream("In_OAList_Add"));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    private void addFileImg() {
        try {
            //上传照片
            int k = 0;
            for (PhotoBean photoBean : photoBeans) {
                k++;
                Bitmap bitmap = photoBean.getBitmap();
                if (bitmap != null) {
                    int bitmapSize = Amount.getBitmapSize(bitmap);
                    String fileCode = Common.genRandomNum(16);
                    EntityFile ent_upfile = new EntityFile();
                    ent_upfile.setfile_code(fileCode);
                    ent_upfile.setfile_extend(".jpg");
                    ent_upfile.setfile_body(Common.bitmapToBase64(bitmap));
                    ent_upfile.setfile_end_flag(true);
                    ent_upfile.setfile_belong("in");
                    ent_upfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\Score\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
                    presenter.uploadData(BLL.UploadFile(ent_upfile));

                    //保存
                    EntityFile entfile = new EntityFile();
                    entfile.setcorp_tn(Common.LoginUser.getcorp_tn());
                    entfile.setunit_code(Common.LoginUser.getunit_code());
                    entfile.setunit_name(Common.LoginUser.getunit_name());
                    entfile.setdept_code(Common.LoginUser.getdept_code());
                    entfile.setdept_name(Common.LoginUser.getdept_name());
                    entfile.setpers_code(Common.LoginUser.getpers_code());
                    entfile.setpers_name(Common.LoginUser.getpers_name());
                    entfile.setpers_un(Common.LoginUser.getpers_un());
                    entfile.settb_code("17");
                    entfile.settype_code("3000");
                    entfile.setkind_code("002");
                    entfile.setfile_openstate(true);
                    entfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\Score\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
//                    entfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\btrip");
                    entfile.setfile_belong("in");
                    entfile.setfile_becode(contentCode);
                    entfile.setfile_name("图片" + k);
                    entfile.setfile_origname("图片" + k);
                    entfile.setfile_id(fileCode);
                    entfile.setfile_code(fileCode);
                    entfile.setfile_extend(".jpg");
                    entfile.setfile_link(fileCode + ".jpg");
                    entfile.setfile_size(bitmapSize);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    entfile.setfile_uptime(simpleDateFormat.format(date));
                    entfile.setfile_uptime_stamp(Common.CalDateTimeStamp(date));
                    presenter.addFile(BLL.FileAdd(entfile));
                }
            }
        } catch (Exception e) {
            ToastUtil.showShort(e.getMessage());
        }
    }

    /**
     * 时间明细
     *
     * @param data
     */
    @Override
    public void getFineStr(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            General<Object> general = new General<>();
            for (int i = 0; i < detailList.size(); i++) {
                EntityLeaveBean entityLeaveBean = detailList.get(i);
                EntityOA oaBean = new EntityOA();
                oaBean.setoa_code(entityOA.getoa_code());
                oaBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                oaBean.setoal_date_1(entityLeaveBean.getOal_date_1());
                oaBean.setoal_date_2(entityLeaveBean.getOal_date_2());
                oaBean.setoal_nvar100_1(entityLeaveBean.getOal_nvar100_1());
                oaBean.setoal_deci2_1(Double.valueOf(entityLeaveBean.getOal_deci2_1()));
                oaBean.setoal_nvarmax_1(entityLeaveBean.getOal_nvarmax_1());
                String Json = general.bllInJson(oaBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.addFineData(encode);
            }
        }
    }

    int fine = 0;

    @Override
    public void addFineData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            fine++;
            if (fine == detailList.size()) {
                //发送短信提醒
                presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void getCodeStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                EntityTable entityTable = new EntityTable();
                entityTable.setcorp_tn(Common.LoginUser.getcorp_tn());
                entityTable.setfield_str("Score_" + Common.LoginUser.getpers_code() + "_" + TimeUtils.getYear()
                        + "_" + TimeUtils.getMonth() + "_");
                entityTable.setfield_num(4);
                entityTable.settable_name("t_oa_main");
                entityTable.setfield_name("oa_code");
                General<Object> general = new General<>();
                String Json = general.bllInJson(entityTable, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getCodeData(encode);
            } else {
                ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
            }
        }
    }


    @Override
    public void getCodeData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            contentCode = Common.defDecode(pubReturn.getContent());
            oaCode.setText(contentCode);
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void smsStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                EntityWorkFlow mWorkFlow = new EntityWorkFlow();
                mWorkFlow.setcorp_tn(Common.LoginUser.getcorp_tn());
                mWorkFlow.settask_code(workFlow.gettask_code());
                General<Object> general = new General<>();
                String Json = general.bllInJson(mWorkFlow, Common.defDecode(pubReturn.getData()));
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
            String decode = Common.defDecode(pubReturn.getData());
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
        fine = 0;
        hideLoading();
        ToastUtil.showShort("提交成功");
        finish();
    }

    @Override
    public void getDetailStr(String data) {

    }

    @Override
    public void addDetailData(String data) {

    }

    @Override
    public void getPersStr(String data) {

    }

    @Override
    public void addPersData(String data) {

    }

    @Override
    public void uploadData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @Override
    public void addFile(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    /**
     * 删除附件  图片
     *
     * @param position
     */
    @Override
    public void onImgLong(int position) {
        photoBeans.remove(position);
        photoAdapter.notifyDataSetChanged();
    }


    /**
     * 相册选择图片的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                if (requestCode == ALBUM_CODE) {//相册
                    try {
                        //处理uri,7.0以后的fileProvider 把URI 以content provider 方式 对外提供的解析方法
                        File fileFromUri = getFileFromUri(data.getData(), this);
                        if (fileFromUri != null) {
                            Luban.with(this)
                                    .load(fileFromUri)
                                    .ignoreBy(100)
                                    .filter(new CompressionPredicate() {
                                        @Override
                                        public boolean apply(String path) {
                                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                        }
                                    })
                                    .setCompressListener(new OnCompressListener() {
                                        @Override
                                        public void onStart() {
                                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            // TODO 压缩成功后调用，返回压缩后的图片文件
                                            if (file != null) {
                                                String path = file.getAbsolutePath();
                                                if (path == null || TextUtils.isEmpty(path)) {
                                                    return;
                                                }

                                                Bitmap bitmap = ImageFactory.getImageThumbnail(path, 1000, 1000);
                                                int width = bitmap.getWidth();
                                                int height = bitmap.getHeight();
                                                String mByte = ImageFactory.byteToFormat(file.length());

                                                PhotoBean photoBean = new PhotoBean();
                                                photoBean.setBitmap(bitmap);
                                                photoBeans.add(photoBean);
                                                if (photoAdapter != null) {
                                                    photoAdapter.setData(photoBeans);
                                                    photoAdapter.notifyDataSetChanged();
                                                }
                                                photoDialog.show();

                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            // TODO 当压缩过程出现问题时调用
                                            ToastUtil.showShort(e.getMessage());
                                        }
                                    }).launch();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }


    @Override
    public void onItem() {
        //开启相册
        takePick();
    }


    /**
     * 删除图片
     *
     * @param position
     */
    @Override
    public void delete(int position) {
        photoBeans.remove(position);
        if (photoAdapter != null) {
            photoAdapter.notifyDataSetChanged();
        }
    }
}
