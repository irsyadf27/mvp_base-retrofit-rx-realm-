package kitmybit.mvp_base_example.api;

import java.util.List;

import io.reactivex.Observable;
import kitmybit.mvp_base_example.model.api.LogsResponse;
import retrofit2.http.GET;

public interface ApiService {

    @GET("users/hadley/orgs")
    Observable<List<LogsResponse>> getLogs();

}
