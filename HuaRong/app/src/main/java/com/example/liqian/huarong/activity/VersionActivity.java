package com.example.liqian.huarong.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.DealWithListener;
import com.example.liqian.huarong.service.UpdateService;
import com.example.liqian.huarong.utils.InstallUtil;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import okhttp3.internal.Version;

public class VersionActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.show)
    TextView show;
    private String version;
    private String mUrl;
    private File file;
    private ProgressDialog dialog;
    private int mVersionCode;


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);//修改字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //权限
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

                if (fileList.size() > 0) {
                    EntityFile _ent = fileList.get(0);
                    //服务器获取版本号
                    version = _ent.getfile_version();
                    //服务器apk下载地址
                    mUrl = Common.upgradehttpAddr + _ent.getfile_link();
                    //获取当前版本号
                    mVersionCode = getVersionCode(VersionActivity.this);
                    try {
                        if (mVersionCode < Integer.parseInt(version)) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("https://www.pgyer.com/UbQx");
                            intent.setData(content_url);
                            startActivity(Intent.createChooser(intent, "请选择浏览器"));
                  /*          Log.i("tag", "有新版本需要更新");
                            showHintDialog(); //弹出对话框，提示用户更新APP*/
                        } else {
                            show.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception ex) {
                        show.setVisibility(View.VISIBLE);
                    }

                } else {
                    show.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void showHintDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("说明");
        alert.setMessage("发现新版本,是否立即更新?");
        alert.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Common.Version == 0) {
                    Common.Version = 1;
                    Intent intent = new Intent(VersionActivity.this, UpdateService.class);
                    intent.putExtra("url", mUrl);
                    startService(intent);
                    ToastUtil.showShort("请稍后...");
                } else if (Common.Version == 1) {
                    ToastUtil.showShort("请稍后，正在下载文件...");
                }

            }
        });
        alert.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create();
        alert.show();
    }

    /*
     * 从服务器端下载最新apk
     * */
    /*private void downloadApk() {
        //显示下载进度
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示!");
        dialog.setMessage("正在下载文件，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();


        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(mUrl)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("downloadApk", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                long max = body.contentLength();

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                }
                saveFile(inputStream, file + "/" + "huarong.apk", max);
            }
        });
    }*/

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

   /* private void saveFile(InputStream inputStream, final String string, long max) {

        //读写的进度
        long count = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(string));

            byte[] bys = new byte[1024 * 10];
            int length = -1;

            while ((length = inputStream.read(bys)) != -1) {
                outputStream.write(bys, 0, length);

                count += length;
                dialog.setMax((int) max);
                dialog.setProgress((int) count);
                Log.d("outputStream", "progress: " + count + "  max:" + max);
            }

            inputStream.close();
            outputStream.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(VersionActivity.this, "下载完成", Toast.LENGTH_SHORT).show();

                    InstallUtil.installApk(VersionActivity.this, string);

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InstallUtil.UNKNOWN_CODE) {
            InstallUtil.installApk(this, file + "/" + "huarong.apk");//再次执行安装流程，包含权限判等
        }
    }
*/
}
