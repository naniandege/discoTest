package com.example.liqian.huarong.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.MainVpAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.fragment.AttendanceFragment;
import com.example.liqian.huarong.fragment.AuditFragment;
import com.example.liqian.huarong.fragment.HomeFragment;
import com.example.liqian.huarong.fragment.InitiateFragment;
import com.example.liqian.huarong.fragment.MyFragment;
import com.example.liqian.huarong.fragment.TaskFragment;
import com.example.liqian.huarong.mvp.p.SelectDatePresenter;
import com.example.liqian.huarong.mvp.v.SelectDateView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.service.UpdateService;
import com.example.liqian.huarong.utils.StatusBarUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.views.NoScrollViewPager;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity<SelectDateView, SelectDatePresenter> implements SelectDateView {

    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.img_click)
    ImageView img_click;
    @BindView(R.id.home_name)
    TextView home_name;

    private String version;
    private String mUrl;
    private int mVersionCode;
    private File file;

    @Override
    protected SelectDatePresenter initPresenter() {
        return new SelectDatePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle(" ");
        toolbarName.setText(" ");
        toolbarName.setTextColor(getResources().getColor(R.color.c_000000));
        setSupportActionBar(toolbar);
        home_name.setText(Common.LoginUser.getpos_name());
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        toolbar.setBackgroundColor(getResources().getColor(R.color.c_0779e4));
        StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.c_0779e4);
//        toolbarColor(R.color.white);  //更改Toolbar回退键颜色

        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        vp.setOffscreenPageLimit(4);    //预加载数量

        //碎片数据源
        initFragment();

        //读写权限
        initSD();
    }


    @Override
    protected void initData() {
        super.initData();
        EntityFile entityFile = new EntityFile();
        entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
        entityFile.settb_code("9999");
        entityFile.settype_code("1000");
        entityFile.setkind_code("005");

        Common.httpPost(BLL.FileSelectForUpgrade(entityFile), 11, this, new DealWithListener() {
            @Override
            public void ServerDataDealWith(PubReturn pubReturn) {

                String str = Common.defDecode(pubReturn.getData());
                Gson gson = new Gson();
                List<EntityFile> fileList = gson.fromJson(str, new TypeToken<List<EntityFile>>() {
                }.getType());
                fileList = Common.defDecodeEntityList(fileList);
                if (fileList != null && fileList.size() > 0) {
                    EntityFile _ent = fileList.get(0);
                    //服务器获取版本号
                    version = _ent.getfile_version();
                    //服务器apk下载地址
                    mUrl = Common.upgradehttpAddr + _ent.getfile_link();
                    //获取当前版本号
                    mVersionCode = getVersionCode(MainActivity.this);
                    try {
                        if (mVersionCode < Integer.parseInt(version)) {
                            Log.i("tag", "有新版本需要更新");
                            showHintDialog(); //弹出对话框，提示用户更新APP
                        } else {

                        }
                    } catch (Exception ex) {
                        ToastUtil.showShort(ex.getMessage());
                    }
                }
            }
        });
    }

    private void initFragment() {
        //fragment 集合
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new TaskFragment());
        list.add(new AttendanceFragment());
        list.add(new InitiateFragment());
        list.add(new MyFragment());

        tab.addTab(tab.newTab().setText("首页").setIcon(R.drawable.select_home));
        tab.addTab(tab.newTab().setText("待办").setIcon(R.drawable.select_shen));
        tab.addTab(tab.newTab().setText("考勤").setIcon(R.drawable.selector_fa));
        tab.addTab(tab.newTab().setText("文件").setIcon(R.drawable.selector_kao));
        tab.addTab(tab.newTab().setText("我的").setIcon(R.drawable.selector_my));
        MainVpAdapter adapter = new MainVpAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(adapter);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                vp.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                if (position == 0) {
                    toolbarName.setText(" ");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.c_0779e4));
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.c_0779e4);
                    img_click.setVisibility(View.VISIBLE);
                    home_name.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    toolbarName.setText("审核");
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.white);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    img_click.setVisibility(View.GONE);
                    home_name.setVisibility(View.GONE);
                } else if (position == 2) {
                    toolbarName.setText("考勤");
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.white);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    img_click.setVisibility(View.GONE);
                    home_name.setVisibility(View.GONE);
//                    toolbarColor(R.color.c_000000);
                } else if (position == 3) {
                    toolbarName.setText("文件");
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.white);
                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    img_click.setVisibility(View.GONE);
                    home_name.setVisibility(View.GONE);
                } else if (position == 4) {
                    toolbarName.setText("我的");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.c_0779e4));
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.c_0779e4);
                    img_click.setVisibility(View.GONE);
                    home_name.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            //点一次返回相当于home键
//            moveTaskToBack(true);
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.showShort("再按一次退出程序");
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void showHintDialog() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.version_item, null);
        ImageView cancle = inflate.findViewById(R.id.cancle);
        Button determine = inflate.findViewById(R.id.determine);
        Button determine1 = inflate.findViewById(R.id.determine1);
        final ClockDialog dialog = new ClockDialog(this, inflate);
        dialog.show();

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.Version == 0) {
                    Common.Version = 1;
                    Intent intent = new Intent(MainActivity.this, UpdateService.class);
                    intent.putExtra("url", mUrl);
                    startService(intent);
                    ToastUtil.showShort("请稍后...");
                } else if (Common.Version == 1) {
                    ToastUtil.showShort("请稍后，正在下载文件...");
                }

                dialog.dismiss();
            }
        });


        determine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.pgyer.com/UbQx");
                intent.setData(content_url);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                dialog.dismiss();
            }
        });
    }


    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }


    private void initSD() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            openSd();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openSd();
        }
    }

    private void openSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = Environment.getExternalStorageDirectory();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        img_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
