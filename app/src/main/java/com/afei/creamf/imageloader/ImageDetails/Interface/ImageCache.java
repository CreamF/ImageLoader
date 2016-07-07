package com.afei.creamf.imageloader.ImageDetails.Interface;

import android.graphics.Bitmap;

/**
 * 内存缓存、sd卡缓存、双缓存均实现接口
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  12:49
 */
public interface ImageCache {
    /**
     * 加入缓存
     *
     * @param imgUrl
     * @param bitmap
     */
    void put(String imgUrl, Bitmap bitmap);

    /**
     * 从缓存中获取位图对象
     *
     * @param imgUrl
     * @return
     */
    Bitmap get(String imgUrl);
}
