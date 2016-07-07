package com.afei.creamf.imageloader.ImageDetails;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载类
 * Author:AFei
 * Email:wtfaijava@139.com
 */
public class ImageLoader {
    // 内存缓存
    MemoryCache memoryCache = new MemoryCache();
    // SD卡缓存
    DiskCache diskCache = new DiskCache();
    // 双缓存
    DoubleCache doubleCache = new DoubleCache();
    // 是否采用SD卡缓存
    Boolean isUseDiskCache = false;
    // 是否采用双缓存
    Boolean isUseDoubleCache = false;
    // 线程池：线程数量为CPU的数量
    ExecutorService mExecutorServer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 显示从网络获取到的图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayImage(final String imageUrl, final ImageView imageView) {
        // 判断采用那种缓存方式
        Bitmap bitmap = null;
        if (isUseDoubleCache) {
            bitmap = doubleCache.get(imageUrl);
        } else if (isUseDiskCache) {
            bitmap = diskCache.get(imageUrl);
        } else {
            bitmap = memoryCache.get(imageUrl);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        imageView.setTag(imageUrl);
        mExecutorServer.submit(new Runnable() {
            @Override
            public void run() {
                // 子线程中进行加载
                Bitmap bitmap = downLoadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                // 设置ImageView，并将图片和URL 保存到LruCache
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                if (isUseDoubleCache) {
                    doubleCache.put(imageUrl, bitmap);
                } else if (isUseDiskCache) {
                    diskCache.put(imageUrl, bitmap);
                } else {
                    memoryCache.put(imageUrl, bitmap);
                }
            }
        });
    }

    /**
     * 采用SD卡缓存
     *
     * @param UserDiskCache
     */
    public void useDiskCache(boolean UseDiskCache) {
        isUseDiskCache = UseDiskCache;
    }

    /**
     * 采用双缓存
     *
     * @param useDoubleCache
     */
    public void useDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }

    /**
     * 从网络中获取bitmap对象
     *
     * @param imageUrl
     * @return
     */
    private Bitmap downLoadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
