package com.carnetwork.hansen.util;


import com.carnetwork.hansen.mvp.model.http.exception.ApiException;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Sun on 2017/8/24/0024.
 * email：1564063766@qq.com
 * remark:统一返回结果处理
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new FlowableTransformer<MyHttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<MyHttpResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<MyHttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(MyHttpResponse<T> tMyHttpResponse) {

                        if(tMyHttpResponse.isSuccess()) {
                            return createData(tMyHttpResponse.getModel());
                        } else {
                            return Flowable.error(new ApiException(tMyHttpResponse.getErrorMessage(), tMyHttpResponse.getErrorCode()));
                        }
                    }
                });
            }
        };
    }





    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }



    /**
     * 创建无返回对象的观察者
     *
     * @return
     */
    public static FlowableTransformer<MyHttpResponse, MyHttpResponse> handleMyEmptyResult() {   //compose判断结果
        return new FlowableTransformer<MyHttpResponse, MyHttpResponse>() {
            @Override
            public Flowable<MyHttpResponse> apply(Flowable<MyHttpResponse> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<MyHttpResponse, Flowable<MyHttpResponse>>() {
                    @Override
                    public Flowable<MyHttpResponse> apply(MyHttpResponse tMyHttpResponse) {
//                                                LogUtil.e("RxUtil.handleMyResult：：",tMyHttpResponse.toString());
                        if (tMyHttpResponse.isSuccess()) {
                            return createEmptyData(tMyHttpResponse);
                        } else {
                            return Flowable.error(new ApiException(tMyHttpResponse.getErrorMessage(), Integer.parseInt(tMyHttpResponse.getErrorCode())));
                        }
                    }
                });
            }
        };
    }




    /**
     * 生成Flowable
     *
     * @param t
     * @return
     */
    public static Flowable<MyHttpResponse> createEmptyData(final MyHttpResponse t) {
        return Flowable.create(new FlowableOnSubscribe<MyHttpResponse>() {
            @Override
            public void subscribe(FlowableEmitter<MyHttpResponse> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
