package com.example.liqian.huarong.bean;

/**
 * Created by lq
 * on 2020/7/21
 */
public class EntityFriend {
    private String corp_tn = "";
    private String _id = "";
    private String pers_un = "";
    private String order_list = "";
    private String msg_content = "";
    private String new_info_count = "";
    private String chat_type = "";
    private String no_pers_uns = "";
    private String frnd_type = "";
    private String frnd_kind = "";
    private String frnd_state = "";
    private String frnd_ctn = "";
    private String frnd_un = "";
    private String frnd_name = "";
    private String frnd_sex = "";
    private String pers_sex = "";
    private String pers_name = "";
    private String corp_name = "";
    private String frnd_dept = "";
    private String frnd_cel = "";
    private String frnd_email = "";
    private String frnd_qq = "";
    private String frnd_wechat = "";
    private String frnd_othercontact = "";
    private String frnd_degree = "";
    private String frnd_graduation = "";
    private String frnd_major = "";
    private String frnd_profession = "";
    private String frnd_job = "";
    private String frnd_nowplace = "";
    private String frnd_homeplace = "";
    private String frnd_birth = "";
    private String frnd_birthtype = "";
    private String frnd_faith = "";
    private String frnd_maristatus = "";
    private String frnd_tel = "";
    private String frnd_cname = "";
    private String frnd_cdesc = "";
    private String frnd_unit = "";
    private String frnd_pos = "";
    private String frnd_number = "";
    private String frnd_desc = "";
    private String frnd_memo = "";
    private String frnd_rank = "";
    private String pers_rank = "";
    private String add_time = "";
    private String chat_id = "";
    private String chat_time = "";
    private String chat_time_1 = "";
    private String chat_time_2 = "";
    private String chat_direct = "";
    private String chat_direct_name = "";
    private String chat_content = "";
    private String group_create_ctn = "";
    private String group_create_cname = "";
    private String group_create_pun = "";
    private String group_create_pname = "";
    private String group_code = "";
    private String group_name = "";
    private String group_kind = "";
    private String group_memo = "";
    private String group_create_time = "";
    private String group_member_pnames = "";
    private String group_member_puns = "";
    private String group_member_count_str = "";
    private String info_time = "";
    private String memo_content = "";
    private String cald_day = "";
    private String cald_time = "";
    private String cald_period = "";
    private String cald_hour = "";
    private String cald_minute = "";
    private String cald_content = "";


    private long add_time_stamp = 0;
    private int chat_count = 0;
    private long chat_time_stamp = 0;
    private long chat_time_stamp_1 = 0;
    private long chat_time_stamp_2 = 0;
    private long group_create_time_stamp = 0;
    private int group_member_count = 0;
    private int group_member_limit = 0;
    private long info_time_stamp = 0;
    private int info_content = 0;
    private int memo_time = 0;
    private long memo_time_stamp = 0;
    private long memo_time_stamp_1 = 0;
    private long memo_time_stamp_2 = 0;
    private int page_size_fld = 0;

    public long getadd_time_stamp() {
        return add_time_stamp;
    }

    public void setadd_time_stamp(long add_time_stamp) {
        this.add_time_stamp = add_time_stamp;
    }

    public int getchat_count() {
        return chat_count;
    }

    public void setchat_count(int chat_count) {
        this.chat_count = chat_count;
    }

    public long getchat_time_stamp() {
        return chat_time_stamp;
    }

    public void setchat_time_stamp(long chat_time_stamp) {
        this.chat_time_stamp = chat_time_stamp;
    }

    public long getchat_time_stamp_1() {
        return chat_time_stamp_1;
    }

    public void setchat_time_stamp_1(long chat_time_stamp_1) {
        this.chat_time_stamp_1 = chat_time_stamp_1;
    }

    public long getchat_time_stamp_2() {
        return chat_time_stamp_2;
    }

    public void setchat_time_stamp_2(long chat_time_stamp_2) {
        this.chat_time_stamp_2 = chat_time_stamp_2;
    }

    public long getgroup_create_time_stamp() {
        return group_create_time_stamp;
    }

    public void setgroup_create_time_stamp(long group_create_time_stamp) {
        this.group_create_time_stamp = group_create_time_stamp;
    }

