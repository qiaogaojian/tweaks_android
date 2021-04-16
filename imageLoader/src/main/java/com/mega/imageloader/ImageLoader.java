//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mega.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpFrameCacheStrategy;
import com.bumptech.glide.integration.webp.decoder.WebpFrameLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mega.imageloader.config.ImageLoaderConfig;
import com.mega.imageloader.config.ImageLoaderConfig.Builder;
import com.mega.imageloader.config.ImageLoaderConfig.DiskCache;
import com.mega.imageloader.config.ImageLoaderConfig.LoadPriority;
import com.mega.imageloader.listener.BitmapLoadingListener;
import com.mega.imageloader.listener.DrawableLoadingListener;
import com.mega.imageloader.listener.LoaderListener;
import com.mega.imageloader.listener.LoaderWebpListener;
import com.mega.imageloader.transform.CornersTranform;
import com.mega.imageloader.transform.RoundedCornersTransformation;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class ImageLoader {
    public static ImageLoaderConfig defConfig;
    static RequestOptions requestOptions2;
    static RequestOptions requestOptions3;
    static RequestOptions requestOptions4;

    public ImageLoader() {
    }

    public static void displayImage(Context context, ImageView view, String imageUrl) {
        Glide.with(context).load(imageUrl).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(Context context, ImageView view, File file) {
        Glide.with(context).load(file).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(Context context, ImageView view, int resourceId) {
        Glide.with(context).load(resourceId).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(Context context, ImageView view, Uri uri) {
        Glide.with(context).load(uri).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(ImageView view, File file) {
        Glide.with(view.getContext()).load(file).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(ImageView view, int resourceId) {
        Glide.with(view.getContext()).load(resourceId).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(ImageView view, Uri uri) {
        Glide.with(view.getContext()).load(uri).apply((new RequestOptions()).dontAnimate()).into(view);
    }

    public static void displayImage(ImageView view, String imageUrl, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, height);
        Glide.with(view.getContext()).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displayImage(ImageView view, String imageUrl, int width, int height,DiskCacheStrategy diskCacheStrategy) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, height).diskCacheStrategy(diskCacheStrategy);
        Glide.with(view.getContext()).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displayImage(ImageView view, File file, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, height).dontAnimate();
        Glide.with(view.getContext()).load(file).apply(requestOptions).into(view);
    }

    public static void displayImage(ImageView view, int resourceId, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, height).dontAnimate();
        Glide.with(view.getContext()).load(resourceId).apply(requestOptions).into(view);
    }

    public static void displayImage(ImageView view, Uri uri, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, height).dontAnimate();
        Glide.with(view.getContext()).load(uri).apply(requestOptions).into(view);
    }

    public static void displayCropCenterImage(ImageView view, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop().dontAnimate();
        Glide.with(view.getContext()).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displayCropCenterImage(ImageView view, File file) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop().dontAnimate();
        Glide.with(view.getContext()).load(file).apply(requestOptions).into(view);
    }

    public static void displayCropCenterImage(ImageView view, int resourceId) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop().dontAnimate();
        Glide.with(view.getContext()).load(resourceId).apply(requestOptions).into(view);
    }

    public static void displayCropCenterImage(ImageView view, Uri uri) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop().dontAnimate();
        Glide.with(view.getContext()).load(uri).apply(requestOptions).into(view);
    }

    public static void displayFitCenterImage(ImageView view, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter().dontAnimate();
        Glide.with(view.getContext()).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displayFitCenterImage(ImageView view, File file) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter().dontAnimate();
        Glide.with(view.getContext()).load(file).apply(requestOptions).into(view);
    }

    public static void displayFitCenterImage(ImageView view, int resourceId) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter().dontAnimate();
        Glide.with(view.getContext()).load(resourceId).apply(requestOptions).into(view);
    }

    public static void displayFitCenterImage(ImageView view, Uri url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter().dontAnimate();
        Glide.with(view.getContext()).load(url).apply(requestOptions).into(view);
    }

    public static void displayFitCenterImage(ImageView view, Object url, boolean isSave) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter().dontAnimate();
        if (isSave) {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }

        Glide.with(view.getContext()).load(url).apply(requestOptions).into(view);
    }

    public static void displayRoundConnerImage(ImageView view, String imageUrl, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundedCornersTransformation(view.getContext(), (float)round));
        Glide.with(view.getContext()).asBitmap().load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(Context context, ImageView view, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(context).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(Context context, ImageView view, File file) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(context).load(file).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(Context context, ImageView view, int resourceId) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(context).load(resourceId).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(Context context, ImageView view, Uri uri) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(context).load(uri).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(ImageView view, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(view.getContext()).load(imageUrl).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(ImageView view, File file) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(view.getContext()).load(file).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(ImageView view, int resourceId) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(view.getContext()).load(resourceId).apply(requestOptions).into(view);
    }

    public static void displaySkipMemoryImage(ImageView view, Uri uri) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true).dontAnimate();
        Glide.with(view.getContext()).load(uri).apply(requestOptions).into(view);
    }

    public static void displayRoundConnerImage(ImageView view, File file, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundedCornersTransformation(view.getContext(), (float)round));
        Glide.with(view.getContext()).asBitmap().load(file).apply(requestOptions).into(view);
    }

    public static void displayRoundConnerImage(ImageView view, int resourceId, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundedCornersTransformation(view.getContext(), (float)round));
        Glide.with(view.getContext()).asBitmap().load(resourceId).apply(requestOptions).into(view);
    }

    public static void displayRoundConnerImage(ImageView view, Uri uri, int round) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundedCornersTransformation(view.getContext(), (float)round));
        Glide.with(view.getContext()).asBitmap().load(uri).apply(requestOptions).into(view);
    }

    public static void displayAsBitmapImage(ImageView view, String gifUrl) {
        RequestOptions requestOptions = (new RequestOptions()).dontAnimate();
        Glide.with(view.getContext()).asBitmap().load(gifUrl).apply(requestOptions).into(view);
    }

    public static void displayAsGifImage(ImageView view, Object gifUrl) {
        Glide.with(view.getContext()).asGif().load(gifUrl).into(view);
    }

    public static void loadBitmap(Context context, Object url, final BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA).dontAnimate();
            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new SimpleTarget<Bitmap>() {
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (listener != null) {
                        listener.onSuccess(resource);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    listener.onError();
                }
            });
        }

    }

    public static void loadBitmap(Context context, Object url, int width,int height,final BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA).override(width,height).dontAnimate();
            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new SimpleTarget<Bitmap>() {
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (listener != null) {
                        listener.onSuccess(resource);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    listener.onError();
                }
            });
        }

    }


    public static void loadBitmap(Context context, Object url, int width,int height,DiskCacheStrategy diskCacheStrategy,final BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(diskCacheStrategy).override(width,height).dontAnimate();
            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new SimpleTarget<Bitmap>() {
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (listener != null) {
                        listener.onSuccess(resource);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    listener.onError();
                }
            });
        }

    }

    public static void loadCacheBitmap(Context context, Object url, final BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(false).dontAnimate();
            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new SimpleTarget<Bitmap>() {
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (listener != null) {
                        listener.onSuccess(resource);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    listener.onError();
                }
            });
        }

    }

    public static void loadDrawble(Context context, Object url, final DrawableLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(context).load(url).into(new SimpleTarget<Drawable>() {
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    listener.onSuccess(resource);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    listener.onError();
                }
            });


        }

    }

    public static void loadImageWithHighPriority(Object url, ImageView imageView, final LoaderListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.priority(Priority.HIGH).dontAnimate();
            Glide.with(imageView.getContext()).asBitmap().load(url).apply(requestOptions).listener(new RequestListener<Bitmap>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    if (null != listener) {
                        listener.onError();
                    }

                    return false;
                }

                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    if (null != listener) {
                        listener.onSuccess();
                    }

                    return false;
                }
            }).into(imageView);
        }

    }

    public static void loadPlayerHead(Context context, ImageView view, Object url, int error, int overview, int radius) {
        RequestOptions requestOptions = (new RequestOptions()).transform(new CornersTranform((float)radius)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (overview != 0) {
            requestOptions.override(overview, overview);
        }

        if (error != 0) {
            requestOptions.error(error);
        }

        Glide.with(context).load(url).apply(requestOptions).into(view);
    }

    public static void loadPlayerHeadForCircle(Context context, ImageView view, Object url, int error, int overview, int radius) {
        RequestOptions requestOptions = (new RequestOptions()).circleCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (overview != 0) {
            requestOptions.override(overview, overview);
        }

        if (error != 0) {
            requestOptions.error(error);
        }

        Glide.with(context).load(url).apply(requestOptions).into(view);
    }

    public static void loadplayImg(Context context, ImageView view, Object url, boolean skipMemoryCache) {
        loadplayImg(context, view, url, 0, 0, 0, skipMemoryCache);
    }

    public static void loadplayImg(Context context, ImageView view, Object url, int h, int w, int error, boolean skipMemoryCache) {
        RequestOptions requestOptions = (new RequestOptions()).skipMemoryCache(skipMemoryCache).dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (w != 0 && h != 0) {
            requestOptions.override(w, h);
        }

        if (error != 0) {
            requestOptions.error(error);
        }

        Glide.with(context).load(url).apply(requestOptions).into(view);
    }

    public static void loadDiskPlayerHeadForCircle(Context context, ImageView view, Object url, int error, int overview, int radius) {
        RequestOptions requestOptions = (new RequestOptions()).transform(new RoundedCornersTransformation(context,radius)).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (overview != 0) {
            requestOptions.override(overview, overview);
        }

        if (error != 0) {
            requestOptions.error(error);
        }

        Glide.with(context).load(url).apply(requestOptions).into(view);
    }


    public static void loadplayImgAsGif(Context context, ImageView view, Object url) {
        Glide.with(context).asGif().load(url).into(view);
    }

    public static void loadplayImg(Context context, ImageView view, Object url, int h, int w) {
        loadplayImg(context, view, url, h, w, 0, false);
    }

    public static void imageUrlNone(Context context, ImageView view, Object url) {
        if (requestOptions3 == null) {
            requestOptions3 = (new RequestOptions()).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        Glide.with(context).load(url).apply(requestOptions3).into(view);
    }

    public static void loadPlayBGImg(Context context, final View view, Object url) {
        if (requestOptions2 == null) {
            requestOptions2 = (new RequestOptions()).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }

        Glide.with(context).asDrawable().load(url).apply(requestOptions2).into(new SimpleTarget<Drawable>() {
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (null != view) {
                    view.setBackgroundDrawable(resource);
                }

            }
        });
    }

    public static Bitmap getBitmap(View view, Object url) throws ExecutionException, InterruptedException {
        if (requestOptions4 == null) {
            requestOptions4 = (new RequestOptions()).dontAnimate().transform(new CornersTranform(100.0F)).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }

        return (Bitmap)Glide.with(view.getContext()).asBitmap().load(url).apply(requestOptions4).submit(100, 100).get();
    }

    public static void loadWebpForAuto(ImageView view, Object url) {
        Glide.with(view.getContext()).load(url).apply((new RequestOptions()).set(WebpFrameLoader.FRAME_CACHE_STRATEGY, WebpFrameCacheStrategy.AUTO)).into(view);
    }

    public static void loadWebpForNone(ImageView view, Object url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    public static void loadWebpForLis(ImageView view, Object url, final LoaderWebpListener loaderWebpListener, final int loopCount) {
        Glide.with(view).load(url).apply(new RequestOptions()).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof WebpDrawable) {
                    final WebpDrawable resource1 = (WebpDrawable)resource;
                    if (loopCount != 0) {
                        resource1.setLoopCount(loopCount);
                    }

                    resource1.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                        public void onAnimationStart(Drawable drawable) {
                            super.onAnimationStart(drawable);
                            if (null != loaderWebpListener) {
                                loaderWebpListener.onAnimationStart(drawable);
                            }

                        }

                        public void onAnimationEnd(Drawable drawable) {
                            if (null != loaderWebpListener) {
                                loaderWebpListener.onAnimationEnd(drawable);
                            }

                            super.onAnimationEnd(drawable);
                            resource1.unregisterAnimationCallback(this);
                        }
                    });
                }

                return false;
            }
        }).into(view);
    }

    public static void loadWebpForLisForFirst(ImageView view, Object url, final LoaderWebpListener loaderWebpListener, final int loopCount) {
        if (view != null) {
            if (view.getDrawable() != null && view.getDrawable() instanceof WebpDrawable && ((WebpDrawable)view.getDrawable()).isRunning()) {
                ((WebpDrawable)view.getDrawable()).stop();
            }

            Glide.with(view).load(url).apply(new RequestOptions()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    if (resource instanceof WebpDrawable) {
                        final WebpDrawable resource1 = (WebpDrawable)resource;
                        if (loopCount != 0) {
                            resource1.setLoopCount(loopCount);
                        }
                        if (resource1.isRunning()){
                            resource1.stop();
                        }

                        resource1.startFromFirstFrame();
                        resource1.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                            public void onAnimationStart(Drawable drawable) {
                                super.onAnimationStart(drawable);
                                if (null != loaderWebpListener) {
                                    loaderWebpListener.onAnimationStart(drawable);
                                }

                            }

                            public void onAnimationEnd(Drawable drawable) {
                                if (null != loaderWebpListener) {
                                    loaderWebpListener.onAnimationEnd(drawable);
                                }

                                super.onAnimationEnd(drawable);
                                resource1.unregisterAnimationCallback(this);
                            }
                        });
                    }

                    return false;
                }
            }).into(view);
        }
    }

    public static void LoadWebpoffRestar(ImageView view, File resourceid) {
//        if (Tools.getShare().getBoolean("isUseGlide",false)){

        if (view != null) {
            if (view.getDrawable() != null && view.getDrawable() instanceof WebpDrawable && ((WebpDrawable) view.getDrawable()).isRunning()) {
                ((WebpDrawable) view.getDrawable()).stop();
            }

            Glide.with(view).load(resourceid).apply(new RequestOptions()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    if (resource instanceof WebpDrawable) {
                        final WebpDrawable resource1 = (WebpDrawable) resource;
                        resource1.setLoopCount(1);
//                            if (resource1.isRunning()){
                        resource1.stop();
//                            }
                        if (resource1.isRunning()) {
                            resource1.start();
                        } else {
                            resource1.startFromFirstFrame();
                        }
                    }

                    return false;
                }
            }).into(view);
        }

