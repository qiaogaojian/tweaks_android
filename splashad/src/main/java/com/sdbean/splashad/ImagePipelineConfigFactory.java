package com.sdbean.splashad;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by Michael
 * Data: 2020/2/20 11:08
 * Desc: Fresco缓存设置
 */
public class ImagePipelineConfigFactory {
    private static ImagePipelineConfig sImagePipelineConfig;

    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";
    private static final int    MAX_HEAP_SIZE            = (int) Runtime.getRuntime().maxMemory();
    public static final  int    MAX_DISK_CACHE_SIZE      = 100 * ByteConstants.MB;
    public static final  int    MAX_MEMORY_CACHE_SIZE    = MAX_HEAP_SIZE / 3;

    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
            configureCaches(configBuilder, context);
            sImagePipelineConfig = configBuilder.build();
        }
        return sImagePipelineConfig;
    }

    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,
                Integer.MAX_VALUE,
                MAX_MEMORY_CACHE_SIZE,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE);
        configBuilder.setBitmapMemoryCacheParamsSupplier(
                new Supplier<MemoryCacheParams>() {
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                })
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(context.getExternalCacheDir())
                        .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                        .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                        .build());
    }
}
