package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.MainVpAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.MyTag;
import com.example.liqian.huarong.bean.MyImgBean;
import com.example.liqian.huarong.bean.PhotoBean;
import com.example.liqian.huarong.fragment.ShowImgFragment;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShowImgActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.lv)
    ViewPager lv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String json;
    private List<MyImgBean> mList;
    private ArrayList<Fragment> fragments;
    private int mPosition;


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_img;
    }

    @Override
    protected void initView() {
        super.initView();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        callToolbar();

        fragments = new ArrayList<>();

        //获取图片的url和当前点击的位置
        Intent urlIntent = getIntent();
        if (urlIntent != null) {
            //图片url
            json = urlIntent.getStringExtra("url");
            //点击图片的位置
            mPosition = urlIntent.getIntExtra("position", 0);
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyImgBean>>() {
        }.getType();
        mList = gson.fromJson(json, listType);
        //遍历图片总数 根据图片数量创建碎片
        for (int i = 0; i < mList.size(); i++) {
            String fileName = mList.get(i).getUrl();
            fragments.add(new ShowImgFragment(fileName));
        }

        //适配器绑定
        MainVpAdapter adapter = new MainVpAdapter(getSupportFragmentManager(), fragments);
        lv.setAdapter(adapter);

        //实现点击哪张图片就显示哪张图片 而不是总从第一张开始
        lv.setCurrentItem(mPosition);

        lv.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int posi) {
                lv.setCurrentItem(posi);
//                ToastUtil.showShort("第" + (posi + 1) + "张图片");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        StatusBarUtil.setColor(this, getResources().getColor(R.color.c_000000));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList.clear();
        fragments.clear();
        finish();
    }


    public void callToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }
}
