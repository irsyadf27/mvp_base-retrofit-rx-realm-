package kitmybit.mvp_base_example.ui.main;

import java.util.List;

import kitmybit.mvp_base_example.model.api.LogsResponse;

public interface MainContract {

    interface View {
        void setLog(List<LogsResponse> logsResponseList);
    }

    interface Presenter {
        void onDestroy();
        void actionGetLogs();
    }

    interface Repository {
        String getLogs();
    }

}
