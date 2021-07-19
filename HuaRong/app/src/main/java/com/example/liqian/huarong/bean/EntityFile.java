package com.example.liqian.huarong.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019-08-17.
 */

public class EntityFile implements Serializable {


    public static EntityFile entityFile = null;

    private String _id = "";
    private String corp_tn = "";
    private String corp_name = "";
    private String base_code = "";
    private String direct_state = "";
    private String make_pcode = "";
    private String order_list = "";


    private String tb_code = "";
    private String type_code = "";
    private String kind_code = "";
    private String file_becode = "";
    private String file_uptime = "";
    private String file_name = "";


    private String file_id = "";
    private String file_code = "";
    private String file_md5 = "";
    private String file_link = "";
    private String file_save = "";
    private String file_server_map = "";
    private String file_path = "";
    private String file_orgpath = "";
    private String file_oldpath = "";
    private String unit_code = "";
    private String unit_name = "";
    private String dept_code = "";
    private String dept_name = "";
    private String pers_un = "";
    private String pers_code = "";
    private String pers_name = "";
    private int fize_size = 0;
    private String file_extend = "";
    private String file_origname = "";
    private String file_version = "";
    private String file_body = "";
    private String file_belong = "";
    private boolean file_openstate = false;
    private boolean file_end_flag = false;

    private long file_uptime_stamp = 0;
    private int file_size = 0;
    private int page_size = 0;
    private int page_current = 0;



    private double file_deci2_1 = 0;
    private double file_deci2_2 = 0;
    private double file_deci2_3 = 0;
    private double file_deci2_4 = 0;
    private double file_deci2_5 = 0;


    private boolean file_check_1 = false;
    private boolean file_check_2 = false;
    private boolean file_check_3 = false;
    private boolean file_check_4 = false;
    private boolean file_check_5 = false;

    private String url = "";
    private String flow_path = "";
    private String flow_trail = "";

    public int getpage_size() {
        return page_size;
    }

    public void setpage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getpage_current() {
        return page_current;
    }

    public void setpage_current(int page_current) {
        this.page_current = page_current;
    }

    public String getorder_list() {
        return order_list;
    }

    public void setorder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getmake_pcode() {
        return make_pcode;
    }

    public void setmake_pcode(String make_pcode) {
        this.make_pcode = make_pcode;
    }

    public String getdirect_state() {
        return direct_state;
    }

    public void setdirect_state(String direct_state) {
        this.direct_state = direct_state;
    }

    public String getbase_code() {
        return base_code;
    }

    public void setbase_code(String base_code) {
        this.base_code = base_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String __id) {
        _id = __id;
    }


    public String getcorp_tn() {
        return corp_tn;
    }

    public void setcorp_tn(String _corp_tn) {
        corp_tn = _corp_tn;
    }

    public String gettb_code() {
        return tb_code;
    }

    public void settb_code(String _tb_code) {
        tb_code = _tb_code;
    }

    public String gettype_code() {
        return type_code;
    }

    public void settype_code(String _type_code) {
        type_code = _type_code;
    }

    public String getkind_code() {
        return kind_code;
    }

    public void setkind_code(String _kind_code) {
        kind_code = _kind_code;
    }

    public String getfile_becode() {
        return file_becode;
    }

    public void setfile_becode(String _file_becode) {
        file_becode = _file_becode;
    }

    public String getfile_uptime() {
        return file_uptime;
    }

    public void setfile_uptime(String _file_uptime) {
        file_uptime = _file_uptime;
    }

    public String getfile_name() {
        return file_name;
    }

    public void setfile_name(String _file_name) {
        file_name = _file_name;
    }

    public String getcorp_name() {
        return corp_name;
    }

    public void setcorp_name(String _corp_name) {
        corp_name = _corp_name;
    }

    public String getfile_id() {
        return file_id;
    }

    public void setfile_id(String _file_id) {
        file_id = _file_id;
    }

    public String getfile_code() {
        return file_code;
    }

    public void setfile_code(String _file_code) {
        file_code = _file_code;
    }

    public String getfile_md5() {
        return file_md5;
    }

    public void setfile_md5(String _file_md5) {
        file_md5 = _file_md5;
    }

    public String getfile_link() {
        return file_link;
    }

    public void setfile_link(String _file_link) {
        file_link = _file_link;
    }

    public String getfile_save() {
        return file_save;
    }

    public void setfile_save(String _file_save) {
        file_save = _file_save;
    }

    public String getfile_server_map() {
        return file_server_map;
    }

    public void setfile_server_map(String _file_server_map) {
        file_server_map = _file_server_map;
    }

    public String getfile_path() {
        return file_path;
    }

    public void setfile_path(String _file_path) {
        file_path = _file_path;
    }

    public String getfile_orgpath() {
        return file_orgpath;
    }

    public void setfile_orgpath(String _file_orgpath) {
        file_orgpath = _file_orgpath;
    }

    public String getfile_oldpath() {
        return file_oldpath;
    }

