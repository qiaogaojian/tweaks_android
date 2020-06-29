package com.etatech.test.adapter;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etatech.test.R;
import com.etatech.test.databinding.ItemMultiAnimtionBinding;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import retrofit2.http.POST;

/**
 * Created by Michael
 * Date:  2020/6/28
 * Func:
 */
public class MultiAniAdapter extends RecyclerView.Adapter<MultiAniAdapter.ViewHolder> {
    private List<String> imgList;

    public MultiAniAdapter(List<String> list) {
        imgList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMultiAnimtionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_multi_animtion, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgUrl = imgList.get(position);
        loadGlideWebp(holder.itemMultiAnimtionBinding.ivAni, imgUrl);

        //        ImageRequest requestLocal = ImageRequestBuilder
        //                .newBuilderWithResourceId(R.drawable.frame)
        //                .setResizeOptions(new ResizeOptions(100, 100))
        //                .build();
        //
        //        ImageRequest request = ImageRequestBuilder
        //                .newBuilderWithSource(Uri.parse(imgUrl))
        //                .setResizeOptions(new ResizeOptions(100, 100))
        //                .build();
        //
        //        holder.itemMultiAnimtionBinding.ivAni.setController(
        //                Fresco.newDraweeControllerBuilder()
        //                        .setImageRequest(request)
        //                        .setAutoPlayAnimations(true)
        //                        .setOldController(holder.itemMultiAnimtionBinding.ivAni.getController())
        //                        .build()
        //        );
    }

    private void loadGlideWebp(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

    @Override
    public int getItemCount() {
        return imgList == null ? 0 : imgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMultiAnimtionBinding itemMultiAnimtionBinding;

        public ViewHolder(ItemMultiAnimtionBinding itemView) {
            super(itemView.getRoot());
            itemMultiAnimtionBinding = itemView;
        }
    }
}
