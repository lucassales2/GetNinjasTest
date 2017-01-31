package lucassales.com.getninjastest.ui.presenter;

import lucassales.com.getninjastest.ui.viewmodel.ItemDetailsViewModel;

/**
 * Created by lucas on 31/01/17.
 */

public interface ItemDetailsPresenterListener {

    void onItemLoaded(ItemDetailsViewModel viewModel);

    void onError(Throwable t);
}
