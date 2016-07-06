package com.afei.creamf.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载类
 * Author:AFei
 * Email:wtfaijava@139.com
 *
 */
public class ImageLoader {
    // 图片缓存
    LruCache<String, Bitmap> mImageCache;
    // 线程池：线程数量为CPU的数量
    ExecutorService mExecutorServer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader() {
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

    /**
     * 显示从网络获取到的图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayImage(final String imageUrl, final ImageView imageView) {
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
                mImageCache.put(imageUrl, bitmap);
            }
        });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
