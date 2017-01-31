package lucassales.com.getninjastest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import lucassales.com.getninjastest.network.GetNinjasApiService;
import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.network.response.LeadsResponse;

/**
 * Created by lucas on 30/01/17.
 */

@RunWith(AndroidJUnit4.class)
public class GetNinjasApiServiceTest {

    private static final GetNinjasApiService apiService = ServiceGenerator.getGetNinjasApiServiceInstance();

    @Test
    public void getLeadsTest() {

        TestObserver<LeadsResponse> testObserver =
                apiService
                        .getLeadsResponse()
                        .doOnSuccess(new Consumer<LeadsResponse>() {
                            @Override
                            public void accept(LeadsResponse leadsResponse) throws Exception {

                            }
                        })
                        .test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();


    }

//    @Test
//    public void getOffersTest() {
//        TestObserver<OffersResponse> testObserver =
//                apiService
//                        .getOffersResponse()
//                        .doOnSuccess(new Consumer<OffersResponse>() {
//                            @Override
//                            public void accept(OffersResponse offersResponse) throws Exception {
//
//                            }
//                        })
//                        .test();
//        testObserver.assertNoErrors();
//        testObserver.assertComplete();
//    }

//    @Test
//    public void getLeadDetailsTest() {
//        TestObserver<DetailsResponse> testObserver = apiService
//                .getDetailsResponse("offer-1")
//                .doOnSuccess(new Consumer<DetailsResponse>() {
//                    @Override
//                    public void accept(DetailsResponse leadDetailsResponse) throws Exception {
//
//                    }
//                })
//                .test();
//
//        testObserver.assertNoErrors();
//        testObserver.assertComplete();
//
//    }


}
