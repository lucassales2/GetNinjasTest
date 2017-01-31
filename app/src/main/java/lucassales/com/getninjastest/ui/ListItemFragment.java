package lucassales.com.getninjastest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.Utility;
import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.network.dto.LeadDto;
import lucassales.com.getninjastest.network.dto.OfferDto;
import lucassales.com.getninjastest.network.response.LeadsResponse;
import lucassales.com.getninjastest.network.response.OffersResponse;
import lucassales.com.getninjastest.ui.viewmodel.ListItemViewModel;

public class ListItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ListItemAdapter.OnItemClickListener {

    private static final String IS_LEADS = "isLeads";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public static ListItemFragment newInstance(boolean isLeads) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_LEADS, isLeads);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lead_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        Single<List<ListItemViewModel>> single;
        if (getArguments().getBoolean(IS_LEADS)) {
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ListItemViewModel>>() {
                    @Override
                    public void accept(List<ListItemViewModel> list) throws Exception {
                        ListItemAdapter adapter = new ListItemAdapter(list, ListItemFragment.this);
                        recyclerView.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onItemClick(String link) {
        startActivity(Utility.createIntentForLink(getContext(), link));
    }
}
