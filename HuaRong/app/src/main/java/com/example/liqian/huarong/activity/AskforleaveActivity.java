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
import com.example.liqian.huarong.adapter.SendBlogAdapter;
import com.example.liqian.huarong.adapter.askAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityLeaveBean;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityTable;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.GoodsCategory;
import com.example.liqian.huarong.bean.PhotoBean;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AddTaskPresenter;
import com.example.liqian.huarong.mvp.v.AddTaskView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.Amount;
import com.example.liqian.huarong.utils.ImageFactory;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.SinglePicker;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AskforleaveActivity extends BaseActivity<AddTaskView, AddTaskPresenter> implements AddTaskView, askAdapter.onItemLong, SendBlogAdapter.onImgItemLong {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.oa_nvarmax_1)
    EditText oaNvarmax1;
    @BindView(R.id.oa_sum_nvarmax_1)
    TextView oaSumNvarmax1;
    @BindView(R.id.oa_sum_deci2_1)
    TextView oaSumDeci21;
    @BindView(R.id.oa_nvar100_1)
    TextView oa_nvar100_1;
    @BindView(R.id.Camera_ok)
    Button CameraOk;
    @BindView(R.id.Camera_cancel)
    Button CameraCancel;
    @BindView(R.id.lv)
    RecyclerView lv;

    private Calendar calendar;
    private int hour;
    private int minute;
    private int mYear;
    private int mMonth;
    private int mDay;

    private EntityOA entityOA;
    private EntityWorkFlow workFlow;

    private List<GoodsCategory> dataList;
    private String contentCode;

    private String oaNvar3 = "";
    private String oaNvar2 = "";

    private askAdapter adapter;
    private ArrayList<EntityLeaveBean> list;
    private ClockDialog clockDialog;
    private TextView oaDate1;
    private TextView oaDate2;
    private Spinner oaNvar1003;
    private Spinner oaNvar1002;
    private Button btn_exit;
    private Button btn_cancel;
    private EntityLeaveBean leaveBean;
    private LinearLayout startDate;
    private LinearLayout endDate;
    private Gson gson;

    private static final int ALBUM_CODE = 200;
    private ArrayList<PhotoBean> photoBeans = new ArrayList<>();
    private SendBlogAdapter photoAdapter;
    private ClockDialog photoDialog;
    private File file;

    @Override
    protected AddTaskPresenter initPresenter() {
        return new AddTaskPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_askforleave;
    }


    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initCalendar();

        initRecy();
        View inflate = LayoutInflater.from(this).inflate(R.layout.ask_item, null);
        oaDate1 = inflate.findViewById(R.id.oa_date_1);
        oaDate2 = inflate.findViewById(R.id.oa_date_2);
        oaNvar1003 = inflate.findViewById(R.id.oa_nvar100_3);
        oaNvar1002 = inflate.findViewById(R.id.oa_nvar100_2);
        btn_exit = inflate.findViewById(R.id.btn_exit);
        btn_cancel = inflate.findViewById(R.id.btn_cancel);
        startDate = inflate.findViewById(R.id.startDate);
        endDate = inflate.findViewById(R.id.endDate);

        if (clockDialog == null) {
            clockDialog = new ClockDialog(this, inflate);
        }

        makeDate.setText(TimeUtils.getFormatedDateTime(System.currentTimeMillis()));//申请时间
        makePname.setText(Common.LoginUser.getpers_name());//申请人
        makeUname.setText(Common.LoginUser.getunit_name()); //单位
        makeDname.setText(Common.LoginUser.getdept_name()); //部门
        makePname.setText(Common.LoginUser.getpers_name()); //申请人

        dataList = new ArrayList<>();
        dataList.add(new GoodsCategory(1, "事假"));
        dataList.add(new GoodsCategory(2, "病假"));
        dataList.add(new GoodsCategory(3, "带薪年休假"));
        dataList.add(new GoodsCategory(4, "婚假"));
        dataList.add(new GoodsCategory(5, "产假"));
        dataList.add(new GoodsCategory(6, "陪产假"));
        dataList.add(new GoodsCategory(7, "哺乳假"));
        dataList.add(new GoodsCategory(8, "探亲假"));
        dataList.add(new GoodsCategory(9, "丧假"));


    }

    private void initRecy() {
        list = new ArrayList<>();
        adapter = new askAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter.setOnItemLong(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initListener() {
        super.initListener();
        final String[] str2 = {"上午", "下午"};
        ArrayAdapter<String> SpingAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str2);
        oaNvar1003.setAdapter(SpingAdapter1);

        oaNvar1003.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oaNvar3 = str2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> SpingAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str2);
        oaNvar1002.setAdapter(SpingAdapter2);

        oaNvar1002.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oaNvar2 = str2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oaDate1.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请选择开始时间");
                    return;
                }
                if (oaDate2.getText().toString().isEmpty()) {
                    ToastUtil.showShort("请选择结束时间");
                    return;
                }

                Date date1 = TimeUtils.stringToDate(oaDate1.getText().toString(), "yyyy-MM-dd");
                Date date2 = TimeUtils.stringToDate(oaDate2.getText().toString(), "yyyy-MM-dd");

                if (date2.getTime() < date1.getTime()) {
                    ToastUtil.showShort("结束时间不能小于开始时间");
                    return;
                }

                if (date1.getTime() == date2.getTime() && oaNvar2.equals("下午") && oaNvar3.equals("上午")) {
                    ToastUtil.showShort("开始日期不能在结束日期之后");
                    return;
                }


                if (list.size() > 0) {
                    for (EntityLeaveBean bean : list) {
                        Date endDate = TimeUtils.stringToDate(bean.getOal_date_2(), "yyyy-MM-dd");
                        if (date1.getTime() <= endDate.getTime()) {
                            ToastUtil.showShort("已有当前时间段");
                            return;
                        }
                    }
                }

                leaveBean = new EntityLeaveBean();
                leaveBean.setOal_date_1(oaDate1.getText().toString());
                leaveBean.setOal_date_2(oaDate2.getText().toString());
                leaveBean.setOal_date_1_1(oaNvar2);
                leaveBean.setOal_date_2_1(oaNvar3);
                list.add(leaveBean);
                adapter.notifyDataSetChanged();
                clockDialog.dismiss();

                NumberTime();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockDialog.dismiss();
            }
        });


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySelectDate(oaDate1);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySelectDate(oaDate2);
            }
        });
    }


    private void NumberTime() {
        //请假时间
        StringBuffer sb = new StringBuffer();

        //请假天数
        double dayNumber = 0;
        for (EntityLeaveBean bean : list) {
            sb.append("从" + bean.getOal_date_1() + bean.getOal_date_1_1() + "到"
                    + bean.getOal_date_2() + bean.getOal_date_2_1());
            sb.append("\r\n");

            String numberDays = TimeUtils.NumberDays(bean.getOal_date_1(), bean.getOal_date_2());
            Date date1 = TimeUtils.stringToDate(bean.getOal_date_1(), "yyyy-MM-dd");
            Date date2 = TimeUtils.stringToDate(bean.getOal_date_2(), "yyyy-MM-dd");
            if (date1.getTime() == date2.getTime()) {
                numberDays = "1";
                oaSumDeci21.setText(numberDays);
                double oaSum = Double.parseDouble(oaSumDeci21.getText().toString());
                if (bean.getOal_date_1_1().equals("上午") && bean.getOal_date_2_1().equals("上午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum - 0.5));
                }
                if (bean.getOal_date_1_1().equals("下午") && bean.getOal_date_2_1().equals("下午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum - 0.5));
                }
                if (bean.getOal_date_1_1().equals("上午") && bean.getOal_date_2_1().equals("下午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum));
                }
            } else {
                oaSumDeci21.setText(numberDays);
                double oaSum = Double.parseDouble(oaSumDeci21.getText().toString());
                if (bean.getOal_date_1_1().equals("上午") && bean.getOal_date_2_1().equals("上午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 0.5));
                }
                if (bean.getOal_date_1_1().equals("下午") && bean.getOal_date_2_1().equals("下午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 0.5));
                }
                if (bean.getOal_date_1_1().equals("上午") && bean.getOal_date_2_1().equals("下午")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 1));
                }
            }


            dayNumber = dayNumber + Double.parseDouble(oaSumDeci21.getText().toString());
        }
        //请假时间
        oaSumNvarmax1.setText(sb.toString());
        //请假天数
        oaSumDeci21.setText(dayNumber + "");
    }

    @Override
    protected void initData() {
        super.initData();
        //获取code
        presenter.getCodeStr(BLL.BLLJsonByStream("In_Code_Create"));
    }

    private void initCalendar() {
        //实例化日期类
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//获取年
        mMonth = calendar.get(Calendar.MONTH);//获取月
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //获取日
        hour = calendar.get(Calendar.HOUR);//获取小时
        minute = calendar.get(Calendar.MINUTE);//获取分钟
    }


    @OnClick({R.id.Camera_ok, R.id.Camera_cancel, R.id.oa_nvar100_1, R.id.leaveData, R.id.attachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.oa_nvar100_1:    //请假类型
                leaveType();
                break;
            case R.id.leaveData:    //请假时间
                clockDialog.show();
                break;
            case R.id.attachment:    //附件
                openAttchment();
                break;
            case R.id.Camera_ok:   //提交
                submitData();
                break;
            case R.id.Camera_cancel:   //取消
                finish();
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


    private void submitData() {

        if (oaSumNvarmax1.getText().toString().isEmpty()) {
            ToastUtil.showShort("请选择请假时间");
            return;
        }
        if (oa_nvar100_1.getText().toString().isEmpty()) {
            ToastUtil.showShort("请选择请假类型");
            return;
        }
        if (oaNvarmax1.getText().toString().isEmpty()) {
            ToastUtil.showShort("请输入请假事由");
            return;
        }

        showLoading();
        entityOA = new EntityOA();
        entityOA.setcorp_tn(Common.LoginUser.getcorp_tn());
        entityOA.settb_code("9");
        entityOA.settype_code("001");
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
        entityOA.setoa_nvar100_1(oa_nvar100_1.getText().toString());
        entityOA.setoa_nvarmax_1(oaNvarmax1.getText().toString());
        entityOA.setoa_sum_nvarmax_1(oaSumNvarmax1.getText().toString());
        if (!oaSumDeci21.getText().toString().isEmpty()) {
            entityOA.setoa_sum_deci2_1(Double.parseDouble(oaSumDeci21.getText().toString()));
        }
        entityOA.setoa_date_1(oaDate1.getText().toString());
        entityOA.setoa_date_2(oaDate2.getText().toString());
        entityOA.setoa_nvar100_2(oaNvar2);
        entityOA.setoa_nvar100_3(oaNvar3);
        presenter.reciPients(BLL.OAMainAdd(entityOA));
    }


    /*
  请假类型
* */
    private void leaveType() {
        SinglePicker<GoodsCategory> picker = new SinglePicker<>(this, dataList);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(false);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<GoodsCategory>() {
            @Override
            public void onItemPicked(int index, GoodsCategory item) {
                oa_nvar100_1.setText(item.getName());
            }
        });
        picker.show();
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

    @Override
    public void addData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            workFlow = new EntityWorkFlow();
            workFlow.setwf_code("0001");//
            workFlow.setwf_name("员工请假审批");//
            workFlow.settask_code(UUID.randomUUID() + "");//uid
            String task_name = Common.LoginUser.getpers_name() + "于" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "提出请假申请";
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
    public void mainUpTaskData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            if (photoBeans.size() > 0) {
                addFileImg();
            }

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
//                    String fileCode = Common.genRandomNum(16);
                    String fileCode = UUID.randomUUID().toString();
                    EntityFile ent_upfile = new EntityFile();
                    ent_upfile.setfile_code(fileCode);
                    ent_upfile.setfile_extend(".jpg");
                    ent_upfile.setfile_body(Common.bitmapToBase64(bitmap));
                    ent_upfile.setfile_end_flag(true);
                    ent_upfile.setfile_belong("in");
                    ent_upfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\QingJia\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
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
                    entfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\QingJia\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
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


    @Override
    public void getFineStr(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }

        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            General<Object> general = new General<>();
            for (EntityLeaveBean bean : list) {
                EntityOA oaBean = new EntityOA();
                oaBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                oaBean.setoa_code(contentCode);
                oaBean.setoal_date_1(bean.getOal_date_1());
                oaBean.setoal_date_2(bean.getOal_date_2());
                oaBean.setoal_date_1_1(bean.getOal_date_1_1());
                oaBean.setoal_date_2_1(bean.getOal_date_2_1());
                String Json = general.bllInJson(oaBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getFineData(encode);
            }
        }
    }


    int fine = 0;

    @Override
    public void getFineData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            fine++;
            if (fine == list.size()) {
                //发送短信提醒
                presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
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
    public void getCodeStr(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                EntityTable entityTable = new EntityTable();
                entityTable.setcorp_tn(Common.LoginUser.getcorp_tn());
                entityTable.setfield_str("QingJia_" + Common.LoginUser.getpers_code() + "_" + TimeUtils.getYear()
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
    public void onItemLongLisner(int position) {
        this.list.remove(position);
        adapter.notifyDataSetChanged();
        NumberTime();
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