    public int getgroup_member_count() {
        return group_member_count;
    }

    public void setgroup_member_count(int group_member_count) {
        this.group_member_count = group_member_count;
    }

    public int getgroup_member_limit() {
        return group_member_limit;
    }

    public void setgroup_member_limit(int group_member_limit) {
        this.group_member_limit = group_member_limit;
    }

    public long getinfo_time_stamp() {
        return info_time_stamp;
    }

    public void setinfo_time_stamp(long info_time_stamp) {
        this.info_time_stamp = info_time_stamp;
    }

    public int getinfo_content() {
        return info_content;
    }

    public void setinfo_content(int info_content) {
        this.info_content = info_content;
    }

    public int getmemo_time() {
        return memo_time;
    }

    public void setmemo_time(int memo_time) {
        this.memo_time = memo_time;
    }

    public long getmemo_time_stamp() {
        return memo_time_stamp;
    }

    public void setmemo_time_stamp(long memo_time_stamp) {
        this.memo_time_stamp = memo_time_stamp;
    }

    public long getmemo_time_stamp_1() {
        return memo_time_stamp_1;
    }

    public void setmemo_time_stamp_1(long memo_time_stamp_1) {
        this.memo_time_stamp_1 = memo_time_stamp_1;
    }

    public long getmemo_time_stamp_2() {
        return memo_time_stamp_2;
    }

    public void setmemo_time_stamp_2(long memo_time_stamp_2) {
        this.memo_time_stamp_2 = memo_time_stamp_2;
    }

    public int getpage_size_fld() {
        return page_size_fld;
    }

    public void setpage_size_fld(int page_size_fld) {
        this.page_size_fld = page_size_fld;
    }

    private boolean read_state = false;
    private boolean if_read_state = false;
    private boolean if_chat_time_1 = false;
    private boolean if_chat_time_2 = false;
    private boolean chat_readstate = false;
    private boolean if_chat_readstate = false;
    private boolean group_state = false;
    private boolean if_group_state = false;
    private boolean group_self_state = false;
    private boolean if_group_self_state = false;
    private boolean group_open = false;
    private boolean if_group_open = false;
    private boolean group_invite = false;
    private boolean if_group_invite = false;
    private boolean if_memo_time_1 = false;
    private boolean if_memo_time_2 = false;
    private boolean black_state = false;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getpers_un() {
        return pers_un;
    }

    public void setpers_un(String pers_un) {
        this.pers_un = pers_un;
    }

    public String getcorp_tn() {
        return corp_tn;
    }

    public void setcorp_tn(String corp_tn) {
        this.corp_tn = corp_tn;
    }

    public String getorder_list() {
        return order_list;
    }

    public void setorder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getmsg_content() {
        return msg_content;
    }

    public void setmsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getnew_info_count() {
        return new_info_count;
    }

    public void setnew_info_count(String new_info_count) {
        this.new_info_count = new_info_count;
    }

    public String getchat_type() {
        return chat_type;
    }

    public void setchat_type(String chat_type) {
        this.chat_type = chat_type;
    }

    public String getno_pers_uns() {
        return no_pers_uns;
    }

    public void setno_pers_uns(String no_pers_uns) {
        this.no_pers_uns = no_pers_uns;
    }

    public String getfrnd_type() {
        return frnd_type;
    }

    public void setfrnd_type(String frnd_type) {
        this.frnd_type = frnd_type;
    }

    public String getfrnd_kind() {
        return frnd_kind;
    }

    public void setfrnd_kind(String frnd_kind) {
        this.frnd_kind = frnd_kind;
    }

    public String getfrnd_state() {
        return frnd_state;
    }

    public void setfrnd_state(String frnd_state) {
        this.frnd_state = frnd_state;
    }

    public String getfrnd_ctn() {
        return frnd_ctn;
    }

    public void setfrnd_ctn(String frnd_ctn) {
        this.frnd_ctn = frnd_ctn;
    }

    public String getfrnd_un() {
        return frnd_un;
    }

    public void setfrnd_un(String frnd_un) {
        this.frnd_un = frnd_un;
    }

    public String getfrnd_name() {
        return frnd_name;
    }

