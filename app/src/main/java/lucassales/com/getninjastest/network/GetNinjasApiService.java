package lucassales.com.getninjastest.network;


import io.reactivex.Single;
import lucassales.com.getninjastest.network.response.DetailsResponse;
import lucassales.com.getninjastest.network.response.LeadsResponse;
import lucassales.com.getninjastest.network.response.OffersResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lucas on 30/01/17.
 */

public interface GetNinjasApiService {


    @GET("leads")
    Single<LeadsResponse> getLeadsResponse();

    @GET("offers")
    Single<OffersResponse> getOffersResponse();

    @GET("{path}")
    Single<DetailsResponse> getDetailsResponse(@Path("path") String path);

}
