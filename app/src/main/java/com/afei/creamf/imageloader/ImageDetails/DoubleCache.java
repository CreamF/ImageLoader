package com.afei.creamf.imageloader.ImageDetails;

import android.graphics.Bitmap;

/**
 * 双缓存（内存缓存和SD卡缓存）
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  10:32
 */
public class DoubleCache {
    DiskCache diskCache = new DiskCache();
    MemoryCache memoryCache = new MemoryCache();

    /**
     * 先从内存缓存中获取，如果没有再从SD卡中获取
     *
     * @param imgUrl
     * @return
     */
    public Bitmap get(String imgUrl) {
        Bitmap bitmap = memoryCache.get(imgUrl);
        if (bitmap == null) {
            bitmap = diskCache.get(imgUrl);
        }
        return bitmap;
    }

    /**
     * 将图片缓存到内存和SD卡中
     *
     * @param imgUrl
     * @param bitmap
     */
    public void put(String imgUrl, Bitmap bitmap) {
        memoryCache.put(imgUrl, bitmap);
        diskCache.put(imgUrl, bitmap);
    }
}