    public void setfrnd_name(String frnd_name) {
        this.frnd_name = frnd_name;
    }

    public String getfrnd_sex() {
        return frnd_sex;
    }

    public void setfrnd_sex(String frnd_sex) {
        this.frnd_sex = frnd_sex;
    }

    public String getpers_sex() {
        return pers_sex;
    }

    public void setpers_sex(String pers_sex) {
        this.pers_sex = pers_sex;
    }

    public String getpers_name() {
        return pers_name;
    }

    public void setpers_name(String pers_name) {
        this.pers_name = pers_name;
    }

    public String getcorp_name() {
        return corp_name;
    }

    public void setcorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getfrnd_dept() {
        return frnd_dept;
    }

    public void setfrnd_dept(String frnd_dept) {
        this.frnd_dept = frnd_dept;
    }

    public String getfrnd_cel() {
        return frnd_cel;
    }

    public void setfrnd_cel(String frnd_cel) {
        this.frnd_cel = frnd_cel;
    }

    public String getfrnd_email() {
        return frnd_email;
    }

    public void setfrnd_email(String frnd_email) {
        this.frnd_email = frnd_email;
    }

    public String getfrnd_qq() {
        return frnd_qq;
    }

    public void setfrnd_qq(String frnd_qq) {
        this.frnd_qq = frnd_qq;
    }

    public String getfrnd_wechat() {
        return frnd_wechat;
    }

    public void setfrnd_wechat(String frnd_wechat) {
        this.frnd_wechat = frnd_wechat;
    }

    public String getfrnd_othercontact() {
        return frnd_othercontact;
    }

    public void setfrnd_othercontact(String frnd_othercontact) {
        this.frnd_othercontact = frnd_othercontact;
    }

    public String getfrnd_degree() {
        return frnd_degree;
    }

    public void setfrnd_degree(String frnd_degree) {
        this.frnd_degree = frnd_degree;
    }

    public String getfrnd_graduation() {
        return frnd_graduation;
    }

    public void setfrnd_graduation(String frnd_graduation) {
        this.frnd_graduation = frnd_graduation;
    }

    public String getfrnd_major() {
        return frnd_major;
    }

    public void setfrnd_major(String frnd_major) {
        this.frnd_major = frnd_major;
    }

    public String getfrnd_profession() {
        return frnd_profession;
    }

    public void setfrnd_profession(String frnd_profession) {
        this.frnd_profession = frnd_profession;
    }

    public String getfrnd_job() {
        return frnd_job;
    }

    public void setfrnd_job(String frnd_job) {
        this.frnd_job = frnd_job;
    }

    public String getfrnd_nowplace() {
        return frnd_nowplace;
    }

    public void setfrnd_nowplace(String frnd_nowplace) {
        this.frnd_nowplace = frnd_nowplace;
    }

    public String getfrnd_homeplace() {
        return frnd_homeplace;
    }

    public void setfrnd_homeplace(String frnd_homeplace) {
        this.frnd_homeplace = frnd_homeplace;
    }

    public String getfrnd_birth() {
        return frnd_birth;
    }

    public void setfrnd_birth(String frnd_birth) {
        this.frnd_birth = frnd_birth;
    }

    public String getfrnd_birthtype() {
        return frnd_birthtype;
    }

    public void setfrnd_birthtype(String frnd_birthtype) {
        this.frnd_birthtype = frnd_birthtype;
    }

    public String getfrnd_faith() {
        return frnd_faith;
    }

    public void setfrnd_faith(String frnd_faith) {
        this.frnd_faith = frnd_faith;
    }

    public String getfrnd_maristatus() {
        return frnd_maristatus;
    }

    public void setfrnd_maristatus(String frnd_maristatus) {
        this.frnd_maristatus = frnd_maristatus;
    }

    public String getfrnd_tel() {
        return frnd_tel;
    }

    public void setfrnd_tel(String frnd_tel) {
        this.frnd_tel = frnd_tel;
    }

    public String getfrnd_cname() {
        return frnd_cname;
    }

    public void setfrnd_cname(String frnd_cname) {
        this.frnd_cname = frnd_cname;
    }

    public String getfrnd_cdesc() {
        return frnd_cdesc;
    }

