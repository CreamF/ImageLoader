package com.afei.creamf.imageloader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.afei.creamf.imageloader.ImageLoader;
import com.afei.creamf.imageloader.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:AFei
 * Email:wtfaijava@139.com
 */
public class MyAdapter extends BaseAdapter {

    private final ImageLoader imageLoader;
    private final Context context;
    List<String> mImgUrl;

    public MyAdapter(List<String> mImgUrl, Context context) {
        this.mImgUrl = mImgUrl;
        this.context = context;
        imageLoader = new ImageLoader();
    }

    @Override
    public int getCount() {
        return mImgUrl.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (holder != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.img_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        imageLoader.displayImage(mImgUrl.get(i),holder.iv);
        return view;
    }

    class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
