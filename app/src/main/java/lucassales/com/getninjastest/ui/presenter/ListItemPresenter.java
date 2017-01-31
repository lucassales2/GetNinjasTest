package lucassales.com.getninjastest.ui.presenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.network.dto.LeadDto;
import lucassales.com.getninjastest.network.dto.OfferDto;
import lucassales.com.getninjastest.network.response.LeadsResponse;
import lucassales.com.getninjastest.network.response.OffersResponse;
import lucassales.com.getninjastest.ui.viewmodel.ListItemViewModel;

/**
 * Created by lucas on 31/01/17.
 */

public class ListItemPresenter extends Presenter<ListItemPresenterListener> {
    private final boolean isLead;

    public ListItemPresenter(ListItemPresenterListener listItemPresenterListener, boolean isLead) {
        super(listItemPresenterListener);
        this.isLead = isLead;
    }

    @Override
    protected void init() {
        refresh();
    }

    public void refresh() {
        Single<List<ListItemViewModel>> single;
        if (isLead) {
            single = ServiceGenerator.getGetNinjasApiServiceInstance()
                    .getLeadsResponse()
                    .map(new Function<LeadsResponse, List<ListItemViewModel>>() {
                        @Override
                        public List<ListItemViewModel> apply(LeadsResponse leadsResponse) throws Exception {
                            List<ListItemViewModel> list = new ArrayList<>();
                            for (LeadDto leadDto : leadsResponse.getLeads()) {
                                String address = leadDto.get_embedded().getAddress().getNeighborhood() + " - " + leadDto.get_embedded().getAddress().getCity();
                                String date = new SimpleDateFormat("dd/MMM", Locale.getDefault()).format(leadDto.getCreated_at());
                                String title = leadDto.get_embedded().getTitleRequest();
                                String user = leadDto.get_embedded().getUser().getName();
                                String link = leadDto.get_links().get("self").getHref();
                                list.add(new ListItemViewModel(address, ListItemViewModel.State.Accepted, date, title, user, link));
                            }

                            return list;
                        }
                    });
        } else {
            single = ServiceGenerator.getGetNinjasApiServiceInstance()
                    .getOffersResponse()
                    .map(new Function<OffersResponse, List<ListItemViewModel>>() {
                        @Override
                        public List<ListItemViewModel> apply(OffersResponse offersResponse) throws Exception {
                            List<ListItemViewModel> list = new ArrayList<>();
                            for (OfferDto offerDto : offersResponse.getOffers()) {
                                String address = offerDto.get_embedded().getRequest().get_embedded().getAddress().getNeighborhood() + " - " + offerDto.get_embedded().getRequest().get_embedded().getAddress().getCity();
                                String date = new SimpleDateFormat("dd/MMM", Locale.getDefault()).format(offerDto.get_embedded().getRequest().getCreated_at());
                                String title = offerDto.get_embedded().getRequest().getTitle();
                                String user = offerDto.get_embedded().getRequest().get_embedded().getUser().getName();
                                ListItemViewModel.State state;
                                if (offerDto.getState().equals("read")) {
                                    state = ListItemViewModel.State.Read;
                                } else {
                                    state = ListItemViewModel.State.Unread;
                                }
                                String link = offerDto.get_links().get("self").getHref();
                                list.add(new ListItemViewModel(address, state, date, title, user, link));
                            }

                            return list;
                        }
                    });


        }
        single
                .filter(new Predicate<List<ListItemViewModel>>() {
                    @Override
                    public boolean test(List<ListItemViewModel> list) throws Exception {
                        return getListener() != null && isRunning();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ListItemViewModel>>() {
                            @Override
                            public void accept(List<ListItemViewModel> list) throws Exception {
                                getListener().onViewModelListLoaded(list);
                            }
                        }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getListener().onError(throwable);
                            }
                        }
                );
    }
}
