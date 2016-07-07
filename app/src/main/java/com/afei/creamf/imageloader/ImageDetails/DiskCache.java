package com.afei.creamf.imageloader.ImageDetails;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 磁盘缓存（解决了内存有限的情况下，重启之后图片容易丢失的问题）
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  10:13
 */
public class DiskCache {
    static String cacheDir;
//    Context context;

    public DiskCache() {
//        this.context = context;
        createSDCardDir();
    }

    /**
     * 获取SD卡的路径
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return null;
        }
    }

    /**
     * 创建不存在的路径
     */
    public void createSDCardDir() {
        if (getSDPath() == null) {
//            Toast.makeText(context, "未找到SD卡", Toast.LENGTH_LONG).show();
        } else {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                // 创建一个文件夹对象，赋值为外部存储的目录
                cacheDir = getSDPath() + "/imageLoader/dir";
                File file = new File(cacheDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
    }

    public Bitmap get(String imgUrl) {
        String substring = imgUrl.substring(imgUrl.lastIndexOf("/"));
//        File file = new File(cacheDir + "/" + substring + ".png");
//
        Bitmap bitmap = null;
//        if (!file.exists()) {
//            file.mkdirs();
//        } else {
        bitmap = BitmapFactory.decodeFile(cacheDir + "/" + substring + ".png");

//        }
        return bitmap;

    }

    // 将图片缓存到内存中
    public void put(String imgUrl, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            String substring = imgUrl.substring(imgUrl.lastIndexOf("/"));
            File file = new File(cacheDir + "/" + substring + ".png");
            file.createNewFile();
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
