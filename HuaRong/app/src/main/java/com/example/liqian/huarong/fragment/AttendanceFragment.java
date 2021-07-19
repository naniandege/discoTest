package com.example.liqian.huarong.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.DialogActivity;
import com.example.liqian.huarong.adapter.AttendanceAdapter;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AttenDanPresenter;
import com.example.liqian.huarong.mvp.v.AttenDanView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.ClockDialog;
import com.example.liqian.huarong.widget.TextDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends BaseFragment<AttenDanView, AttenDanPresenter> implements AttenDanView, AttendanceAdapter.onItemLisnner {
    private static final int DISTANCE = 800;
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.distance_tv)
    TextView mDistance_tv;
    @BindView(R.id.arriver_timetv)
    TextView mTime_tv;
    @BindView(R.id.arriver_bt)
    RelativeLayout commit_bt;
    @BindView(R.id.but)
    LinearLayout but;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
//    @BindView(R.id.sm)
//    SmartRefreshLayout sm;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LatLng mUser_latlng;

    private LatLng mDestinationPoint;//目的地坐标点
    private LocationClient client;//定位监听
    private LocationClientOption mOption;//定位属性
    private MyLocationData locData;//定位坐标
    private InfoWindow mInfoWindow;//地图文字位置提醒
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private int mCurrentDirection = 0;
    private double mDistance = 0;
    private LatLng mCenterPos;
    private float mZoomScale = 0; //比例
    private Double lastX = 0.0;
    private ArrayList<EntityBase> list;
    private AttendanceAdapter adapter;
    private EntityBase entityBase;
    private int count = 1;
    private int total_page;
    private String ISSTATE = "";
    private String mShow;
    private String mAddress;
    private EditText editWhy;
    private EditText editAddress;
    private String mString;
    private EntityOA entityOA;
    private EntityWorkFlow workFlow;
    private String attendTime;
    //tQZsPlxGyXcwr2tGEehZK3xYAb2lb0xm

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    protected AttenDanPresenter initPresenter() {
        return new AttenDanPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attendance;
    }

    @Override
    protected void initView(final View inflate) {
        super.initView(inflate);
        initPer();
        mBaiduMap = mMapView.getMap();

        initBaiduMap();     //1、初始化地图

        getLocationClientOption();//2、定位开启

        mHandler.post(run);//设置系统时间

        //打卡信息列表
        list = new ArrayList<>();

        adapter = new AttendanceAdapter(list, getActivity());

        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        lv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                count = 1;
                list.clear();
                initData();
                srl.finishRefresh(true);

            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (count < total_page) {
                    ++count;
                    initData();
                    srl.finishLoadMore(true);
                } else {
                    srl.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            }
        });

        adapter.setOnItemLisnner(this);
    }


    @Override
    protected void initData() {
        super.initData();
        EntityBase base0 = new EntityBase();
        base0.setbase_number(Common.LoginUser.getpers_code());
        base0.settb_code("9001");
        base0.settype_code("001");
        base0.setkind_code("001");
        base0.setorder_list("base_date_1 desc");
        presenter.setPlaceList(BLL.BasePageSelect(15, count, base0));
    }

    private void initPer() {
        String[] per = {Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(getActivity(), per, 100);
    }


    /**
     * 初始化地图
     */
    private void initBaiduMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
    }

    //设置打卡目标范围圈
    private void setCircleOptions() {
        if (mDestinationPoint == null) return;
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x4057FFF8)
                .center(mDestinationPoint).stroke(new Stroke(1, 0xB6FFFFFF)).radius(DISTANCE);
        mBaiduMap.addOverlay(ooCircle);
    }


    /***
     * 定位选项设置
     */
    public void getLocationClientOption() {
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(2000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
        mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        client = new LocationClient(getActivity());
        client.setLocOption(mOption);
        client.registerLocationListener(BDAblistener);
        client.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @OnClick({R.id.but, R.id.arriver_bt})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.arriver_bt:
                locate2User();     //自己当前位置
                initSelector();    //刷新距离工作地点距离
                break;
            case R.id.but:
                if (Common.WorkLocation == "") {
                    initChoose(); //选择工作地点
                } else {
                    //自己当前坐标
                    LatLng LocationPoint = new LatLng(mUser_latlng.latitude, mUser_latlng.longitude);
                    //打卡范围
                    mDestinationPoint = new LatLng(Common.WorkLatitude, Common.WorkLongitude);//假设公司坐标
                    //计算两点距离,单位：米
                    mDistance = DistanceUtil.getDistance(mDestinationPoint, LocationPoint);
                    BigDecimal bg = new BigDecimal(mDistance);
                    double mValue = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    presenter.getTime(BLL.getServerTime());
                    if (mValue <= DISTANCE) {
                        backOffice1();  //内勤打卡
                    } else {
                        field1();  //外勤打卡
                    }
                }
                break;
        }
    }

    private void backOffice1() {
        final TextDialog textDialog = new TextDialog(getActivity());
        textDialog.setTvSize(16);
        textDialog.setbutton_exit("确定");
        textDialog.setbutton_cancel("取消");
        textDialog.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.dismiss();
            }
        });

        textDialog.setOnExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOffice();  //内勤打卡
                textDialog.dismiss();

            }
        });

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.attend_item, null);
        Button sb = inflate.findViewById(R.id.sb);
        Button xb = inflate.findViewById(R.id.xb);
        final ClockDialog clockDialog = new ClockDialog(getActivity(), inflate);
        clockDialog.show();

        //上班
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mString = "上班";
                textDialog.setTv("您当前的选择为上班");
                textDialog.show();
                clockDialog.dismiss();
            }
        });


        //下班
        xb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.setTv("您当前的选择为下班");
                mString = "下班";
                textDialog.show();
                clockDialog.dismiss();
            }
        });
    }

    private void field1() {
        final TextDialog textDialog = new TextDialog(getActivity());
        textDialog.setTvSize(16);
        textDialog.setbutton_exit("确定");
        textDialog.setbutton_cancel("取消");
        textDialog.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.dismiss();
            }
        });

        textDialog.setOnExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field();  //外勤打卡
                textDialog.dismiss();

            }
        });

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.attend_item, null);
        Button sb = inflate.findViewById(R.id.sb);
        Button xb = inflate.findViewById(R.id.xb);
        final ClockDialog clockDialog = new ClockDialog(getActivity(), inflate);
        clockDialog.show();

        //上班
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mString = "上班";
                textDialog.setTv("您当前的选择为上班");
                textDialog.show();
                clockDialog.dismiss();
            }
        });


        //下班
        xb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.setTv("您当前的选择为下班");
                mString = "下班";
                textDialog.show();
                clockDialog.dismiss();
            }
        });
    }


    /*
     * 外勤打卡
     * */
    private void field() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.attendance_editor, null);
        editWhy = inflate.findViewById(R.id.editWhy);
        editAddress = inflate.findViewById(R.id.editAddress);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("外勤打卡")
                .setView(inflate)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAddress = editAddress.getText().toString();
                        mShow = editWhy.getText().toString();
                        if (mShow != null && mShow.length() != 0 && mAddress != null && mAddress.length()
                                != 0) {
                            ISSTATE = "外勤";
                            initClock();  //打卡
//                            setMarkerOptions(mDestinationPoint, R.drawable.icon_gcoding);
//                            commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_gray));
                            mBaiduMap.setMyLocationEnabled(true);
                        } else {
                            ToastUtil.showShort("请输入地址和原因");
                        }

                        //任务添加
                        String getdept_code = Common.LoginUser.getdept_code();
                        if (!getdept_code.equals("009")) {
                            addTask();
                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
    }


    @Override
    public void getTime(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            attendTime = pubReturn.getContent();
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
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void wfTaskSms(String data) {

    }

    private void addTask() {
        //第一步，OAMainAdd
        entityOA = new EntityOA();
        entityOA.settb_code("9");
        entityOA.settype_code("014");
        entityOA.setkind_code("001");
        entityOA.setoa_code(UUID.randomUUID() + "");
        entityOA.setmake_ucode(Common.LoginUser.getunit_code());
        entityOA.setmake_uname(Common.LoginUser.getunit_name());
        entityOA.setmake_dcode(Common.LoginUser.getdept_code());
        entityOA.setmake_dname(Common.LoginUser.getdept_name());
        entityOA.setmake_pcode(Common.LoginUser.getpers_code());
        entityOA.setmake_pname(Common.LoginUser.getpers_name());
        entityOA.setmake_date(TimeUtils.getFormatedDateTime(System.currentTimeMillis()));
        entityOA.setoa_nvar100_1(mAddress);//外勤地点
        entityOA.setoa_nvarmax_1(mShow);//外勤说明
        //调用OAMainAdd
        presenter.reciPients(BLL.OAMainAdd(entityOA));
    }


    @Override
    public void addData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //MyinitiateTaskAdd加任务
            workFlow = new EntityWorkFlow();
            workFlow.setwf_code("0015");//
            workFlow.setwf_name("外勤打卡审核");//
            workFlow.settask_code(UUID.randomUUID() + "");//uid
            String task_name = Common.LoginUser.getpers_name() + "于" + TimeUtils.getFormatedDateTime(System.currentTimeMillis()) + "提出外勤审核";
            workFlow.settask_name(task_name);
            workFlow.settask_key(entityOA.getoa_code());
            workFlow.settb_code(entityOA.gettb_code());
            workFlow.settype_code(entityOA.gettype_code());
            workFlow.setkind_code(entityOA.getkind_code());
            workFlow.setstage_code("0101");
            workFlow.setstage_name("任务未提交");
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
            //MyinitiateTaskAdd
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
            //第三步OAMainUpdateTask
            EntityOA entityOA1 = new EntityOA();
            entityOA1.setoa_code(workFlow.gettask_key());
            entityOA1.setwf_code(workFlow.getwf_code());
            entityOA1.settask_code(workFlow.gettask_code());
            //调用OAMainUpdateTask
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
            Common.defDecode(pubReturn.getData());
            //发送短信提醒
            presenter.smsStr(BLL.BLLJsonByStream("In_Task_SelectForSMS"));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    /*
     * 选择工作地点
     * */
    private void initChoose() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("提示！")
                .setMessage("请您先选择工作地点")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), DialogActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
    }

    /*
     * 内勤打卡
     * */
    private void backOffice() {

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("内勤打卡")
                .setMessage("")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Common.WorkLocation != null) {
                            ISSTATE = "内勤";
                            initClock();  //打卡
//                            commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_yellow));
                            mBaiduMap.setMyLocationEnabled(false);
                        } else {
                            ToastUtil.showShort("请选择工作地点");
                            Intent intent = new Intent(getActivity(), DialogActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.c_608ce0));
    }


    /*
     * 刷新自己的位置  距离工作地点的距离
     * */
    private void initSelector() {
        LatLng LocationPoint = new LatLng(mUser_latlng.latitude, mUser_latlng.longitude);
        if (Common.WorkLocation != null) {
            //打卡范围
            mDestinationPoint = new LatLng(Common.WorkLatitude, Common.WorkLongitude);//假设公司坐标
            setCircleOptions();
            //计算两点距离,单位：米
            mDistance = DistanceUtil.getDistance(mDestinationPoint, LocationPoint);
            BigDecimal bg = new BigDecimal(mDistance);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mDistance_tv.setText("距离目的地：" + f1 + "米");
        } else {
            ToastUtil.showShort("请选择工作地点");
        }
    }


    /*
     * 考勤打卡
     * */
    private void initClock() {
      /*  SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sDateFormat.format(new Date(System.currentTimeMillis() + 0));
        Date date = TimeUtils.stringToDate(time, "yyyy/MM/dd HH:mm:ss");
        Date date1 = TimeUtils.stringToDate(attendTime, "yyyy/MM/dd HH:mm:ss");
        if (date.getTime()-date1.getTime()>=3*60*1000){
            ToastUtil.showShort("本地时间不能进行修改后打卡");
        }else {

        }*/

        EntityBase mBase = new EntityBase();
        mBase.setbase_code(UUID.randomUUID() + "");
        mBase.setbase_number(Common.LoginUser.getpers_code());
        mBase.setbase_name(Common.LoginUser.getpers_name());
        mBase.setbase_date_1(TimeUtils.getFormatedDateTime2(System.currentTimeMillis()));//打卡的时间
        mBase.setbase_deci2_1(mUser_latlng.longitude);//打卡的经度
        mBase.setbase_deci2_2(mUser_latlng.latitude);//打卡的纬度
        BigDecimal bg3 = new BigDecimal(mDistance);

        if (ISSTATE.equals("内勤")) {
            mBase.setbase_type("内勤");
        } else if (ISSTATE.equals("外勤")) {
            mBase.setbase_type("外勤");
            mBase.setbase_nvar100_3(mAddress);//打卡的地址
            mBase.setbase_nvarmax_1(mShow);//外勤说明
        }
        mBase.setbase_nvar100_1(Common.WorkLocation);//工作地点
        mBase.setbase_nvar100_4(mString);//上班 下班
        mBase.setbase_deci2_3(Common.WorkLongitude);//工作地点经度
        mBase.setbase_deci2_4(Common.WorkLatitude);//工作地点纬度
        mBase.setbase_deci2_5(bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        mBase.setbase_nvar100_2(Common.getUniquePsuedoID());
        mBase.settb_code("9001");
        mBase.settype_code("001");
        mBase.setkind_code("001");
        presenter.setPlaceModel(BLL.Base_Add(mBase));
    }

    /*
     * 定位其实已经完成了。
     * 点击按钮只是把地图的视图拉到用户的位置
     * */
    private void locate2User() {
        if (mUser_latlng != null) {
            MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(mUser_latlng);
            mBaiduMap.setMapStatus(status2);
        }
    }

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private BDAbstractLocationListener BDAblistener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            //定位方向
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            //骑手定位
            locData = new MyLocationData.Builder()
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            mUser_latlng = new LatLng(location.getLatitude(), location.getLongitude());
            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, null));
       /*     //更改UI
            Message message = new Message();
            message.obj = location;
            mHandler.sendMessage(message);*/
        }
    };

    /**
     * 处理连续定位的地图UI变化
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            setMapZoomScale(LocationPoint);*/
        }
    };

    /**
     * 添加地图文字
     *
     * @param point
     * @param str
     * @param color 字体颜色
     */
    private void setTextOption(LatLng point, String str, String color) {
        //使用MakerInfoWindow
        if (point == null) return;
        TextView view = new TextView(getActivity());
//        view.setBackgroundResource(R.mipmap.map_textbg);
        view.setPadding(0, 23, 0, 0);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setTextSize(14);
        view.setGravity(Gravity.CENTER);
        view.setText(str);
        view.setTextColor(Color.parseColor(color));
        mInfoWindow = new InfoWindow(view, point, 170);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * 设置marker覆盖物
     *
     * @param ll   坐标
     * @param icon 图标
     */
    private void setMarkerOptions(LatLng ll, int icon) {
        if (ll == null) return;
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(icon);
        MarkerOptions ooD = new MarkerOptions().position(ll).icon(bitmap);
        mBaiduMap.addOverlay(ooD);
    }

    //改变地图缩放
    private void setMapZoomScale(LatLng ll) {
        if (mDestinationPoint == null) {
            mZoomScale = getZoomScale(ll);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(ll, mZoomScale));//缩放
        } else {
            mZoomScale = getZoomScale(ll);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(mCenterPos, mZoomScale));//缩放
        }
    }

    /**
     * 获取地图的中心点和缩放比例
     *
     * @return float
     */
    private float getZoomScale(LatLng LocationPoint) {
        double maxLong;    //最大经度
        double minLong;    //最小经度
        double maxLat;     //最大纬度
        double minLat;     //最小纬度
        List<Double> longItems = new ArrayList<Double>();    //经度集合
        List<Double> latItems = new ArrayList<Double>();     //纬度集合

        if (null != LocationPoint) {
            longItems.add(LocationPoint.longitude);
            latItems.add(LocationPoint.latitude);
        }
        if (null != mDestinationPoint) {
            longItems.add(mDestinationPoint.longitude);
            latItems.add(mDestinationPoint.latitude);
        }

        maxLong = longItems.get(0);    //最大经度
        minLong = longItems.get(0);    //最小经度
        maxLat = latItems.get(0);     //最大纬度
        minLat = latItems.get(0);     //最小纬度

        for (int i = 0; i < longItems.size(); i++) {
            maxLong = Math.max(maxLong, longItems.get(i));   //获取集合中的最大经度
            minLong = Math.min(minLong, longItems.get(i));   //获取集合中的最小经度
        }

        for (int i = 0; i < latItems.size(); i++) {
            maxLat = Math.max(maxLat, latItems.get(i));   //获取集合中的最大纬度
            minLat = Math.min(minLat, latItems.get(i));   //获取集合中的最小纬度
        }
        double latCenter = (maxLat + minLat) / 2;
        double longCenter = (maxLong + minLong) / 2;
        int jl = (int) getDistance(new LatLng(maxLat, maxLong), new LatLng(minLat, minLong));//缩放比例参数
        mCenterPos = new LatLng(latCenter, longCenter);   //获取中心点经纬度
        int zoomLevel[] = {2500000, 2000000, 1000000, 500000, 200000, 100000,
                50000, 25000, 20000, 10000, 5000, 2000, 1000, 500, 100, 50, 20, 0};
        int i;
        for (i = 0; i < 18; i++) {
            if (zoomLevel[i] < jl) {
                break;
            }
        }
        float zoom = i + 4;
        return zoom;
    }

    /**
     * 缩放比例参数
     *
     * @param var0
     * @param var1
     * @return
     */
    public double getDistance(LatLng var0, LatLng var1) {
        if (var0 != null && var1 != null) {
            Point var2 = CoordUtil.ll2point(var0);
            Point var3 = CoordUtil.ll2point(var1);
            return var2 != null && var3 != null ? CoordUtil.getDistance(var2, var3) : -1.0D;
        } else {
            return -1.0D;
        }
    }


    /**
     * 设置系统时间
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            mTime_tv.setText(simpleDateFormat.format(date)); //更新时间
            mHandler.postDelayed(run, 1000);
        }
    };

    @Override
    public void setData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            ToastUtil.showShort("打卡成功");
            initData();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void setListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<EntPages<EntityBase>>() {
            }.getType();
            EntPages<EntityBase> entPages = gson.fromJson(decode, type);
            List<EntityBase> pageResults1 = entPages.getPageResults();
            List<EntityBase> bases = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            list.clear();
            if (bases != null && bases.size() > 0) {
                list.addAll(bases);
            }
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void onPosition(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String temp = (String) SpUtil.getParam("entityBase", "");
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                entityBase = (EntityBase) ois.readObject();

                Common.WorkLocation = entityBase.getbase_name();
                Common.WorkLongitude = entityBase.getbase_deci2_1();
                Common.WorkLatitude = entityBase.getbase_deci2_2();

                LatLng LocationPoint = new LatLng(mUser_latlng.latitude, mUser_latlng.longitude);
                //打卡范围
                mDestinationPoint = new LatLng(Common.WorkLatitude, Common.WorkLongitude);//假设公司坐标
                setCircleOptions();
                //计算两点距离,单位：米
                mDistance = DistanceUtil.getDistance(mDestinationPoint, LocationPoint);
                BigDecimal bg = new BigDecimal(mDistance);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                mDistance_tv.setText("距离目的地：" + f1 + "米");
            } catch (IOException e) {
            } catch (ClassNotFoundException e1) {

            }
        }
    }
}
