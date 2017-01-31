package lucassales.com.getninjastest.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import lucassales.com.getninjastest.BR;
import lucassales.com.getninjastest.R;
import lucassales.com.getninjastest.network.dto.InfoDto;

/**
 * Created by lucas on 31/01/17.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private List<InfoDto> infoDtoList;
    private final boolean isLead;

    public InfoAdapter(List<InfoDto> infoDtoList, boolean isLead) {
        this.infoDtoList = infoDtoList;
        this.isLead = isLead;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.info_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.isLead, isLead);
        holder.viewDataBinding.setVariable(BR.info, infoDtoList.get(position));
    }

    @Override
    public int getItemCount() {
        return infoDtoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding viewDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