    public void setfrnd_cdesc(String frnd_cdesc) {
        this.frnd_cdesc = frnd_cdesc;
    }

    public String getfrnd_unit() {
        return frnd_unit;
    }

    public void setfrnd_unit(String frnd_unit) {
        this.frnd_unit = frnd_unit;
    }

    public String getfrnd_pos() {
        return frnd_pos;
    }

    public void setfrnd_pos(String frnd_pos) {
        this.frnd_pos = frnd_pos;
    }

    public String getfrnd_number() {
        return frnd_number;
    }

    public void setfrnd_number(String frnd_number) {
        this.frnd_number = frnd_number;
    }

    public String getfrnd_desc() {
        return frnd_desc;
    }

    public void setfrnd_desc(String frnd_desc) {
        this.frnd_desc = frnd_desc;
    }

    public String getfrnd_memo() {
        return frnd_memo;
    }

    public void setfrnd_memo(String frnd_memo) {
        this.frnd_memo = frnd_memo;
    }

    public String getfrnd_rank() {
        return frnd_rank;
    }

    public void setfrnd_rank(String frnd_rank) {
        this.frnd_rank = frnd_rank;
    }

    public String getpers_rank() {
        return pers_rank;
    }

    public void setpers_rank(String pers_rank) {
        this.pers_rank = pers_rank;
    }

    public String getadd_time() {
        return add_time;
    }

    public void setadd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getchat_id() {
        return chat_id;
    }

    public void setchat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getchat_time() {
        return chat_time;
    }

    public void setchat_time(String chat_time) {
        this.chat_time = chat_time;
    }

    public String getchat_time_1() {
        return chat_time_1;
    }

    public void setchat_time_1(String chat_time_1) {
        this.chat_time_1 = chat_time_1;
    }

    public String getchat_time_2() {
        return chat_time_2;
    }

    public void setchat_time_2(String chat_time_2) {
        this.chat_time_2 = chat_time_2;
    }

    public String getchat_direct() {
        return chat_direct;
    }

    public void setchat_direct(String chat_direct) {
        this.chat_direct = chat_direct;
    }

    public String getchat_direct_name() {
        return chat_direct_name;
    }

    public void setchat_direct_name(String chat_direct_name) {
        this.chat_direct_name = chat_direct_name;
    }

    public String getchat_content() {
        return chat_content;
    }

    public void setchat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public String getgroup_create_ctn() {
        return group_create_ctn;
    }

    public void setgroup_create_ctn(String group_create_ctn) {
        this.group_create_ctn = group_create_ctn;
    }

    public String getgroup_create_cname() {
        return group_create_cname;
    }

    public void setgroup_create_cname(String group_create_cname) {
        this.group_create_cname = group_create_cname;
    }

    public String getgroup_create_pun() {
        return group_create_pun;
    }

    public void setgroup_create_pun(String group_create_pun) {
        this.group_create_pun = group_create_pun;
    }

    public String getgroup_create_pname() {
        return group_create_pname;
    }

    public void setgroup_create_pname(String group_create_pname) {
        this.group_create_pname = group_create_pname;
    }

    public String getgroup_code() {
        return group_code;
    }

    public void setgroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getgroup_name() {
        return group_name;
    }

    public void setgroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getgroup_kind() {
        return group_kind;
    }

    public void setgroup_kind(String group_kind) {
        this.group_kind = group_kind;
    }

    public String getgroup_memo() {
        return group_memo;
    }

    public void setgroup_memo(String group_memo) {
        this.group_memo = group_memo;
    }

    public String getgroup_create_time() {
        return group_create_time;
    }

    public void setgroup_create_time(String group_create_time) {
        this.group_create_time = group_create_time;
    }

    public String getgroup_member_pnames() {
        return group_member_pnames;
    }

    public void setgroup_member_pnames(String group_member_pnames) {
        this.group_member_pnames = group_member_pnames;
    }

    public String getgroup_member_puns() {
        return group_member_puns;
    }

    public void setgroup_member_puns(String group_member_puns) {
        this.group_member_puns = group_member_puns;
    }

    public String getgroup_member_count_str() {
        return group_member_count_str;
    }

    public void setgroup_member_count_str(String group_member_count_str) {
        this.group_member_count_str = group_member_count_str;
    }

