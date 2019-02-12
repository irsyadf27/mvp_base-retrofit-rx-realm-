package kitmybit.mvp_base_example.utils;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

public class UtilsUI {
    public static KProgressHUD getKProgressHUD(Activity activity) {
        return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

}
