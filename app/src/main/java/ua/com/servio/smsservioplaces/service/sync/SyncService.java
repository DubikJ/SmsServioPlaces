package ua.com.servio.smsservioplaces.service.sync;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ua.com.servio.smsservioplaces.model.json.DownloadResponse;

import static ua.com.servio.smsservioplaces.common.Consts.CONNECT_PATTERN_URL;

public interface SyncService {

//    @GET(CONNECT_PATTERN_URL)
//    Call<DownloadResponse> getPlaces();

    @GET(CONNECT_PATTERN_URL)
    Observable<DownloadResponse> getPlaces();


}
