package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.utils.ImageLoader;
import com.example.liqian.huarong.utils.StatusBarUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoFileActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.tolbar)
    Toolbar tolbar;
    @BindView(R.id.jzVideo)
    JZVideoPlayerStandard jzVideo;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        super.initView();
        tolbar.setTitle("视频文件");
        setSupportActionBar(tolbar);

        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色
        StatusBarUtils.setStatusBarColor(VideoFileActivity.this, R.color.c_dcdcdc);

        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            if (url != null) {
                initJzVideo(url);  //饺子视频
            }
        }
    }

    //"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
    private void initJzVideo(String url) {
        jzVideo.setUp(url,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "视频文件");
        ImageLoader.setImage(this, R.drawable.guide_a
                , jzVideo.thumbImageView, R.drawable.placeholder);
//        jzVideo.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
