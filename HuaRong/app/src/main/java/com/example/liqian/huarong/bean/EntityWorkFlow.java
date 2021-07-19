package com.example.liqian.huarong.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019-07-11.
 */

public class EntityWorkFlow implements Serializable {

    public static EntityWorkFlow entityWorkFlow = null;

    private String wf_code = "";
    private String corp_tn = "";
    private String task_code = "";
    private int task_id = 0;
    private String pers_code = "";
    private String task_imp_date = "";
    private String task_imp_result = "";
    private String task_imp_content = "";
    private String task_imp_sign = "";
    private String front_node_path = "";
    private String tb_code = "";
    private String type_code = "";
    private String kind_code = "";
    private String node_code = "";
    private String node_name = "";
    private String wfb_code = "";
    private String wfb_ncode = "";
    private String task_bcode = "";
    private String parent_wfb_code = "";
    private String parent_wfb_ncode = "";
    private String parent_taskb_code = "";

    private String stage_code = "";
    private String stage_code_no = "";
    private String stage_name = "";
    private String stage_code_for_init = "";
    private String stage_code_no_for_init = "";
    private String state_code = "";
    private String wf_name = "";
    private String task_name = "";
    private String node_fullname = "";
    private String init_pname = "";
    private String init_date = "";
    private String task_arrvdate = "";
    private String task_key = "";
    private String accepter_pcode = "";
    private String accepter_pun = "";
    private String accepter_pname = "";

    private String papt_pcode = "";
    private String papt_pun = "";
    private String papt_pname = "";

    private String order_list = "";
    private boolean if_no_imp = false;
    private int  is_end = 0;

    private String flow_trail = "";
    private String flow_path = "";

    private String init_pers_ucode = "";
    private String init_pers_uname = "";
    private String init_pcode = "";
    private String init_pun = "";
    private String nit_pcode = "";
    private String papt_code = "";
    private String papt_name = "";
    private double papt_order = 0;
    private int papt_level = 0;
    private int term_day = 0;
    private int term_hour = 0;
    private int term_minute = 0;
    private int if_check = 0;
    private String form_code = "";
    private String papt_tcode = "";
    private String papt_tname = "";
    private String papt_key = "";
    private String init_pers_dcode = "";
    private String init_pers_dname = "";
    private String task_prmvalue = "";
    private String task_condition = "";
    private String doing_state = "";


    public String getcorp_tn() {
        return corp_tn;
    }

    public void setcorp_tn(String corp_tn) {
        this.corp_tn = corp_tn;
    }

    public String getdoing_state() {
        return doing_state;
    }

    public void setdoing_state(String doing_state) {
        this.doing_state = doing_state;
    }


    public String gettask_condition() {
        return task_condition;
    }

    public void settask_condition(String task_condition) {
        this.task_condition = task_condition;
    }


    public String gettask_prmvalue() {
        return task_prmvalue;
    }

    public void settask_prmvalue(String task_prmvalue) {
        this.task_prmvalue = task_prmvalue;
    }

    public int getif_check() {
        return if_check;
    }

    public void setif_check(int if_check) {
        this.if_check = if_check;
    }

    public String getinit_pers_dname() {
        return init_pers_dname;
    }

    public void setinit_pers_dname(String init_pers_dname) {
        this.init_pers_dname = init_pers_dname;
    }


    public String getinit_pers_dcode() {
        return init_pers_dcode;
    }

    public void setinit_pers_dcode(String init_pers_dcode) {
        this.init_pers_dcode = init_pers_dcode;
    }

    public String getnit_pcode() {
        return nit_pcode;
    }

    public void setnit_pcode(String nit_pcode) {
        this.nit_pcode = nit_pcode;
    }

    public String getinit_pers_ucode() {
        return init_pers_ucode;
    }

    public void setinit_pers_ucode(String init_pers_ucode) {
        this.init_pers_ucode = init_pers_ucode;
    }

    public String getinit_pers_uname() {
        return init_pers_uname;
    }

    public void setinit_pers_uname(String init_pers_uname) {
        this.init_pers_uname = init_pers_uname;
    }

    public String getinit_pcode() {
        return init_pcode;
    }

    public void setinit_pcode(String init_pcode) {
        this.init_pcode = init_pcode;
    }

    public String getinit_pun() {
        return init_pun;
    }

    public void setinit_pun(String init_pun) {
        this.init_pun = init_pun;
    }

    public String getpapt_code() {
        return papt_code;
    }

    public void setpapt_code(String papt_code) {
        this.papt_code = papt_code;
    }

