package lucassales.com.getninjastest.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import lucassales.com.getninjastest.network.dto.InfoDto;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucas on 19/12/16.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://testemobile.getninjas.com.br/";
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .registerTypeAdapter(InfoDto.class, new InfoDto.InfoValuesDeserilizer())
            .create();

    private static GetNinjasApiService ninjasApiService;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static GetNinjasApiService getGetNinjasApiServiceInstance() {
        if (ninjasApiService == null) {
            ninjasApiService = createService(GetNinjasApiService.class);
        }
        return ninjasApiService;
    }


}
