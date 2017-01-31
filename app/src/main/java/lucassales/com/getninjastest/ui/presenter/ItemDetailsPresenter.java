package lucassales.com.getninjastest.ui.presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.network.dto.InfoDto;
import lucassales.com.getninjastest.network.response.DetailsResponse;
import lucassales.com.getninjastest.ui.viewmodel.ItemDetailsViewModel;

/**
 * Created by lucas on 31/01/17.
 */

public class ItemDetailsPresenter extends Presenter<ItemDetailsPresenterListener> {

    private final String path;
    private final boolean isLead;

    public ItemDetailsPresenter(ItemDetailsPresenterListener itemDetailsPresenterListener, String path, boolean isLead) {
        super(itemDetailsPresenterListener);
        this.path = path;
        this.isLead = isLead;
    }

    @Override
    protected void init() {
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
                        double latitude = detailsResponse.get_embedded().getAddress().getGeolocation().getLatitude();
                        double longitude = detailsResponse.get_embedded().getAddress().getGeolocation().getLongitude();
                        if (isLead) {
                            return new ItemDetailsViewModel(address, distance, email, infoDtos, true, phone, title, user, latitude, longitude);
                        } else {
                            String linkAccept = detailsResponse.get_links().get("accept").getHref();
                            String linkReject = detailsResponse.get_links().get("reject").getHref();
                            return new ItemDetailsViewModel(address, distance, email, infoDtos, false, phone, title, user, latitude, longitude, linkAccept, linkReject);
                        }
                    }
                })
                .filter(new Predicate<ItemDetailsViewModel>() {
                    @Override
                    public boolean test(ItemDetailsViewModel viewModel) throws Exception {
                        return isRunning() && getListener() != null;
                    }
                })
                .subscribe(
                        new Consumer<ItemDetailsViewModel>() {
                            @Override
                            public void accept(ItemDetailsViewModel itemDetailsViewModel) throws Exception {
                                getListener().onItemLoaded(itemDetailsViewModel);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getListener().onError(throwable);
                            }
                        }
                );
    }
}
