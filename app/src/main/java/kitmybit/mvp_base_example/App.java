package kitmybit.mvp_base_example;

import android.app.Application;

import kitmybit.mvp_base_example.api.base.ApiFactory;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        recreateRetrofit();
    }

    public static void recreateRetrofit() {
        ApiFactory.recreate();
    }
}