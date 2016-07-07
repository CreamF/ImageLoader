package com.afei.creamf.imageloader.ImageDetails;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 图片的内存缓存
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  9:48
 */
public class ImageCache {
    // 图片LRU缓存
    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        initImageCache();
    }

    /**
     * 初始化图片缓存
     */
    private void initImageCache() {
        // 计算手机可使用的最大内存
        final int mazMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大存储的1/ 8 来进行图片的内存缓存
        final int cacheMemory = mazMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public Bitmap get(String imgUrl) {
        return mImageCache.get(imgUrl);
    }

    public void put(String imgUrl, Bitmap bitmap) {
        mImageCache.put(imgUrl, bitmap);
    }
}