    public String getpapt_name() {
        return papt_name;
    }

    public void setpapt_name(String papt_name) {
        this.papt_name = papt_name;
    }

    public double getpapt_order() {
        return papt_order;
    }

    public void setpapt_order(double papt_order) {
        this.papt_order = papt_order;
    }

    public int getpapt_level() {
        return papt_level;
    }

    public void setpapt_level(int papt_level) {
        this.papt_level = papt_level;
    }

    public int getterm_day() {
        return term_day;
    }

    public void setterm_day(int term_day) {
        this.term_day = term_day;
    }

    public int getterm_hour() {
        return term_hour;
    }

    public void setterm_hour(int term_hour) {
        this.term_hour = term_hour;
    }

    public int getterm_minute() {
        return term_minute;
    }

    public void setterm_minute(int term_minute) {
        this.term_minute = term_minute;
    }

    public String getform_code() {
        return form_code;
    }

    public void setform_code(String form_code) {
        this.form_code = form_code;
    }

    public String getpapt_tcode() {
        return papt_tcode;
    }

    public void setpapt_tcode(String papt_tcode) {
        this.papt_tcode = papt_tcode;
    }

    public String getpapt_tname() {
        return papt_tname;
    }

    public void setpapt_tname(String papt_tname) {
        this.papt_tname = papt_tname;
    }

    public String getpapt_key() {
        return papt_key;
    }

    public void setpapt_key(String papt_key) {
        this.papt_key = papt_key;
    }

    public String getstage_code() {
        return stage_code;
    }

    public void setstage_code(String _stage_code) {
        stage_code = _stage_code;
    }

    public String getstage_code_no() {
        return stage_code_no;
    }

    public void setstage_code_no(String _stage_code_no) {
        stage_code_no = _stage_code_no;
    }

    public String getstage_name() {
        return stage_name;
    }

    public void setstage_name(String _stage_name) {
        stage_name = _stage_name;
    }

    public String getstage_code_for_init() {
        return stage_code_for_init;
    }

    public void setstage_code_for_init(String _stage_code_for_init) {
        stage_code_for_init = _stage_code_for_init;
    }

    public String getstage_code_no_for_init() {
        return stage_code_no_for_init;
    }

    public void setstage_code_no_for_init(String _stage_code_no_for_init) {
        stage_code_no_for_init = _stage_code_no_for_init;
    }

    public String getstate_code() {
        return state_code;
    }

    public void setstate_code(String _state_code) {
        state_code = _state_code;
    }

    public String getwf_name() {
        return wf_name;
    }

    public void setwf_name(String _wf_name) {
        wf_name = _wf_name;
    }

    public String gettask_name() {
        return task_name;
    }

    public void settask_name(String _task_name) {
        task_name = _task_name;
    }

    public String getnode_fullname() {
        return node_fullname;
    }

    public void setnode_fullname(String _node_fullname) {
        node_fullname = _node_fullname;
    }

    public String getinit_pname() {
        return init_pname;
    }

    public void setinit_pname(String _init_pname) {
        init_pname = _init_pname;
    }

    public String getinit_date() {
        return init_date;
    }

    public void setinit_date(String _init_date) {
        init_date = _init_date;
    }

    public String gettask_arrvdate() {
        return task_arrvdate;
    }

    public void settask_arrvdate(String _task_arrvdate) {
        task_arrvdate = _task_arrvdate;
    }

    public String gettask_key() {
        return task_key;
    }

    public void settask_key(String _task_key) {
        task_key = _task_key;
    }

    public String getwf_code() {
        return wf_code;
    }

    public void setwf_code(String _wf_code) {
        wf_code = _wf_code;
    }

    public String gettask_code() {
        return task_code;
    }

    public void settask_code(String _task_code) {
        task_code = _task_code;
    }

    public int gettask_id() {
        return task_id;
    }

    public void settask_id(int _task_id) {
        task_id = _task_id;
    }

    public String getpers_code() {
        return pers_code;
    }

    public void setpers_code(String _pers_code) {
        pers_code = _pers_code;
    }

    public String gettask_imp_date() {
        return task_imp_date;
    }

    public void settask_imp_date(String _task_imp_date) {
        task_imp_date = _task_imp_date;
    }

    public String gettask_imp_result() {
        return task_imp_result;
    }

    public void settask_imp_result(String _task_imp_result) {
        task_imp_result = _task_imp_result;
    }

    public String gettask_imp_content() {
        return task_imp_content;
    }