    public String getinfo_time() {
        return info_time;
    }

    public void setinfo_time(String info_time) {
        this.info_time = info_time;
    }

    public String getmemo_content() {
        return memo_content;
    }

    public void setmemo_content(String memo_content) {
        this.memo_content = memo_content;
    }

    public String getcald_day() {
        return cald_day;
    }

    public void setcald_day(String cald_day) {
        this.cald_day = cald_day;
    }

    public String getcald_time() {
        return cald_time;
    }

    public void setcald_time(String cald_time) {
        this.cald_time = cald_time;
    }

    public String getcald_period() {
        return cald_period;
    }

    public void setcald_period(String cald_period) {
        this.cald_period = cald_period;
    }

    public String getcald_hour() {
        return cald_hour;
    }

    public void setcald_hour(String cald_hour) {
        this.cald_hour = cald_hour;
    }

    public String getcald_minute() {
        return cald_minute;
    }

    public void setcald_minute(String cald_minute) {
        this.cald_minute = cald_minute;
    }

    public String getcald_content() {
        return cald_content;
    }

    public void setcald_content(String cald_content) {
        this.cald_content = cald_content;
    }


    public boolean isread_state() {
        return read_state;
    }

    public void setread_state(boolean read_state) {
        this.read_state = read_state;
    }

    public boolean isif_read_state() {
        return if_read_state;
    }

    public void setif_read_state(boolean if_read_state) {
        this.if_read_state = if_read_state;
    }

    public boolean isif_chat_time_1() {
        return if_chat_time_1;
    }

    public void setif_chat_time_1(boolean if_chat_time_1) {
        this.if_chat_time_1 = if_chat_time_1;
    }

    public boolean isif_chat_time_2() {
        return if_chat_time_2;
    }

    public void setif_chat_time_2(boolean if_chat_time_2) {
        this.if_chat_time_2 = if_chat_time_2;
    }

    public boolean ischat_readstate() {
        return chat_readstate;
    }

    public void setchat_readstate(boolean chat_readstate) {
        this.chat_readstate = chat_readstate;
    }

    public boolean isif_chat_readstate() {
        return if_chat_readstate;
    }

    public void setif_chat_readstate(boolean if_chat_readstate) {
        this.if_chat_readstate = if_chat_readstate;
    }

    public boolean isgroup_state() {
        return group_state;
    }

    public void setgroup_state(boolean group_state) {
        this.group_state = group_state;
    }

    public boolean isif_group_state() {
        return if_group_state;
    }

    public void setif_group_state(boolean if_group_state) {
        this.if_group_state = if_group_state;
    }

    public boolean isgroup_self_state() {
        return group_self_state;
    }

    public void setgroup_self_state(boolean group_self_state) {
        this.group_self_state = group_self_state;
    }

    public boolean isif_group_self_state() {
        return if_group_self_state;
    }

    public void setif_group_self_state(boolean if_group_self_state) {
        this.if_group_self_state = if_group_self_state;
    }

    public boolean isgroup_open() {
        return group_open;
    }

    public void setgroup_open(boolean group_open) {
        this.group_open = group_open;
    }

    public boolean isif_group_open() {
        return if_group_open;
    }

    public void setif_group_open(boolean if_group_open) {
        this.if_group_open = if_group_open;
    }

    public boolean isgroup_invite() {
        return group_invite;
    }

    public void setgroup_invite(boolean group_invite) {
        this.group_invite = group_invite;
    }

    public boolean isif_group_invite() {
        return if_group_invite;
    }

    public void setif_group_invite(boolean if_group_invite) {
        this.if_group_invite = if_group_invite;
    }

    public boolean isif_memo_time_1() {
        return if_memo_time_1;
    }

    public void setif_memo_time_1(boolean if_memo_time_1) {
        this.if_memo_time_1 = if_memo_time_1;
    }

    public boolean isif_memo_time_2() {
        return if_memo_time_2;
    }

    public void setif_memo_time_2(boolean if_memo_time_2) {
        this.if_memo_time_2 = if_memo_time_2;
    }

    public boolean isblack_state() {
        return black_state;
    }

    public void setblack_state(boolean black_state) {
        this.black_state = black_state;
    }
}
