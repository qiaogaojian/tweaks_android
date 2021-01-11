package com.etatech.test.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.etatech.test.R;
import com.etatech.test.bean.PhoneAreaBean;
import com.etatech.test.databinding.ItemCountryCodeBinding;
import com.etatech.test.utils.App;

import java.util.List;

/**
 * Created by Michael
 * Data: 2020/1/8 14:01
 * Desc: 国家地区编号适配器
 */
public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.ViewHolder> {
    private List<PhoneAreaBean.DataBean> dataBeanList;

    public CountryCodeAdapter(List<PhoneAreaBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCountryCodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_country_code, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhoneAreaBean.DataBean bean = dataBeanList.get(position);
        holder.itemCountryCodeBinding.tvCountry.setText(bean.getChineseName());
        holder.itemCountryCodeBinding.tvCode.setText("+" + bean.getCode());
        if (position % 2 == 0) {
            holder.itemCountryCodeBinding.tvCountry.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorPrimary));
            holder.itemCountryCodeBinding.tvCode.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorAccent));
        } else {
            holder.itemCountryCodeBinding.tvCountry.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorPrimary));
            holder.itemCountryCodeBinding.tvCode.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemCountryCodeBinding itemCountryCodeBinding;

        public ViewHolder(ItemCountryCodeBinding itemView) {
            super(itemView.getRoot());
            itemCountryCodeBinding = itemView;
        }
    }
}
