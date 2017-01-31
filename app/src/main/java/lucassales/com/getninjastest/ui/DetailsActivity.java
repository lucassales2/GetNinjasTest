package lucassales.com.getninjastest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.network.dto.InfoDto;
import lucassales.com.getninjastest.network.response.DetailsResponse;
import lucassales.com.getninjastest.ui.viewmodel.ItemDetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    public static final String PATH = "path";
    public static final String IS_LEAD = "isLead";
    private boolean isLead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isLead = getIntent().getBooleanExtra(IS_LEAD, false);
        startRequest(getIntent().getStringExtra(PATH));
    }

    private void startRequest(String path) {

        ServiceGenerator.getGetNinjasApiServiceInstance().getDetailsResponse(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<DetailsResponse, ItemDetailsViewModel>() {
                    @Override
                    public ItemDetailsViewModel apply(DetailsResponse detailsResponse) throws Exception {
                        String address = detailsResponse.get_embedded().getAddress().getNeighborhood() + " - " + detailsResponse.get_embedded().getAddress().getCity();
                        long distance = detailsResponse.getDistance();
                        String email = detailsResponse.get_embedded().getUser().getEmail();
                        List<InfoDto> infoDtos = detailsResponse.get_embedded().getInfo();
                        String phone = detailsResponse.get_embedded().getUser().get_embedded().getPhones().get(0).getNumber();
                        String title = detailsResponse.getTitle();
                        String user = detailsResponse.get_embedded().getUser().getName();
                        if (isLead) {
                            return new ItemDetailsViewModel(address, distance, email, infoDtos, true, phone, title, user);
                        } else {
                            String linkAccept = detailsResponse.get_links().get("accept").getHref();
                            String linkReject = detailsResponse.get_links().get("reject").getHref();
                            return new ItemDetailsViewModel(address, distance, email, infoDtos, false, phone, title, user, linkAccept, linkReject);
                        }
                    }
                })
                .subscribe(new Consumer<ItemDetailsViewModel>() {
                    @Override
                    public void accept(ItemDetailsViewModel itemDetailsViewModel) throws Exception {
                        //test
                    }
                });

    }

}
