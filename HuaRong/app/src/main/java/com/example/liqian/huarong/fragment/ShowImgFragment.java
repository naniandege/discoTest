package com.example.liqian.huarong.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.base.MyTag;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImgFragment extends BaseFragment<EmptyView, EmptyPresenter> implements EmptyView {


    @BindView(R.id.img)
    PhotoView img;
    /*    @BindView(R.id.rotate_Img)
        ImageView rotate_Img;*/
    private String url;

    @SuppressLint("ValidFragment")
    public ShowImgFragment(String url) {
        this.url = url;
    }

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    public ShowImgFragment() {
        // Required empty public constructor
    }


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show_img;
    }


    @Override
    protected void initView(View inflate) {
        super.initView(inflate);
//        isViewCreated = true;
        loadData();
    }

  /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }
*/

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    private void loadData() {
        ImageLoader.setImage(getActivity(), url, img, R.drawable.show_img);
        // 启用图片缩放功能
        img.setMaxScale(10);
        img.enable();
    }

    /**
     * 旋转动画
     * 参数说明:
     * fromDegrees ：动画开始时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
     * toDegrees ：动画结束时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
     * pivotXType：旋转轴点的x坐标的模式
     * pivotXValue：旋转轴点x坐标的相对值
     * pivotYType：旋转轴点的y坐标的模式
     * pivotYValue：旋转轴点y坐标的相对值
     */
    private void initAnimaTion(int fromDegrees, int toDegrees) {
        Animation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);//持续时间
        rotateAnimation.setFillAfter(true);
//        rotateAnimation.setStartOffset(10);//执行前的等待时间
//        rotateAnimation.setRepeatMode(Animation.REVERSE);
        img.startAnimation(rotateAnimation);
    }


    private int a = 0;  //标记的动画开始时 视图的旋转角度
    private int b = 90; //动画结束时 视图的旋转角度
    private boolean tag = false; //是否为第一次点击

  /*  @OnClick({R.id.rotate_Img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rotate_Img:

                if (tag == false) {
                    initAnimaTion(a, b);
                    tag = true;
                } else {
                    if (a == 360) {
                        a = 0;
                        b = 90;
                    }
                    a += 90;
                    b += 90;
                    initAnimaTion(a, b);
                }
                break;
        }
    }*/

}