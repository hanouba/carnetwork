package com.carnetwork.hansen.di.module;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.di.qualifier.MyUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.carnetwork.hansen.BuildConfig;

import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.carnetwork.hansen.mvp.model.http.manege.RetrofitUrlManager;
import com.carnetwork.hansen.util.SystemUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * remark:
 */
@Module
public class HttpModule {



    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @MyUrl
    Retrofit provideMyRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, MyApis.HOST);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {


        if(BuildConfig.DEBUG){
            //显示日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }else {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            builder.addInterceptor(loggingInterceptor);
        }
        LogUtils.d("请求拦截器-provideClient"+SystemUtil.isNetworkConnected());
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                LogUtils.d("请求拦截器-cacheInterceptor"+SystemUtil.isNetworkConnected());
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    String token = SPUtils.getInstance().getString(Constants.TOKEN);
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .header("X-APP-TOKEN",token)
                            .build();
                }
                Response response = chain.proceed(request);

                MediaType mediaType = response.body().contentType();
                String content= response.body().string();
                try {
                    JSONObject object = new JSONObject(content);

                    String msg = object.optString("msg");
                    if ("未匹配到项目".equals(msg)) {

                    }else {
                        if ("401".equals(object.optString("errorCode")) || "403".equals(object.optString("errorCode"))) {
//                            MyApplication.getAppComponent().getDataManager().setAutoLoginState(false);
//                            RxBus.getDefault().post(new CommonEvent(EventCode.ERROR_RELOGIN));

                        }else if ("500".equals(object.optString("errorCode"))) {
                            LogUtils.d("请求拦截器重新登录"+object.toString());
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (SystemUtil.isNetworkConnected()) {
                    String token = SPUtils.getInstance().getString(Constants.TOKEN);
                    LogUtils.d("请求头参数+token"+token);
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .header("X-APP-TOKEN",token)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build();
            }
        };

        Interceptor apikey = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = SPUtils.getInstance().getString(Constants.TOKEN);
                LogUtils.d("请求头参数222+token"+token);
                Request request = chain.request();
                request = request.newBuilder()
                        .header("X-APP-TOKEN",token)
                        .build();
                return chain.proceed(request);
            }
        };

        //设置统一的请求头部参数
        builder.addNetworkInterceptor(apikey);

//         添加公共参数拦截器


        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);

        OkHttpClient mHttpClient = RetrofitUrlManager.getInstance().with(builder) //RetrofitUrlManager 初始化
                .build();
//        return builder.build();
        return mHttpClient;
    }


    @Singleton
    @Provides
    MyApis provideMyService(@MyUrl Retrofit retrofit) {
        return retrofit.create(MyApis.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return  builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
