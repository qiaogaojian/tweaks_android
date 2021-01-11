package com.etatech.test.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.etatech.test.R;
import com.etatech.test.databinding.ItemUserInfoBinding;

import java.util.List;

/**
 * Created by Michael
 * Date:  2020/2/6
 * Func:
 */
public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {
    private List<String> userInfoList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserInfoBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user_info, parent, false);
        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dataBinding.tvContent.setText(userInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return userInfoList == null || userInfoList.size() == 0 ? 0 : userInfoList.size();
    }

    public void setUserInfoList(List<String> userInfoList) {
        this.userInfoList = userInfoList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserInfoBinding dataBinding;

        public ViewHolder(ItemUserInfoBinding binding) {
            super(binding.getRoot());
            dataBinding = binding;
        }
    }
}
