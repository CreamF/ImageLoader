package com.afei.creamf.quickimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.afei.creamf.quickimageloader.Interface.ImageCache;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载类
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  15:13
 */
public class ImageLoader {
    // 图片缓存，依赖于抽象，有一个默认缓存
    ImageCache imageCache = new MemoryCache();
    // 线程池：线程数量为CPU的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 注入缓存实现（依赖于抽象而不依赖于具体实现）
    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    /**
     * 显示从网络获取到的图片
     *
     * @param imgUrl
     * @param bitmap
     */
    public void displayImage(final String imgUrl, final ImageView imageView) {
        Bitmap bitmap = imageCache.get(imgUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imgUrl, imageView);
    }

    /**
     * 图片没有缓存，提交到线程池中进行下载图片
     *
     * @param imgUrl
     * @param imageView
     */
    private void submitLoadRequest(final String imgUrl, final ImageView imageView) {
        imageView.setTag(imgUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downLoadImage(imgUrl);
                if (imageView.getTag().equals(imgUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                imageCache.put(imgUrl, bitmap);
            }
        });
    }

    /**
     * 从网络中获取bitmap对象
     *
     * @param imgUrl
     * @return
     */
    private Bitmap downLoadImage(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
