package lucassales.com.getninjastest.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import lucassales.com.getninjastest.BR;
import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.ui.viewmodel.ListItemViewModel;

/**
 * Created by lucas on 30/01/17.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<ListItemViewModel> list;
    private WeakReference<OnItemClickListener> listener;

    public ListItemAdapter(List<ListItemViewModel> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.lead_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemViewModel value = list.get(position);
        holder.viewDataBinding.setVariable(BR.item, value);
        holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && listener.get() != null) {
                    listener.get().onItemClick(value.getLink());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String link);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding viewDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
