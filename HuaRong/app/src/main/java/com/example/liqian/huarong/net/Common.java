package com.example.liqian.huarong.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liqian.huarong.activity.LoginActivity;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.EntityInvoice;
import com.example.liqian.huarong.bean.EntityLogin;
import com.example.liqian.huarong.bean.PubReturn;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Version;
import okio.BufferedSink;

/**
 * Created by Administrator on 2019-07-09.
 */

public class Common {

    public static final String httpAddr = "http://218.246.23.195/NewUpload/";

    public static final String clientAddr = "http://218.246.23.195:1889/";
    public static final String serverAddr = "http://218.246.23.195:1890/";
    public static final String clientWebAddr = "http://218.246.23.195:1888/";
    public static final String clientFileAddr = "http://218.246.23.195:1891/";
    //    public static final String upgradehttpAddr="http://211.144.33.54/upload/";
    public static final String upgradeAddr = "http://218.246.23.195:1890/";
    public static final String upgradehttpAddr = "http://218.246.23.195/NewUpload/Upgrade/";

    public static final String login = "LoginVerifyByStream/";
    public static final String pageSelect = "DataPageSelectByStream/";
    public static final String Select = "DataSelectByStream/";
    public static final String Uploadfile = "UploadFileByStream/";
    public static final String DataListDelete = "DataListDeleteByStream/";
    public static final String DataInsert = "DataInsertByStream/";
    public static final String DataUpdate = "DataUpdateByStream/";
    public static final String BLLJson = "BLLJsonByStream/";


    private static String TAG = "";

    public static String MachineCode = "";
    public static String RandomCode = "";
    public static String OldRandomCode = "";
    public static String LoginType = "";
    public static float leftX = 40;
    public static float leftY = 50;

    public static String WorkLocation = "";
    public static double WorkLongitude = 0;
    public static double WorkLatitude = 0;
    public static String wDate1 = "";
    public static String wDate2 = "";
    public static String wDate3 = "";
    public static String wDate4 = "";
    public static String attachmentTag = "";
    public static EntityLogin LoginUser = new EntityLogin();
    public static int Version = 0;
    public static boolean smsTag = false;
    private static Context context = null;
    private static DealWithListener dealWithListener = null;
    private static MylWithListener mylWithListener = null;


    public static String defEncode(String str) {
        if (str != null && str != "") {
            //str = str.replace(' ', '+');
            str = Uri.encode(str);//base64编码
            str = Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
        }
        return str;
    }


    public static String defDecode(String str) {
        if (str != null && str != "") {
            try {
                str = new String(Base64.decode(str.getBytes(), Base64.NO_WRAP));
                str = Uri.decode(str);
                str = str.replace('+', ' ');
            } catch (Exception e) {
            }
        }
        return str;
    }

