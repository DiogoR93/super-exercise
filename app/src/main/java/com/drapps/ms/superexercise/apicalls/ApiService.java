package com.drapps.ms.superexercise.apicalls;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Diogo on 16/10/2017.
 */

public interface ApiService {

    String mUrl = "https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/";

    @GET("https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/codigos_postais.csv")
    Observable<ResponseBody> getString();
;
    class Factory {
        private static Retrofit retrofit;
        public static ApiService create() {

            try {
                //Retrofit initialization, for REST api calls
                retrofit = new Retrofit.Builder()
                        .baseUrl(mUrl)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

                return retrofit.create(ApiService.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public static Retrofit getRetrofit(){
            return Factory.retrofit;
        }
    }
}
