package com.etatech.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etatech.test.R;

import java.util.List;

/**
 * Created by Michael
 * Date:  2019/12/30
 * Func:
 */
public class StateLogAdapter extends RecyclerView.Adapter<StateLogAdapter.ViewHolder>
{
    private List<String> logArrList;

    public StateLogAdapter(List<String> list)
    {
        this.logArrList = list;
    }

    public void setLogArrList(List<String> logArrList)
    {
        this.logArrList = logArrList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.tvLog.setText(String.format("%3d : %s",position + 1,logArrList.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return logArrList == null ? 0 : logArrList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvLog;

        public ViewHolder(View itemView)
        {
            super(itemView);
            tvLog = itemView.findViewById(R.id.tv_surfaceview_state);
        }
    }
}