    public static String MD5UTF8(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String MD5UnicodeOld(String string) {

        byte[] strByte;
        byte[] hash;
        try {

            strByte = string.getBytes("unicode");

            byte[] newByte = new byte[strByte.length - 2];

            for (int i = 2; i < strByte.length; i++) {
                newByte[i - 2] = strByte[i];
            }

            hash = MessageDigest.getInstance("MD5").digest(newByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }

    public static String MD5Unicode(String string) {

        byte[] strByte;
        byte[] hash;
        try {

            strByte = string.getBytes("unicode");

            byte[] newByte = new byte[strByte.length - 2];

            for (int i = 3; i < strByte.length; i++) {
                newByte[i - 3] = strByte[i];
            }
            newByte[newByte.length - 1] = strByte[2];

            hash = MessageDigest.getInstance("MD5").digest(newByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String genRandomNum(int len) {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < len) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 文件转base64字符串
     *
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }


    /*
     * bitmap转base64字符串
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String StringPick(String str) {
        Log.d(TAG, "StringPick: " + str);
        if (str.indexOf("<string") == 0) {
            int p = str.indexOf(">");
            if (p != -1) {
                str = str.substring(p + 1, str.length());
                p = str.indexOf("<");
                if (p != -1) {
                    str = str.substring(0, p);
                }
            }
        }

        str = defDecode(str);

        return str;
    }


    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long CalDateTimeStamp(Date _date) {
        long timeStamp = 0;

        //  Date startTime = StrToDate("1970-01-01 00:00:00"); // 当地时区
        timeStamp = _date.getTime();

        return timeStamp;
    }

    public static <T> T defDecodeEntity(T _ent) {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = _ent.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            String name = field[i].getName();
            // 将属性的首字母大写,这里不用大写了，因为都是小写
            // name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
            //       .toUpperCase());
            String type = field[i].getGenericType().toString();
            if (type.equals("class java.lang.String")) {
                try {
                    Method m = _ent.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(_ent);
                    if (value != null) {
                        value = defDecode(value);
                        Method m1 = _ent.getClass().getMethod("set" + name, String.class);
                        m1.invoke(_ent, value);
                    }
                } catch (Exception e) {

                }
            }
        }

        return _ent;
    }

    public static <T> List<T> defDecodeEntityList(List<T> _entList) {

        for (int i = 0; i < _entList.size(); i++) {
            T ent = _entList.get(i);
            _entList.set(i, defDecodeEntity(ent));
        }
        return _entList;
    }


    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    //以下是数据访问
    public static void httpPost(final String json, final int whatType
            , final Context pcontext, final DealWithListener pDealWithListener) {

        context = pcontext;
        //接口，回调数据使用，有问题，如果同时发起两个请求，先发起请求的回调对象就被后发起的覆盖了
        //数据最终回调不回去
        dealWithListener = pDealWithListener;

        //client对象，核心类
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(json);
            }
        };

        String httpurl = serverAddr + login + json.length();

        if (whatType == 1) {
            httpurl = serverAddr + login + json.length();    //登录
        } else if (whatType == 2) {
            httpurl = clientAddr + pageSelect + json.length();
        } else if (whatType == 3) {
            httpurl = clientAddr + Select + json.length();
        } else if (whatType == 4) {
            httpurl = clientWebAddr + Select + json.length();
        } else if (whatType == 5) {
            httpurl = clientFileAddr + Uploadfile + json.length();
        } else if (whatType == 6) {
            httpurl = clientWebAddr + DataListDelete + json.length();
        } else if (whatType == 7) {
            httpurl = clientWebAddr + DataInsert + json.length();
        } else if (whatType == 9) {
            httpurl = clientAddr + DataUpdate + json.length();
        } else if (whatType == 10) {
            httpurl = clientWebAddr + DataUpdate + json.length();
        } else if (whatType == 11) {
            httpurl = upgradeAddr + Select + json.length();
        } else if (whatType == 12) {
            httpurl = clientAddr + BLLJson + json.length();
        }
        else if (whatType == 13) {
            httpurl = clientAddr + Select + json.length();
        }
        //这里面添加参数
        Request requestBuilder = new Request.Builder().url(httpurl).post(requestBody).build();

        client.newCall(requestBuilder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //子线程中
                e.printStackTrace();
                if (e != null) {
                    Log.d(TAG, "onFailure e: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String str1 = response.body().string();
                Log.d(TAG, "response: " + str1);
                String str = StringPick(str1);
                handler.sendMessage(handler.obtainMessage(whatType, str1));
            }
        });
    }
    public static void httpPost4(final String json, final int whatType
            , final Context pcontext, final DealWithListener pDealWithListener) {

        context = pcontext;
        //接口，回调数据使用，有问题，如果同时发起两个请求，先发起请求的回调对象就被后发起的覆盖了
        //数据最终回调不回去
        dealWithListener = pDealWithListener;

        //client对象，核心类
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(json);
            }
        };

        String httpurl = serverAddr + login + json.length();

        if (whatType == 13) {
            httpurl = clientAddr + Select + json.length();
        }
        //这里面添加参数
        Request requestBuilder = new Request.Builder().url(httpurl).post(requestBody).build();

        client.newCall(requestBuilder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //子线程中
                e.printStackTrace();
                if (e != null) {
                    Log.d(TAG, "onFailure e: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String str1 = response.body().string();
                Log.d(TAG, "response: " + str1);
                String str = StringPick(str1);
                handler.sendMessage(handler.obtainMessage(whatType, str1));

                EntityInvoice entityInvoice = new EntityInvoice();
                entityInvoice.setpub_return(str1);
                entityInvoice.setold_model(json);
                handler.sendMessage(handler.obtainMessage(whatType, entityInvoice));
            }
        });
    }

    public static void httpPost1(final String json, final int whatType
            , final Context pcontext, final MylWithListener pDealWithListener) {

        context = pcontext;
        mylWithListener = pDealWithListener;

        //client对象，核心类
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(json);
            }
        };

        String httpurl = serverAddr + login + json.length();

        if (whatType == 1) {
            httpurl = serverAddr + login + json.length();
        } else if (whatType == 2) {
            httpurl = clientAddr + pageSelect + json.length();
        } else if (whatType == 3) {
            httpurl = clientAddr + Select + json.length();
        } else if (whatType == 4) {
            httpurl = clientWebAddr + Select + json.length();
        } else if (whatType == 5) {
            httpurl = clientFileAddr + Uploadfile + json.length();
        } else if (whatType == 6) {
            httpurl = clientWebAddr + DataListDelete + json.length();
        } else if (whatType == 7) {
            httpurl = clientWebAddr + DataInsert + json.length();
        } else if (whatType == 9) {
            httpurl = clientAddr + DataUpdate + json.length();
        } else if (whatType == 10) {
            httpurl = clientWebAddr + DataUpdate + json.length();
        } else if (whatType == 11) {
            httpurl = upgradeAddr + Select + json.length();
        } else if (whatType == 12) {
            httpurl = clientAddr + BLLJson + json.length();
        }

        //这里面添加参数
        Request requestBuilder = new Request.Builder().url(httpurl).post(requestBody).build();

        client.newCall(requestBuilder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //子线程中
                e.printStackTrace();
                if (e != null) {
                    Log.d(TAG, "onFailure e: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String str1 = response.body().string();
                Log.d(TAG, "response: " + str1);
                handler.sendMessage(handler.obtainMessage(whatType, str1));
            }
        });
    }

    public static void httpPost2(final String json, final int whatType
            , final ImageView tv_invoice_pic, final Context pcontext, final DealWithListener pDealWithListener) {
        context = pcontext;
        dealWithListener = pDealWithListener;

        //client对象，核心类
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(json);
            }
        };

        String httpurl = clientWebAddr + Select + json.length();

        //这里面添加参数
        Request requestBuilder = new Request.Builder().url(httpurl).post(requestBody).build();

        client.newCall(requestBuilder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //子线程中
                e.printStackTrace();
                if (e != null) {
                    Log.d(TAG, "onFailure e: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String str1 = response.body().string();
                Log.d(TAG, "response: " + str1);

                EntityInvoice entityInvoice = new EntityInvoice();
                entityInvoice.setpub_return(str1);
                entityInvoice.settv_invoice_pic(tv_invoice_pic);
                //entityInvoice.setEntityFile(entityFile);
                //String str = StringPick(str1);
                handler.sendMessage(handler.obtainMessage(whatType, entityInvoice));
            }
        });
    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Object model = (Object) msg.obj;
            Log.d(TAG, "msg: " + model.toString());
            try {
                if (msg.what == 1) {

                    String str = StringPick(model.toString());
                    Gson gson = new Gson();
                    PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
                    if (pubReturn.getState()) {
                        RandomCode = defDecode(pubReturn.getRandomCode());
                        OldRandomCode = defDecode(pubReturn.getOldRandomCode());
                        String dataStr = defDecode(pubReturn.getData());
                        ((LoginActivity) context).InitUserList(dataStr);

                    } else {
                        // ((LoginActivity) context).Show(defDecode(pubReturn.getMessage()));
                        Toast.makeText(context, defDecode(pubReturn.getMessage()), Toast.LENGTH_LONG).show();
                    }
                } else if (msg.what == 2 || msg.what == 3
                        || msg.what == 5
                        || msg.what == 6 || msg.what == 7
                        || msg.what == 9 || msg.what == 10
                        || msg.what == 11
                        ) {
                    String str = StringPick(model.toString());
                    Gson gson = new Gson();
                    PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
                    if (pubReturn.getState()) {
                        dealWithListener.ServerDataDealWith(pubReturn);//回调
                    } else {
                        String str11 = defDecode(pubReturn.getMessage());
                        if (str11.equals("请设置相关的参与人！")) {
                            dealWithListener.ServerDataDealWith(pubReturn);//回调
                        } else {
                            Toast.makeText(context, defDecode(pubReturn.getMessage()), Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (msg.what == 12
                        ) {

                    String str = StringPick(model.toString());
                    PubReturn pubReturn = new PubReturn();
                    pubReturn.setState(true);
                    pubReturn.setData(str);
                    mylWithListener.ServerDataDealWith(pubReturn);//回调
                } else if (msg.what == 8) {
                    EntityInvoice entityInvoice = (EntityInvoice) model;
                    String str = StringPick(entityInvoice.getpub_return());
                    Gson gson = new Gson();
                    PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
                    if (pubReturn.getState()) {
                        pubReturn.setobj(entityInvoice.gettv_invoice_pic());
                        pubReturn.setFile(entityInvoice.getEntityFile());
                        dealWithListener.ServerDataDealWith(pubReturn);//回调
                    } else {
                        Toast.makeText(context, defDecode(pubReturn.getMessage()), Toast.LENGTH_LONG).show();
                    }

                } else if (msg.what == 4) {
                    EntityInvoice entityInvoice = (EntityInvoice) model;
                    String str = StringPick(entityInvoice.getpub_return());
                    Gson gson = new Gson();
                    PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
                    if (pubReturn.getState()) {
                        pubReturn.setobj(entityInvoice.gettv_invoice_pic());
                        // pubReturn.setFile(entityInvoice.getEntityFile());
                        dealWithListener.ServerDataDealWith(pubReturn);//回调
                    } else {
                        Toast.makeText(context, defDecode(pubReturn.getMessage()), Toast.LENGTH_LONG).show();
                    }

                }
                else if (msg.what == 13) {
                    EntityInvoice entityInvoice = (EntityInvoice) model;
                    String str = StringPick(entityInvoice.getpub_return());
                    Gson gson = new Gson();
                    PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
                    if (pubReturn.getState()) {
                        pubReturn.setobj(entityInvoice.getold_model());
                        dealWithListener.ServerDataDealWith(pubReturn);//回调
                    } else {

                        String str11 = defDecode(pubReturn.getMessage());
                        if (str11.equals("请设置相关的参与人！")) {
                            dealWithListener.ServerDataDealWith(pubReturn);//回调
                        } else {
                            Toast.makeText(context, defDecode(pubReturn.getMessage()), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "error: " + e.getMessage());
            }
        }
    };
}
