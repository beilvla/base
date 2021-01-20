package com.my.base.net;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        // 从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        // 获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        // 从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers("url_name");
        if (headerValues != null && headerValues.size() > 0) {
            // 如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("url_name");
            // 匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            if ("login".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(RetrofitAndOkhttpAndRxAndriodUtil.BaseUrl_Login);
            } else if ("saishi".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(RetrofitAndOkhttpAndRxAndriodUtil.BaseUrl_SaiShi);
            } else if ("bisai".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(RetrofitAndOkhttpAndRxAndriodUtil.BaseUrl_BiSai);
            }else if ("shouye".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(RetrofitAndOkhttpAndRxAndriodUtil.BaseUrl_ShouYe);
            }else if ("shouye_zx".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(RetrofitAndOkhttpAndRxAndriodUtil.BaseUrl_ShouYeZx);
            } else {
                newBaseUrl = oldHttpUrl;
            }
            // 重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    // 更换网络协议
                    .scheme(newBaseUrl.scheme())
                    // 更换主机名
                    .host(newBaseUrl.host())
                    // 更换端口
                    .port(newBaseUrl.port())
                    .build();
            // 重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.d("CSDN_LQR", String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                            request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                Log.d("CSDN_LQR", String.format("发送请求 %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }

            Response response = chain.proceed(builder.url(newFullUrl).build());
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d("CSDN_LQR",
                    String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d,
                            response.headers()
                    ));
            return response;
        }

        return null;

    }
}