    public void setfile_oldpath(String _file_oldpath) {
        file_oldpath = _file_oldpath;
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

    public String getpers_un() {
        return pers_un;
    }

    public void setpers_un(String _pers_un) {
        pers_un = _pers_un;
    }

    public String getpers_code() {
        return pers_code;
    }

    public void setpers_code(String _pers_code) {
        pers_code = _pers_code;
    }

    public String getpers_name() {
        return pers_name;
    }

    public void setpers_name(String _pers_name) {
        pers_name = _pers_name;
    }

    public int getfize_size() {
        return fize_size;
    }

    public void setfize_size(int _fize_size) {
        fize_size = _fize_size;
    }

    public String getfile_extend() {
        return file_extend;
    }

    public void setfile_extend(String _file_extend) {
        file_extend = _file_extend;
    }

    public String getfile_origname() {
        return file_origname;
    }

    public void setfile_origname(String _file_origname) {
        file_origname = _file_origname;
    }

    public String getfile_version() {
        return file_version;
    }

    public void setfile_version(String _file_version) {
        file_version = _file_version;
    }

    public Boolean getfile_openstate() {
        return file_openstate;
    }

    public void setfile_openstate(Boolean _file_openstate) {
        file_openstate = _file_openstate;
    }

    public Boolean getfile_end_flag() {
        return file_end_flag;
    }

    public void setfile_end_flag(Boolean _file_end_flag) {
        file_end_flag = _file_end_flag;
    }

    public String getfile_body() {
        return file_body;
    }

    public void setfile_body(String _file_body) {
        file_body = _file_body;
    }

    public String getfile_belong() {
        return file_belong;
    }

    public void setfile_belong(String _file_belong) {
        file_belong = _file_belong;
    }

    public long getfile_uptime_stamp() {
        return file_uptime_stamp;
    }

    public void setfile_uptime_stamp(long _file_uptime_stamp) {
        file_uptime_stamp = _file_uptime_stamp;
    }

    public int getfile_size() {
        return file_size;
    }

    public void setfile_size(int _file_size) {
        file_size = _file_size;
    }

    public double getfile_deci2_1() {
        return file_deci2_1;
    }

    public void setfile_deci2_1(double _file_deci2_1) {
        file_deci2_1 = _file_deci2_1;
    }

    public double getfile_deci2_2() {
        return file_deci2_2;
    }

    public void setfile_deci2_2(double _file_deci2_2) {
        file_deci2_2 = _file_deci2_2;
    }

    public double getfile_deci2_3() {
        return file_deci2_3;
    }

    public void setfile_deci2_3(double _file_deci2_3) {
        file_deci2_3 = _file_deci2_3;
    }

    public double getfile_deci2_4() {
        return file_deci2_4;
    }

    public void setfile_deci2_4(double _file_deci2_4) {
        file_deci2_4 = _file_deci2_4;
    }

    public double getfile_deci2_5() {
        return file_deci2_5;
    }

    public void setfile_deci2_5(double _file_deci2_5) {
        file_deci2_5 = _file_deci2_5;
    }

    public boolean getfile_check_1() {
        return file_check_1;
    }

    public void setfile_check_1(boolean _file_check_1) {
        file_check_1 = _file_check_1;
    }

    public boolean getfile_check_2() {
        return file_check_2;
    }

    public void setfile_check_2(boolean _file_check_2) {
        file_check_2 = _file_check_2;
    }

    public boolean getfile_check_3() {
        return file_check_3;
    }

    public void setfile_check_3(boolean _file_check_3) {
        file_check_3 = _file_check_3;
    }

    public boolean getfile_check_4() {
        return file_check_4;
    }

    public void setfile_check_4(boolean _file_check_4) {
        file_check_4 = _file_check_4;
    }

    public boolean getfile_check_5() {
        return file_check_5;
    }

    public void setfile_check_5(boolean _file_check_5) {
        file_check_5 = _file_check_5;
    }

    public String getflow_path() {
        return flow_path;
    }

    public void setflow_path(String _flow_path) {
        flow_path = _flow_path;
    }

    public String getflow_trail() {
        return flow_trail;
    }

    public void setflow_trail(String _flow_trail) {
        flow_trail = _flow_trail;
    }

    @Override
    public String toString() {
        return "EntityFile{" +
                "_id='" + _id + '\'' +
                ", corp_tn='" + corp_tn + '\'' +
                ", corp_name='" + corp_name + '\'' +
                ", tb_code='" + tb_code + '\'' +
                ", type_code='" + type_code + '\'' +
                ", kind_code='" + kind_code + '\'' +
                ", file_becode='" + file_becode + '\'' +
                ", file_uptime='" + file_uptime + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_id='" + file_id + '\'' +
                ", file_code='" + file_code + '\'' +
                ", file_md5='" + file_md5 + '\'' +
                ", file_link='" + file_link + '\'' +
                ", file_save='" + file_save + '\'' +
                ", file_server_map='" + file_server_map + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_orgpath='" + file_orgpath + '\'' +
                ", file_oldpath='" + file_oldpath + '\'' +
                ", unit_code='" + unit_code + '\'' +
                ", unit_name='" + unit_name + '\'' +
                ", dept_code='" + dept_code + '\'' +
                ", dept_name='" + dept_name + '\'' +
                ", pers_un='" + pers_un + '\'' +
                ", pers_code='" + pers_code + '\'' +
                ", pers_name='" + pers_name + '\'' +
                ", fize_size=" + fize_size +
                ", file_extend='" + file_extend + '\'' +
                ", file_origname='" + file_origname + '\'' +
                ", file_version='" + file_version + '\'' +
                ", file_body='" + file_body + '\'' +
                ", file_belong='" + file_belong + '\'' +
                ", file_openstate=" + file_openstate +
                ", file_end_flag=" + file_end_flag +
                ", file_uptime_stamp=" + file_uptime_stamp +
                ", file_size=" + file_size +
                ", file_deci2_1=" + file_deci2_1 +
                ", file_deci2_2=" + file_deci2_2 +
                ", file_deci2_3=" + file_deci2_3 +
                ", file_deci2_4=" + file_deci2_4 +
                ", file_deci2_5=" + file_deci2_5 +
                ", file_check_1=" + file_check_1 +
                ", file_check_2=" + file_check_2 +
                ", file_check_3=" + file_check_3 +
                ", file_check_4=" + file_check_4 +
                ", file_check_5=" + file_check_5 +
                ", flow_path='" + flow_path + '\'' +
                ", flow_trail='" + flow_trail + '\'' +
                '}';
    }
}
