package com.example.liqian.huarong.bean;

import java.io.Serializable;


public class GoodsCategory implements Serializable {
    private int id;
    private String name;

    public GoodsCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        //重写该方法，作为选择器显示的名称
        return name;
    }

}
