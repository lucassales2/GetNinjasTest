package lucassales.com.getninjastest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.List;

import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.Utility;
import lucassales.com.getninjastest.ui.presenter.ListItemPresenter;
import lucassales.com.getninjastest.ui.presenter.ListItemPresenterListener;
import lucassales.com.getninjastest.ui.viewmodel.ListItemViewModel;

public class ListItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ListItemAdapter.OnItemClickListener, ListItemPresenterListener {

    private static final String IS_LEADS = "isLeads";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ListItemPresenter presenter;

    public static ListItemFragment newInstance(boolean isLeads) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_LEADS, isLeads);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListItemPresenter(this, getArguments().getBoolean(IS_LEADS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lead_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }


    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void onItemClick(String link) {
        startActivity(Utility.createIntentForLink(getContext(), link));
    }

    @Override
    public void onViewModelListLoaded(List<ListItemViewModel> list) {
        ListItemAdapter adapter = new ListItemAdapter(list, ListItemFragment.this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
        }
        refreshLayout.setRefreshing(false);
    }
}