//            ImageLoader.loadWebpForLisForFirst(view, resourceid, new LoaderWebpListener() {
//                @Override
//                public void onAnimationEnd(Drawable drawable) {
//                    onDestroyImg(view);
//                    Tools.logUtils("LoadWebpoffRestar","onAnimationEnd");
//                }
//
//                @Override
//                public void onAnimationStart(Drawable drawable) {
//                    Tools.logUtils("LoadWebpoffRestar","onAnimationStart");
//                }
//
//                @Override
//                public void onAnimationFail() {
//
//                }
//            },1);
//        }else {
//            view.setController(Fresco.newDraweeControllerBuilder()
//                    .setUri(Uri.fromFile(resourceid))
//                    .setAutoPlayAnimations(true)
//                    .setOldController(view.getController())
//                    .build());
//        }

    }

    public static void stopDraw(Drawable drawable){
        if(drawable instanceof WebpDrawable){
            WebpDrawable webpDrawable = (WebpDrawable) drawable;
            webpDrawable.stop();
            // webpDrawable.mStop = true;
           // Log.i("fyh","stop webpDrawable="+webpDrawable + "Frame="+webpDrawable.getFrameCount());
          //  Log.i("fyh","my stop");
        }
    }

    public static void onDestroyImg(ImageView view) {
        if (view != null) {
            if (view.getDrawable() != null && view.getDrawable() instanceof WebpDrawable && ((WebpDrawable) view.getDrawable()).isRunning()) {
                ((WebpDrawable) view.getDrawable()).stop();
            }

//            if (view.getController() != null){
//                view.getController().onDetach();
//            }
        }
    }


    static {
        defConfig = (new Builder()).setAsBitmap(true).setDiskCacheStrategy(DiskCache.SOURCE).setPrioriy(LoadPriority.HIGH).build();
    }
}
