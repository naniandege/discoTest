package com.example.liqian.huarong.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.liqian.huarong.R;

public class TextDialog extends Dialog {
    private Button button_cancel, button_exit;//定义取消与确认按钮
    private TextView tv;//定义标题文字

    //构造方法
    public TextDialog(Context context) {

        //设置对话框样式
        super(context, R.style.MyDialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.text_dialog, null);
        tv = view.findViewById(R.id.title);   //获取显示标题的文本框控件
        button_cancel = view.findViewById(R.id.btn_cancel);    //获取取消按钮
        button_exit = view.findViewById(R.id.btn_exit);  //获取确认退出按钮
        setContentView(view);  //设置显示的视图
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottom_menu_animation);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    //设置标题
    public void setTv(String content) {
        tv.setText(content);
    }

    //设置标题
    public void setTvSize(float size) {
        tv.setTextSize(size);
    }


    //设置取消
    public void setbutton_cancel(String content) {
        button_cancel.setText(content);
    }


    //设置确定
    public void setbutton_exit(String content) {
        button_exit.setText(content);
    }

    //取消监听
    public void setOnCancelListener(View.OnClickListener listener) {
        button_cancel.setOnClickListener(listener);
    }

    //退出监听
    public void setOnExitListener(View.OnClickListener listener) {
        button_exit.setOnClickListener(listener);
    }
}
