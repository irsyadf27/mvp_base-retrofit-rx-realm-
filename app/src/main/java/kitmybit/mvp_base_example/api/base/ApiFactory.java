package kitmybit.mvp_base_example.api.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kitmybit.mvp_base_example.api.ApiService;
import kitmybit.mvp_base_example.utils.LogInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    //https://api.github.com/users/hadley/orgs
    private static final String BASE_URL = "https://api.github.com/";

    private static OkHttpClient sClient;
    private static volatile ApiService apiService;
    private static Gson sGson;

    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sGson = getsGson();
        apiService = buildRetrofit().create(ApiService.class);
    }

    public static ApiService getApiService() {
        ApiService service = apiService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = apiService;
                if (service == null) {
                    service = apiService = buildRetrofit().create(ApiService.class);
                }
            }
        }
        return service;
    }

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(getsGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static Gson getsGson() {
        Gson gson = sGson;
        if(gson == null) {
            synchronized (ApiFactory.class) {
                gson = sGson;
                if(gson == null) {
                    gson = sGson = buldGson();
                }
            }
        }
        return gson;
    }

    @NonNull
    private static Gson buldGson() {
        return new GsonBuilder()
                .create();
    }


    @NonNull
    private static OkHttpClient buildClient() {
        LogInterceptor logging = new LogInterceptor();

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }
}
