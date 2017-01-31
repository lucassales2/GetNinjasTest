package lucassales.com.getninjastest.ui.presenter;

import java.util.List;

import lucassales.com.getninjastest.ui.viewmodel.ListItemViewModel;

/**
 * Created by lucas on 31/01/17.
 */

public interface ListItemPresenterListener {
    void onViewModelListLoaded(List<ListItemViewModel> list);

    void onError(Throwable throwable);
}
