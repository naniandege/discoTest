package com.example.liqian.huarong.fragment;


import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.FrameLayout;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends BaseFragment<EmptyView, EmptyPresenter> implements EmptyView {

    private int lastfragment = 0; //上一个碎片
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.fra)
    FrameLayout fra;
    private ArrayList<Fragment> fragments;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }


    @Override
    protected void initView(View inflate) {
        super.initView(inflate);

        tab.addTab(tab.newTab().setText("单个审核"));
        tab.addTab(tab.newTab().setIcon(R.drawable.tab_sh));
        tab.addTab(tab.newTab().setText("批量审核"));

        fragments = new ArrayList<>();
        fragments.add(new AuditFragment());
        fragments.add(new BatchFragment());

        setFragmentPosition(0);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    setFragmentPosition(0);
                } else if (position == 2) {
                    setFragmentPosition(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }


    /**
     * 切换fragment
     *
     * @param position
     */
    private void setFragmentPosition(int position) {
        //初始化碎片管理器
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        //要显示的碎片
        Fragment currentFragment = fragments.get(position);
        //要隐藏的碎片
        Fragment lastFeagment = fragments.get(lastfragment);
        lastfragment = position;
        ft.hide(lastFeagment);   //隐藏
        if (!currentFragment.isAdded()) {
            ft.add(R.id.fra, currentFragment);
        }
        ft.show(currentFragment);  //显示
        ft.commitAllowingStateLoss();
    }
}
