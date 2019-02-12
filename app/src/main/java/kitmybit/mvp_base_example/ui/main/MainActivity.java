package kitmybit.mvp_base_example.ui.main;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kitmybit.mvp_base_example.model.api.LogsResponse;
import kitmybit.mvp_base_example.ui.BaseActivity;
import kitmybit.mvp_base_example.R;

public class MainActivity extends BaseActivity implements MainContract.View{

    private final static String TAG = "MainActivity";
    private MainPresenter mainPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this, this);
    }


    @OnClick(R.id.button)
    public void actionGetLogs() {
        mainPresenter.actionGetLogs();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void setLog(List<LogsResponse> logsResponseList) {
        Log.e(TAG, String.valueOf(logsResponseList.size()));
    }
}
