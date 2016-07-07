package com.afei.creamf.imageloader.ImageDetails;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.afei.creamf.imageloader.ImageDetails.Interface.ImageCache;

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
    // 图片缓存，依赖于抽象，有一个默认实现
    ImageCache imageCache = new MemoryCache();
    // 线程池：线程数量为CPU的数量
    ExecutorService mExecutorServer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 注入缓存实现（依赖于抽象而不依赖与具体实现）
    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    /**
     * 显示从网络获取到的图片
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayImage(final String imageUrl, final ImageView imageView) {
        Bitmap bitmap = imageCache.get(imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imageUrl, imageView);
    }

    /**
     * 图片没有缓存，提交到线程池中进行下载图片
     *
     * @param imageUrl
     * @param imageView
     */
    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
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
                imageCache.put(imageUrl, bitmap);
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
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
