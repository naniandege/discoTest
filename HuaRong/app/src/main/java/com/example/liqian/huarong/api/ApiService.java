package com.example.liqian.huarong.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    String httpAddr = "http://218.246.23.195/NewUpload/";
    String clientAddr = "http://218.246.23.195:1889/";
    String serverAddr = "http://218.246.23.195:1890/";
    String clientWebAddr = "http://218.246.23.195:1888/";
    String clientFileAddr = "http://218.246.23.195:1891/";
    String upgradeAddr = "http://218.246.23.195:1890/";
    String upgradehttpAddr = "http://218.246.23.195/NewUpload/Upgrade/";


    String Login = "LoginVerifyByStream/";
    String PageSelect = "DataPageSelectByStream/";
    String Select = "DataSelectByStream/";
    String Uploadfile = "UploadFileByStream/";
    String DataListDelete = "DataListDeleteByStream/";
    String DataInsert = "DataInsertByStream/";
    String DataUpsert = "DataUpsertByStream/";
    String DataUpdate = "DataUpdateByStream/";
    String BLLJson = "BLLJsonByStream/";


    /**
     * 登录接口
     *
     * @param jsonLength 数据的长度
     * @param body       所有的数据封装成了一个json
     * @return
     */
    @POST("LoginVerifyByStream/{jsonLength}")
    Observable<String> login(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /**
     * 分页查询接口
     *
     * @param jsonLength
     * @param body
     * @return
     */
    @POST("DataPageSelectByStream/{jsonLength}")
    Observable<String> pageSelect(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /**
     * 查询 详情
     *
     * @param jsonLength
     * @param body
     * @return
     */
    @POST("DataSelectByStream/{jsonLength}")
    Observable<String> select(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    @POST("WFTaskSms/{jsonLength}")
    Observable<String> wfTask(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    @POST("GetServerTime/{jsonLength}")
    Observable<String> getTime(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /**
     * 数据更新
     *
     * @param jsonLength
     * @param body
     * @return
     */
    @POST("DataUpdateByStream/{jsonLength}")
    Observable<String> upDate(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /**
     * 数据插入
     *
     * @param jsonLength
     * @param body
     * @return
     */
    @POST("DataInsertByStream/{jsonLength}")
    Observable<String> insertData(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /**
     * 生成json
     *
     * @param jsonLength
     * @param body
     * @return
     */
    @POST("BLLJsonByStream/{jsonLength}")
    Observable<String> bllJson(@Path("jsonLength") int jsonLength, @Body RequestBody body);


    /*
     *  生成oaCode
     * */
    @POST("DataUpsertByStream/{jsonLength}")
    Observable<String> upsert(@Path("jsonLength") int jsonLength, @Body RequestBody body);




    @POST("UploadFileByStream/{jsonLength}")
    Observable<String> upLoadFile(@Path("jsonLength") int jsonLength, @Body RequestBody body);
}
