package com.my.base.net;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitAndOkhttpAndRxAndriodUtil {

    public static String BaseUrl_Login = "http://api.ybyfx.top/";
    public static String BaseUrl_SaiShi = "http://api.saishikong.com/";
    public static String BaseUrl_BiSai = "http://prod2-app.api.dianjingquan.cn/";
    public static String BaseUrl_ShouYeZx = "http://apis.zhanqi.com/";
    public static String BaseUrl_ShouYe = "http://open.taiesport.com/";

    private static RetrofitAndOkhttpAndRxAndriodUtil requestClient;

    private Retrofit mRetrofit;

    private ApiService apiService;

    private RetrofitAndOkhttpAndRxAndriodUtil() {
        //使全局就一个OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request()
                                .newBuilder()
//                            .addHeader("Content-Type","application/x-www-form-urlencoded")
//                            .addHeader("Accept","application/json")
                                .build();

                        return chain.proceed(request);
                    }

                })
//            .cookieJar(new CookiesManager())
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                //日志拦截器
                .build();


        //使全局就一个Retrofit对象,设置基础Url
        apiService = new Retrofit.Builder()
                //使我们能高度自定义转化器
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))

                //把 以前的 call 转化成 Observable,这是Retrofit与RxJava结合使用的关键
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BaseUrl_Login)
                .build().create(ApiService.class);
    }

    public static RetrofitAndOkhttpAndRxAndriodUtil getInstance() {
        if (null == requestClient) {
            requestClient = new RetrofitAndOkhttpAndRxAndriodUtil();
        }
        return requestClient;
    }


    public Observable<String> get(String url) {

        return apiService.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<String> post(String url, Map<String, String> map) {
        return apiService.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Observable<String> postjson(String url, String jsonObject) {
        return apiService.postjson(url, jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<String> login(String code, RequestBody body) {
        return apiService.login(code, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSaishi(RequestBody body) {
        return apiService.getSaishi(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSaishiHead(RequestBody body) {
        return apiService.getSaishiHead(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSaishiBody(RequestBody body) {
        return apiService.getSaishiBody(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getBiSai(Map<String, String> map) {
        return apiService.getBiSai(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getBiSaiDetail(Map<String, String> map) {
        return apiService.getBiSaiDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSYZX(Map<String, String> map) {
        return apiService.getSYZX(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSYTJ(Map<String, String> map) {
        return apiService.getSYTJ(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSJZD(Map<String, String> map) {
        return apiService.getSJZD(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSJXS(Map<String, String> map) {
        return apiService.getSJXS(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getSJYX(Map<String, String> map) {
        return apiService.getSJYX(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getZXXQ(String id) {
        return apiService.getZXXQ(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getZDXQ(Map<String, String> map) {
        return apiService.getZDXQ(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getXSXQ(Map<String, String> map) {
        return apiService.getXSXQ(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}