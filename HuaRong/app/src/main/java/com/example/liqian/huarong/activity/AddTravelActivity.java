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
import com.example.liqian.huarong.adapter.PhotoAdapter;
import com.example.liqian.huarong.adapter.TravelDetailAdapter;
import com.example.liqian.huarong.adapter.askAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.BaseApp;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityLeaveBean;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.EntityTable;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PhotoBean;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.bean.TravelBean;
import com.example.liqian.huarong.mvp.p.TravelPresenter;
import com.example.liqian.huarong.mvp.v.TravelView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.Amount;
import com.example.liqian.huarong.utils.ImageFactory;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.ViewUtil;
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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class AddTravelActivity extends BaseActivity<TravelView, TravelPresenter> implements TravelView, askAdapter.onItemLong, TravelDetailAdapter.myOnItemListener, PhotoAdapter.onImgItemLong {

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
    @BindView(R.id.oa_nvar100_1)
    TextView oaNvar1001;
    @BindView(R.id.oa_nvarmax_1)
    EditText oaNvarmax1;
    @BindView(R.id.oa_nvar100_2)
    EditText oaNvar1002;
    @BindView(R.id.oa_sum_nvarmax_1)
    TextView oaSumNvarmax1;
    @BindView(R.id.oa_sum_deci2_1)
    TextView oaSumDeci21;
    @BindView(R.id.oa_sum_deci2_2)
    TextView oaSumDeci22;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.Camera_cancel)
    Button CameraCancel;
    @BindView(R.id.Camera_ok)
    Button CameraOk;
    private ClockDialog clockDialog;
    private TextView oaDate1;
    private TextView oaDate2;
    private Button btn_exit;
    private Button btn_cancel;
    private EntityLeaveBean leaveBean;
    private LinearLayout startDate;
    private LinearLayout endDate;

    private Calendar calendar;
    private int hour;
    private int minute;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Spinner spOaNvar1003;
    private Spinner spOaNvar1002;

    private String oaNvar3 = "";
    private String oaNvar2 = "";

    private askAdapter adapter;
    private ArrayList<EntityLeaveBean> list;
    private ClockDialog detailDialog;
    private String selectPosition;
    private ArrayList<TravelBean> travelBeans;
    private TravelDetailAdapter detailAdapter;
    double a = 0.0;//??????????????????

    private static final int ALBUM_CODE = 200;
    private ArrayList<PhotoBean> photoBeans = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private ClockDialog photoDialog;
    private String contentCode;
    private EntityWorkFlow workFlow;
    private Gson gson;
    private EntityOA entityOA;
    private List<EntityPersonal> mPersonals;
    private File file;

    @Override
    protected TravelPresenter initPresenter() {
        return new TravelPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_travel;
    }

    @Override
    protected void initData() {
        super.initData();
        //??????code
        presenter.getCodeStr(BLL.BLLJsonByStream("In_Code_Create"));
    }

    @OnClick({R.id.Camera_cancel, R.id.Camera_ok, R.id.travel_date, R.id.travel_pres, R.id.Detail, R.id.attachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Camera_cancel:
                finish();
                break;
            case R.id.Camera_ok:
                submitData();
                break;
            case R.id.travel_pres:
                //??????????????????
                startActivityForResult(new Intent(this, PersonaActivity.class), 1);
                break;
            case R.id.travel_date:
                //??????????????????
                clockDialog.show();
                break;
            case R.id.Detail:
                //????????????
                initDetailDialog();
                break;
            case R.id.attachment:
                if (photoAdapter == null) {
                    photoAdapter = new PhotoAdapter(photoBeans, this);
                }


                View inflate = LayoutInflater.from(this).inflate(R.layout.photo_item, null);
                RecyclerView photoLv = inflate.findViewById(R.id.phpto_lv);
                Button determine = inflate.findViewById(R.id.determine);
                Button add = inflate.findViewById(R.id.add);
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


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //????????????
                        takePick();
                    }
                });
                break;
        }
    }

    private void addFileImg() {
        try {
            //????????????
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
                    ent_upfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\btrip\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
                    presenter.uploadData(BLL.UploadFile(ent_upfile));

                    //??????
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
                    entfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\btrip\\" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "\\" + Common.LoginUser.getpers_un());
//                    entfile.setfile_server_map(Common.LoginUser.getcorp_tn() + "\\OA\\btrip");
                    entfile.setfile_belong("in");
                    entfile.setfile_becode(contentCode);
                    entfile.setfile_name("??????" + k);
                    entfile.setfile_origname("??????" + k);
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

    private void submitData() {
        if (oaNvar1001.getText().toString().isEmpty()) {
            ToastUtil.showShort("?????????????????????");
            return;
        }
        if (oaNvarmax1.getText().toString().isEmpty()) {
            ToastUtil.showShort("?????????????????????");
            return;
        }
        if (oaNvar1002.getText().toString().isEmpty()) {
            ToastUtil.showShort("?????????????????????");
            return;
        }

        if (oaSumNvarmax1.getText().toString().isEmpty()) {
            ToastUtil.showShort("?????????????????????");
            return;
        }
        if (oaSumDeci22.getText().toString().isEmpty()) {
            ToastUtil.showShort("?????????????????????");
            return;
        }

        showLoading();

        entityOA = new EntityOA();
        entityOA.setcorp_tn(Common.LoginUser.getcorp_tn());
        entityOA.settb_code("9");
        entityOA.settype_code("008");
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
        entityOA.setoa_nvar100_1(oaNvar1001.getText().toString());
        entityOA.setoa_nvar100_2(oaNvar1002.getText().toString());
        entityOA.setoa_nvarmax_1(oaNvarmax1.getText().toString());
        entityOA.setoa_sum_nvarmax_1(oaSumNvarmax1.getText().toString());
        if (!oaSumDeci21.getText().toString().isEmpty()) {
            entityOA.setoa_sum_deci2_1(Double.parseDouble(oaSumDeci21.getText().toString()));
        }
        if (!oaSumDeci22.getText().toString().isEmpty()) {
            entityOA.setoa_sum_deci2_2(Double.parseDouble(oaSumDeci22.getText().toString()));
        }
        presenter.reciPients(BLL.OAMainAdd(entityOA));
    }


    private void takePick() {//??????Sd?????????
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    /**
     * ????????????
     * ??????????????????sdk????????????19;
     * ????????????19?????????Intent.ACTION_PICK???????????????;
     * ??????19??????Intent.ACTION_GET_CONTENt???????????????;
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

    private void initDetailDialog() {
        //??????????????????spinner???????????????
        final String[] spData = {"??????????????????", "?????????", "???????????????", "????????????????????????", "??????", ""};
        View inflate = LayoutInflater.from(this).inflate(R.layout.traveal_item, null);
        final Spinner spinner = inflate.findViewById(R.id.spinner); //??????
        final EditText amount = inflate.findViewById(R.id.amount);//??????
        final EditText note = inflate.findViewById(R.id.note);//??????
        Button determine = inflate.findViewById(R.id.determine);//??????
        Button cancel = inflate.findViewById(R.id.cancel);//??????
        Button add = inflate.findViewById(R.id.add);//??????
        final RecyclerView travel_lv = inflate.findViewById(R.id.travel_lv);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spData);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(5, true);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = spData[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (detailDialog == null) {
            detailDialog = new ClockDialog(this, inflate);
        }
        detailDialog.show();


        //?????????????????????
        if (travelBeans == null) {
            travelBeans = new ArrayList<>();
        }

        //???????????????
        if (detailAdapter == null) {
            detailAdapter = new TravelDetailAdapter(travelBeans, BaseApp.getInstance());
        }
        travel_lv.setAdapter(detailAdapter);
        travel_lv.setLayoutManager(new LinearLayoutManager(BaseApp.getInstance()));
        travel_lv.addItemDecoration(new DividerItemDecoration(BaseApp.getInstance(), LinearLayoutManager.VERTICAL));
        detailAdapter.setMyOnItemListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(selectPosition)) {
                    ToastUtil.showShort("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(amount.getText().toString().trim())) {
                    ToastUtil.showShort("???????????????");
                    return;
                }

                //????????????
                TravelBean travelBean = new TravelBean();
                travelBean.setProjectName(selectPosition);
                travelBean.setAmount(amount.getText().toString());
                travelBean.setNote(note.getText().toString());
                travelBeans.add(travelBean);
                detailAdapter.notifyDataSetChanged();
                ToastUtil.showShort("????????????");
                ViewUtil.hideAllInputMethod(AddTravelActivity.this);
                spinner.setSelection(5, true);
                amount.setText("");
                note.setText("");

            }
        });


        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0.0;
                if (travelBeans.size() > 0) {
                    for (TravelBean travelBean : travelBeans) {
                        Double aDouble = Double.valueOf(travelBean.getAmount());
                        a += aDouble;
                    }
                    oaSumDeci22.setText(a + "");
                    detailDialog.dismiss();
                } else {
                    ToastUtil.showShort("??????????????????");
                    return;
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailDialog.dismiss();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initListener() {
        super.initListener();
        final String[] str2 = {"??????", "??????"};
        ArrayAdapter<String> SpingAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str2);
        spOaNvar1003.setAdapter(SpingAdapter1);

        spOaNvar1003.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spOaNvar1002.setAdapter(SpingAdapter2);

        spOaNvar1002.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    ToastUtil.showShort("?????????????????????");
                    return;
                }
                if (oaDate2.getText().toString().isEmpty()) {
                    ToastUtil.showShort("?????????????????????");
                    return;
                }

                Date date1 = TimeUtils.stringToDate(oaDate1.getText().toString(), "yyyy-MM-dd");
                Date date2 = TimeUtils.stringToDate(oaDate2.getText().toString(), "yyyy-MM-dd");

                if (date2.getTime() < date1.getTime()) {
                    ToastUtil.showShort("????????????????????????????????????");
                    return;
                }

                if (date1.getTime() == date2.getTime() && oaNvar2.equals("??????") && oaNvar3.equals("??????")) {
                    ToastUtil.showShort("???????????????????????????????????????");
                    return;
                }


                if (list.size() > 0) {
                    for (EntityLeaveBean bean : list) {
                        Date endDate = TimeUtils.stringToDate(bean.getOal_date_2(), "yyyy-MM-dd");
                        if (date1.getTime() <= endDate.getTime()) {
                            ToastUtil.showShort("?????????????????????");
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
        //????????????
        StringBuffer sb = new StringBuffer();

        //????????????
        double dayNumber = 0;
        for (EntityLeaveBean bean : list) {
            sb.append("???" + bean.getOal_date_1() + bean.getOal_date_1_1() + "???"
                    + bean.getOal_date_2() + bean.getOal_date_2_1());
            sb.append("\r\n");

            String numberDays = TimeUtils.NumberDays(bean.getOal_date_1(), bean.getOal_date_2());
            Date date1 = TimeUtils.stringToDate(bean.getOal_date_1(), "yyyy-MM-dd");
            Date date2 = TimeUtils.stringToDate(bean.getOal_date_2(), "yyyy-MM-dd");
            if (date1.getTime() == date2.getTime()) {
                numberDays = "1";
                oaSumDeci21.setText(numberDays);
                double oaSum = Double.parseDouble(oaSumDeci21.getText().toString());
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum - 0.5));
                }
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum - 0.5));
                }
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum));
                }
            } else {
                oaSumDeci21.setText(numberDays);
                double oaSum = Double.parseDouble(oaSumDeci21.getText().toString());
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 0.5));
                }
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 0.5));
                }
                if (bean.getOal_date_1_1().equals("??????") && bean.getOal_date_2_1().equals("??????")) {
                    oaSumDeci21.setText(String.valueOf(oaSum + 1));
                }
            }


            dayNumber = dayNumber + Double.parseDouble(oaSumDeci21.getText().toString());
        }
        //????????????
        oaSumNvarmax1.setText(sb.toString());
        //????????????
        oaSumDeci21.setText(dayNumber + "");
    }


    /**
     * ????????????
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


    private void initCalendar() {
        //??????????????????
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//?????????
        mMonth = calendar.get(Calendar.MONTH);//?????????
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //?????????
        hour = calendar.get(Calendar.HOUR);//????????????
        minute = calendar.get(Calendar.MINUTE);//????????????
    }


    private void initRecy() {
        list = new ArrayList<>();
        adapter = new askAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter.setOnItemLong(this);
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initCalendar();
        initRecy();
        makeDate.setText(TimeUtils.getFormatedDateTime(System.currentTimeMillis()));//????????????
        makePname.setText(Common.LoginUser.getpers_name());//?????????
        makeUname.setText(Common.LoginUser.getunit_name()); //??????
        makeDname.setText(Common.LoginUser.getdept_name()); //??????
        makePname.setText(Common.LoginUser.getpers_name()); //?????????


        View inflate = LayoutInflater.from(this).inflate(R.layout.ask_item, null);
        oaDate1 = inflate.findViewById(R.id.oa_date_1);
        oaDate2 = inflate.findViewById(R.id.oa_date_2);
        spOaNvar1003 = inflate.findViewById(R.id.oa_nvar100_3);
        spOaNvar1002 = inflate.findViewById(R.id.oa_nvar100_2);
        btn_exit = inflate.findViewById(R.id.btn_exit);
        btn_cancel = inflate.findViewById(R.id.btn_cancel);
        startDate = inflate.findViewById(R.id.startDate);
        endDate = inflate.findViewById(R.id.endDate);

        if (clockDialog == null) {
            clockDialog = new ClockDialog(this, inflate);
        }
    }

    @Override
    public void onItemLongLisner(int position) {
        this.list.remove(position);
        adapter.notifyDataSetChanged();
        NumberTime();
    }

    /**
     * ???????????????????????????
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            StringBuffer sb = new StringBuffer();
            int count = 0;
            if (requestCode == 1 && resultCode == 2) {
                String json = data.getStringExtra("json");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<EntityPersonal>>() {
                }.getType();
                mPersonals = gson.fromJson(json, listType);
                for (EntityPersonal entityPersonal : mPersonals) {
                    count++;
                    sb.append(entityPersonal.getpers_name());
                    if (count != mPersonals.size()) {
                        sb.append(",");
                    }
                }
                oaNvar1001.setText(sb.toString());
            }
            if (resultCode == RESULT_OK) {
                if (requestCode == ALBUM_CODE) {//??????
                    //?????????????????????????????????URi??????
                    Uri imageUri = data.getData();
                    //??????????????????????????????
                    try {
                        //??????uri,7.0?????????fileProvider ???URI ???content provider ?????? ???????????????????????????
                        if (imageUri != null) {
                            file = getFileFromUri(imageUri, this);
                            Luban.with(this)
                                    .load(file)
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
                                            // TODO ???????????????????????????????????????????????? loading UI
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            // TODO ??????????????????????????????????????????????????????
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
                                                photoAdapter.notifyDataSetChanged();
                                                photoDialog.show();

                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            // TODO ????????????????????????????????????
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
     * ???????????????????????????uri??????????????????
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


    /**
     * ??????????????????
     *
     * @param position
     */
    @Override
    public void onItem(final int position) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.delete_item, null);
        final ClockDialog mDialog = new ClockDialog(this, inflate);
        mDialog.show();
        inflate.findViewById(R.id.determine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelBeans.remove(position);
                detailAdapter.notifyDataSetChanged();
                mDialog.dismiss();

                a = 0;
                if (travelBeans.size() > 0) {
                    for (TravelBean bean : travelBeans) {
                        Double aDouble = Double.valueOf(bean.getAmount());
                        a += aDouble;
                    }
                    oaSumDeci22.setText(a + "");
                } else {
                    oaSumDeci22.setText(0 + "");
                }

                detailDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }

    /**
     * ????????????  ??????
     *
     * @param position
     */
    @Override
    public void onImgLong(int position) {
        photoBeans.remove(position);
        photoAdapter.notifyDataSetChanged();
    }


    @Override
    public void addData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            workFlow = new EntityWorkFlow();
            workFlow.setwf_code("0008");//
            workFlow.setwf_name("??????????????????");//
            workFlow.settask_code(UUID.randomUUID() + "");//uid
            String task_name = Common.LoginUser.getpers_name() + "???" + TimeUtils.getFormatedDateTime1(System.currentTimeMillis()) + "????????????????????????";
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

            //entityOA?????????json
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

            //??????????????????
            presenter.getFineStr(BLL.BLLJsonByStream("In_OAList_Add"));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    int fine = 0;

    /**
     * ????????????
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
            for (int i = 0; i < list.size(); i++) {
                EntityLeaveBean entityLeaveBean = list.get(i);
                EntityOA oaBean = new EntityOA();
                oaBean.setoal_type("01");
                oaBean.setoa_code(entityOA.getoa_code());
                oaBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                oaBean.setoal_date_1(entityLeaveBean.getOal_date_1());
                oaBean.setoal_date_2(entityLeaveBean.getOal_date_2());
                oaBean.setoal_date_1_1(entityLeaveBean.getOal_date_1_1());
                oaBean.setoal_date_2_1(entityLeaveBean.getOal_date_2_1());
                String Json = general.bllInJson(oaBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.addFineData(encode);
            }
        }
    }

    @Override
    public void addFineData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            fine++;
            if (fine == list.size()) {
                //??????????????????
                presenter.getDetailStr(BLL.BLLJsonByStream("In_OAList_Add"));
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    int dateil = 0;


    /**
     * ????????????
     *
     * @param data
     */
    @Override
    public void getDetailStr(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            General<Object> general = new General<>();
            for (int i = 0; i < travelBeans.size(); i++) {
                TravelBean detailBean = travelBeans.get(i);
                EntityOA oaBean = new EntityOA();
                oaBean.setoal_type("02");
                oaBean.setoa_code(entityOA.getoa_code());
                oaBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                oaBean.setoal_nvar100_1(detailBean.getProjectName());
                oaBean.setoal_deci2_1(Double.parseDouble(detailBean.getAmount()));
                oaBean.setoal_nvarmax_1(detailBean.getNote());
                String Json = general.bllInJson(oaBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.addDetailData(encode);
            }
        }
    }

    @Override
    public void addDetailData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            dateil++;
            if (dateil == travelBeans.size()) {
                //????????????
                presenter.getPersStr(BLL.BLLJsonByStream("In_OAPers_Add"));
            }
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    int pers = 0;

    /**
     * ????????????
     *
     * @param data
     */
    @Override
    public void getPersStr(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            General<Object> general = new General<>();
            for (EntityPersonal entityPersonal : mPersonals) {
                EntityOA oaBean = new EntityOA();
                oaBean.setoa_type("01");
                oaBean.setoa_code(entityOA.getoa_code());
                oaBean.setcorp_tn(Common.LoginUser.getcorp_tn());
                oaBean.setpers_code(entityPersonal.getpers_code());
                oaBean.setpers_name(entityPersonal.getpers_name());
                String Json = general.bllInJson(oaBean, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.addPersData(encode);
            }
        }
    }

    @Override
    public void addPersData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            pers++;
            if (pers == mPersonals.size()) {
                //??????????????????
                presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
            }
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
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
        pers = 0;
        dateil = 0;
        fine = 0;
        hideLoading();
        ToastUtil.showShort("????????????");
        finish();
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
                entityTable.setfield_str("ETrip_" + Common.LoginUser.getpers_code() + "_" + TimeUtils.getYear()
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

}
