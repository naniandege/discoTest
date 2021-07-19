package com.example.liqian.huarong.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.utils.WpsUtil;
import com.example.liqian.huarong.widget.ClockDialog;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;

public class OpenFilesActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView, WpsUtil.WpsInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    ImageView img;
    private WpsUtil wpsUtil;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_files;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();

        //Url 路径
        Intent urlIntent = getIntent();
        if (urlIntent != null) {
            String url = urlIntent.getStringExtra("url").toLowerCase();
            String subUrl = url.substring(url.lastIndexOf(".") + 1);
            wpsUtil = new WpsUtil(OpenFilesActivity.this, "", url, false, OpenFilesActivity.this);
            if (subUrl.equals("mp4")) {
                Intent intent = new Intent(this, VideoFileActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                finish();
            } else {
                myOpenFile();
                finish();
            }
        }

    }

    private void initToolbar() {
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    private boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    private void myOpenFile() {
        //检测是否安装了wps软件，没有安装则去下载
        boolean install = isInstall(OpenFilesActivity.this, "cn.wps.moffice_eng");
        if (install) {
            wpsUtil.openDocument();
        } else {
            View inflate = LayoutInflater.from(this).inflate(R.layout.openfile_item, null);
            Button cancle = inflate.findViewById(R.id.btn_cancel);
            Button determine = inflate.findViewById(R.id.btn_determine);
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
                    // 从市场上下载
                    Uri uri = Uri.parse("market://details?id=cn.wps.moffice_eng");
                    // 直接从指定网址下载
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
            });
        }
    }


    /**
     * 文件保存的回调
     *
     * @param filePath
     */
    @Override
    public void doRequest(String filePath) {

    }


    /**
     * 按手机回退键 回到app
     */
    @Override
    public void doFinish() {
        wpsUtil.appBack();
    }
}
