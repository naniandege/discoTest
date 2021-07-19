package com.example.liqian.huarong.fragment;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.AddActivity;
import com.example.liqian.huarong.activity.AnnouncementActivity;
import com.example.liqian.huarong.activity.DetailActivity;
import com.example.liqian.huarong.activity.MainActivity;
import com.example.liqian.huarong.activity.SharedFileActivity;
import com.example.liqian.huarong.activity.SummaryActivity;
import com.example.liqian.huarong.activity.WorkDistributionActivity;
import com.example.liqian.huarong.adapter.HomeAdapter;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.bean.EntityFriend;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.HomePresenter;
import com.example.liqian.huarong.mvp.v.HomeView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
import com.example.liqian.huarong.views.NoScrollViewPager;
import com.example.liqian.huarong.widget.ClockDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, HomeAdapter.onItemLongClick {

    /*    @BindView(R.id.home_name)
        TextView homeName;*/
    @BindView(R.id.home_miao)
    TextView homeMiao;
    @BindView(R.id.notice)
    LinearLayout notice;
    @BindView(R.id.sharedFile)
    LinearLayout sharedFile;
    @BindView(R.id.workDistribution)
    LinearLayout workDistribution;
    @BindView(R.id.workPlan)
    LinearLayout workPlan;
    @BindView(R.id.workReport)
    LinearLayout workReport;
    @BindView(R.id.detail)
    LinearLayout detail;
    @BindView(R.id.summary)
    LinearLayout summary;
    @BindView(R.id.task)
    LinearLayout task;
    @BindView(R.id.refresh)
    LinearLayout refresh;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.latestMsg)
    TextView latestMsg;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img_msg)
    ImageView img_msg;
    private HomeAdapter homeAdapter;
    private ArrayList<EntityFriend> list;
    private String allReadData;
    private String singleReadData;
    private RotateAnimation animation;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView(View inflate) {
        super.initView(inflate);
//        homeName.setText(Common.LoginUser.getpos_name());
        homeMiao.setText(Common.LoginUser.getpers_name());

        list = new ArrayList<>();
        homeAdapter = new HomeAdapter(list, getActivity());
        lv.setAdapter(homeAdapter);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        homeAdapter.setOnItemLongClick(this);
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @OnClick({R.id.notice, R.id.sharedFile, R.id.workDistribution, R.id.workPlan,
            R.id.workReport, R.id.detail, R.id.summary, R.id.task, R.id.refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice: //通知公告
                Common.attachmentTag = "通知";
                startActivity(new Intent(getActivity(), AnnouncementActivity.class));
                break;
            case R.id.sharedFile:  //共享文件
                startActivity(new Intent(getActivity(), SharedFileActivity.class));
                break;
            case R.id.workDistribution:    //工作分配
                Common.attachmentTag = "工作分配";
                startActivity(new Intent(getActivity(), WorkDistributionActivity.class));
                break;
            case R.id.workPlan:    //工作计划
                Common.attachmentTag = "工作计划";
                startActivity(new Intent(getActivity(), WorkDistributionActivity.class));
                break;
            case R.id.workReport:   //工作汇报
                Common.attachmentTag = "工作汇报";
                startActivity(new Intent(getActivity(), WorkDistributionActivity.class));
                break;
            case R.id.detail:
                //查看明细
                startActivity(new Intent(getActivity(), DetailActivity.class));
                break;
            case R.id.summary:
                //查看汇总
                startActivity(new Intent(getActivity(), SummaryActivity.class));
                break;
            case R.id.task:
                //任务审核
            /*    MainActivity activity = (MainActivity) getActivity();
                NoScrollViewPager vp = activity.findViewById(R.id.vp);
                TabLayout tabLayout = activity.findViewById(R.id.tab);
                vp.setCurrentItem(1);
                tabLayout.setSelectedTabIndicatorHeight(1);*/
                startActivity(new Intent(getActivity(), AddActivity.class));
                break;
            case R.id.refresh:
                animation(img);
                presenter.getData(BLL.BLLJsonByStream("Out_Mongo_Chat_Select"));
                break;
        }
    }


    /*
     * 旋转
     * */
    private void animation(ImageView img) {
        /*防止图片旋转停顿*/
     /*   int magnify = 10000;
        int toDegrees = 360;
        int duration = 2000;
        toDegrees *= magnify;
        duration *= magnify;*/
        animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setRepeatCount(Animation.RESTART);
        animation.setRepeatMode(Animation.RESTART);
        img.startAnimation(animation);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("Out_Mongo_Chat_Select"));
        presenter.getData1(BLL.BLLJsonByStream("Out_Mongo_Chat_UpdateReadStateAll"));
        presenter.getData2(BLL.BLLJsonByStream("Out_Mongo_Chat_UpdateReadState"));
    }


    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityFriend friend = new EntityFriend();
            friend.setcorp_tn(Common.LoginUser.getcorp_tn());
            friend.setfrnd_ctn(Common.LoginUser.getcorp_tn());
            friend.setpers_un(Common.LoginUser.getpers_un());
            friend.setif_chat_readstate(true);
            friend.setchat_readstate(false);
            General<Object> general = new General<>();
            String Json = general.bllMonJson(friend, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }

    @Override
    public void setData1(String data) {
        allReadData = Common.StringPick(data);
    }

    @Override
    public void setData2(String data) {
        singleReadData = Common.StringPick(data);
    }


    @Override
    public void setListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityFriend>>() {
            }.getType();
            List<EntityFriend> entityFriends = gson.fromJson(decode, type);
            List<EntityFriend> entityList = Common.defDecodeEntityList(entityFriends);
            if (entityList != null && entityList.size() > 0) {
                list.clear();
                list.addAll(entityList);
                homeAdapter.notifyDataSetChanged();
//                img.clearAnimation();
                latestMsg.setTextColor(getResources().getColor(R.color.c_0779e4));
                img_msg.setImageDrawable(getResources().getDrawable(R.drawable.tz));
            } else {
                latestMsg.setTextColor(getResources().getColor(R.color.c_a9a9a9));
                img_msg.setImageDrawable(getResources().getDrawable(R.drawable.tz1));
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    /**
     * 全部已读
     *
     * @param data
     */
    @Override
    public void setAllReadData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            list.clear();
            homeAdapter.notifyDataSetChanged();
            latestMsg.setTextColor(getResources().getColor(R.color.c_a9a9a9));
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    /**
     * 单个已读
     *
     * @param data
     * @param position
     */
    @Override
    public void setSingleReadData(String data, int position) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            list.remove(position);
            homeAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    /*
     * 点击长按
     * */
    @Override
    public void onItemLongListenner(final int position) {
        final EntityFriend entityFriend = list.get(position);

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.home_dialog_item, null);
        final TextView allRead = inflate.findViewById(R.id.allRead);
        TextView singleRead = inflate.findViewById(R.id.singleRead);
        TextView clear = inflate.findViewById(R.id.clear);
        final ClockDialog dialog = new ClockDialog(getActivity(), inflate);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottom_menu_animation);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        dialog.show();

        //全部已读
        allRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringPick = Common.StringPick(allReadData);
                Gson gson = new Gson();
                PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
                if (pubReturn.getState()) {
                    EntityFriend friend = new EntityFriend();
                    friend.setcorp_tn(Common.LoginUser.getcorp_tn());
                    friend.setfrnd_ctn(Common.LoginUser.getcorp_tn());
                    friend.setpers_un(Common.LoginUser.getpers_un());
                    friend.setif_chat_readstate(false);
                    friend.setchat_readstate(true);
                    General<Object> general = new General<>();
                    String Json = general.bllMonJson(friend, Common.defDecode(pubReturn.getData()));
                    String encode = Common.defEncode(Json);
                    presenter.allRead(encode);
                    dialog.dismiss();
                }
            }

        });


        //单个已读
        singleRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringPick = Common.StringPick(singleReadData);
                Gson gson = new Gson();
                PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
                if (pubReturn.getState()) {
                    EntityFriend friend = new EntityFriend();
                    friend.setcorp_tn(entityFriend.getcorp_tn());
                    friend.set_id(entityFriend.get_id());
                    friend.setchat_readstate(true);
                    General<Object> general = new General<>();
                    String Json = general.bllMonJson(friend, Common.defDecode(pubReturn.getData()));
                    String encode = Common.defEncode(Json);
                    presenter.singleRead(encode, position);
                    dialog.dismiss();
                }
            }
        });


        //取消
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
