package ua.com.servio.smsservioplaces.service.sync;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ua.com.servio.smsservioplaces.model.json.DownloadResponse;

import static ua.com.servio.smsservioplaces.common.Consts.CONNECT_PATTERN_URL;

public interface SyncService {

    @GET(CONNECT_PATTERN_URL)
    Call<DownloadResponse> search(@QueryMap Map<String, String> params);

//    @GET
//    Call<OwnerDTO> getOwner(@Url String url);

}
