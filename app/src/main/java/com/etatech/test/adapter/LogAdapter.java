package com.etatech.test.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etatech.test.R;
import com.etatech.test.utils.App;

import java.util.List;

/**
 * Created by Michael
 * Date:  2020/7/13
 * Func:
 */

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private List<Spanned> logArrList;

    public LogAdapter(List<Spanned> list) {
        this.logArrList = list;
    }

    public void setLogArrList(List<Spanned> logArrList) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLog.setText(logArrList.get(position),TextView.BufferType.SPANNABLE);
        holder.tvLog.setTypeface(App.getInstance().getTypeface());
    }

    @Override
    public int getItemCount() {
        return logArrList == null ? 0 : logArrList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLog;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLog = itemView.findViewById(R.id.tv_surfaceview_state);
        }
    }
}
