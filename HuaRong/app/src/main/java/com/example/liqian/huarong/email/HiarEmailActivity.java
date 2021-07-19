package com.example.liqian.huarong.email;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.MainActivity;
import com.example.liqian.huarong.adapter.AttchmenAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AttachmenPresenter;
import com.example.liqian.huarong.mvp.p.HiarPresenter;
import com.example.liqian.huarong.mvp.v.HiarView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.FileUtils;
import com.example.liqian.huarong.utils.OpenFiles;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class HiarEmailActivity extends BaseActivity<HiarView, HiarPresenter> implements HiarView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.emailTitle)
    EditText emailTitle;
    @BindView(R.id.recipientName)
    TextView recipientName;
    @BindView(R.id.recipient)
    LinearLayout recipient;
    @BindView(R.id.emailBody)
    EditText emailBody;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;

    private Gson gson;
    private EntityBase entityBase;


    @Override
    protected HiarPresenter initPresenter() {
        return new HiarPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hiar_email;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Mongo_File_Add"));
    }

    @OnClick({R.id.recipient, R.id.button1, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recipient:
                Intent intent = new Intent(this, RecipientNameActivity.class);
                startActivityForResult(intent, Constants.REQUESTCODE);
                break;
            case R.id.button1:
                //邮件发送
                presenter.getSendData(BLL.BLLJsonByStream("In_Base_Add"));
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                //打开文件选择器
                Intent openFile = new Intent(Intent.ACTION_GET_CONTENT);
                openFile.addCategory(Intent.CATEGORY_OPENABLE);
//                openFile.setType(“image/*”);//选择图片
                //openFile.setType(“audio/*”); //选择音频
                //openFile.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //openFile.setType(“video/*;image/*”);//同时选择视频和图片
                openFile.setType("*/*"); //无类型限制
                this.startActivityForResult(openFile, 1);
                break;
        }
    }


    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityFile entityFile = new EntityFile();
            entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
//            entityFile.setfile_becode(baseCode);
            entityFile.settb_code("100");
            entityFile.settype_code("001");
            entityFile.setkind_code("003");
            entityFile.setfile_server_map(Common.LoginUser.getdept_code() + "\\Email");
            entityFile.setfile_code(UUID.randomUUID() + "");
            entityFile.setfile_code(UUID.randomUUID() + "");

            General<Object> general = new General<>();
            String Json = general.bllMonJson(entityFile, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }

    @Override
    public void setListData(String data) {
  /*      String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityFile>>() {
            }.getType();
            List<EntityFile> entityFiles = gson.fromJson(decode, type);
            List<EntityFile> entityFileList = Common.defDecodeEntityList(entityFiles);
            if (entityFileList != null && entityFileList.size() > 0) {
                list.addAll(entityFileList);
                adapter.notifyDataSetChanged();
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }*/
    }


    @Override
    public void getSendData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            if (emailTitle.getText().toString().isEmpty()) {
                ToastUtil.showShort("邮件标题为空");
                return;
            }

            if (recipientName.getText().toString().isEmpty()) {
                ToastUtil.showShort("收件人为空");
                return;
            }

            if (emailBody.getText().toString().isEmpty()) {
                ToastUtil.showShort("邮件正文为空");
                return;
            }

//            EntityBase entityBase = new EntityBase();
            entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityBase.setbase_name(emailTitle.getText().toString());
            entityBase.setbase_nvarmax_1(emailBody.getText().toString());
            entityBase.setbase_code(UUID.randomUUID() + "");
            entityBase.settb_code("100000");
            entityBase.settype_code("001");
            entityBase.setkind_code("001");
            entityBase.setmake_pcode(Common.LoginUser.getpers_code());
            entityBase.setmake_pname(Common.LoginUser.getpers_name());
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entityBase.setmake_date(sDateFormat.format(new Date(System.currentTimeMillis())));
            entityBase.setbase_state_code("001");
            entityBase.setbase_state_name("未读");
            entityBase.setbase_type("原创");
            entityBase.setdirect_state("发");
//            entityBase.setfile_num();
            General<Object> general = new General<>();
            String Json = general.bllMonJson(entityBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.send(encode);
        }
    }


    @Override
    public void send(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());

        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    private String path = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUESTCODE && resultCode == Constants.RESULTCODE) {
            StringBuffer sb = new StringBuffer();
            String json = data.getStringExtra("entityPersonals");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<EntityPersonal>>() {
            }.getType();
            String pers_names = "";
            entityBase = new EntityBase();
            List<EntityPersonal> mList = gson.fromJson(json, listType);
            for (EntityPersonal entityPersonal : mList) {
                if (pers_names.isEmpty()) {
                    pers_names = entityPersonal.getpers_name();
                } else {
                    pers_names += "," + entityPersonal.getpers_name();
                }
                entityBase.setbase_pers_names(pers_names);
                sb.append(entityPersonal.getpers_name());
            }
            recipientName.setText(sb.toString());
        } else if (requestCode == 1) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
            }

            File file = new File(path);
            String fileName = file.getName();
            ToastUtil.showShort(fileName);
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }

        return res;
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
