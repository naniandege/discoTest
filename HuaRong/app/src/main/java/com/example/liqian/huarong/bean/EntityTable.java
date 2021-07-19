package com.example.liqian.huarong.bean;

public class EntityTable {
    private String field_str;
    //    private String field_num;
    private String table_name;
    private String field_name;
    private String corp_tn;
    private int field_num;
    private String tb_code;
    private String type_code;
    private String kind_code;

    public String getkind_code() {
        return kind_code;
    }

    public void setkind_code(String kind_code) {
        this.kind_code = kind_code;
    }


    public String gettype_code() {
        return type_code;
    }

    public void settype_code(String type_code) {
        this.type_code = type_code;
    }

    public String gettb_code() {
        return tb_code;
    }

    public void settb_code(String tb_code) {
        this.tb_code = tb_code;
    }

    public int getfield_num() {
        return field_num;
    }

    public void setfield_num(int field_num) {
        this.field_num = field_num;
    }

    public String getcorp_tn() {
        return corp_tn;
    }

    public void setcorp_tn(String corp_tn) {
        this.corp_tn = corp_tn;
    }

    public String getfield_str() {
        return field_str;
    }

    public void setfield_str(String field_str) {
        this.field_str = field_str;
    }

    /*public String getfield_num() {
        return field_num;
    }

    public void setfield_num(String field_num) {
        this.field_num = field_num;
    }*/

    public String gettable_name() {
        return table_name;
    }

    public void settable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getfield_name() {
        return field_name;
    }

    public void setfield_name(String field_name) {
        this.field_name = field_name;
    }

}