    public void settask_imp_content(String _task_imp_content) {
        task_imp_content = _task_imp_content;
    }

    public String gettask_imp_sign() {
        return task_imp_sign;
    }

    public void settask_imp_sign(String _task_imp_sign) {
        task_imp_sign = _task_imp_sign;
    }

    public String getfront_node_path() {
        return front_node_path;
    }

    public void setfront_node_path(String _front_node_path) {
        front_node_path = _front_node_path;
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

    public String getnode_code() {
        return node_code;
    }

    public void setnode_code(String _node_code) {
        node_code = _node_code;
    }

    public String getnode_name() {
        return node_name;
    }

    public void setnode_name(String _node_name) {
        node_name = _node_name;
    }

    public String getwfb_code() {
        return wfb_code;
    }

    public void setwfb_code(String _wfb_code) {
        wfb_code = _wfb_code;
    }

    public String getwfb_ncode() {
        return wfb_ncode;
    }

    public void setwfb_ncode(String _wfb_ncode) {
        wfb_ncode = _wfb_ncode;
    }

    public String gettask_bcode() {
        return task_bcode;
    }

    public void settask_bcode(String _task_bcode) {
        task_bcode = _task_bcode;
    }

    public String getparent_wfb_code() {
        return parent_wfb_code;
    }

    public void setparent_wfb_code(String _parent_wfb_code) {
        parent_wfb_code = _parent_wfb_code;
    }

    public String getparent_wfb_ncode() {
        return parent_wfb_ncode;
    }

    public void setparent_wfb_ncode(String _parent_wfb_ncode) {
        parent_wfb_ncode = _parent_wfb_ncode;
    }

    public String getparent_taskb_code() {
        return parent_taskb_code;
    }

    public void setparent_taskb_code(String _parent_taskb_code) {
        parent_taskb_code = _parent_taskb_code;
    }

    public String getaccepter_pcode() {
        return accepter_pcode;
    }

    public void setaccepter_pcode(String _accepter_pcode) {
        accepter_pcode = _accepter_pcode;
    }

    public String getaccepter_pun() {
        return accepter_pun;
    }

    public void setaccepter_pun(String _accepter_pun) {
        accepter_pun = _accepter_pun;
    }

    public String getaccepter_pname() {
        return accepter_pname;
    }

    public void setaccepter_pname(String _accepter_pname) {
        accepter_pname = _accepter_pname;
    }

    public String getpapt_pcode() {
        return papt_pcode;
    }

    public void setpapt_pcode(String _papt_pcode) {
        papt_pcode = _papt_pcode;
    }

    public String getpapt_pun() {
        return papt_pun;
    }

    public void setpapt_pun(String _papt_pun) {
        papt_pun = _papt_pun;
    }

    public String getpapt_pname() {
        return papt_pname;
    }

    public void setpapt_pname(String _papt_pname) {
        papt_pname = _papt_pname;
    }

    public String getorder_list() {
        return order_list;
    }

    public void setorder_list(String _order_list) {
        order_list = _order_list;
    }

    public boolean getif_no_imp() {
        return if_no_imp;
    }

    public void setif_no_imp(boolean _if_no_imp) {
        if_no_imp = _if_no_imp;
    }

    public int getis_end() {
        return is_end;
    }

    public void setis_end(int _is_end) {
        is_end = _is_end;
    }


    public String getflow_trail() {
        return flow_trail;
    }

    public void setflow_trail(String _flow_trail) {
        flow_trail = _flow_trail;
    }

    public String getflow_path() {
        return flow_path;
    }

    public void setflow_path(String _flow_path) {
        flow_path = _flow_path;
    }


    @Override
    public String toString() {
        return "{" +
                "stage_code=\"" + stage_code + "\"" +
                ", stage_code_no=\"" + stage_code_no + "\"" +
                ", stage_name=\"" + stage_name + "\"" +
                ", stage_code_for_init=\"" + stage_code_for_init + "\"" +
                ", stage_code_no_for_init=\"" + stage_code_no_for_init + "\"" +
                ", state_code=\"" + state_code + "\"" +
                ", wf_name=\"" + wf_name + "\"" +
                ", task_name=\"" + task_name + "\"" +
                ", node_fullname=\"" + node_fullname + "\"" +
                ", init_pname=\"" + init_pname + "\"" +
                ", init_date=\"" + init_date + "\"" +
                ", task_arrvdate=\"" + task_arrvdate + "\"" +
                ", task_key=\"" + task_key + "\"" +
                '}';
    }


}
