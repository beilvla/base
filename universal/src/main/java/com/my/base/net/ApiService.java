package com.my.base.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

//接口里面的方法

public interface ApiService {

    String URL_LOGIN = "url_name:login";
    String URL_SAISHI = "url_name:saishi";
    String URL_BISAI = "url_name:bisai";
    String URL_SHOUYE = "url_name:shouye";
    String URL_SHOUYEZX = "url_name:shouye_zx";

    /**
     * Get基本请求,这里从Call改为Observable被观察者
     *
     * @param url
     * @return
     */
    @GET
    public Observable<String> get(@Url String url);

    /**
     * Post请求提交表单
     *
     * @param url
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST
    public Observable<String> post(@Url String url, @FieldMap Map<String, String> map);

    @POST
    public Observable<String> postjson(@Url String url, @Body String jsonObject);

    //登录
    @Headers(URL_LOGIN)
    @POST("api.php?")
    public Observable<String> login(@Query("action") String code, @Body RequestBody body);

    //赛事
    @Headers(URL_SAISHI)
    @POST("/data/match-api/get-match-list")
    public Observable<String> getSaishi(@Body RequestBody body);

    //赛事详情头部
    @Headers(URL_SAISHI)
    @POST("data/match-api/get-match-header")
    public Observable<String> getSaishiHead(@Body RequestBody body);

    //赛事详情身体
    @Headers(URL_SAISHI)
    @POST("/data/match-api/get-match-prospect")
    public Observable<String> getSaishiBody(@Body RequestBody body);

    //比赛
    @Headers(URL_BISAI)
    @GET("v1/match/app/query")
    public Observable<String> getBiSai(@QueryMap Map<String, String> map);

    //比赛详情
    @Headers(URL_BISAI)
    @GET("v2019/match/detail/mobile")
    public Observable<String> getBiSaiDetail(@QueryMap Map<String, String> map);

    //首页资讯
    @Headers(URL_SHOUYEZX)
    @GET("esports/v1/recommend/4/collection/mixture")
    public Observable<String> getSYZX(@QueryMap Map<String, String> map);

    //首页推荐
    @Headers(URL_SHOUYE)
    @GET("client/NewsList")
    public Observable<String> getSYTJ(@QueryMap Map<String, String> map);

    //数据战队
    @Headers(URL_SHOUYE)
    @GET("d2Client/odTeamRanking")
    public Observable<String> getSJZD(@QueryMap Map<String, String> map);

    //数据选手
    @Headers(URL_SHOUYE)
    @GET("d2Client/odPlayerRanking")
    public Observable<String> getSJXS(@QueryMap Map<String, String> map);

    //数据英雄
    @Headers(URL_SHOUYE)
    @GET("d2Client/odHeroRanking")
    public Observable<String> getSJYX(@QueryMap Map<String, String> map);

    //咨询详情
    @Headers(URL_SHOUYEZX)
    @GET("esports/v1/information/{id}/detail.json")
    public Observable<String> getZXXQ(@Path("id") String id);

    //战队详情
    @Headers(URL_SHOUYE)
    @GET("d2Client/odTeamInfo")
    public Observable<String> getZDXQ(@QueryMap Map<String, String> map);

    //选手详情
    @Headers(URL_SHOUYE)
    @GET("d2Client/odPlayerInfo")
    public Observable<String> getXSXQ(@QueryMap Map<String, String> map);

}
