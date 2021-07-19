package com.example.liqian.huarong.bean;

import android.widget.ImageView;

public class EntityInvoice {

//    pub_return 是存放数据库查询后返回的结果
//    tv_invoice_pic 存放传进去的自定义控件

    public static EntityInvoice entityInvoice = null;

    private String pub_return = "";//
    private String old_model = "";//
    private ImageView tv_invoice_pic = null;
    private EntityFile entityFile;

    public EntityFile getEntityFile() {
        return entityFile;
    }

    public void setEntityFile(EntityFile entityFile) {
        this.entityFile = entityFile;
    }

    public String getold_model() {
        return old_model;
    }

    public void setold_model(String _old_model) {
        old_model = _old_model;
    }
    public String getpub_return() {
        return pub_return;
    }

    public void setpub_return(String _pub_return) {
        pub_return = _pub_return;
    }

    public ImageView gettv_invoice_pic() {
        return tv_invoice_pic;
    }

    public void settv_invoice_pic(ImageView _tv_invoice_pic) {
        tv_invoice_pic = _tv_invoice_pic;
    }


}



