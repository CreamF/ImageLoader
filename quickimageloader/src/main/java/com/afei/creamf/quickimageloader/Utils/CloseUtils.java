package com.afei.creamf.quickimageloader.Utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author:AFei
 * Email:wtfaijava@139.com
 * Created By:2016/7/7  13:29
 */
public class CloseUtils {
    /**
     * 关闭Closeable对象
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
