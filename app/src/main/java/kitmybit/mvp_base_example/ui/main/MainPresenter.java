package kitmybit.mvp_base_example.ui.main;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kitmybit.mvp_base_example.api.base.ApiFactory;
import kitmybit.mvp_base_example.ui.BaseView;

public class MainPresenter implements MainContract.Presenter{

    private static final String TAG = "MainPresenter";

    private MainContract.View mView;
    private MainContract.Repository mRepository;
    private BaseView baseView;

    private Disposable allLog = null;

    public MainPresenter(MainContract.View mView, BaseView baseView) {
        this.mView = mView;
        this.mRepository = new MainRepository();
        this.baseView = baseView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void onDestroy() {
        // clear all
        if(allLog != null) {
            allLog.dispose();
        }

    }

    @Override
    public void actionGetLogs() {
        if(baseView.isOnline()) {
            allLog = ApiFactory.
                    getApiService().
                    getLogs()
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> baseView.showLoading())
                    .doOnTerminate(() -> baseView.hideLoading())
                    .subscribe(logsResponses -> {
                        mView.setLog(logsResponses);
                    }, throwable -> {

                    });
        }

    }
}
