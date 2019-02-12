package kitmybit.mvp_base_example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;

import kitmybit.mvp_base_example.utils.UtilsNetwork;
import kitmybit.mvp_base_example.utils.UtilsUI;


public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private final String TAG = "BaseActivity";
    private KProgressHUD hud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hud = UtilsUI.getKProgressHUD(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "base onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "base onPause");
    }

    @Override
    public void showLoading() {
        try {
            if (hud != null) {
                if (!hud.isShowing()) {
                    hud.show();
                }
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void hideLoading() {
        try {
            if (hud != null) {
                if (hud.isShowing()) {
                    hud.dismiss();
                }
            }
        } catch (Exception ignored) { }
    }

    @Override
    public boolean isOnline() {
        return UtilsNetwork.isNetwork(this);
    }
}
