package kitmybit.mvp_base_example.ui;

import io.reactivex.disposables.Disposable;

public interface BaseView {

    void showLoading();
    void hideLoading();
    boolean isOnline();

}
