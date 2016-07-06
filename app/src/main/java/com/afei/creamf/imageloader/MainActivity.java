package com.afei.creamf.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.afei.creamf.imageloader.Adapter.MyAdapter;
import com.afei.creamf.imageloader.Utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    List<String> mImgUrl;
    @BindView(R.id.gv)
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mImgUrl = new ArrayList<String>();
        mImgUrl.add(UrlUtil.IMG01);
        mImgUrl.add(UrlUtil.IMG02);
        mImgUrl.add(UrlUtil.IMG03);
        mImgUrl.add(UrlUtil.IMG04);
        mImgUrl.add(UrlUtil.IMG05);
        mImgUrl.add(UrlUtil.IMG06);
        mImgUrl.add(UrlUtil.IMG07);
        mImgUrl.add(UrlUtil.IMG08);
        mImgUrl.add(UrlUtil.IMG09);
        mImgUrl.add(UrlUtil.IMG10);
        mImgUrl.add(UrlUtil.IMG11);
        mImgUrl.add(UrlUtil.IMG12);
        mImgUrl.add(UrlUtil.IMG13);
        mImgUrl.add(UrlUtil.IMG14);
        mImgUrl.add(UrlUtil.IMG15);
        gv.setAdapter(new MyAdapter(mImgUrl, getApplicationContext()));
    }
}
