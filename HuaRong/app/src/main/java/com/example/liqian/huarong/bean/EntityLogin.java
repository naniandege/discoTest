package com.example.liqian.huarong.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019-07-10.
 */

public class EntityLogin implements Serializable {
    public static EntityLogin entityLogin = null;

    private String corp_name = null;//
    private String corp_tn = null;//
    private String pers_code = null;//
    private String pers_un = null;//
    private String pers_name = null;//
    private String pers_sex = null;//
    private String pers_number = null;//
    private String pers_qq = null;//
    private String pers_cel = null;//
    private String pers_email = null;//
    private String pers_othercontact = null;//
    private String pers_wechat = null;//
    private String pos_code = null;//
    private String pos_name = null;//
    private String dept_code = null;//
    private String dept_name = null;//
    private String dept_fname = null;//
    private String unit_code = null;//
    private String unit_name = null;//
    private String unit_number = null;//

    public String getcorp_name() {
        return corp_name;
    }

    public void setcorp_name(String _corp_name) {
        corp_name = _corp_name;
    }

    public String getcorp_tn() {
        return corp_tn;
    }

    public void setcorp_tn(String _corp_tn) {
        corp_tn = _corp_tn;
    }

    public String getpers_code() {
        return pers_code;
    }

    public void setpers_code(String _pers_code) {
        pers_code = _pers_code;
    }

    public String getpers_un() {
        return pers_un;
    }

    public void setpers_un(String _pers_un) {
        pers_un = _pers_un;
    }

    public String getpers_name() {
        return pers_name;
    }

    public void setpers_name(String _pers_name) {
        pers_name = _pers_name;
    }

    public String getpers_sex() {
        return pers_sex;
    }

    public void setpers_sex(String _pers_sex) {
        pers_sex = _pers_sex;
    }

    public String getpers_number() {
        return pers_number;
    }

    public void setpers_number(String _pers_number) {
        pers_number = _pers_number;
    }

    public String getpers_qq() {
        return pers_qq;
    }

    public void setpers_qq(String _pers_qq) {
        pers_qq = _pers_qq;
    }

    public String getpers_cel() {
        return pers_cel;
    }

    public void setpers_cel(String _pers_cel) {
        pers_cel = _pers_cel;
    }

    public String getpers_email() {
        return pers_email;
    }

    public void setpers_email(String _pers_email) {
        pers_email = _pers_email;
    }

    public String getpers_othercontact() {
        return pers_othercontact;
    }

    public void setpers_othercontact(String _pers_othercontact) {
        pers_othercontact = _pers_othercontact;
    }

    public String getpers_wechat() {
        return pers_wechat;
    }

    public void setpers_wechat(String _pers_wechat) {
        pers_wechat = _pers_wechat;
    }

    public String getpos_code() {
        return pos_code;
    }

    public void setpos_code(String _pos_code) {
        pos_code = _pos_code;
    }

    public String getpos_name() {
        return pos_name;
    }

    public void setpos_name(String _pos_name) {
        pos_name = _pos_name;
    }

    public String getdept_code() {
        return dept_code;
    }

    public void setdept_code(String _dept_code) {
        dept_code = _dept_code;
    }

    public String getdept_name() {
        return dept_name;
    }

    public void setdept_name(String _dept_name) {
        dept_name = _dept_name;
    }

    public String getdept_fname() {
        return dept_fname;
    }

    public void setdept_fname(String _dept_fname) {
        dept_fname = _dept_fname;
    }

    public String getunit_code() {
        return unit_code;
    }

    public void setunit_code(String _unit_code) {
        unit_code = _unit_code;
    }

    public String getunit_name() {
        return unit_name;
    }

    public void setunit_name(String _unit_name) {
        unit_name = _unit_name;
    }

    public String getunit_number() {
        return unit_number;
    }

    public void setunit_number(String _unit_number) {
        unit_number = _unit_number;
    }

    @Override
    public String toString() {
        return "{" +
                "corp_name=\"" + corp_name + "\"" +
                ", corp_tn=\"" + corp_tn + "\"" +
                ", pers_code=\"" + pers_code + "\"" +
                ", pers_un=\"" + pers_un + "\"" +
                ", pers_name=\"" + pers_name + "\"" +
                ", pers_sex=\"" + pers_sex + "\"" +
                ", pers_number=\"" + pers_number + "\"" +
                ", pers_qq=\"" + pers_qq + "\"" +
                ", pers_cel=\"" + pers_cel + "\"" +
                ", pers_email=\"" + pers_email + "\"" +
                ", pers_othercontact=\"" + pers_othercontact + "\"" +
                ", pers_wechat=\"" + pers_wechat + "\"" +
                ", pos_code=\"" + pos_code + "\"" +
                ", pos_name=\"" + pos_name + "\"" +
                ", dept_code =\"" + dept_code + "\"" +
                ", dept_name =\"" + dept_name + "\"" +
                ", dept_fname =\"" + dept_fname + "\"" +
                ", unit_code =\"" + unit_code + "\"" +
                ", unit_name =\"" + unit_name + "\"" +
                ", unit_number =\"" + unit_number + "\"" +
                '}';
    }
}
